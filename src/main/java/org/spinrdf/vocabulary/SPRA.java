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
 * Vocabulary for http://spinrdf.org/spra
 *
 * @author Holger Knublauch
 * @version $Id: $Id
 */
public class SPRA {

    /** Constant <code>BASE_URI="http://spinrdf.org/spra"</code> */
    public final static String BASE_URI = "http://spinrdf.org/spra";

    /** Constant <code>NS="BASE_URI + #"</code> */
    public final static String NS = BASE_URI + "#";

    /** Constant <code>PREFIX="spra"</code> */
    public final static String PREFIX = "spra";


    /** Constant <code>Table</code> */
    public final static Resource Table = ResourceFactory.createResource(NS + "Table");


    /**
     * <p>getURI.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public static String getURI() {
        return NS;
    }
}
