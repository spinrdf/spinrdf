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
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.impl.PropertyImpl;
import org.apache.jena.vocabulary.RDFS;
import org.spinrdf.model.AbstractAttribute;
import org.spinrdf.model.print.PrintContext;
import org.spinrdf.vocabulary.SPL;


/**
 * <p>Abstract AbstractAttributeImpl class.</p>
 *

 * @version $Id: $Id
 */
public abstract class AbstractAttributeImpl extends AbstractSPINResourceImpl implements AbstractAttribute {
	
	/**
	 * <p>Constructor for AbstractAttributeImpl.</p>
	 *
	 * @param node a {@link org.apache.jena.graph.Node} object.
	 * @param eg a {@link org.apache.jena.enhanced.EnhGraph} object.
	 */
	public AbstractAttributeImpl(Node node, EnhGraph eg) {
		super(node, eg);
	}

	
	/**
	 * <p>getPredicate.</p>
	 *
	 * @return a {@link org.apache.jena.rdf.model.Property} object.
	 */
	public Property getPredicate() {
		Resource r = getResource(SPL.predicate);
		if(r != null && r.isURIResource()) {
			return new PropertyImpl(r.asNode(), (EnhGraph)r.getModel());
		}
		else {
			return null;
		}
	}


	/**
	 * <p>getValueType.</p>
	 *
	 * @return a {@link org.apache.jena.rdf.model.Resource} object.
	 */
	public Resource getValueType() {
		return getResource(SPL.valueType);
	}
	
	
	/**
	 * <p>getComment.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getComment() {
		return getString(RDFS.comment);
	}


	/** {@inheritDoc} */
	public void print(PrintContext p) {
		// TODO Auto-generated method stub

	}
}
