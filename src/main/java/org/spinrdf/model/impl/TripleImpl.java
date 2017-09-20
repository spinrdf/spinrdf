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
import org.spinrdf.model.Triple;
import org.spinrdf.model.print.PrintContext;
import org.spinrdf.vocabulary.SP;


/**
 * <p>Abstract TripleImpl class.</p>
 *

 * @version $Id: $Id
 */
public abstract class TripleImpl extends TupleImpl implements Triple {

	/**
	 * <p>Constructor for TripleImpl.</p>
	 *
	 * @param node a {@link org.apache.jena.graph.Node} object.
	 * @param graph a {@link org.apache.jena.enhanced.EnhGraph} object.
	 */
	public TripleImpl(Node node, EnhGraph graph) {
		super(node, graph);
	}


	/**
	 * <p>getPredicate.</p>
	 *
	 * @return a {@link org.apache.jena.rdf.model.Resource} object.
	 */
	public Resource getPredicate() {
		return (Resource) getRDFNodeOrVariable(SP.predicate);
	}
	
	
	/** {@inheritDoc} */
	public void print(PrintContext p) {
		print(getSubject(), p);
		p.print(" ");
		print(getPredicate(), p, true);
		p.print(" ");
		print(getObject(), p);
	}
}
