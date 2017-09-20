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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.jena.enhanced.EnhGraph;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.spinrdf.model.Argument;
import org.spinrdf.model.Command;
import org.spinrdf.model.Module;
import org.spinrdf.model.SPINFactory;
import org.spinrdf.model.print.PrintContext;
import org.spinrdf.util.JenaUtil;
import org.spinrdf.util.ModulesUtil;
import org.spinrdf.vocabulary.SPIN;
import org.spinrdf.vocabulary.SPL;


/**
 * <p>ModuleImpl class.</p>
 *

 * @version $Id: $Id
 */
public class ModuleImpl extends AbstractSPINResourceImpl implements Module {
	
	
	/**
	 * <p>Constructor for ModuleImpl.</p>
	 *
	 * @param node a {@link org.apache.jena.graph.Node} object.
	 * @param eg a {@link org.apache.jena.enhanced.EnhGraph} object.
	 */
	public ModuleImpl(Node node, EnhGraph eg) {
		super(node, eg);
	}

	
	/** {@inheritDoc} */
	public List<Argument> getArguments(boolean ordered) {
		List<Argument> results = new ArrayList<Argument>();
		StmtIterator it = null;
		JenaUtil.setGraphReadOptimization(true);
		try {
			Set<Resource> classes = JenaUtil.getAllSuperClasses(this);
			classes.add(this);
			for(Resource cls : classes) {
				it = cls.listProperties(SPIN.constraint);
				while(it.hasNext()) {
					Statement s = it.nextStatement();
					addArgumentFromConstraint(s, results);
				}
			}
		}
		finally {
			if (it != null) {
				it.close();
			}
			JenaUtil.setGraphReadOptimization(false);
		}
		
		if(ordered) {
			Collections.sort(results, new Comparator<Argument>() {
				public int compare(Argument o1, Argument o2) {
					Property p1 = o1.getPredicate();
					Property p2 = o2.getPredicate();
					if(p1 != null && p2 != null) {
						return p1.getLocalName().compareTo(p2.getLocalName());
					}
					else {
						return 0;
					}
				}
			});
		}
		
		return results;
	}

	/**
	 * 
	 * @param constaint is a statement whose subject is a class, and whose predicate is SPIN.constraint
	 * @param results
	 */
	private void addArgumentFromConstraint(Statement constaint, List<Argument> results) {
		if(constaint.getObject().isAnon()) {
			// Optimized case to avoid walking up class hierarchy
			StmtIterator types = constaint.getResource().listProperties(RDF.type);
			while(types.hasNext()) {
				Statement typeS = types.next();
				if(typeS.getObject().isURIResource()) {
					if(SPL.Argument.equals(typeS.getObject())) {
						results.add(constaint.getResource().as(Argument.class));
					}
					else if(!SPL.Attribute.equals(typeS.getObject())) {
						if(JenaUtil.hasSuperClass(typeS.getResource(), SPL.Argument.inModel(typeS.getModel()))) {
							results.add(constaint.getResource().as(Argument.class));
						}
					}
				}
			}
		}
		else if(constaint.getObject().isURIResource() && JenaUtil.hasIndirectType(constaint.getResource(), SPL.Argument.inModel(constaint.getModel()))) {
			results.add(constaint.getResource().as(Argument.class));
		}
	}


	/**
	 * <p>getArgumentsMap.</p>
	 *
	 * @return a {@link java.util.Map} object.
	 */
	public Map<String, Argument> getArgumentsMap() {
		Map<String,Argument> results = new HashMap<String,Argument>();
		for(Argument argument : getArguments(false)) {
			Property property = argument.getPredicate();
			if(property != null) {
				results.put(property.getLocalName(), argument);
			}
		}
		return results;
	}


	/**
	 * <p>getBody.</p>
	 *
	 * @return a {@link org.spinrdf.model.Command} object.
	 */
	public Command getBody() {
		RDFNode node = ModulesUtil.getBody(this);
		if(node instanceof Resource) {
			return SPINFactory.asCommand((Resource)node);
		}
		else {
			return null;
		}
	}
	
	
	/**
	 * <p>getComment.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getComment() {
		return getString(RDFS.comment);
	}


	/**
	 * <p>isAbstract.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isAbstract() {
		return SPINFactory.isAbstract(this);
	}


	/** {@inheritDoc} */
	public void print(PrintContext p) {
		// TODO Auto-generated method stub

	}
}
