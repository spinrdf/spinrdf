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

import org.apache.jena.rdf.model.Resource;
import org.spinrdf.model.Function;


/**
 * A (default) SPINFunctionDriver using spin:body to find an executable
 * body for a SPIN function.
 *

 * @version $Id: $Id
 */
public class SPINBodyFunctionDriver implements SPINFunctionDriver {

	/** {@inheritDoc} */
	@Override
	public SPINFunctionFactory create(Resource spinFunction) {
		return doCreate(spinFunction.as(Function.class));
	}
	
	
	/**
	 * <p>doCreate.</p>
	 *
	 * @param spinFunction a {@link org.spinrdf.model.Function} object.
	 * @return a {@link org.spinrdf.arq.SPINFunctionFactory} object.
	 */
	public static SPINFunctionFactory doCreate(Function spinFunction) {
		return new SPINARQFunction(spinFunction);
	}
}
