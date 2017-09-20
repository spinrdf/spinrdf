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
import org.apache.jena.vocabulary.RDF;
import org.spinrdf.arq.ARQFactory;
import org.spinrdf.model.*;
import org.spinrdf.model.print.PrintContext;
import org.spinrdf.model.print.StringPrintContext;
import org.spinrdf.system.SPINLabels;
import org.spinrdf.system.SPINModuleRegistry;
import org.spinrdf.vocabulary.SPIN;


/**
 * <p>TemplateCallImpl class.</p>
 *

 * @version $Id: $Id
 */
public class TemplateCallImpl extends ModuleCallImpl implements TemplateCall {

	/**
	 * <p>Constructor for TemplateCallImpl.</p>
	 *
	 * @param node a {@link org.apache.jena.graph.Node} object.
	 * @param graph a {@link org.apache.jena.enhanced.EnhGraph} object.
	 */
	public TemplateCallImpl(Node node, EnhGraph graph) {
		super(node, graph);
	}

	
	/** {@inheritDoc} */
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
		return ARQFactory.get().createQueryExecution(query, dataset, initialBindings);
	}


	/**
	 * <p>getArgumentsMap.</p>
	 *
	 * @return a {@link java.util.Map} object.
	 */
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


	/**
	 * <p>getArgumentsMapByProperties.</p>
	 *
	 * @return a {@link java.util.Map} object.
	 */
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


	/**
	 * <p>getArgumentsMapByVarNames.</p>
	 *
	 * @return a {@link java.util.Map} object.
	 */
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

	
	/** {@inheritDoc} */
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


	/** {@inheritDoc} */
	@Override
	public Module getModule() {
		return getTemplate();
	}


	/**
	 * <p>getQueryString.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getQueryString() {
		Map<String,RDFNode> map = getArgumentsMapByVarNames();
		StringPrintContext p = new StringPrintContext(new StringBuilder(), map);
		Template template = getTemplate();
		p.setUsePrefixes(false);
		template.getBody().print(p);
		return p.getString();
	}


	/**
	 * <p>getTemplate.</p>
	 *
	 * @return a {@link org.spinrdf.model.Template} object.
	 */
	public Template getTemplate() {
		Statement s = getProperty(RDF.type);
		if(s != null && s.getObject().isURIResource()) {
			return SPINModuleRegistry.get().getTemplate(s.getResource().getURI(), getModel());
		}
		else {
			return null;
		}
	}


	/** {@inheritDoc} */
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
