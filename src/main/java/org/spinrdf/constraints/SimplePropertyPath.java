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

package org.spinrdf.constraints;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;


/**
 * A property path that describes a mechanism to get values starting
 * from a given RDF node (root) by following a given predicate.
 * There are two subclasses for SP-&gt;O and OP-&gt;S paths.
 *

 * @version $Id: $Id
 */
public abstract class SimplePropertyPath {

	private Property predicate;
	
	private Resource root;
	
	
	/**
	 * <p>Constructor for SimplePropertyPath.</p>
	 *
	 * @param root a {@link org.apache.jena.rdf.model.Resource} object.
	 * @param predicate a {@link org.apache.jena.rdf.model.Property} object.
	 */
	public SimplePropertyPath(Resource root, Property predicate) {
		this.predicate = predicate;
		this.root = root;
	}
	
	
	/**
	 * <p>Getter for the field <code>predicate</code>.</p>
	 *
	 * @return a {@link org.apache.jena.rdf.model.Property} object.
	 */
	public Property getPredicate() {
		return predicate;
	}
	
	
	/**
	 * <p>Getter for the field <code>root</code>.</p>
	 *
	 * @return a {@link org.apache.jena.rdf.model.Resource} object.
	 */
	public Resource getRoot() {
		return root;
	}
}
