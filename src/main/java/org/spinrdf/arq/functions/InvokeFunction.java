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

import org.apache.jena.graph.Node;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.sparql.engine.binding.BindingRoot;
import org.apache.jena.sparql.expr.ExprEvalException;
import org.apache.jena.sparql.expr.ExprList;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.Function;
import org.apache.jena.sparql.function.FunctionEnv;
import org.apache.jena.sparql.function.FunctionFactory;
import org.apache.jena.sparql.function.FunctionRegistry;
import org.apache.jena.sparql.util.FmtUtils;
import org.spinrdf.arq.ARQFactory;
import org.spinrdf.arq.AbstractFunction;
import org.spinrdf.vocabulary.SP;
import org.spinrdf.vocabulary.SPIN;
import org.spinrdf.vocabulary.SPL;

/**
 * The function sh:invoke (and spif:invoke).
 */
public class InvokeFunction	extends AbstractFunction {

    @Override
    protected NodeValue exec(Node[] nodes, FunctionEnv env) {
        if(nodes.length == 0) {
            throw new ExprEvalException("Missing function URI argument");
        }
        Node commandNode = nodes[0];
        if(!commandNode.isURI()) {
            throw new ExprEvalException("First argument must be the URI of a function");
        }

        String uri = commandNode.getURI();

        // Special handling of SPARQL system functions such as sp:gt
        Resource functionResource = SPL.getModel().getResource(uri);
        if(SP.NS.equals(functionResource.getNameSpace())) {
            Statement symbolS = functionResource.getProperty(SPIN.symbol);
            if(symbolS != null) {
                final String varName = "result";
                StringBuffer sb = new StringBuffer();
                sb.append("SELECT ?" + varName + " \n");
                sb.append("WHERE {\n");
                sb.append("    BIND (");
                sb.append(FmtUtils.stringForNode(nodes[1], env.getActiveGraph().getPrefixMapping()));
                sb.append(" ");
                sb.append(symbolS.getString());
                sb.append(" ");
                sb.append(FmtUtils.stringForNode(nodes[2], env.getActiveGraph().getPrefixMapping()));
                sb.append(" AS ?" + varName + ") . \n");
                sb.append("}");

                Model model = ModelFactory.createModelForGraph(env.getActiveGraph());
                Query arq = ARQFactory.get().createQuery(model, sb.toString());
                QueryExecution qexec = ARQFactory.get().createQueryExecution(arq, model).build();
                ResultSet rs = qexec.execSelect();
                try {
                    if(rs.hasNext()) {
                        RDFNode result = rs.next().get(varName);
                        if(result != null) {
                            return NodeValue.makeNode(result.asNode());
                        }
                    }
                    throw new ExprEvalException("Failed to evaluate function - empty result set");
                }
                finally {
                    qexec.close();
                }
            }
        }

        FunctionFactory ff = FunctionRegistry.get().get(uri);
        if(ff == null) {
            throw new ExprEvalException("Unknown function " + uri);
        }

        Function function = ff.create(uri);
        ExprList exprList = new ExprList();

        for(int i = 1; i < nodes.length; i++) {
            Node node = nodes[i];
            if(node != null) {
                exprList.add(NodeValue.makeNode(node));
            }
            else {
                exprList.add(null);
            }
        }

        NodeValue result = function.exec(BindingRoot.create(), exprList, uri, env);
        return result;
    }
}
