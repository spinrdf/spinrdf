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

import java.util.Set;

import org.apache.jena.rdf.model.Resource;
import org.spinrdf.model.print.Printable;


/**
 * A variable in a SPIN query.
 *

 * @version $Id: $Id
 */
public interface Variable extends Resource, Printable {

	/**
	 * Gets the name of this variable (without the '?').
	 *
	 * @return the variable name
	 */
	String getName();
	
	
	/**
	 * Gets all TriplePatterns where this Variable is mentioned.
	 *
	 * @return the TriplePatterns
	 */
	Set<TriplePattern> getTriplePatterns();
	
	
	/**
	 * Checks if this represents a blank node var.
	 *
	 * @return true  if a blank node var
	 */
	boolean isBlankNodeVar();
}
