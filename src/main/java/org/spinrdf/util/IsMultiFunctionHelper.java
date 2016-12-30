/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  See the NOTICE file distributed with this work for additional
 *  information regarding copyright ownership.
 */

package org.spinrdf.util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.jena.datatypes.TypeMapper;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.XSD;
import org.spinrdf.vocabulary.ARG;
import org.spinrdf.vocabulary.SPIN;
import org.spinrdf.vocabulary.SPL;

/**
 * An internal helper object for the algorithm that determines whether a property
 * is considered multi-valued, as exposed by JenaUtil.isMulti().
 */
class IsMultiFunctionHelper {
	
	private static Node integerOne = NodeFactory.createLiteral("1", TypeMapper.getInstance().getSafeTypeByName(XSD.integer.getURI()));

	
	public static boolean isMulti(Node property, Node type, Graph graph) {
		
		// FILTER NOT EXISTS { ?property a owl:FunctionalProperty }
		if(graph.contains(property, RDF.type.asNode(), OWL.FunctionalProperty.asNode())) {
			return false;
		}

		if(type != null) {
			// Walk up classes, doing restrictions and constraints at once
			Set<Node> reached = new HashSet<Node>();
			return walk(property, type, graph, reached);
		}
		else {
			return true;
		}
	}


	private static boolean hasMaxCardinality(Node restriction, Graph graph, Node predicate) {
		ExtendedIterator<Triple> it = graph.find(restriction, predicate, Node.ANY);
		if(it.hasNext()) {
			Node object = it.next().getObject();
			it.close();
			if(object.isLiteral()) {
				String lex = object.getLiteralLexicalForm();
				if("0".equals(lex) || "1".equals(lex)) {
					return true;
				}
			}
		}
		return false;
	}

	
	private static boolean walk(Node property, Node type, Graph graph, Set<Node> classes) {
		
		classes.add(type);
		
		{
			ExtendedIterator<Triple> it = graph.find(type, SPIN.constraint.asNode(), Node.ANY);
			while(it.hasNext()) {
				Node constraint = it.next().getObject();
				if(graph.contains(constraint, SPL.predicate.asNode(), property) &&
						graph.contains(constraint, RDF.type.asNode(), SPL.Argument.asNode())) {
					it.close();
					return false;
				}
				else if(graph.contains(constraint, ARG.property.asNode(), property)) {
					if(graph.contains(constraint, RDF.type.asNode(), SPL.ObjectCountPropertyConstraint.asNode()) &&
						graph.contains(constraint, ARG.maxCount.asNode(), integerOne)) {
						it.close();
						return false;
					}
					else if(graph.contains(constraint, RDF.type.asNode(), SPL.PrimaryKeyPropertyConstraint.asNode())) {
						it.close();
						return false;
					}
				}
			}
		}		

		List<Node> superClasses = new LinkedList<Node>();
		{
			ExtendedIterator<Triple> it = graph.find(type, RDFS.subClassOf.asNode(), Node.ANY);
			while(it.hasNext()) {
				Node restriction = it.next().getObject();
				if(restriction.isBlank() &&
						graph.contains(restriction, OWL.onProperty.asNode(), property)) {
					if(hasMaxCardinality(restriction, graph, OWL.maxCardinality.asNode()) ||
							hasMaxCardinality(restriction, graph, OWL.cardinality.asNode())) {
						it.close();
						return false;
					}
				}
				else if(restriction.isURI()) {
					superClasses.add(restriction);
				}
			}
		}		
		
		for(Node superClass : superClasses) {
			if(!classes.contains(superClass)) {
				if(!walk(property, superClass, graph, classes)) {
					return false;
				}
			}
		}
		
		return true;
	}
}
