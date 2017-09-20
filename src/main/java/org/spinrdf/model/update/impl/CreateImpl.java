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

package org.spinrdf.model.update.impl;

import org.apache.jena.enhanced.EnhGraph;
import org.apache.jena.graph.Node;
import org.spinrdf.model.print.PrintContext;
import org.spinrdf.model.update.Create;
import org.spinrdf.util.JenaUtil;
import org.spinrdf.vocabulary.SP;


/**
 * <p>CreateImpl class.</p>
 *

 * @version $Id: $Id
 */
public class CreateImpl extends UpdateImpl implements Create {

	/**
	 * <p>Constructor for CreateImpl.</p>
	 *
	 * @param node a {@link org.apache.jena.graph.Node} object.
	 * @param graph a {@link org.apache.jena.enhanced.EnhGraph} object.
	 */
	public CreateImpl(Node node, EnhGraph graph) {
		super(node, graph);
	}

	
	/** {@inheritDoc} */
	@Override
	public void printSPINRDF(PrintContext p) {
		p.printKeyword("CREATE");
		p.print(" ");
		printSilent(p);
		p.printKeyword("GRAPH");
		p.print(" ");
		p.printURIResource(JenaUtil.getResourceProperty(this, SP.graphIRI));
	}
}
