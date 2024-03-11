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

package org.spinrdf.model.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.jena.enhanced.EnhGraph;
import org.apache.jena.graph.Node;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolutionMap;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDF;
import org.spinrdf.arq.ARQFactory;
import org.spinrdf.model.Argument;
import org.spinrdf.model.Module;
import org.spinrdf.model.SPINFactory;
import org.spinrdf.model.Template;
import org.spinrdf.model.TemplateCall;
import org.spinrdf.model.print.PrintContext;
import org.spinrdf.model.print.StringPrintContext;
import org.spinrdf.system.SPINLabels;
import org.spinrdf.system.SPINModuleRegistry;
import org.spinrdf.vocabulary.SPIN;


public class TemplateCallImpl extends ModuleCallImpl implements TemplateCall {

    public TemplateCallImpl(Node node, EnhGraph graph) {
        super(node, graph);
    }


    @Override
    public QueryExecution createQueryExecution(Dataset dataset) {
        Module template = getModule();
        Query query = ARQFactory.get().createQuery(SPINFactory.asQuery(template.getBody()));
        QuerySolutionMap initialBindings = new QuerySolutionMap();
        Map<Argument,RDFNode> args = getArgumentsMap();
        for(Argument arg : args.keySet()) {
            RDFNode value = args.get(arg);
            initialBindings.add(arg.getVarName(), value);
        }
        return ARQFactory.get().createQueryExecution(query, dataset, initialBindings).build();
    }


    public Map<Argument,RDFNode> getArgumentsMap() {
        Map<Argument,RDFNode> map = new HashMap<Argument,RDFNode>();
        Template template = getTemplate();
        if(template != null) {
            for(Argument ad : template.getArguments(false)) {
                Property argProperty = ad.getPredicate();
                if(argProperty != null) {
                    Statement valueS = getProperty(argProperty);
                    if(valueS != null) {
                        map.put(ad, valueS.getObject());
                    }
                }
            }
        }

        return map;
    }


    public Map<Property, RDFNode> getArgumentsMapByProperties() {
        Map<Property,RDFNode> map = new HashMap<Property,RDFNode>();
        Template template = getTemplate();
        if(template != null) {
            for(Argument ad : template.getArguments(false)) {
                Property argProperty = ad.getPredicate();
                if(argProperty != null) {
                    Statement valueS = getProperty(argProperty);
                    if(valueS != null) {
                        map.put(argProperty, valueS.getObject());
                    }
                }
            }
        }

        return map;
    }


    public Map<String, RDFNode> getArgumentsMapByVarNames() {
        Map<String,RDFNode> map = new HashMap<String,RDFNode>();
        Template template = getTemplate();
        if(template != null) {
            for(Argument ad : template.getArguments(false)) {
                Property argProperty = ad.getPredicate();
                if(argProperty != null) {
                    String varName = ad.getVarName();
                    Statement valueS = getProperty(argProperty);
                    if(valueS != null) {
                        map.put(varName, valueS.getObject());
                    }
                    else if(ad.getDefaultValue() != null) {
                        map.put(varName, ad.getDefaultValue());
                    }
                }
            }
        }
        return map;
    }


    @Override
    public QuerySolutionMap getInitialBinding() {
        QuerySolutionMap map = new QuerySolutionMap();
        Map<String,RDFNode> input = getArgumentsMapByVarNames();
        for(String varName : input.keySet()) {
            RDFNode value = input.get(varName);
            map.add(varName, value);
        }
        return map;
    }


    @Override
    public Module getModule() {
        return getTemplate();
    }


    public String getQueryString() {
        Map<String,RDFNode> map = getArgumentsMapByVarNames();
        StringPrintContext p = new StringPrintContext(new StringBuilder(), map);
        Template template = getTemplate();
        p.setUsePrefixes(false);
        template.getBody().print(p);
        return p.getString();
    }


    public Template getTemplate() {
        StmtIterator it = listProperties(RDF.type);
        try
        {
            while (it.hasNext()) {
                Statement s = it.next();
                if(s != null && s.getObject().isURIResource()) {
                    String uri = s.getResource().getURI();
                    Template template = SPINModuleRegistry.get().getTemplate(uri, getModel());
                    if(template != null) {
                        return template;
                    }
                }
            }
        }
        finally {
            it.close();
        }
        return null;
    }


    public void print(PrintContext p) {
        Template template = getTemplate();
        String str = template.getLabelTemplate();
        if(str != null) {
            Map<String,RDFNode> args = getArgumentsMapByVarNames();
            StringBuffer buffer = new StringBuffer();
            SPINLabels.appendTemplateCallLabel(buffer, str, args);
            p.print(buffer.toString());
        }
        else if(template.getComment() != null) {
            p.print(template.getComment());
        }
        else {
            p.print("<No " + SPIN.PREFIX + ":" + SPIN.labelTemplate.getLocalName() + " set for " + template.getURI() + ">");
        }
    }
}
