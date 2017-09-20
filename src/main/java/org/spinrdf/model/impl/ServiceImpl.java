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

import org.apache.jena.enhanced.EnhGraph;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.Statement;
import org.spinrdf.model.SPINFactory;
import org.spinrdf.model.Service;
import org.spinrdf.model.Variable;
import org.spinrdf.model.print.PrintContext;
import org.spinrdf.model.visitor.ElementVisitor;
import org.spinrdf.vocabulary.SP;

/**
 * <p>ServiceImpl class.</p>
 *

 * @version $Id: $Id
 */
public class ServiceImpl extends ElementImpl implements Service {
	
	/**
	 * <p>Constructor for ServiceImpl.</p>
	 *
	 * @param node a {@link org.apache.jena.graph.Node} object.
	 * @param enhGraph a {@link org.apache.jena.enhanced.EnhGraph} object.
	 */
	public ServiceImpl(Node node, EnhGraph enhGraph) {
		super(node, enhGraph);
	}

	
	/**
	 * <p>getServiceURI.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getServiceURI() {
		Statement s = getProperty(SP.serviceURI);
		if(s != null && s.getObject().isURIResource()) {
			Variable variable = SPINFactory.asVariable(s.getResource());
			if(variable == null) {
				return s.getResource().getURI();
			}
		}
		return null;
	}


	/** {@inheritDoc} */
	@Override
	public Variable getServiceVariable() {
		Statement s = getProperty(SP.serviceURI);
		if(s != null && s.getObject().isResource()) {
			Variable variable = SPINFactory.asVariable(s.getResource());
			if(variable != null) {
				return variable;
			}
		}
		return null;
	}


	/** {@inheritDoc} */
	public void print(PrintContext p) {
		p.printKeyword("SERVICE");
		Variable var = getServiceVariable();
		if(var != null) {
			p.print(" ");
			p.printVariable(var.getName());
		}
		else {
			String uri = getServiceURI();
			if(uri != null) {
				p.print(" ");
				p.printURIResource(getModel().getResource(uri));
			}
		}
		printNestedElementList(p);
	}


	/** {@inheritDoc} */
	public void visit(ElementVisitor visitor) {
		visitor.visit(this);
	}
}
