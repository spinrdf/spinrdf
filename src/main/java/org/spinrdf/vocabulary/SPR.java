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

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;

/**
 * Vocabulary for http://spinrdf.org/spr
 *

 * @version $Id: $Id
 */
public class SPR {

    /** Constant <code>BASE_URI="http://spinrdf.org/spr"</code> */
    public final static String BASE_URI = "http://spinrdf.org/spr";

    /** Constant <code>NS="BASE_URI + #"</code> */
    public final static String NS = BASE_URI + "#";

    /** Constant <code>PREFIX="spr"</code> */
    public final static String PREFIX = "spr";


    /** Constant <code>Table</code> */
    public final static Resource Table = ResourceFactory.createResource(NS + "Table");

    /** Constant <code>TableClass</code> */
    public final static Resource TableClass = ResourceFactory.createResource(NS + "TableClass");

    /** Constant <code>cell</code> */
    public final static Resource cell = ResourceFactory.createResource(NS + "cell");

    /** Constant <code>cellFunction</code> */
    public final static Resource cellFunction = ResourceFactory.createResource(NS + "cellFunction");

    /** Constant <code>colCount</code> */
    public final static Resource colCount = ResourceFactory.createResource(NS + "colCount");

    /** Constant <code>colCountFunction</code> */
    public final static Resource colCountFunction = ResourceFactory.createResource(NS + "colCountFunction");

    /** Constant <code>colName</code> */
    public final static Resource colName = ResourceFactory.createResource(NS + "colName");

    /** Constant <code>colNameFunction</code> */
    public final static Resource colNameFunction = ResourceFactory.createResource(NS + "colNameFunction");

    /** Constant <code>colTypeFunction</code> */
    public final static Resource colTypeFunction = ResourceFactory.createResource(NS + "colTypeFunction");

    /** Constant <code>colWidthFunction</code> */
    public final static Resource colWidthFunction = ResourceFactory.createResource(NS + "colWidthFunction");

    /** Constant <code>colType</code> */
    public final static Resource colType = ResourceFactory.createResource(NS + "colType");

    /** Constant <code>colWidth</code> */
    public final static Resource colWidth = ResourceFactory.createResource(NS + "colWidth");

    /** Constant <code>contains</code> */
    public final static Resource contains = ResourceFactory.createResource(NS + "contains");

    /** Constant <code>hasCell</code> */
    public final static Resource hasCell = ResourceFactory.createResource(NS + "hasCell");

    /** Constant <code>hasCellFunction</code> */
    public final static Resource hasCellFunction = ResourceFactory.createResource(NS + "hasCellFunction");

    /** Constant <code>rowCount</code> */
    public final static Resource rowCount = ResourceFactory.createResource(NS + "rowCount");

    /** Constant <code>rowCountFunction</code> */
    public final static Resource rowCountFunction = ResourceFactory.createResource(NS + "rowCountFunction");

    /** Constant <code>union</code> */
    public final static Resource union = ResourceFactory.createResource(NS + "union");


    /**
     * <p>getURI.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public static String getURI() {
        return NS;
    }
    
    
    /**
     * <p>exists.</p>
     *
     * @param model a {@link org.apache.jena.rdf.model.Model} object.
     * @return a boolean.
     */
    public static boolean exists(Model model) {
    	return model.contains(SPR.Table, RDF.type, (RDFNode)null);
    }
}
