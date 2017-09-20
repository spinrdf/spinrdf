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

package org.spinrdf.model;

import org.apache.jena.enhanced.EnhGraph;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.impl.ResourceImpl;
import org.spinrdf.util.JenaUtil;


/**
 * Base implementation of SPINResource.
 * This is not in the impl package because the impl package is not
 * part of the public API.
 *

 * @version $Id: $Id
 */
public class SPINResourceImpl extends ResourceImpl implements SPINResource {

	
	/**
	 * <p>Constructor for SPINResourceImpl.</p>
	 *
	 * @param node a {@link org.apache.jena.graph.Node} object.
	 * @param graph a {@link org.apache.jena.enhanced.EnhGraph} object.
	 */
	public SPINResourceImpl(Node node, EnhGraph graph) {
		super(node, graph);
	}
	
	
	/**
	 * <p>getInteger.</p>
	 *
	 * @param predicate a {@link org.apache.jena.rdf.model.Property} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	public Integer getInteger(Property predicate) {
		return JenaUtil.getIntegerProperty(this, predicate);
	}

	
	/** {@inheritDoc} */
	public Literal getLiteral(Property predicate) {
		Statement s = getProperty(predicate);
		if(s != null && s.getObject().isLiteral()) {
			return s.getLiteral();
		}
		else {
			return null;
		}
	}


	/** {@inheritDoc} */
	public Long getLong(Property predicate) {
		Literal literal = getLiteral(predicate);
		if(literal != null) {
			return literal.getLong();
		}
		else {
			return null;
		}
	}


	/** {@inheritDoc} */
	public RDFNode getRDFNode(Property predicate) {
		Statement s = getProperty(predicate);
		if(s != null) {
			return s.getObject();
		}
		else {
			return null;
		}
	}


	/** {@inheritDoc} */
	public Resource getResource(Property predicate) {
		Statement s = getProperty(predicate);
		if(s != null && s.getObject().isResource()) {
			return s.getResource();
		}
		else {
			return null;
		}
	}


	/** {@inheritDoc} */
	public String getString(Property predicate) {
		Statement s = getProperty(predicate);
		if(s != null && s.getObject().isLiteral()) {
			return s.getString();
		}
		else {
			return null;
		}
	}


	/**
	 * <p>inferRDFNode.</p>
	 *
	 * @param predicate a {@link org.apache.jena.rdf.model.Property} object.
	 * @return a {@link org.apache.jena.rdf.model.RDFNode} object.
	 */
	public RDFNode inferRDFNode(Property predicate) {
		RDFNode existing = getRDFNode(predicate);
		if(existing != null) {
			return existing;
		}
		else {
			return null;
		}
	}
}
