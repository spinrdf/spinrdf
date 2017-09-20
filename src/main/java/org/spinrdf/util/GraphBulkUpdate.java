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
import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.Model;

/**
 * Utility methods that allow switching between the deprecated BulkUpdateHandler
 * and the supposed replacement in GraphUtil later.
 *
 * Former usages of BulkUpdateHandler in TopBraid have been replaced with those methods.
 *
 * In TopBraid, we cannot use the default implementation of GraphUtil.add because
 * they first call performAdd and then graph.getEventManager().notifyAdd.
 * In comparison, AbstractDelegatingGraph.add calls performAdd followed by
 * graph.getDelegate().getEventManager(), which is the expected behavior.
 * For example with CachingGraph, its own EventManager does not have listeners attached
 * to it, while its delegate (e.g. SDB) has a listener that updates the cache when a
 * triple was added.
 *

 * @version $Id: $Id
 */
public class GraphBulkUpdate {
	
	/**
	 * <p>add.</p>
	 *
	 * @param graph a {@link org.apache.jena.graph.Graph} object.
	 * @param triples an array of {@link org.apache.jena.graph.Triple} objects.
	 */
	public static void add(Graph graph, Triple[] triples) {
		for(Triple triple : triples) {
			graph.add(triple);
		}
	}

	
	/**
	 * <p>add.</p>
	 *
	 * @param graph a {@link org.apache.jena.graph.Graph} object.
	 * @param triples a {@link java.util.Iterator} object.
	 */
	public static void add(Graph graph, Iterator<Triple> triples) {
		// Avoiding parallel traversal of Iterator (for now) -> copy into List first
		List<Triple> list = new LinkedList<Triple>();
		while(triples.hasNext()) {
			list.add(triples.next());
		}
		add(graph, list);
	}
	
	
	/**
	 * <p>add.</p>
	 *
	 * @param graph a {@link org.apache.jena.graph.Graph} object.
	 * @param triples a {@link java.util.List} object.
	 */
	public static void add(Graph graph, List<Triple> triples) {
		for(Triple triple : triples) {
			graph.add(triple);
		}
	}
	
	
	/**
	 * <p>addInto.</p>
	 *
	 * @param graph a {@link org.apache.jena.graph.Graph} object.
	 * @param src a {@link org.apache.jena.graph.Graph} object.
	 */
	public static void addInto(Graph graph, Graph src) {
		add(graph, src.find(Triple.ANY));
	}
	
	
	/**
	 * <p>addInto.</p>
	 *
	 * @param model a {@link org.apache.jena.rdf.model.Model} object.
	 * @param src a {@link org.apache.jena.rdf.model.Model} object.
	 */
	public static void addInto(Model model, Model src) {
		addInto(model.getGraph(), src.getGraph());
	}
	
	
	/**
	 * <p>delete.</p>
	 *
	 * @param graph a {@link org.apache.jena.graph.Graph} object.
	 * @param triples an array of {@link org.apache.jena.graph.Triple} objects.
	 */
	public static void delete(Graph graph, Triple[] triples) {
		for(Triple triple : triples) {
			graph.delete(triple);
		}
	}
	
	
	/**
	 * <p>delete.</p>
	 *
	 * @param graph a {@link org.apache.jena.graph.Graph} object.
	 * @param triples a {@link java.util.Iterator} object.
	 */
	public static void delete(Graph graph, Iterator<Triple> triples) {
		// Avoiding parallel traversal of Iterator (for now) -> copy into List first
		List<Triple> list = new LinkedList<Triple>();
		while(triples.hasNext()) {
			list.add(triples.next());
		}
		delete(graph, list);
	}
	
	
	/**
	 * <p>delete.</p>
	 *
	 * @param graph a {@link org.apache.jena.graph.Graph} object.
	 * @param triples a {@link java.util.List} object.
	 */
	public static void delete(Graph graph, List<Triple> triples) {
		for(Triple triple : triples) {
			graph.delete(triple);
		}
	}
	
	
	/**
	 * <p>deleteFrom.</p>
	 *
	 * @param graph a {@link org.apache.jena.graph.Graph} object.
	 * @param src a {@link org.apache.jena.graph.Graph} object.
	 */
	public static void deleteFrom(Graph graph, Graph src) {
		delete(graph, src.find(Triple.ANY));
	}
}
