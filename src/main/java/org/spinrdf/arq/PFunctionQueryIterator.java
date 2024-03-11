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

import java.util.List;
import java.util.Map;

import org.apache.jena.atlas.io.IndentedWriter;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.engine.binding.BindingBuilder;
import org.apache.jena.sparql.engine.binding.BindingFactory;
import org.apache.jena.sparql.engine.iterator.QueryIteratorBase;
import org.apache.jena.sparql.serializer.SerializationContext;


/**
 * A QueryIterator produced by a SPIN Magic Property.
 *
 * This basically walks through the resultset of the body SELECT query.
 */
class PFunctionQueryIterator extends QueryIteratorBase {

    private Binding parentBinding;

    private QueryExecution qexec;

    private ResultSet rs;

    private List<String> rvs;

    private Map<String,Var> vars;


    PFunctionQueryIterator(ResultSet rs, QueryExecution qexec, Map<String,Var> vars, Binding parentBinding) {
        this.parentBinding = parentBinding;
        this.qexec = qexec;
        this.rs = rs;
        this.rvs = rs.getResultVars();
        this.vars = vars;
    }


    @Override
    protected void closeIterator() {
        qexec.close();
    }


    @Override
    protected boolean hasNextBinding() {
        return rs.hasNext();
    }


    @Override
    protected Binding moveToNextBinding() {
        QuerySolution s = rs.nextSolution();
        BindingBuilder bb = BindingFactory.builder(parentBinding);
        for(String varName : rvs) {
            RDFNode resultNode = s.get(varName);
            if(resultNode != null) {
                Var var = vars.get(varName);
                if(var != null) {
                    bb.add(var, resultNode.asNode());
                }
            }
        }
        Binding result = bb.build();
        return result;
    }


    public void output(IndentedWriter out, SerializationContext sCxt) {
    }


    @Override
    protected void requestCancel() {
        // TODO: what needs to happen here?
    }
}
