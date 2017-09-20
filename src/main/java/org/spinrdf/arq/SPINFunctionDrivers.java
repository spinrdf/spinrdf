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

package org.spinrdf.arq;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.spinrdf.util.JenaDatatypes;
import org.spinrdf.util.JenaUtil;
import org.spinrdf.vocabulary.SPIN;


/**
 * The singleton that creates ARQ FunctionFactories from SPIN and SHACL functions.
 * Can be used by applications to install a different singleton with support
 * for different kinds of functions, such as SPINx.
 *

 * @version $Id: $Id
 */
public class SPINFunctionDrivers implements SPINFunctionDriver {

	private static SPINFunctionDrivers singleton = new SPINFunctionDrivers();
	
	/**
	 * <p>get.</p>
	 *
	 * @return a {@link org.spinrdf.arq.SPINFunctionDrivers} object.
	 */
	public static SPINFunctionDrivers get() {
		return singleton;
	}
	
	/**
	 * <p>set.</p>
	 *
	 * @param value a {@link org.spinrdf.arq.SPINFunctionDrivers} object.
	 */
	public static void set(SPINFunctionDrivers value) {
		singleton = value;
	}
	
	
	private Map<Property,SPINFunctionDriver> drivers = new HashMap<Property,SPINFunctionDriver>();
	
	SPINFunctionDrivers() {
		register(SPIN.body, new SPINBodyFunctionDriver());
	}


	/** {@inheritDoc} */
	@Override
	public SPINFunctionFactory create(Resource function) {
		SPINFunctionDriver driver = getDriver(function);
		if(driver != null) {
			return driver.create(function);
		}
		else {
			return null;
		}
	}
	

	/**
	 * Registers a new SPINFunctionDriver for a given key predicate.
	 * For example, SPARQLMotion functions are recognized via sm:body.
	 * Any previous entry will be overwritten.
	 *
	 * @param predicate  the key predicate
	 * @param driver  the driver to register
	 */
	public void register(Property predicate, SPINFunctionDriver driver) {
		drivers.put(predicate, driver);
	}
	
	
	private SPINFunctionDriver getDriver(Resource spinFunction) {
		JenaUtil.setGraphReadOptimization(true);
		try {
			SPINFunctionDriver direct = getDirectDriver(spinFunction);
			if(direct != null) {
				return direct;
			}
			else {
				return getDriver(spinFunction, new HashSet<Resource>());
			}
		}
		finally {
			JenaUtil.setGraphReadOptimization(false);
		}
	}
	
	
	private SPINFunctionDriver getDriver(Resource spinFunction, Set<Resource> reached) {
		reached.add(spinFunction);
		for(Resource superClass : JenaUtil.getSuperClasses(spinFunction)) {
			if(!reached.contains(spinFunction)) {
				SPINFunctionDriver superFunction = getDirectDriver(superClass);
				if(superFunction != null) {
					return superFunction;
				}
			}
		}
		return null;
	}
	
	
	private SPINFunctionDriver getDirectDriver(Resource spinFunction) {
		if(!spinFunction.hasProperty(SPIN.abstract_, JenaDatatypes.TRUE)) {
			StmtIterator it = spinFunction.listProperties();
			while(it.hasNext()) {
				Statement s = it.next();
				final SPINFunctionDriver driver = drivers.get(s.getPredicate());
				if(driver != null) {
					it.close();
					return driver;
				}
			}
		}
		return null;
	}
}
