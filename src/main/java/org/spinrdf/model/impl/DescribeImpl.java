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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.jena.enhanced.EnhGraph;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.spinrdf.model.Describe;
import org.spinrdf.model.SPINFactory;
import org.spinrdf.model.Variable;
import org.spinrdf.model.print.PrintContext;
import org.spinrdf.vocabulary.SP;


/**
 * <p>DescribeImpl class.</p>
 *

 * @version $Id: $Id
 */
public class DescribeImpl extends QueryImpl implements Describe {

	/**
	 * <p>Constructor for DescribeImpl.</p>
	 *
	 * @param node a {@link org.apache.jena.graph.Node} object.
	 * @param eh a {@link org.apache.jena.enhanced.EnhGraph} object.
	 */
	public DescribeImpl(Node node, EnhGraph eh) {
		super(node, eh);
	}

	
	/**
	 * <p>getResultNodes.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<Resource> getResultNodes() {
		List<Resource> results = new LinkedList<Resource>();
		for(RDFNode node : getList(SP.resultNodes)) {
			Variable variable = SPINFactory.asVariable(node);
			if(variable != null) {
				results.add(variable);
			}
			else if(node.isURIResource()) {
				results.add((Resource)node);
			}
		}
		return results;
	}
	
	
	/** {@inheritDoc} */
	public void printSPINRDF(PrintContext context) {
		printComment(context);
		printPrefixes(context);
		context.printKeyword("DESCRIBE");
		context.print(" ");
		List<Resource> nodes = getResultNodes();
		if(nodes.isEmpty()) {
			context.print("*");
		}
		else {
			for(Iterator<Resource> nit = nodes.iterator(); nit.hasNext(); ) {
				Resource node = nit.next();
				if(node instanceof Variable) {
					context.print(node.toString());
				}
				else {
					printVarOrResource(context, node);
				}
				if(nit.hasNext()) {
					context.print(" ");
				}
			}
		}
		printStringFrom(context);
		if(!getWhereElements().isEmpty()) {
			context.println();
			printWhere(context);
		}
		printSolutionModifiers(context);
		printValues(context);
	}
}
