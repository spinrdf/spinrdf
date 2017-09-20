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

import org.apache.jena.rdf.model.RDFNode;
import org.spinrdf.model.Aggregation;
import org.spinrdf.model.FunctionCall;
import org.spinrdf.model.Variable;


/**
 * An "empty" base implementation of ExpressionVisitor.
 *

 * @version $Id: $Id
 */
public class AbstractExpressionVisitor implements ExpressionVisitor {

	/**
	 * <p>visit.</p>
	 *
	 * @param aggregation a {@link org.spinrdf.model.Aggregation} object.
	 */
	public void visit(Aggregation aggregation) {
	}

	
	/**
	 * <p>visit.</p>
	 *
	 * @param functionCall a {@link org.spinrdf.model.FunctionCall} object.
	 */
	public void visit(FunctionCall functionCall) {
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param node a {@link org.apache.jena.rdf.model.RDFNode} object.
	 */
	public void visit(RDFNode node) {
	}


	/** {@inheritDoc} */
	public void visit(Variable variable) {
	}
}
