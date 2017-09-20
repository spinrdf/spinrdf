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
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Statement;
import org.spinrdf.model.Attribute;
import org.spinrdf.vocabulary.SPL;


/**
 * <p>AttributeImpl class.</p>
 *

 * @version $Id: $Id
 */
public class AttributeImpl extends AbstractAttributeImpl implements Attribute {
	
	/**
	 * <p>Constructor for AttributeImpl.</p>
	 *
	 * @param node a {@link org.apache.jena.graph.Node} object.
	 * @param eg a {@link org.apache.jena.enhanced.EnhGraph} object.
	 */
	public AttributeImpl(Node node, EnhGraph eg) {
		super(node, eg);
	}


	/**
	 * <p>isOptional.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isOptional() {
		return getMinCount() == 0;
	}


	/**
	 * <p>getDefaultValue.</p>
	 *
	 * @return a {@link org.apache.jena.rdf.model.RDFNode} object.
	 */
	public RDFNode getDefaultValue() {
		return getRDFNode(SPL.defaultValue);
	}


	/**
	 * <p>getMaxCount.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public Integer getMaxCount() {
		Statement s = getProperty(SPL.maxCount);
		if(s != null && s.getObject().isLiteral()) {
			return s.getInt();
		}
		else {
			return null;
		}
	}


	/**
	 * <p>getMinCount.</p>
	 *
	 * @return a int.
	 */
	public int getMinCount() {
		Statement s = getProperty(SPL.minCount);
		if(s != null && s.getObject().isLiteral()) {
			return s.getInt();
		}
		else {
			return 0;
		}
	}
}
