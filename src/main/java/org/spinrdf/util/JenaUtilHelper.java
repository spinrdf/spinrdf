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

import org.apache.jena.graph.Factory;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.compose.MultiUnion;
import org.apache.jena.mem.GraphMemBase;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

/**
 * This is an extension point for the SPIN library
 * allowing modification of some low level utilities
 * that are exposed through {@link org.spinrdf.util.JenaUtil}.
 *
 * Note: Unstable - should not be used outside of TopBraid.
 *
 * @author Jeremy Carroll
 * @version $Id: $Id
 */
public class JenaUtilHelper {
	
	/**
	 * Return a multiunion.
	 *
	 * @return a {@link org.apache.jena.graph.compose.MultiUnion} object.
	 */
	public MultiUnion createMultiUnion() {
		return new MultiUnion();
	}
	
	
	/**
	 * Return a multiunion, initialized with the given graphs.
	 *
	 * @param graphs a {@link java.util.Iterator} object.
	 * @return a {@link org.apache.jena.graph.compose.MultiUnion} object.
	 */
	public MultiUnion createMultiUnion(Iterator<Graph> graphs) {
		return new MultiUnion(graphs);
	}

	
	/**
	 * Return a multiunion, initialized with the given graphs.
	 *
	 * @param graphs an array of {@link org.apache.jena.graph.Graph} objects.
	 * @return a {@link org.apache.jena.graph.compose.MultiUnion} object.
	 */
	public MultiUnion createMultiUnion(Graph[] graphs) {
		return new MultiUnion(graphs);
	}
	
	
	/**
	 * A memory graph with no reification.
	 *
	 * @return a {@link org.apache.jena.graph.Graph} object.
	 */
	public Graph createDefaultGraph() {
		return Factory.createDefaultGraph();
	}

	
	/**
	 * Returns true if optimizations for faster graphs should
	 * be applied; false if graph is slower. A typical fast graph
	 * is stored in memory, a typical slow graph is stored in a database.
	 * The calling code {@link org.spinrdf.util.JenaUtil#isMemoryGraph(Graph)}
	 * deals with {@link org.apache.jena.graph.compose.MultiUnion}s by taking
	 * the logical AND of the subgraphs.
	 *
	 * @param graph A simple graph, not a {@link org.apache.jena.graph.compose.MultiUnion}
	 * @return true if the graph is fast
	 */
	public boolean isMemoryGraph(Graph graph) {
		return (graph instanceof GraphMemBase);
	}
	
	
	/**
	 * The default implementation does nothing. In TB this is enforced.
	 *
	 * @param m a {@link org.apache.jena.rdf.model.Model} object.
	 * @return a {@link org.apache.jena.rdf.model.Model} object.
	 */
	public Model asReadOnlyModel(Model m) {
		return m;
	}
	
	
	/**
	 * <p>asReadOnlyGraph.</p>
	 *
	 * @param g a {@link org.apache.jena.graph.Graph} object.
	 * @return a {@link org.apache.jena.graph.Graph} object.
	 */
	public Graph asReadOnlyGraph(Graph g) {
		return g;
	}
	
	
	/**
	 * <p>createOntologyModel.</p>
	 *
	 * @param spec a {@link org.apache.jena.ontology.OntModelSpec} object.
	 * @param base a {@link org.apache.jena.rdf.model.Model} object.
	 * @return a {@link org.apache.jena.ontology.OntModel} object.
	 */
	public OntModel createOntologyModel(OntModelSpec spec, Model base) {
		return ModelFactory.createOntologyModel(spec, base);
	}
	
	
	/**
	 * <p>createOntologyModel.</p>
	 *
	 * @return a {@link org.apache.jena.ontology.OntModel} object.
	 */
	public OntModel createOntologyModel() {
		return ModelFactory.createOntologyModel();
	}
	
	
	/**
	 * <p>createOntologyModel.</p>
	 *
	 * @param spec a {@link org.apache.jena.ontology.OntModelSpec} object.
	 * @return a {@link org.apache.jena.ontology.OntModel} object.
	 */
	public OntModel createOntologyModel(OntModelSpec spec) {
		return ModelFactory.createOntologyModel(spec);
	}
	
	
	/**
	 * <p>createConcurrentGraph.</p>
	 *
	 * @return a {@link org.apache.jena.graph.Graph} object.
	 */
	public Graph createConcurrentGraph() {
		return createDefaultGraph();
	}
	
	
	/**
	 * <p>setGraphReadOptimization.</p>
	 *
	 * @param b a boolean.
	 */
	public void setGraphReadOptimization(boolean b) {
	}
	
	/**
	 * <p>deepCloneReadOnlyGraph.</p>
	 *
	 * @param g a {@link org.apache.jena.graph.Graph} object.
	 * @return a {@link org.apache.jena.graph.Graph} object.
	 */
	public Graph deepCloneReadOnlyGraph(Graph g) {
		return asReadOnlyGraph(g);
	}
}
