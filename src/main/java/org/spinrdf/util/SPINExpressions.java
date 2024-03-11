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

package org.spinrdf.util;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryParseException;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.shared.PrefixMapping;
import org.apache.jena.shared.impl.PrefixMappingImpl;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.function.FunctionRegistry;
import org.apache.jena.sparql.syntax.ElementAssign;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.util.FmtUtils;
import org.spinrdf.arq.ARQ2SPIN;
import org.spinrdf.arq.ARQFactory;
import org.spinrdf.model.Aggregation;
import org.spinrdf.model.FunctionCall;
import org.spinrdf.model.SPINFactory;
import org.spinrdf.model.Variable;
import org.spinrdf.model.impl.AbstractSPINResourceImpl;
import org.spinrdf.model.print.PrintContext;
import org.spinrdf.model.print.StringPrintContext;
import org.spinrdf.system.SPINModuleRegistry;
import org.spinrdf.vocabulary.SP;


/**
 * Static utilities on SPIN Expressions.
 */
public class SPINExpressions {

    public static final PrefixMapping emptyPrefixMapping = new PrefixMappingImpl();


    public static String checkExpression(String str, Model model) {
        String queryString = "ASK WHERE { LET (?xqoe := (" + str + ")) }";
        try {
            ARQFactory.get().createQuery(model, queryString);
            return null;
        }
        catch(QueryParseException ex) {
            String s = ex.getMessage();
            int startIndex = s.indexOf("at line ");
            if(startIndex >= 0) {
                int endIndex = s.indexOf('.', startIndex);
                StringBuffer sb = new StringBuffer();
                sb.append(s.substring(0, startIndex));
                sb.append("at column ");
                sb.append(ex.getColumn() - 27);
                sb.append(s.substring(endIndex));
                return sb.toString();
            }
            else {
                return s;
            }
        }
    }


    /**
     * Evaluates a given SPIN expression.
     * Prior to calling this, the caller must make sure that the expression has the
     * most specific Java type, e.g. using SPINFactory.asExpression().
     * @param expression  the expression (must be cast into the best possible type)
     * @param queryModel  the Model to query
     * @param bindings  the initial bindings
     * @return the result RDFNode or null
     */
    public static RDFNode evaluate(Resource expression, Model queryModel, QuerySolution bindings) {
        return evaluate(expression, ARQFactory.get().getDataset(queryModel), bindings);
    }


    public static RDFNode evaluate(Resource expression, Dataset dataset, QuerySolution bindings) {
        if(expression instanceof Variable) {
            // Optimized case if the expression is just a variable
            String varName = ((Variable)expression).getName();
            return bindings.get(varName);
        }
        else if(expression.isURIResource()) {
            return expression;
        }
        else {
            Query arq = ARQFactory.get().createExpressionQuery(expression);
            QueryExecution qexec = ARQFactory.get().createQueryExecution(arq, dataset)
                    .substitution(bindings)
                    .build();
            return SPINUtil.getFirstResult(qexec);
        }
    }


    public static String getExpressionString(RDFNode expression) {
        return getExpressionString(expression, true);
    }


    public static String getExpressionString(RDFNode expression, boolean usePrefixes) {
        if(usePrefixes) {
            StringPrintContext p = new StringPrintContext();
            p.setUsePrefixes(usePrefixes);
            SPINExpressions.printExpressionString(p, expression, false, false, expression.getModel().getGraph().getPrefixMapping());
            return p.getString();
        }
        else {
            return ARQFactory.get().createExpressionString(expression);
        }
    }


    /**
     * Checks whether a given RDFNode is an expression.
     * In order to be regarded as expression it must be a well-formed
     * function call, aggregation or variable.
     * @param node  the RDFNode
     * @return true if node is an expression
     */
    public static boolean isExpression(RDFNode node) {
        if(node instanceof Resource && SP.exists(((Resource)node).getModel())) {
            RDFNode expr = SPINFactory.asExpression(node);
            if(expr instanceof Variable) {
                return true;
            }
            else if(!node.isAnon()) {
                return false;
            }
            if(expr instanceof FunctionCall) {
                Resource function = ((FunctionCall)expr).getFunction();
                if(function.isURIResource()) {
                    if(SPINModuleRegistry.get().getFunction(function.getURI(), ((Resource)node).getModel()) != null) {
                        return true;
                    }
                    if(FunctionRegistry.get().isRegistered(function.getURI())) {
                        return true;
                    }
                }
            }
            else {
                return expr instanceof Aggregation;
            }
        }
        return false;
    }


    public static Expr parseARQExpression(String str, Model model) {
        String queryString = "ASK WHERE { LET (?xqoe := (" + str + ")) }";
        Query arq = ARQFactory.get().createQuery(model, queryString);
        ElementGroup group = (ElementGroup) arq.getQueryPattern();
        ElementAssign assign = (ElementAssign) group.getElements().get(0);
        Expr expr = assign.getExpr();
        return expr;
    }


    public static RDFNode parseExpression(String str, Model model) {
        Expr expr = parseARQExpression(str, model);
        return parseExpression(expr, model);
    }


    public static RDFNode parseExpression(Expr expr, Model model) {
        ARQ2SPIN a2s = new ARQ2SPIN(model);
        return a2s.createExpression(expr);
    }


    public static void printExpressionString(PrintContext p, RDFNode node, boolean nested, boolean force, PrefixMapping prefixMapping) {
        if(node instanceof Resource && SPINFactory.asVariable(node) == null) {
            Resource resource = (Resource) node;

            Aggregation aggr = SPINFactory.asAggregation(resource);
            if(aggr != null) {
                PrintContext pc = p.clone();
                pc.setNested(nested);
                aggr.print(pc);
                return;
            }

            FunctionCall call = SPINFactory.asFunctionCall(resource);
            if(call != null) {
                PrintContext pc = p.clone();
                pc.setNested(nested);
                call.print(pc);
                return;
            }
        }
        if(force) {
            p.print("(");
        }
        if(node instanceof Resource) {
            AbstractSPINResourceImpl.printVarOrResource(p, (Resource)node);
        }
        else {
            PrefixMapping pm = p.getUsePrefixes() ? prefixMapping : emptyPrefixMapping;
            String str = FmtUtils.stringForNode(node.asNode(), pm);
            p.print(str);
        }
        if(force) {
            p.print(")");
        }
    }
}
