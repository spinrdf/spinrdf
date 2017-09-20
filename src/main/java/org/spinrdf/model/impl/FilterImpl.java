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
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Statement;
import org.spinrdf.model.Filter;
import org.spinrdf.model.SPINFactory;
import org.spinrdf.model.print.PrintContext;
import org.spinrdf.model.visitor.ElementVisitor;
import org.spinrdf.vocabulary.SP;


/**
 * <p>FilterImpl class.</p>
 *

 * @version $Id: $Id
 */
public class FilterImpl extends ElementImpl implements Filter {
	
	/**
	 * <p>Constructor for FilterImpl.</p>
	 *
	 * @param node a {@link org.apache.jena.graph.Node} object.
	 * @param graph a {@link org.apache.jena.enhanced.EnhGraph} object.
	 */
	public FilterImpl(Node node, EnhGraph graph) {
		super(node, graph);
	}

	
	/**
	 * <p>getExpression.</p>
	 *
	 * @return a {@link org.apache.jena.rdf.model.RDFNode} object.
	 */
	public RDFNode getExpression() {
		Statement s = getProperty(SP.expression);
		if(s != null) {
			RDFNode object = s.getObject();
			return SPINFactory.asExpression(object);
		}
		else {
			return null;
		}
	}
	
	
	/** {@inheritDoc} */
	public void print(PrintContext context) {
		context.printKeyword("FILTER");
		context.print(" ");
		RDFNode expression = getExpression();
		if(expression == null) {
			context.print("<Error: Missing expression>");
		}
		else {
			printNestedExpressionString(context, expression, true);
		}
	}


	/** {@inheritDoc} */
	public void visit(ElementVisitor visitor) {
		visitor.visit(this);
	}
}
