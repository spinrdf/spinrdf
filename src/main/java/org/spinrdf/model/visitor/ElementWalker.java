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

package org.spinrdf.model.visitor;

import java.util.List;

import org.apache.jena.rdf.model.RDFNode;
import org.spinrdf.model.*;


/**
 * An object that can be used to recursively walk through an Element
 * and the embedded expressions.
 *

 * @version $Id: $Id
 */
public class ElementWalker implements ElementVisitor {
	
	private ElementVisitor elementVisitor;
	
	private ExpressionVisitor expressionVisitor;
	
	
	/**
	 * <p>Constructor for ElementWalker.</p>
	 *
	 * @param elementVisitor a {@link org.spinrdf.model.visitor.ElementVisitor} object.
	 * @param expressionVisitor a {@link org.spinrdf.model.visitor.ExpressionVisitor} object.
	 */
	public ElementWalker(ElementVisitor elementVisitor, ExpressionVisitor expressionVisitor) {
		this.elementVisitor = elementVisitor;
		this.expressionVisitor = expressionVisitor;
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param bind a {@link org.spinrdf.model.Bind} object.
	 */
	public void visit(Bind bind) {
		elementVisitor.visit(bind);
		visitExpression(bind.getExpression());
	}

	
	/**
	 * <p>visit.</p>
	 *
	 * @param elementList a {@link org.spinrdf.model.ElementList} object.
	 */
	public void visit(ElementList elementList) {
		elementVisitor.visit(elementList);
		visitChildren(elementList);
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param exists a {@link org.spinrdf.model.Exists} object.
	 */
	public void visit(Exists exists) {
		elementVisitor.visit(exists);
		visitChildren(exists);
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param filter a {@link org.spinrdf.model.Filter} object.
	 */
	public void visit(Filter filter) {
		elementVisitor.visit(filter);
		visitExpression(filter.getExpression());
	}


	/** {@inheritDoc} */
	@Override
	public void visit(Minus minus) {
		elementVisitor.visit(minus);
		visitChildren(minus);
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param namedGraph a {@link org.spinrdf.model.NamedGraph} object.
	 */
	public void visit(NamedGraph namedGraph) {
		elementVisitor.visit(namedGraph);
		visitChildren(namedGraph);
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param notExists a {@link org.spinrdf.model.NotExists} object.
	 */
	public void visit(NotExists notExists) {
		elementVisitor.visit(notExists);
		visitChildren(notExists);
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param optional a {@link org.spinrdf.model.Optional} object.
	 */
	public void visit(Optional optional) {
		elementVisitor.visit(optional);
		visitChildren(optional);
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param service a {@link org.spinrdf.model.Service} object.
	 */
	public void visit(Service service) {
		elementVisitor.visit(service);
		visitChildren(service);
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param subQuery a {@link org.spinrdf.model.SubQuery} object.
	 */
	public void visit(SubQuery subQuery) {
		elementVisitor.visit(subQuery);
	}


	/** {@inheritDoc} */
	public void visit(TriplePath triplePath) {
		elementVisitor.visit(triplePath);
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param triplePattern a {@link org.spinrdf.model.TriplePattern} object.
	 */
	public void visit(TriplePattern triplePattern) {
		elementVisitor.visit(triplePattern);
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param union a {@link org.spinrdf.model.Union} object.
	 */
	public void visit(Union union) {
		elementVisitor.visit(union);
		visitChildren(union);
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param values a {@link org.spinrdf.model.Values} object.
	 */
	public void visit(Values values) {
		elementVisitor.visit(values);
	}
	
	
	/**
	 * <p>visitChildren.</p>
	 *
	 * @param group a {@link org.spinrdf.model.ElementGroup} object.
	 */
	protected void visitChildren(ElementGroup group) {
		List<Element> childElements = group.getElements();
		for(Element childElement : childElements) {
			childElement.visit(this);
		}
	}
	
	
	private void visitExpression(RDFNode node) {
		if(expressionVisitor != null) {
			ExpressionWalker expressionWalker = new ExpressionWalker(expressionVisitor);
			ExpressionVisitors.visit(node, expressionWalker);
		}
	}
}
