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

import org.apache.jena.atlas.lib.StrUtils;
import org.apache.jena.enhanced.EnhGraph;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.vocabulary.RDF;
import org.spinrdf.arq.Aggregations;
import org.spinrdf.model.Aggregation;
import org.spinrdf.model.SPINFactory;
import org.spinrdf.model.Variable;
import org.spinrdf.model.print.PrintContext;
import org.spinrdf.model.print.Printable;
import org.spinrdf.vocabulary.SP;


public class AggregationImpl extends AbstractSPINResourceImpl implements Aggregation {

	public AggregationImpl(Node node, EnhGraph graph) {
		super(node, graph);
	}

	
	public Variable getAs() {
		Resource as = getResource(SP.as);
		if(as != null) {
			return SPINFactory.asVariable(as);
		}
		else {
			return null;
		}
	}

	
	public Resource getExpression() {
		return getResource(SP.expression);
	}

	
	public boolean isDistinct() {
		return hasProperty(SP.distinct, getModel().createTypedLiteral(true));
	}

	
	public void print(PrintContext p) {
		
		Variable asVar = getAs(); 
		if(asVar != null) {
			p.print("(");
		}

		Resource aggType = getResource(RDF.type);
		String aggName = Aggregations.getName(aggType);
		p.printKeyword(aggName);
		p.print("(");
		
		if(isDistinct()) {
			p.print("DISTINCT ");
		}
		
		Statement exprS = getProperty(SP.expression);
		if(exprS != null && exprS.getObject().isResource()) {
			Resource r = exprS.getResource();
			RDFNode expr = SPINFactory.asExpression(r);
			if(expr instanceof Printable) {
				((Printable)expr).print(p);
			}
			else {
				p.printURIResource(r);
			}
		}
		else {
			p.print("*");
		}
		String separator = getString(SP.separator);
		if(separator != null) {
			p.print("; ");
			p.printKeyword("SEPARATOR");
			p.print("='" + StrUtils.escapeString(separator) + "'");
		}
		if(asVar != null) {
			p.print(") ");
			p.printKeyword("AS");
			p.print(" ");
			p.print(asVar.toString());
		}
		p.print(")");
	}
}
