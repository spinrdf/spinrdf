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

import org.apache.jena.graph.Graph;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.compose.MultiUnion;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolutionMap;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.core.DatasetImpl;
import org.apache.jena.sparql.expr.ExprEvalException;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.Function;
import org.apache.jena.sparql.function.FunctionEnv;
import org.apache.jena.sparql.function.FunctionFactory;
import org.apache.jena.sparql.sse.SSE;
import org.apache.jena.sparql.util.FmtUtils;
import org.apache.jena.vocabulary.RDF;
import org.spinrdf.arq.ARQFactory;
import org.spinrdf.arq.AbstractFunction;
import org.spinrdf.arq.DatasetWithDifferentDefaultModel;
import org.spinrdf.model.Ask;
import org.spinrdf.model.SPINFactory;
import org.spinrdf.model.Select;
import org.spinrdf.statistics.SPINStatistics;
import org.spinrdf.statistics.SPINStatisticsManager;
import org.spinrdf.util.JenaDatatypes;
import org.spinrdf.util.JenaUtil;
import org.spinrdf.util.SPINExpressions;
import org.spinrdf.vocabulary.SP;
import org.spinrdf.vocabulary.SPIN;

/**
 * The SPARQL function spin:eval.
 *
 * The first argument is a SPIN expression, e.g. a function call or variable.
 * All other arguments must come in pairs, alternating between an argument property
 * and its value, e.g.
 *
 *  	spin:eval(ex:myInstance, sp:arg3, "value")
 *
 * The expression will be evaluated with all bindings from the property-value pairs above.
 */
public class EvalFunction extends AbstractFunction implements FunctionFactory {


    private void addStatistics(Node[] nodes, FunctionEnv env, long startTime, String expr, RDFNode result) {
        long endTime = System.currentTimeMillis();
        StringBuffer sb = new StringBuffer();
        sb.append("spin:eval(");
        sb.append(expr);
        for(int i = 1; i < nodes.length; i++) {
            sb.append(", ");
            if(nodes[i] == null) {
                sb.append("?arg" + (i + 1));
            }
            else {
                sb.append(FmtUtils.stringForNode(nodes[i], env.getActiveGraph().getPrefixMapping()));
            }
        }
        sb.append(")");
        if(result != null) {
            sb.append(" = ");
            sb.append(FmtUtils.stringForNode(result.asNode(), env.getActiveGraph().getPrefixMapping()));
        }
        SPINStatistics stats = new SPINStatistics(sb.toString(),
                "(spin:eval)", endTime - startTime, startTime, SPIN.eval.asNode());
        SPINStatisticsManager.get().addSilently(Collections.singleton(stats));
    }


    @Override
    public Function create(String uri) {
        return this;
    }


    @Override
    public NodeValue exec(Node[] nodes, FunctionEnv env) {

        if(nodes.length == 0) {
            throw new ExprEvalException("Missing arguments");
        }

        Model baseModel = ModelFactory.createModelForGraph(env.getActiveGraph());
        Node exprNode = nodes[0];
        if(exprNode == null) {
            throw new ExprEvalException("No expression specified");
        }
        else if(exprNode.isLiteral()) {
            return NodeValue.makeNode(exprNode);
        }
        else {
            Model model = baseModel;
            if(!model.contains(SPIN._arg1, RDF.type, SP.Variable)) {
                MultiUnion multiUnion = JenaUtil.createMultiUnion(new Graph[] {
                        env.getActiveGraph(),
                        SPIN.getModel().getGraph()
                });
                model = ModelFactory.createModelForGraph(multiUnion);
            }
            Resource exprRDFNode = (Resource) model.asRDFNode(exprNode);
            QuerySolutionMap bindings = getBindings(nodes, model);
            org.spinrdf.model.Query spinQuery = SPINFactory.asQuery(exprRDFNode);
            Dataset newDataset = new DatasetWithDifferentDefaultModel(model, DatasetImpl.wrap(env.getDataset()));
            long startTime = System.currentTimeMillis();
            if(spinQuery instanceof Select || spinQuery instanceof Ask) {
                Query query = ARQFactory.get().createQuery(spinQuery);
                QueryExecution qexec = ARQFactory.get().createQueryExecution(query, newDataset, bindings).build();
                if(query.isAskType()) {
                    boolean result = qexec.execAsk();
                    if(SPINStatisticsManager.get().isRecording() && SPINStatisticsManager.get().isRecordingSPINFunctions()) {
                        addStatistics(nodes, env, startTime, "ASK...", result ? JenaDatatypes.TRUE : JenaDatatypes.FALSE);
                    }
                    return NodeValue.makeBoolean(result);
                }
                else {
                    ResultSet rs = qexec.execSelect();
                    try {
                        String var = rs.getResultVars().get(0);
                        if(rs.hasNext()) {
                            RDFNode result = rs.next().get(var);
                            if(SPINStatisticsManager.get().isRecording() && SPINStatisticsManager.get().isRecordingSPINFunctions()) {
                                addStatistics(nodes, env, startTime, "SELECT...", result);
                            }
                            if(result != null) {
                                return NodeValue.makeNode(result.asNode());
                            }
                        }
                    }
                    finally {
                        qexec.close();
                    }
                }
            }
            else {
                RDFNode expr = SPINFactory.asExpression(exprRDFNode);
                RDFNode result = SPINExpressions.evaluate((Resource) expr, newDataset, bindings);
                if(SPINStatisticsManager.get().isRecording() && SPINStatisticsManager.get().isRecordingSPINFunctions()) {
                    addStatistics(nodes, env, startTime, SPINExpressions.getExpressionString(expr), result);
                }
                if(result != null) {
                    return NodeValue.makeNode(result.asNode());
                }
            }
            throw new ExprEvalException("Expression has no result");
        }
    }


    private QuerySolutionMap getBindings(Node[] nodes, Model model) {
        QuerySolutionMap bindings = new QuerySolutionMap();
        for(int i = 1; i < nodes.length - 1; i += 2) {
            Node property = nodes[i];
            Node value = nodes[i + 1];
            if(value != null) {
                bindings.add(property.getLocalName(), model.asRDFNode(value));
            }
        }
        return bindings;
    }
}
