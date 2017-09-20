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
import org.apache.jena.rdf.model.Resource;
import org.spinrdf.model.Function;
import org.spinrdf.util.JenaDatatypes;
import org.spinrdf.util.JenaUtil;
import org.spinrdf.vocabulary.SPIN;


/**
 * <p>FunctionImpl class.</p>
 *

 * @version $Id: $Id
 */
public class FunctionImpl extends ModuleImpl implements Function {
	
	/**
	 * <p>Constructor for FunctionImpl.</p>
	 *
	 * @param node a {@link org.apache.jena.graph.Node} object.
	 * @param eg a {@link org.apache.jena.enhanced.EnhGraph} object.
	 */
	public FunctionImpl(Node node, EnhGraph eg) {
		super(node, eg);
	}

	
	/**
	 * <p>getReturnType.</p>
	 *
	 * @return a {@link org.apache.jena.rdf.model.Resource} object.
	 */
	public Resource getReturnType() {
		return getResource(SPIN.returnType);
	}


	/** {@inheritDoc} */
	@Override
	public boolean isMagicProperty() {
		return JenaUtil.hasIndirectType(this, SPIN.MagicProperty);
	}


	/** {@inheritDoc} */
	@Override
	public boolean isPrivate() {
		return hasProperty(SPIN.private_, JenaDatatypes.TRUE);
	}
}
