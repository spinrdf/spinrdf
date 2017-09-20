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
import org.spinrdf.model.Bind;
import org.spinrdf.model.SPINFactory;
import org.spinrdf.model.Variable;
import org.spinrdf.model.print.PrintContext;
import org.spinrdf.model.visitor.ElementVisitor;
import org.spinrdf.vocabulary.SP;


/**
 * <p>BindImpl class.</p>
 *

 * @version $Id: $Id
 */
public class BindImpl extends ElementImpl implements Bind {
    
	/**
	 * <p>Constructor for BindImpl.</p>
	 *
	 * @param node a {@link org.apache.jena.graph.Node} object.
	 * @param graph a {@link org.apache.jena.enhanced.EnhGraph} object.
	 */
	public BindImpl(Node node, EnhGraph graph) {
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
			return SPINFactory.asExpression(s.getObject());
		}
		else {
			return null;
		}
	}

	
	/**
	 * <p>getVariable.</p>
	 *
	 * @return a {@link org.spinrdf.model.Variable} object.
	 */
	public Variable getVariable() {
		Statement s = getProperty(SP.variable);
		if(s != null && s.getObject().isResource()) {
			return s.getResource().as(Variable.class);
		}
		else {
			return null;
		}
	}
	
	
	/** {@inheritDoc} */
	public void print(PrintContext context) {
		context.printKeyword("BIND");
		context.print(" (");
		RDFNode expression = getExpression();
		if(expression != null) {
			printNestedExpressionString(context, expression);
		}
		else {
			context.print("<Error: Missing expression>");
		}
		context.print(" ");
		context.printKeyword("AS");
		context.print(" ");
		Variable variable = getVariable();
		if(variable != null) {
			context.print(variable.toString());
		}
		else {
			context.print("<Error: Missing variable>");
		}
		context.print(")");
	}


	/** {@inheritDoc} */
	public void visit(ElementVisitor visitor) {
		visitor.visit(this);
	}
}
