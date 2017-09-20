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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.jena.graph.Graph;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.QuerySolutionMap;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.core.DatasetImpl;
import org.apache.jena.sparql.expr.ExprEvalException;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionEnv;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.spinrdf.arq.ARQFactory;
import org.spinrdf.arq.AbstractFunction;
import org.spinrdf.arq.DatasetWithDifferentDefaultModel;

/**
 * The base implementation of sh:walkObjects and sh:walkSubjects.
 *

 * @version $Id: $Id
 */
public abstract class AbstractWalkFunction extends AbstractFunction {

	/** {@inheritDoc} */
	@Override
	protected NodeValue exec(Node[] nodes, FunctionEnv env) {
		Node startNode = nodes[0];
		Node predicate = nodes[1];
		Node function = nodes[2];
		Model model = ModelFactory.createModelForGraph(env.getActiveGraph());
		QuerySolutionMap initialBinding = new QuerySolutionMap();
		StringBuffer expression = new StringBuffer("<" + function + ">(?arg1");
		for(int i = 3; i < nodes.length; i++) {
			expression.append(", ");
			expression.append("?");
			String varName = "arg" + (i - 1);
			expression.append(varName);
			if(nodes[i] != null) {
				initialBinding.add(varName, model.asRDFNode(nodes[i]));
			}
		}
		expression.append(")");
		Query query = ARQFactory.get().createExpressionQuery(expression.toString());
		Node result = walkTree(model, 
				DatasetImpl.wrap(env.getDataset()), 
				startNode, predicate, query, initialBinding, new HashSet<Node>());
		if(result != null) {
			return NodeValue.makeNode(result);
		}
		else {
			throw new ExprEvalException("No result");
		}
	}
	
	
	private Node walkTree(Model model, Dataset oldDataset, Node node, Node predicate, 
			Query query, QuerySolution initialBinding, Set<Node> reached) {
		QuerySolutionMap localBinding = new QuerySolutionMap();
		localBinding.addAll(initialBinding);
		localBinding.add("arg1", model.asRDFNode(node));
		Dataset dataset = new DatasetWithDifferentDefaultModel(model, oldDataset);
		QueryExecution qexec = ARQFactory.get().createQueryExecution(query, dataset, localBinding);
		ResultSet rs = qexec.execSelect();
		try {
			if(rs.hasNext()) {
				List<String> resultVars = rs.getResultVars();
				String varName = resultVars.get(0);
				RDFNode resultNode = rs.next().get(varName);
				if(resultNode != null) {
					return resultNode.asNode();
				}
			}
		} 
		finally {
			qexec.close();
		}
		
		// Recurse into parents
		ExtendedIterator<Triple> it = createIterator(model.getGraph(), node, predicate);
		try {
			while(it.hasNext()) {
				Node next = getNext(it.next());
				if((next.isBlank() || next.isURI()) && !reached.contains(next)) {
					reached.add(next);
					Node nextResult = walkTree(model, oldDataset, next, predicate, query, initialBinding, reached);
					if(nextResult != null) {
						return nextResult;
					}
				}
			}
		}
		finally {
			it.close();
		}
		
		return null;
	}


	/**
	 * <p>createIterator.</p>
	 *
	 * @param graph a {@link org.apache.jena.graph.Graph} object.
	 * @param node a {@link org.apache.jena.graph.Node} object.
	 * @param predicate a {@link org.apache.jena.graph.Node} object.
	 * @return a {@link org.apache.jena.util.iterator.ExtendedIterator} object.
	 */
	protected abstract ExtendedIterator<Triple> createIterator(Graph graph, Node node, Node predicate);
	
	
	/**
	 * <p>getNext.</p>
	 *
	 * @param triple a {@link org.apache.jena.graph.Triple} object.
	 * @return a {@link org.apache.jena.graph.Node} object.
	 */
	protected abstract Node getNext(Triple triple);
}
