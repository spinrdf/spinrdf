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

package org.spinrdf.vocabulary;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

/**
 * Constants to access the arg: namespace.
 *

 * @version $Id: $Id
 */
public class ARG {

	/** Constant <code>BASE_URI="http://spinrdf.org/arg"</code> */
	public final static String BASE_URI = "http://spinrdf.org/arg";
	
	/** Constant <code>NS="BASE_URI + #"</code> */
	public final static String NS = BASE_URI + "#";
	
	/** Constant <code>PREFIX="arg"</code> */
	public final static String PREFIX = "arg";
	
	
	/** Constant <code>property</code> */
	public final static Resource property = ResourceFactory.createResource(NS + "property");
	
	/** Constant <code>maxCount</code> */
	public final static Resource maxCount = ResourceFactory.createResource(NS + "maxCount");
	
	/** Constant <code>minCount</code> */
	public final static Resource minCount = ResourceFactory.createResource(NS + "minCount");
}
