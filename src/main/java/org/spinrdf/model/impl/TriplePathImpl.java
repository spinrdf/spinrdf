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
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.shared.PrefixMapping;
import org.apache.jena.sparql.core.Prologue;
import org.apache.jena.sparql.path.P_Alt;
import org.apache.jena.sparql.path.P_Inverse;
import org.apache.jena.sparql.path.P_Link;
import org.apache.jena.sparql.path.P_Mod;
import org.apache.jena.sparql.path.P_NegPropSet;
import org.apache.jena.sparql.path.P_OneOrMore1;
import org.apache.jena.sparql.path.P_ReverseLink;
import org.apache.jena.sparql.path.P_Seq;
import org.apache.jena.sparql.path.P_ZeroOrMore1;
import org.apache.jena.sparql.path.P_ZeroOrOne;
import org.apache.jena.sparql.path.Path;
import org.apache.jena.sparql.path.PathWriter;
import org.apache.jena.vocabulary.RDF;
import org.spinrdf.model.TriplePath;
import org.spinrdf.model.print.PrintContext;
import org.spinrdf.model.visitor.ElementVisitor;
import org.spinrdf.util.JenaUtil;
import org.spinrdf.vocabulary.SP;


public class TriplePathImpl extends TupleImpl implements TriplePath {

	public TriplePathImpl(Node node, EnhGraph graph) {
		super(node, graph);
	}

	
	public void visit(ElementVisitor visitor) {
		visitor.visit(this);
	}

	
	public void print(PrintContext p) {
		print(getSubject(), p);
		p.print(" ");
		
		Statement pathS = getProperty(SP.path);
		if(pathS == null || pathS.getObject().isLiteral()) {
			p.print("<Missing path>");
		}
		else {
			Resource path = pathS.getResource();
			printPath(path, p);
		}
		
		p.print(" ");
		print(getObject(), p);
	}
	
	
	private void printPath(Resource path, PrintContext p) {
		Path arqPath = createPath(path);
		if(p.getUsePrefixes()) {
			PrefixMapping prefixMapping = path.getModel().getGraph().getPrefixMapping();
			String str = PathWriter.asString(arqPath, new Prologue(prefixMapping));
			p.print(str);
		}
		else {
			String str = PathWriter.asString(arqPath);
			p.print(str);
		}
	}
	
	
	private Path createPath(Resource path) {
		if(path.isURIResource()) {
			return new P_Link(path.asNode());
		}
		else {
			Statement typeS = path.getProperty(RDF.type);
			if(typeS != null && typeS.getObject().isURIResource()) {
				Resource type = typeS.getResource();
				if(SP.AltPath.equals(type)) {
					Path leftPath = createPath(path, SP.path1);
					Path rightPath = createPath(path, SP.path2);
					return new P_Alt(leftPath, rightPath);
				}
				else if(SP.ModPath.equals(type)) {
					Path subPath = createPath(path, SP.subPath);
					long min = path.getProperty(SP.modMin).getLong();
					long max = path.getProperty(SP.modMax).getLong();
					if(max < 0) {
						if(min == 1) {
							return new P_OneOrMore1(subPath);  // TODO: is this correct?
						}
						else if(max == -1) {
							return new P_ZeroOrOne(subPath);
						}
						else { // -2
							return new P_ZeroOrMore1(subPath);  // TODO: is this correct?
						}
					}
					else {
						return new P_Mod(subPath, min, max);
					}
				}
				else if(SP.ReversePath.equals(type)) {
					Path subPath = createPath(path, SP.subPath);
					return new P_Inverse(subPath);
				}
				else if(SP.SeqPath.equals(type)) {
					Path leftPath = createPath(path, SP.path1);
					Path rightPath = createPath(path, SP.path2);
					return new P_Seq(leftPath, rightPath);
				}
				else if(SP.ReverseLinkPath.equals(type)) {
					Node node = JenaUtil.getProperty(path, SP.node).asNode();
					return new P_ReverseLink(node);
				} else if(SP.NegatedPath.equals(type)) {
					Node node = JenaUtil.getProperty(path, SP.node).asNode();
					P_NegPropSet p = new P_NegPropSet();
					p.add(new P_Link(node));
					return p;
				}

			}
			return null;
		}
	}

	
	private Path createPath(Resource subject, Property predicate) {
		Statement s = subject.getProperty(predicate);
		if(s != null && s.getObject().isResource()) {
			return createPath(s.getResource());
		}
		else {
			return null;
		}
	}	
}
