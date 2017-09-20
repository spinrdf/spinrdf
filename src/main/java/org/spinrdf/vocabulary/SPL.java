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

import java.io.InputStream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.util.FileUtils;



/**
 * Vocabulary of the SPIN Standard Modules Library (SPL).
 *

 * @version $Id: $Id
 */
public class SPL {

	/** Constant <code>BASE_URI="http://spinrdf.org/spl"</code> */
	public final static String BASE_URI = "http://spinrdf.org/spl";
	
	/** Constant <code>NS="BASE_URI + #"</code> */
	public final static String NS = BASE_URI + "#";
	
	/** Constant <code>PREFIX="spl"</code> */
	public final static String PREFIX = "spl";
	

    /** Constant <code>Argument</code> */
    public final static Resource Argument = ResourceFactory.createResource(NS + "Argument");
    
	/** Constant <code>Attribute</code> */
	public final static Resource Attribute = ResourceFactory.createResource(NS + "Attribute");
    
	/** Constant <code>InferDefaultValue</code> */
	public final static Resource InferDefaultValue = ResourceFactory.createResource(NS + "InferDefaultValue");
    
	/** Constant <code>ObjectCountPropertyConstraint</code> */
	public final static Resource ObjectCountPropertyConstraint = ResourceFactory.createResource(NS + "ObjectCountPropertyConstraint");
    
	/** Constant <code>primaryKeyProperty</code> */
	public final static Resource primaryKeyProperty = ResourceFactory.createResource(NS + "primaryKeyProperty");
    
	/** Constant <code>primaryKeyURIStart</code> */
	public final static Resource primaryKeyURIStart = ResourceFactory.createResource(NS + "primaryKeyURIStart");
    
	/** Constant <code>PrimaryKeyPropertyConstraint</code> */
	public final static Resource PrimaryKeyPropertyConstraint = ResourceFactory.createResource(NS + "PrimaryKeyPropertyConstraint");
	
	/** Constant <code>RunTestCases</code> */
	public final static Resource RunTestCases = ResourceFactory.createResource(NS + "RunTestCases");
    
	/** Constant <code>SPINOverview</code> */
	public final static Resource SPINOverview = ResourceFactory.createResource(NS + "SPINOverview");
    
	/** Constant <code>TestCase</code> */
	public final static Resource TestCase = ResourceFactory.createResource(NS + "TestCase");

    /** Constant <code>UnionTemplate</code> */
    public final static Resource UnionTemplate = ResourceFactory.createResource(NS + "UnionTemplate");
    
    /** Constant <code>object</code> */
    public final static Resource object = ResourceFactory.createResource(NS + "object");
	
	/** Constant <code>objectCount</code> */
	public final static Resource objectCount = ResourceFactory.createResource(NS + "objectCount");
	
	/** Constant <code>subjectCount</code> */
	public final static Resource subjectCount = ResourceFactory.createResource(NS + "subjectCount");

	
	/** Constant <code>defaultValue</code> */
	public final static Property defaultValue = ResourceFactory.createProperty(NS + "defaultValue");

	/** Constant <code>dynamicEnumRange</code> */
	public static final Property dynamicEnumRange = ResourceFactory.createProperty(NS + "dynamicEnumRange");
	
	/** Constant <code>hasValue</code> */
	public final static Property hasValue = ResourceFactory.createProperty(NS + "hasValue");
	
	/** Constant <code>maxCount</code> */
	public final static Property maxCount = ResourceFactory.createProperty(NS + "maxCount");
	
	/** Constant <code>minCount</code> */
	public final static Property minCount = ResourceFactory.createProperty(NS + "minCount");
    
    /** Constant <code>optional</code> */
    public final static Property optional = ResourceFactory.createProperty(NS + "optional");
    
	/** Constant <code>predicate</code> */
	public final static Property predicate = ResourceFactory.createProperty(NS + "predicate");
	
	/** Constant <code>valueType</code> */
	public final static Property valueType = ResourceFactory.createProperty(NS + "valueType");
	
	static {
		// Force initialization
		SP.getURI();
	}
	
	
	private static Model model;
	

	/**
	 * Gets a Model with the content of the SPL namespace, from a file
	 * that is bundled with this API.
	 *
	 * @return the namespace Model
	 */
	public static synchronized Model getModel() {
		if(model == null) {
			model = ModelFactory.createDefaultModel();
			InputStream is = SPL.class.getResourceAsStream("/etc/spl.spin.ttl");
			if(is == null) {
				model.read(SPL.BASE_URI);
			}
			else {
				model.read(is, "http://dummy", FileUtils.langTurtle);
			}
		}
		return model;
	}
}
