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

package org.spinrdf.model;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;


/**
 * An extension of the Jena Resource interface with additional
 * convenience methods to easier access property values.
 *

 * @version $Id: $Id
 */
public interface SPINResource extends Resource {

	/**
	 * Gets the "first" property value of this but only if it's a Literal.
	 * Returns null if the value is a Resource.
	 *
	 * @param predicate  the predicate
	 * @return the "first" property value or null
	 */
	Literal getLiteral(Property predicate);
	
	
	/**
	 * <p>getLong.</p>
	 *
	 * @param predicate a {@link org.apache.jena.rdf.model.Property} object.
	 * @return a {@link java.lang.Long} object.
	 */
	Long getLong(Property predicate);
	
	
	/**
	 * <p>getRDFNode.</p>
	 *
	 * @param predicate a {@link org.apache.jena.rdf.model.Property} object.
	 * @return a {@link org.apache.jena.rdf.model.RDFNode} object.
	 */
	RDFNode getRDFNode(Property predicate);
	
	
	/**
	 * <p>getResource.</p>
	 *
	 * @param predicate a {@link org.apache.jena.rdf.model.Property} object.
	 * @return a {@link org.apache.jena.rdf.model.Resource} object.
	 */
	Resource getResource(Property predicate);
	
	
	/**
	 * <p>getString.</p>
	 *
	 * @param predicate a {@link org.apache.jena.rdf.model.Property} object.
	 * @return a {@link java.lang.String} object.
	 */
	String getString(Property predicate);
}
