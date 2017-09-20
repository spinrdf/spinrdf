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

import java.util.Collections;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.expr.ExprEvalException;
import org.apache.jena.sparql.expr.ExprList;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.Function;
import org.apache.jena.sparql.function.FunctionEnv;
import org.apache.jena.sparql.sse.SSE;
import org.apache.jena.sparql.util.FmtUtils;
import org.spinrdf.statistics.SPINStatistics;
import org.spinrdf.statistics.SPINStatisticsManager;


/**
 * Base implementation of Function comparable to Jena's FunctionBase.
 *

 * @version $Id: $Id
 */
public abstract class AbstractFunction implements Function {

	/** {@inheritDoc} */
	public void build(String uri, ExprList args) {
	}

	
	/** {@inheritDoc} */
	public NodeValue exec(Binding binding, ExprList args, String uri, FunctionEnv env) {
		Node[] nodes = new Node[args.size()];
		for(int i = 0; i < args.size(); i++) {
            Expr e = args.get(i);
            try {
            	if(e != null && (!e.isVariable() || (e.isVariable() && binding.get(e.asVar()) != null)) ) {
	            	NodeValue x = e.eval(binding, env);
	            	if (x != null) {
						nodes[i] = x.asNode();
					} 
            	}
            }
            catch(ExprEvalException ex) {
            	throw ex;
            }
            catch(Exception ex) {
            	throw new IllegalArgumentException("Error during function evaluation", ex);
            }
        }
		if(SPINStatisticsManager.get().isRecording() && SPINStatisticsManager.get().isRecordingNativeFunctions()) {
			StringBuffer sb = new StringBuffer();
			sb.append("SPARQL Function ");
			sb.append(SSE.format(NodeFactory.createURI(uri), env.getActiveGraph().getPrefixMapping()));
			sb.append("(");
			for(int i = 0; i < nodes.length; i++) {
				if(i > 0) {
					sb.append(", ");
				}
				if(nodes[i] == null) {
					sb.append("?arg" + (i + 1));
				}
				else {
					sb.append(SSE.format(nodes[i], env.getActiveGraph().getPrefixMapping()));
				}
			}
			sb.append(")");
			long startTime = System.currentTimeMillis();
			NodeValue result;
			try {
				result = exec(nodes, env);
				sb.append(" = ");
				sb.append(FmtUtils.stringForNode(result.asNode(), env.getActiveGraph().getPrefixMapping()));
			}
			catch(ExprEvalException ex) {
				sb.append(" : ");
				sb.append(ex.getLocalizedMessage());
				throw ex;
			}
			finally {
				long endTime = System.currentTimeMillis();
				SPINStatistics stats = new SPINStatistics(sb.toString(), 
						"(Native built-in function)", endTime - startTime, startTime, NodeFactory.createURI(uri));
				SPINStatisticsManager.get().addSilently(Collections.singleton(stats));
			}
			return result;
		}
		else {
			return exec(nodes, env);
		}
	}
	
	
	/**
	 * <p>exec.</p>
	 *
	 * @param nodes an array of {@link org.apache.jena.graph.Node} objects.
	 * @param env a {@link org.apache.jena.sparql.function.FunctionEnv} object.
	 * @return a {@link org.apache.jena.sparql.expr.NodeValue} object.
	 */
	protected abstract NodeValue exec(Node[] nodes, FunctionEnv env);
}
