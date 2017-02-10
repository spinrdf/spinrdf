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

package org.spinrdf.inference;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.jena.atlas.iterator.Iter;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.query.ARQ;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.shared.Lock;
import org.apache.jena.sparql.core.DatasetGraph;
import org.apache.jena.sparql.core.Quad;
import org.apache.jena.sparql.util.Context;


/**
 * A GraphStore that wraps a given Dataset, so that each updateable
 * graph is wrapped with a ControlledUpdateGraph instead of the default.
 */
class ControlledUpdateGraphStore implements DatasetGraph {
	
	private Map<Graph,ControlledUpdateGraph> cugs = new HashMap<Graph,ControlledUpdateGraph>();
	
	private Dataset dataset;
	
	
	ControlledUpdateGraphStore(Dataset dataset, Iterable<Graph> controlledGraphs) {
		this.dataset = dataset;
		for(Graph graph : controlledGraphs) {
			ControlledUpdateGraph cug = new ControlledUpdateGraph(graph);
			cugs.put(graph, cug);
		}
	}
	
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
	}


	private Graph getControlledUpdateGraph(Graph graph) {
		Graph cug = cugs.get(graph);
		if(cug != null) {
			return cug;
		}
		else {
			return graph;
		}
	}
	
	
	public Iterable<ControlledUpdateGraph> getControlledUpdateGraphs() {
		return cugs.values();
	}


	@Override
	public Graph getDefaultGraph() {
		Model defaultModel = dataset.getDefaultModel();
		if(defaultModel != null) {
			return getControlledUpdateGraph(defaultModel.getGraph());
		}
		else {
			return null;
		}
	}


	@Override
	public Graph getGraph(Node graphNode) {
		Model model = dataset.getNamedModel(graphNode.getURI());
		if(model != null) {
			return getControlledUpdateGraph(model.getGraph());
		}
		else {
			return null;
		}
	}


	@Override
	public boolean containsGraph(Node graphNode) {
		return dataset.containsNamedModel(graphNode.getURI());
	}


	@Override
	public void setDefaultGraph(Graph g) {
	}


	@Override
	public void addGraph(Node graphName, Graph graph) {
	}


	@Override
	public void removeGraph(Node graphName) {
	}


	@Override
	public Iterator<Node> listGraphNodes() {
		List<Node> results = new LinkedList<Node>();
		Iterator<String> it = dataset.listNames();
		while(it.hasNext()) {
			results.add(NodeFactory.createURI(it.next()));
		}
		return results.iterator();
	}


	@Override
	public void add(Quad quad) {
		Graph graph;
		if(quad.isDefaultGraph()) {
			graph = getDefaultGraph();
		}
		else {
			graph = getGraph(quad.getGraph());
		}
		if(graph != null) {
			graph.add(quad.asTriple());
		}
	}


	@Override
	public void delete(Quad quad) {
		Graph graph;
		if(quad.isDefaultGraph()) {
			graph = getDefaultGraph();
		}
		else {
			graph = getGraph(quad.getGraph());
		}
		if(graph != null) {
			graph.delete(quad.asTriple());
		}
	}


	@Override
	public void deleteAny(Node g, Node s, Node p, Node o) {
        Iterator<Quad> iter = find(g, s, p, o) ;
        List<Quad> list = Iter.toList(iter) ;
        for (Quad q : list) {
            delete(q);
        }
	}


	@Override
	public Iterator<Quad> find() {
		return null;
	}


	@Override
	public Iterator<Quad> find(Quad quad) {
		return null;
	}


	@Override
	public Iterator<Quad> find(Node g, Node s, Node p, Node o) {
		return null;
	}


	@Override
	public Iterator<Quad> findNG(Node g, Node s, Node p, Node o) {
		return null;
	}


	@Override
	public boolean contains(Node g, Node s, Node p, Node o) {
		Graph graph = getGraph(g);
		if(graph != null) {
			return graph.contains(s, p, o);
		}
		else {
			return false;
		}
	}


	@Override
	public boolean contains(Quad quad) {
		return false;
	}


	@Override
	public boolean isEmpty() {
		return false;
	}


	@Override
	public Lock getLock() {
		return null;
	}


	@Override
	public Context getContext() {
		return ARQ.getContext() ;
	}


	@Override
	public long size() {
		return 0;
	}


	@Override
	public void close() {
	}


	@Override
	public void add(Node g, Node s, Node p, Node o) {
		add(Quad.create(g, s, p, o));
	}


	@Override
	public void delete(Node g, Node s, Node p, Node o) {
		delete(Quad.create(g, s, p, o));
	}


    @Override
    public void begin(ReadWrite readWrite) { throw new UnsupportedOperationException("Tarsnactions not supported"); }


    @Override
    public void commit() {}


    @Override
    public void abort() {}


    @Override
    public void end() {}


    @Override
    public boolean isInTransaction() {
        return false;
    }


    @Override
    public boolean supportsTransactions() {
        return false;
    }
}
