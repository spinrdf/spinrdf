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

import java.util.LinkedList;
import java.util.List;

import org.apache.jena.enhanced.EnhGraph;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDF;
import org.spinrdf.arq.ARQ2SPIN;
import org.spinrdf.model.*;
import org.spinrdf.model.print.PrintContext;
import org.spinrdf.model.print.Printable;
import org.spinrdf.util.JenaUtil;
import org.spinrdf.vocabulary.SP;


/**
 * <p>Abstract QueryImpl class.</p>
 *

 * @version $Id: $Id
 */
public abstract class QueryImpl extends AbstractSPINResourceImpl implements SolutionModifierQuery {
	
	
	/**
	 * <p>Constructor for QueryImpl.</p>
	 *
	 * @param node a {@link org.apache.jena.graph.Node} object.
	 * @param graph a {@link org.apache.jena.enhanced.EnhGraph} object.
	 */
	public QueryImpl(Node node, EnhGraph graph) {
		super(node, graph);
	}


	/**
	 * <p>getFrom.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<String> getFrom() {
		return getStringList(SP.from);
	}


	/**
	 * <p>getFromNamed.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<String> getFromNamed() {
		return getStringList(SP.fromNamed);
	}
	
	
	/**
	 * <p>getLimit.</p>
	 *
	 * @return a {@link java.lang.Long} object.
	 */
	public Long getLimit() {
		return getLong(SP.limit);
	}


	/**
	 * <p>getOffset.</p>
	 *
	 * @return a {@link java.lang.Long} object.
	 */
	public Long getOffset() {
		return getLong(SP.offset);
	}


	private List<String> getStringList(Property predicate) {
		List<String> results = new LinkedList<String>();
		StmtIterator it = listProperties(predicate);
		while(it.hasNext()) {
			RDFNode node = it.nextStatement().getObject();
			if(node.isLiteral()) {
				results.add(((Literal)node).getLexicalForm());
			}
			else if(node.isURIResource()) {
				results.add(((Resource)node).getURI());
			}
		}
		return results;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public Values getValues() {
		Resource values = JenaUtil.getResourceProperty(this, SP.values);
		if(values != null) {
			return values.as(Values.class);
		}
		else {
			return null;
		}
	}


	/**
	 * <p>getWhere.</p>
	 *
	 * @return a {@link org.spinrdf.model.ElementList} object.
	 */
	public ElementList getWhere() {
		Statement whereS = getProperty(SP.where);
		if(whereS != null) {
			Element element = SPINFactory.asElement(whereS.getResource());
			return (ElementList) element;
		}
		else {
			return null;
		}
	}


	/**
	 * <p>getWhereElements.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<Element> getWhereElements() {
		return getElements(SP.where);
	}


	/** {@inheritDoc} */
	@Override
	public void print(PrintContext p) {
		String text = ARQ2SPIN.getTextOnly(this);
		if(text != null) {
			if(p.hasInitialBindings()) {
				throw new IllegalArgumentException("Queries that only have an sp:text cannot be converted to a query string if initial bindings are present.");
			}
			else {
				p.print(text);
			}
		}
		else {
			printSPINRDF(p);
		}
	}
	
	
	/**
	 * <p>printSPINRDF.</p>
	 *
	 * @param p a {@link org.spinrdf.model.print.PrintContext} object.
	 */
	protected abstract void printSPINRDF(PrintContext p);


	/**
	 * <p>printStringFrom.</p>
	 *
	 * @param context a {@link org.spinrdf.model.print.PrintContext} object.
	 */
	protected void printStringFrom(PrintContext context) {
		for(String from : getFrom()) {
			context.println();
			context.printKeyword("FROM");
			context.print(" <");
			context.print(from);
			context.print(">");
		}
		for(String fromNamed : getFromNamed()) {
			context.println();
			context.printKeyword("FROM NAMED");
			context.print(" <");
			context.print(fromNamed);
			context.print(">");
		}
	}
	
	
	/**
	 * <p>printSolutionModifiers.</p>
	 *
	 * @param context a {@link org.spinrdf.model.print.PrintContext} object.
	 */
	protected void printSolutionModifiers(PrintContext context) {
		List<RDFNode> orderBy = getList(SP.orderBy);
		if(!orderBy.isEmpty()) {
			context.println();
			context.printIndentation(context.getIndentation());
			context.printKeyword("ORDER BY");
			for(RDFNode node : orderBy) {
				if(node.isResource()) {
					Resource resource = (Resource) node;
					if(resource.hasProperty(RDF.type, SP.Asc)) {
						context.print(" ");
						context.printKeyword("ASC");
						context.print(" ");
						RDFNode expression = resource.getProperty(SP.expression).getObject();
						printOrderByExpression(context, expression);
					}
					else if(resource.hasProperty(RDF.type, SP.Desc)) {
						context.print(" ");
						context.printKeyword("DESC");
						context.print(" ");
						RDFNode expression = resource.getProperty(SP.expression).getObject();
						printOrderByExpression(context, expression);
					}
					else {
						context.print(" ");
						printOrderByExpression(context, node);
					}
				}
			}
		}
		Long limit = getLimit();
		if(limit != null) {
			context.println();
			context.printIndentation(context.getIndentation());
			context.printKeyword("LIMIT");
			context.print(" " + limit);
		}
		Long offset = getOffset();
		if(offset != null) {
			context.println();
			context.printIndentation(context.getIndentation());
			context.print("OFFSET");
			context.print(" " + offset);
		}
	}


	private void printOrderByExpression(PrintContext sb, RDFNode node) {
		
		if(node instanceof Resource) {
			Resource resource = (Resource) node;
			Printable printable = SPINFactory.asAggregation(resource);
			if(printable == null) {
				printable = SPINFactory.asFunctionCall(resource);
			}
			if(printable != null) {
				sb.print("(");
				PrintContext pc = sb.clone();
				pc.setNested(true);
				printable.print(pc);
				sb.print(")");
				return;
			}
		}
		
		printNestedExpressionString(sb, node, true);
	}


	/**
	 * <p>printValues.</p>
	 *
	 * @param p a {@link org.spinrdf.model.print.PrintContext} object.
	 */
	protected void printValues(PrintContext p) {
		Values values = getValues();
		if(values != null) {
			p.println();
			values.print(p);
		}
	}


	/**
	 * <p>printWhere.</p>
	 *
	 * @param p a {@link org.spinrdf.model.print.PrintContext} object.
	 */
	protected void printWhere(PrintContext p) {
		p.printIndentation(p.getIndentation());
		p.printKeyword("WHERE");
		printNestedElementList(p, SP.where);
	}
}
