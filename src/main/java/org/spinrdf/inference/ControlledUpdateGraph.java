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

import java.util.HashSet;
import java.util.Set;

import org.apache.jena.graph.Capabilities;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.GraphEventManager;
import org.apache.jena.graph.GraphStatisticsHandler;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.TransactionHandler;
import org.apache.jena.graph.Triple;
import org.apache.jena.graph.impl.GraphWithPerform;
import org.apache.jena.shared.AddDeniedException;
import org.apache.jena.shared.DeleteDeniedException;
import org.apache.jena.shared.PrefixMapping;
import org.apache.jena.util.iterator.ExtendedIterator;


/**
 * A Graph implementation that is used by SPIN inferencing to
 * support UPDATE rules.
 * The Graph wraps another delegate Graph, and delegates most of
 * its operations to that.
 * However, it records which of the triples have actually been
 * added or deleted - the usual Graph policy is to perform those
 * operations regardless of whether a triple was already there.
 * This makes it possible to determine whether further iterations
 * are needed, and which new rdf:type triples have been added. 
 */
class ControlledUpdateGraph implements GraphWithPerform {

	private Graph delegate;
	
	private Set<Triple> addedTriples = new HashSet<Triple>();
	
	private Set<Triple> deletedTriples = new HashSet<Triple>();
	
	
	ControlledUpdateGraph(Graph delegate) {
		this.delegate = delegate;
	}

	
	/** {@inheritDoc} */
	@Override
	public void add(Triple t) throws AddDeniedException {
		performAdd(t);
	}

	
	/** {@inheritDoc} */
	@Override
	public void clear() {
		for(Triple triple : find(Node.ANY, Node.ANY, Node.ANY).toList()) {
			delete(triple);
		}
	}


	/** {@inheritDoc} */
	@Override
	public boolean dependsOn(Graph other) {
		return delegate.dependsOn(other);
	}

	/** {@inheritDoc} */
	@Override
	public TransactionHandler getTransactionHandler() {
		return delegate.getTransactionHandler();
	}

	/** {@inheritDoc} */
	@Override
	public Capabilities getCapabilities() {
		return delegate.getCapabilities();
	}

	/** {@inheritDoc} */
	@Override
	public GraphEventManager getEventManager() {
		return delegate.getEventManager();
	}

	/** {@inheritDoc} */
	@Override
	public GraphStatisticsHandler getStatisticsHandler() {
		return delegate.getStatisticsHandler();
	}

	/** {@inheritDoc} */
	@Override
	public PrefixMapping getPrefixMapping() {
		return delegate.getPrefixMapping();
	}

	/** {@inheritDoc} */
	@Override
	public void delete(Triple t) throws DeleteDeniedException {
		performDelete(t);
	}

	/** {@inheritDoc} */
	@Override
	public ExtendedIterator<Triple> find(Triple m) {
		return delegate.find(m);
	}

	/** {@inheritDoc} */
	@Override
	public ExtendedIterator<Triple> find(Node s, Node p, Node o) {
		return delegate.find(s, p, o);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isIsomorphicWith(Graph g) {
		return delegate.isIsomorphicWith(g);
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(Node s, Node p, Node o) {
		return delegate.contains(s, p, o);
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(Triple t) {
		return delegate.contains(t);
	}

	/** {@inheritDoc} */
	@Override
	public void close() {
		delegate.close();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	/** {@inheritDoc} */
	@Override
	public int size() {
		return delegate.size();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isClosed() {
		return delegate.isClosed();
	}


	/** {@inheritDoc} */
	@Override
	public void performAdd(Triple t) {
		if(!delegate.contains(t)) {
			addedTriples.add(t);
		}
		delegate.add(t);
	}


	/** {@inheritDoc} */
	@Override
	public void performDelete(Triple t) {
		if(delegate.contains(t)) {
			deletedTriples.add(t);
		}
		delegate.delete(t);
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void remove(Node s, Node p, Node o) {
		for(Triple triple : find(s, p, o).toList()) {
			delete(triple);
		}
	}


	/**
	 * <p>Getter for the field <code>addedTriples</code>.</p>
	 *
	 * @return a {@link java.lang.Iterable} object.
	 */
	public Iterable<Triple> getAddedTriples() {
		return addedTriples;
	}
	
	
	/**
	 * <p>isChanged.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isChanged() {
		return !addedTriples.isEmpty() || !deletedTriples.isEmpty(); 
	}
}
