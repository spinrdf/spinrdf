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

package org.spinrdf.model.impl;

import org.apache.jena.enhanced.EnhGraph;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.Resource;
import org.spinrdf.model.NamedGraph;
import org.spinrdf.model.SPINFactory;
import org.spinrdf.model.Variable;
import org.spinrdf.model.print.PrintContext;
import org.spinrdf.model.visitor.ElementVisitor;
import org.spinrdf.vocabulary.SP;


public class NamedGraphImpl extends ElementImpl implements NamedGraph {
	
	public NamedGraphImpl(Node node, EnhGraph graph) {
		super(node, graph);
	}
	
	
	public Resource getNameNode() {
		Resource r = getResource(SP.graphNameNode);
		if(r != null) {
			Variable variable = SPINFactory.asVariable(r);
			if(variable != null) {
				return variable;
			}
			else {
				return r;
			}
		}
		else {
			return null;
		}
	}


	public void print(PrintContext p) {
		p.printKeyword("GRAPH");
		p.print(" ");
		printVarOrResource(p, getNameNode());
		printNestedElementList(p);
	}


	public void visit(ElementVisitor visitor) {
		visitor.visit(this);
	}
}
