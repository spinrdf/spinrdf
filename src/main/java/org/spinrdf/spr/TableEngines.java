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

package org.spinrdf.spr;

import java.util.HashMap;
import java.util.Map;

import org.apache.jena.rdf.model.Resource;
import org.spinrdf.spr.spra.ATableEngine;
import org.spinrdf.vocabulary.SPRA;


/**
 * A Singleton managing the registered TableEngines.
 *

 * @version $Id: $Id
 */
public class TableEngines {

	private static TableEngines singleton = new TableEngines();
	
	/**
	 * <p>get.</p>
	 *
	 * @return a {@link org.spinrdf.spr.TableEngines} object.
	 */
	public static TableEngines get() {
		return singleton;
	}
	
	/**
	 * <p>set.</p>
	 *
	 * @param value a {@link org.spinrdf.spr.TableEngines} object.
	 */
	public static void set(TableEngines value) {
		TableEngines.singleton = value;
	}
	
	private TableEngine defaultTableEngine = new ATableEngine();
	
	private Map<Resource,TableEngine> map = new HashMap<Resource,TableEngine>();
	
	/**
	 * <p>Constructor for TableEngines.</p>
	 */
	public TableEngines() {
		map.put(SPRA.Table, defaultTableEngine);
	}
	
	
	/**
	 * <p>Getter for the field <code>defaultTableEngine</code>.</p>
	 *
	 * @return a {@link org.spinrdf.spr.TableEngine} object.
	 */
	public TableEngine getDefaultTableEngine() {
		return defaultTableEngine;
	}
	
	
	/**
	 * <p>getForType.</p>
	 *
	 * @param type a {@link org.apache.jena.rdf.model.Resource} object.
	 * @return a {@link org.spinrdf.spr.TableEngine} object.
	 */
	public TableEngine getForType(Resource type) {
		return map.get(type);
	}
	
	
	/**
	 * <p>register.</p>
	 *
	 * @param type a {@link org.apache.jena.rdf.model.Resource} object.
	 * @param tableEngine a {@link org.spinrdf.spr.TableEngine} object.
	 */
	public void register(Resource type, TableEngine tableEngine) {
		map.put(type, tableEngine);
	}
}
