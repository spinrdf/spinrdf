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

import org.apache.jena.rdf.model.Resource;


/**
 * A named graph element (GRAPH keyword in SPARQL).
 *

 * @version $Id: $Id
 */
public interface NamedGraph extends ElementGroup {

	/**
	 * Gets the URI Resource or Variable that holds the name of this
	 * named graph.  If it's a Variable, then this method will typecast
	 * it into an instance of Variable.
	 *
	 * @return a Resource or Variable
	 */
	Resource getNameNode();
}
