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

import java.lang.reflect.Constructor;

import org.apache.jena.enhanced.EnhGraph;
import org.apache.jena.enhanced.EnhNode;
import org.apache.jena.enhanced.Implementation;
import org.apache.jena.graph.Node;
import org.apache.jena.vocabulary.RDF;

/**
 * An extension of the Jena polymorphism mechanism.
 * In contrast to SimpleImplementation, this maps to two different RDF classes.
 *

 * @version $Id: $Id
 */
public class SimpleImplementation2 extends Implementation {

	@SuppressWarnings("rawtypes")
	private Constructor constructor;

	private final Node type1;
	
	private final Node type2;


	/**
	 * <p>Constructor for SimpleImplementation2.</p>
	 *
	 * @param type1 a {@link org.apache.jena.graph.Node} object.
	 * @param type2 a {@link org.apache.jena.graph.Node} object.
	 * @param implClass a {@link java.lang.Class} object.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SimpleImplementation2(Node type1, Node type2, Class implClass) {
		this.type1 = type1;
		this.type2 = type2;
		try {
			constructor = implClass.getConstructor(Node.class, EnhGraph.class);
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
	}


	/** {@inheritDoc} */
	@Override
	public boolean canWrap(Node node, EnhGraph eg) {
		return 	eg.asGraph().contains(node, RDF.type.asNode(), type1) ||
				eg.asGraph().contains(node, RDF.type.asNode(), type2);
	}


	/** {@inheritDoc} */
	@Override
	public EnhNode wrap(Node node, EnhGraph eg) {
		try {
			return (EnhNode)constructor.newInstance(node, eg);
		}
		catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}
}
