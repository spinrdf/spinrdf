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

import org.spinrdf.model.Function;

/**
 * A factory for instances of SPINARQPFunction.
 * This can be overloaded by custom applications to create specific subclasses
 * with different behavior.
 *
 * @author Holger Knublauch
 * @version $Id: $Id
 */
public class SPINARQPFunctionFactory {

	private static SPINARQPFunctionFactory singleton = new SPINARQPFunctionFactory();
	
	/**
	 * <p>get.</p>
	 *
	 * @return a {@link org.spinrdf.arq.SPINARQPFunctionFactory} object.
	 */
	public static SPINARQPFunctionFactory get() {
		return singleton;
	}
	
	/**
	 * <p>set.</p>
	 *
	 * @param value a {@link org.spinrdf.arq.SPINARQPFunctionFactory} object.
	 */
	public static void set(SPINARQPFunctionFactory value) {
		SPINARQPFunctionFactory.singleton = value;
	}
	
	
	/**
	 * <p>create.</p>
	 *
	 * @param functionCls a {@link org.spinrdf.model.Function} object.
	 * @return a {@link org.spinrdf.arq.SPINARQPFunction} object.
	 */
	public SPINARQPFunction create(Function functionCls) {
		return new SPINARQPFunction(functionCls);
	}
}
