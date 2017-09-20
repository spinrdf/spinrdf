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

import java.util.Arrays;
import java.util.List;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.vocabulary.OWL;

/**
 * A singleton controlling which properties shall be used to expand imports.
 * This includes owl:imports.
 *

 * @version $Id: $Id
 */
public class ImportProperties {

	private static ImportProperties singleton = new ImportProperties();
	
	/**
	 * <p>get.</p>
	 *
	 * @return a {@link org.spinrdf.util.ImportProperties} object.
	 */
	public static ImportProperties get() {
		return singleton;
	}
	
	/**
	 * <p>set.</p>
	 *
	 * @param value a {@link org.spinrdf.util.ImportProperties} object.
	 */
	public static void set(ImportProperties value) {
		singleton = value;
	}
	
	
	private List<Property> results = Arrays.asList(new Property[] {
		OWL.imports
	});
	
	
	/**
	 * <p>getImportProperties.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<Property> getImportProperties() {
		return results;
	}
}
