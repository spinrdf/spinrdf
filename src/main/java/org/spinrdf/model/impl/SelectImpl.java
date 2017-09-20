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

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.jena.enhanced.EnhGraph;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.RDFList;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.spinrdf.arq.ARQ2SPIN;
import org.spinrdf.arq.ARQFactory;
import org.spinrdf.model.Aggregation;
import org.spinrdf.model.SPINFactory;
import org.spinrdf.model.Select;
import org.spinrdf.model.Variable;
import org.spinrdf.model.print.PrintContext;
import org.spinrdf.model.print.Printable;
import org.spinrdf.vocabulary.SP;


/**
 * <p>SelectImpl class.</p>
 *

 * @version $Id: $Id
 */
public class SelectImpl extends QueryImpl implements Select {
	
	/**
	 * <p>Constructor for SelectImpl.</p>
	 *
	 * @param node a {@link org.apache.jena.graph.Node} object.
	 * @param eh a {@link org.apache.jena.enhanced.EnhGraph} object.
	 */
	public SelectImpl(Node node, EnhGraph eh) {
		super(node, eh);
	}
	
	
	/**
	 * <p>getResultVariableNames.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<String> getResultVariableNames() {
		if(hasProperty(SP.resultVariables)) {
			List<String> results = new LinkedList<String>();
			for(Resource item : getResultVariables()) {
				if(item instanceof Variable) {
					results.add(((Variable)item).getName());
				}
				else {
					results.add(null);
				}
			}
			return results;
		}
		else {
			String text = ARQ2SPIN.getTextOnly(this);
			if(text != null) {
				org.apache.jena.query.Query arqQuery = ARQFactory.get().createQuery(this);
				return arqQuery.getResultVars();
			}
			else {
				return Collections.emptyList();
			}
		}
	}

	
	/**
	 * <p>getResultVariables.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<Resource> getResultVariables() {
		List<Resource> results = new LinkedList<Resource>();
		for(RDFNode node : getList(SP.resultVariables)) {
			RDFNode e = SPINFactory.asExpression(node);
			results.add((Resource)e);
		}
		return results;
	}


	/**
	 * <p>isDistinct.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isDistinct() {
		return hasProperty(SP.distinct, getModel().createTypedLiteral(true));
	}
	
	
	/**
	 * <p>isReduced.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isReduced() {
		return hasProperty(SP.reduced, getModel().createTypedLiteral(true));
	}


	/** {@inheritDoc} */
	public void printSPINRDF(PrintContext p) {
		printComment(p);
		printPrefixes(p);
		p.printIndentation(p.getIndentation());
		p.printKeyword("SELECT");
		p.print(" ");
		if(isDistinct()) {
			p.printKeyword("DISTINCT");
			p.print(" ");
		}
		if(isReduced()) {
			p.printKeyword("REDUCED");
			p.print(" ");
		}
		List<Resource> vars = getResultVariables();
		if(vars.isEmpty()) {
			p.print("*");
		}
		else {
			for(Iterator<Resource> vit = vars.iterator(); vit.hasNext(); ) {
				Resource var = vit.next();
				if(var instanceof Variable) {
					if(var.hasProperty(SP.expression)) {
						printProjectExpression(p, (Variable) var);
					}
					else {
						((Variable)var).print(p);
					}
				}
				else if(var instanceof Aggregation) {
					((Printable)var).print(p);
				}
				else {
					p.print("(");
					((Printable)var).print(p);
					p.print(")");
				}
				if(vit.hasNext()) {
					p.print(" ");
				}
			}
		}
		printStringFrom(p);
		p.println();
		printWhere(p);
		printGroupBy(p);
		printHaving(p);
		printSolutionModifiers(p);
		printValues(p);
	}
	
	
	private void printGroupBy(PrintContext p) {
		Statement groupByS = getProperty(SP.groupBy);
		if(groupByS != null) {
			RDFList list = groupByS.getObject().as(RDFList.class);
			ExtendedIterator<RDFNode> it = list.iterator();
			if(it.hasNext()) {
				p.println();
				p.printIndentation(p.getIndentation());
				p.printKeyword("GROUP BY");
				while(it.hasNext()) {
					p.print(" ");
					RDFNode node = it.next();
					printNestedExpressionString(p, node);
				}
			}
		}
	}
	
	
	private void printHaving(PrintContext p) {
		Statement havingS = getProperty(SP.having);
		if(havingS != null) {
			RDFList list = havingS.getObject().as(RDFList.class);
			ExtendedIterator<RDFNode> it = list.iterator();
			if(it.hasNext()) {
				p.println();
				p.printIndentation(p.getIndentation());
				p.printKeyword("HAVING");
				while(it.hasNext()) {
					p.print(" ");
					RDFNode node = it.next();
					printNestedExpressionString(p, node);
				}
			}
		}
	}
	
	
	private void printProjectExpression(PrintContext p, Variable var) {
		p.print("((");
		RDFNode expr = var.getProperty(SP.expression).getObject();
		Printable expression = (Printable) SPINFactory.asExpression(expr);
		expression.print(p);
		p.print(") ");
		p.printKeyword("AS");
		p.print(" ");
		p.print(var.toString());
		p.print(")");
	}
}
