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

package org.spinrdf.arq.functions;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.jena.graph.Node;
import org.apache.jena.query.QuerySolutionMap;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.expr.ExprEvalException;
import org.apache.jena.sparql.pfunction.PropFuncArg;
import org.spinrdf.model.TemplateCall;

/**
 * <p>SPINFunctionUtil class.</p>
 *

 * @version $Id: $Id
 */
public class SPINFunctionUtil {

	static void addBindingsFromTemplateCall(QuerySolutionMap initialBinding, TemplateCall templateCall) {
		Map<String,RDFNode> tbs = templateCall.getArgumentsMapByVarNames();
		for(String varName : tbs.keySet()) {
			if(!initialBinding.contains(varName)) {
				initialBinding.add(varName, tbs.get(varName));
			}
		}
	}

	
	static QuerySolutionMap getInitialBinding(Node[] nodes, Model model) {
		QuerySolutionMap map = new QuerySolutionMap();
		for(int i = 1; i < nodes.length - 1; i++) {
			Node varNameNode = nodes[i++];
			Node valueNode = nodes[i];
			if(valueNode.isConcrete()) {
				String varName = varNameNode.getLiteralLexicalForm();
				map.add(varName, model.asRDFNode(valueNode));
			}
		}
		return map;
	}

	
	/**
	 * <p>getNodes.</p>
	 *
	 * @param arg a {@link org.apache.jena.sparql.pfunction.PropFuncArg} object.
	 * @return a {@link java.util.List} object.
	 */
	public static List<Node> getNodes(PropFuncArg arg) {
		if(arg.isNode()) {
			return Collections.singletonList(arg.getArg());
		}
		else {
			return arg.getArgList();
		}
	}

	
	static Resource getQueryOrTemplateCall(PropFuncArg subject, Model model) {
		Node node;
		if(subject.isNode()) {
			node = subject.getArg();
		}
		else {
			node = subject.getArg(0);
		}
		if(node == null || node.isLiteral() || node.isVariable()) {
			throw new ExprEvalException("First argument must be a sp:Select");
		}
		return model.asRDFNode(node).asResource();
	}
}
