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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * A simple implementation of the ParameterProvider interface, based
 * on a HashMap.
 *

 * @version $Id: $Id
 */
public class SimpleParameterProvider implements ParameterProvider {

	private final Map<String,String> map;


	/**
	 * <p>Constructor for SimpleParameterProvider.</p>
	 */
	public SimpleParameterProvider() {
		this(new HashMap<String,String>());
	}


	/**
	 * <p>Constructor for SimpleParameterProvider.</p>
	 *
	 * @param map a {@link java.util.Map} object.
	 */
	public SimpleParameterProvider(Map<String,String> map) {
		this.map = map;
	}
	
	
	/**
	 * Adds a new entry to the internal Map.
	 * This is typically used in conjunction with the constructor
	 * without arguments.
	 *
	 * @param key  the parameter key
	 * @param value  the value
	 */
	public void add(String key, String value) {
		map.put(key, value);
	}


	/** {@inheritDoc} */
	public String getParameter(String key) {
		return map.get(key);
	}


	/**
	 * <p>listParameterNames.</p>
	 *
	 * @return a {@link java.util.Iterator} object.
	 */
	public Iterator<String> listParameterNames() {
		return map.keySet().iterator();
	}
}
