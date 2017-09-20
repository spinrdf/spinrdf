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

package org.spinrdf.arq;

import org.apache.jena.graph.Node;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionEnv;


/**
 * An abstract superclass for Functions with 2 arguments.
 *

 * @version $Id: $Id
 */
public abstract class AbstractFunction2 extends AbstractFunction {

	/** {@inheritDoc} */
	@Override
	protected NodeValue exec(Node[] nodes, FunctionEnv env) {
		Node arg1 = nodes.length > 0 ? nodes[0] : null;
		Node arg2 = nodes.length > 1 ? nodes[1] : null;
		return exec(arg1, arg2, env);
	}
	
	
	/**
	 * <p>exec.</p>
	 *
	 * @param arg1 a {@link org.apache.jena.graph.Node} object.
	 * @param arg2 a {@link org.apache.jena.graph.Node} object.
	 * @param env a {@link org.apache.jena.sparql.function.FunctionEnv} object.
	 * @return a {@link org.apache.jena.sparql.expr.NodeValue} object.
	 */
	protected abstract NodeValue exec(Node arg1, Node arg2, FunctionEnv env);
}
