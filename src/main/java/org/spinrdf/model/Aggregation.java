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
import org.spinrdf.model.print.Printable;


/**
 * Part of a SPARQL expression that calls an Aggregation (such as SUM).
 *

 * @version $Id: $Id
 */
public interface Aggregation extends Printable, Resource {
	
	/**
	 * <p>getAs.</p>
	 *
	 * @return a {@link org.spinrdf.model.Variable} object.
	 */
	Variable getAs();
	

	/**
	 * <p>getExpression.</p>
	 *
	 * @return a {@link org.apache.jena.rdf.model.Resource} object.
	 */
	Resource getExpression();
	
	
	/**
	 * <p>isDistinct.</p>
	 *
	 * @return a boolean.
	 */
	boolean isDistinct();
}
