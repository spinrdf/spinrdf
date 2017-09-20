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

package org.spinrdf.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.jena.graph.Graph;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.shared.Lock;
import org.apache.jena.sparql.core.DatasetGraphBase;
import org.apache.jena.sparql.core.Quad;

/**
 * An implementation of DatasetGraph that delegates all work to a given
 * Dataset implementation.
 *

 * @version $Id: $Id
 */
public class DatasetWrappingDatasetGraph extends DatasetGraphBase {

	private Dataset dataset;
	
	
	/**
	 * <p>Constructor for DatasetWrappingDatasetGraph.</p>
	 *
	 * @param dataset a {@link org.apache.jena.query.Dataset} object.
	 */
	public DatasetWrappingDatasetGraph(Dataset dataset) {
		this.dataset = dataset;
	}

	
	/** {@inheritDoc} */
	@Override
	public void add(Quad quad) {
		Graph graph = getGraph(quad);
		if(graph != null) {
			graph.add(quad.asTriple());
		}
	}


	/** {@inheritDoc} */
	@Override
	public boolean containsGraph(Node graphNode) {
		return dataset.containsNamedModel(graphNode.getURI());
	}


	/** {@inheritDoc} */
	@Override
	public void delete(Quad quad) {
		Graph graph = getGraph(quad);
		if(graph != null) {
			graph.delete(quad.asTriple());
		}
	}


	/** {@inheritDoc} */
	@Override
	public boolean isEmpty() {
		return false;
	}


	/** {@inheritDoc} */
	@Override
	public Iterator<Node> listGraphNodes() {
		List<Node> results = new LinkedList<Node>();
		Iterator<String> names = dataset.listNames();
		while(names.hasNext()) {
			String name = names.next();
			results.add(NodeFactory.createURI(name));
		}
		return results.iterator();
	}


	/** {@inheritDoc} */
	@Override
	public Iterator<Quad> find(Node g, Node s, Node p, Node o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}


	/** {@inheritDoc} */
	@Override
	public Iterator<Quad> findNG(Node g, Node s, Node p, Node o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}


	/** {@inheritDoc} */
	@Override
	public Graph getDefaultGraph() {
		Model defaultModel = dataset.getDefaultModel();
		if(defaultModel != null) {
			return defaultModel.getGraph();
		}
		else {
			return null;
		}
	}


	/** {@inheritDoc} */
	@Override
	public Graph getGraph(Node graphNode) {
		Model model = dataset.getNamedModel(graphNode.getURI());
		if(model != null) {
			return model.getGraph();
		}
		else {
			return null;
		}
	}

	
	/**
	 * <p>getGraph.</p>
	 *
	 * @param quad a {@link org.apache.jena.sparql.core.Quad} object.
	 * @return a {@link org.apache.jena.graph.Graph} object.
	 */
	protected Graph getGraph(Quad quad) {
		if(quad.isDefaultGraph()) {
			return getDefaultGraph();
		}
		else {
			return getGraph(quad.getGraph());
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public Lock getLock() {
		return dataset.getLock();
	}

	
	/** {@inheritDoc} */
	@Override
	public long size() {
		int count = 0;
		Iterator<Node> it = listGraphNodes();
		while(it.hasNext()) {
			it.next();
			count++;
		}
		return count;
	}


	/** {@inheritDoc} */
	@Override
	public void addGraph(Node graphName, Graph graph) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}


	/** {@inheritDoc} */
	@Override
	public void removeGraph(Node graphName) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}
