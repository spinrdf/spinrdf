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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.jena.graph.compose.MultiUnion;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.spinrdf.util.JenaUtil;


/**
 * A Dataset that wraps another Dataset but changes its default and
 * named graphs based on the FROM and FROM NAMED clauses of a given
 * Query.
 *

 * @version $Id: $Id
 */
public class FromDataset extends DelegatingDataset {
	
	private Set<String> defaultGraphs;
	
	private Model defaultModel;

	private Set<String> namedGraphs;
	
	
	/**
	 * <p>Constructor for FromDataset.</p>
	 *
	 * @param delegate a {@link org.apache.jena.query.Dataset} object.
	 * @param query a {@link org.apache.jena.query.Query} object.
	 */
	public FromDataset(Dataset delegate, Query query) {
		super(delegate);
		defaultGraphs = new HashSet<String>(query.getGraphURIs());
		namedGraphs = new HashSet<String>(query.getNamedGraphURIs());
	}


	/** {@inheritDoc} */
	@Override
	public boolean containsNamedModel(String uri) {
		if(namedGraphs.isEmpty()) {
			return true;
		}
		else {
			return namedGraphs.contains(uri);
		}
	}


	/** {@inheritDoc} */
	@Override
	public Model getDefaultModel() {
		if(defaultGraphs.isEmpty()) {
			return super.getDefaultModel();
		}
		else {
			if(defaultModel == null) {
				if(defaultGraphs.size() == 1) {
					String defaultGraphURI = defaultGraphs.iterator().next();
					defaultModel = getNamedModel(defaultGraphURI);
				}
				else {
					MultiUnion multiUnion = JenaUtil.createMultiUnion();
					for(String baseURI : defaultGraphs) {
						Model model = getNamedModel(baseURI);
						multiUnion.addGraph(model.getGraph());
					}
					defaultModel = ModelFactory.createModelForGraph(multiUnion);
				}
			}
			return defaultModel;
		}
	}


	/** {@inheritDoc} */
	@Override
	public Iterator<String> listNames() {
		if(namedGraphs.isEmpty()) {
			return super.listNames();
		}
		else {
			return namedGraphs.iterator();
		}
	}
}
