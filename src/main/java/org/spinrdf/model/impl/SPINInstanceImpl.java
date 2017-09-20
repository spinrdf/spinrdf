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

import java.util.LinkedList;
import java.util.List;

import org.apache.jena.enhanced.EnhGraph;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.impl.ResourceImpl;
import org.spinrdf.model.QueryOrTemplateCall;
import org.spinrdf.model.SPINInstance;
import org.spinrdf.util.JenaUtil;
import org.spinrdf.util.SPINUtil;


/**
 * <p>SPINInstanceImpl class.</p>
 *

 * @version $Id: $Id
 */
public class SPINInstanceImpl extends ResourceImpl implements SPINInstance {

	/**
	 * <p>Constructor for SPINInstanceImpl.</p>
	 *
	 * @param node a {@link org.apache.jena.graph.Node} object.
	 * @param eg a {@link org.apache.jena.enhanced.EnhGraph} object.
	 */
	public SPINInstanceImpl(Node node, EnhGraph eg) {
		super(node, eg);
	}

	
	/** {@inheritDoc} */
	public List<QueryOrTemplateCall> getQueriesAndTemplateCalls(Property predicate) {
		List<QueryOrTemplateCall> results = new LinkedList<QueryOrTemplateCall>();
		for(Resource cls : JenaUtil.getAllTypes(this)) {
			SPINUtil.addQueryOrTemplateCalls(cls, predicate, results);
		}
		return results;
	}
}
