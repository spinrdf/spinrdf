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
 * Utility functions for ExpressionVisitors.
 */
public class ExpressionVisitors {

	public static void visit(RDFNode node, ExpressionVisitor visitor) {
		if(node instanceof Variable) {
			visitor.visit((Variable)node);
		}
		else if(node instanceof FunctionCall) {
			visitor.visit((FunctionCall)node);
		}
		else if(node instanceof Aggregation) {
			visitor.visit((Aggregation)node);
		}
		else if(node != null) {
			visitor.visit(node);
		}
	}
}
