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

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.jena.graph.Graph;
import org.apache.jena.graph.GraphEvents;
import org.apache.jena.graph.GraphListener;
import org.apache.jena.graph.Triple;
import org.apache.jena.util.IteratorCollection;
import org.apache.jena.util.iterator.ClosableIterator;

/**
 * An abstract GraphListeners that forwards each call
 * into {@link #notifyAddTriple} and
 * {@link #notifyDeleteTriple} to
 * reduce the implementation burden of subclasses.
 * All of the bulk operations are forwarded to
 * {@link #notifyAddIterator} and
 * {@link #notifyDeleteIterator}.
 * So subclasses can override those two methods to
 * modify all the bulk operations, except the removeAll
 * ones.
 * For the removeAll operations, subclasses should implement
 * {@link #notifyRemoveAll(Graph, Triple)},
 * this is only called by the default implementation
 * of {@link #notifyEvent(Graph, Object)}.
 *
 * @author Holger Knublauch, Jeremy Carroll
 * @version $Id: $Id
 */
public abstract class AbstractGraphListener implements GraphListener {



	/**
	 * <p>notifyAddArray.</p>
	 *
	 * @param g a {@link org.apache.jena.graph.Graph} object.
	 * @param triples an array of {@link org.apache.jena.graph.Triple} objects.
	 */
	public void notifyAddArray(Graph g, Triple[] triples) {
		notifyAddIterator(g,Arrays.asList(triples).iterator());
	}

	/** {@inheritDoc} */
	public void notifyAddGraph(Graph g, Graph added) {
		notifyAddIterator(g,added.find(Triple.ANY));
	}


	/** {@inheritDoc} */
	public void notifyAddIterator(Graph g, Iterator<Triple> it) {
		if (it instanceof ClosableIterator) {
			// copy in case the find result is holding locks ...
			notifyAddList(g,IteratorCollection.iteratorToList(it));
		} else {
			while (it.hasNext()) {
				Triple t = it.next();
				notifyAddTriple(g, t);
			}
		}
	}


	/** {@inheritDoc} */
	public void notifyAddList(Graph g, List<Triple> triples) {
		notifyAddIterator(g, triples.iterator());
	}

	/**
	 * <p>notifyDeleteArray.</p>
	 *
	 * @param g a {@link org.apache.jena.graph.Graph} object.
	 * @param triples an array of {@link org.apache.jena.graph.Triple} objects.
	 */
	public void notifyDeleteArray(Graph g, Triple[] triples) {
		notifyDeleteIterator(g,Arrays.asList(triples).iterator());
	}
	/** {@inheritDoc} */
	public void notifyDeleteGraph(Graph g, Graph removed) {
		notifyDeleteIterator(g,removed.find(Triple.ANY));
	}



	/** {@inheritDoc} */
	public void notifyDeleteIterator(Graph g, Iterator<Triple> it) {
		if (it instanceof ClosableIterator) {
			// copy in case the find result is holding locks ...
			notifyDeleteList(g,IteratorCollection.iteratorToList(it));
		} else {
			while (it.hasNext()) {
				Triple triple = it.next();
				notifyDeleteTriple(g, triple);
			}
		}
	}



	/** {@inheritDoc} */
	public void notifyDeleteList(Graph g, List<Triple> list) {
		notifyDeleteIterator(g, list.iterator());
	}


// TODO: FIXDOC : These correspond to the bulk operations {@link #notifyRemoveAll()}, and {@link #remove(Node, Node, Node)}, respectively.
	/**
	 * {@inheritDoc}
	 *
	 *    <code>value</code> is usually a {@link GraphEvents}.
	 *    Special attention is drawn to {@link GraphEvents#removeAll}
	 *    and events whose {@link GraphEvents#getTitle()} is <code>"remove"</code>
	 *    (see {@link GraphEvents#remove(Node, Node, Node)}. 
	 *    Unlike other notifications, the listener cannot tell which triples
	 *    have been modified, since they have already been deleted by the time
	 *    this event is sent, and the event does not include a record of them.
	 *    This default implementation maps these two events to
	 *    {@link #notifyRemoveAll(Graph, Triple)} calls.
	 */
	public void notifyEvent(Graph source, Object value) {
		if (value instanceof GraphEvents) {
			if (GraphEvents.removeAll.equals(value)) {
				notifyRemoveAll(source,Triple.ANY);
			} else {
				GraphEvents event = (GraphEvents)value;
				if ("remove".equals(event.getTitle())) {
					notifyRemoveAll(source,(Triple)event.getContent());
				}
			}
		}
	}

	/**
	 * Called after a removeAll modification. The
	 * actual triples deleted cannot be identified easily.
	 * See {@link #notifyEvent(Graph, Object)} for explanation
	 * of this method.
	 *
	 * @param source a {@link org.apache.jena.graph.Graph} object.
	 * @param pattern The pattern of triples being removed, often {@link org.apache.jena.graph.Triple#ANY}.
	 */
	protected abstract void notifyRemoveAll(Graph source, Triple pattern);
}
