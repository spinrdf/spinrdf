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
 * A SimplePropertyPath of the form SP-&gt;O.
 *

 * @version $Id: $Id
 */
public class ObjectPropertyPath extends SimplePropertyPath {
	
	/**
	 * <p>Constructor for ObjectPropertyPath.</p>
	 *
	 * @param subject a {@link org.apache.jena.rdf.model.Resource} object.
	 * @param predicate a {@link org.apache.jena.rdf.model.Property} object.
	 */
	public ObjectPropertyPath(Resource subject, Property predicate) {
		super(subject, predicate);
	}
}
