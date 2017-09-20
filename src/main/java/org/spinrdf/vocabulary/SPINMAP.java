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
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;

/**
 * Vocabulary for http://spinrdf.org/spinmap
 *
 * Automatically generated with TopBraid Composer.
 *

 * @version $Id: $Id
 */
public class SPINMAP {

    /** Constant <code>BASE_URI="http://spinrdf.org/spinmap"</code> */
    public static final String BASE_URI = "http://spinrdf.org/spinmap";

    /** Constant <code>NS="BASE_URI + #"</code> */
    public static final String NS = BASE_URI + "#";

    /** Constant <code>PREFIX="spinmap"</code> */
    public static final String PREFIX = "spinmap";

    /** Constant <code>TARGET_PREDICATE="targetPredicate"</code> */
    public static final String TARGET_PREDICATE = "targetPredicate";
    
    /** Constant <code>SOURCE_PREDICATE="sourcePredicate"</code> */
    public static final String SOURCE_PREDICATE = "sourcePredicate";

    /** Constant <code>Context</code> */
    public static final Resource Context = ResourceFactory.createResource(NS + "Context");

    /** Constant <code>Mapping</code> */
    public static final Resource Mapping = ResourceFactory.createResource(NS + "Mapping");

    /** Constant <code>Mapping_0_1</code> */
    public static final Resource Mapping_0_1 = ResourceFactory.createResource(NS + "Mapping-0-1");

    /** Constant <code>Mapping_1</code> */
    public static final Resource Mapping_1 = ResourceFactory.createResource(NS + "Mapping-1");

    /** Constant <code>Mapping_1_1</code> */
    public static final Resource Mapping_1_1 = ResourceFactory.createResource(NS + "Mapping-1-1");

    /** Constant <code>Mapping_1_1_Inverse</code> */
    public static final Resource Mapping_1_1_Inverse = ResourceFactory.createResource(NS + "Mapping-1-1-Inverse");

    /** Constant <code>Mapping_1_Path_1</code> */
    public static final Resource Mapping_1_Path_1 = ResourceFactory.createResource(NS + "Mapping-1-Path-1");
    
    /** Constant <code>Mapping_2_1</code> */
    public static final Resource Mapping_2_1 = ResourceFactory.createResource(NS + "Mapping-2-1");

    /** Constant <code>SplitMapping_1_1</code> */
    public static final Resource SplitMapping_1_1 = ResourceFactory.createResource(NS + "SplitMapping-1-1");

    /** Constant <code>TargetFunction</code> */
    public static final Resource TargetFunction = ResourceFactory.createResource(NS + "TargetFunction");

    /** Constant <code>TargetFunctions</code> */
    public static final Resource TargetFunctions = ResourceFactory.createResource(NS + "TargetFunctions");

    /** Constant <code>TransformationFunction</code> */
    public static final Resource TransformationFunction = ResourceFactory.createResource(NS + "TransformationFunction");

    /** Constant <code>TransformationFunctions</code> */
    public static final Resource TransformationFunctions = ResourceFactory.createResource(NS + "TransformationFunctions");

    /** Constant <code>context</code> */
    public static final Property context = ResourceFactory.createProperty(NS + "context");

    /** Constant <code>equals</code> */
    public static final Resource equals = ResourceFactory.createResource(NS + "equals");

    /** Constant <code>expression</code> */
    public static final Property expression = ResourceFactory.createProperty(NS + "expression");

    /** Constant <code>function</code> */
    public static final Property function = ResourceFactory.createProperty(NS + "function");

    /** Constant <code>inverseExpression</code> */
    public static final Property inverseExpression = ResourceFactory.createProperty(NS + "inverseExpression");

    /** Constant <code>postRule</code> */
    public static final Property postRule = ResourceFactory.createProperty(NS + "postRule");

    /** Constant <code>predicate</code> */
    public static final Property predicate = ResourceFactory.createProperty(NS + "predicate");

    /** Constant <code>prepRule</code> */
    public static final Property prepRule = ResourceFactory.createProperty(NS + "prepRule");

    /** Constant <code>rule</code> */
    public static final Property rule = ResourceFactory.createProperty(NS + "rule");

    /** Constant <code>separator</code> */
    public static final Property separator = ResourceFactory.createProperty(NS + "separator");

    /** Constant <code>shortLabel</code> */
    public static final Property shortLabel = ResourceFactory.createProperty(NS + "shortLabel");

    /** Constant <code>source</code> */
    public static final Property source = ResourceFactory.createProperty(NS + "source");

    /** Constant <code>sourceClass</code> */
    public static final Property sourceClass = ResourceFactory.createProperty(NS + "sourceClass");

    /** Constant <code>sourcePath</code> */
    public static final Property sourcePath = ResourceFactory.createProperty(NS + "sourcePath");

    /** Constant <code>sourcePredicate1</code> */
    public static final Property sourcePredicate1 = ResourceFactory.createProperty(NS + SOURCE_PREDICATE + "1");

    /** Constant <code>sourcePredicate2</code> */
    public static final Property sourcePredicate2 = ResourceFactory.createProperty(NS + SOURCE_PREDICATE + "2");

    /** Constant <code>sourcePredicate3</code> */
    public static final Property sourcePredicate3 = ResourceFactory.createProperty(NS + SOURCE_PREDICATE + "3");

    /** Constant <code>sourceVariable</code> */
    public static final Resource sourceVariable = ResourceFactory.createResource(NS + "_source");

    /** Constant <code>suggestion_0_1</code> */
    public static final Property suggestion_0_1 = ResourceFactory.createProperty(NS + "suggestion-0-1");

    /** Constant <code>suggestion_1_1</code> */
    public static final Property suggestion_1_1 = ResourceFactory.createProperty(NS + "suggestion-1-1");

    /** Constant <code>suggestionScore</code> */
    public static final Property suggestionScore = ResourceFactory.createProperty(NS + "suggestionScore");

    /** Constant <code>target</code> */
    public static final Property target = ResourceFactory.createProperty(NS + "target");

    /** Constant <code>targetClass</code> */
    public static final Property targetClass = ResourceFactory.createProperty(NS + "targetClass");

    /** Constant <code>targetPredicate1</code> */
    public static final Property targetPredicate1 = ResourceFactory.createProperty(NS + TARGET_PREDICATE + "1");
    
    /** Constant <code>targetPredicate2</code> */
    public static final Property targetPredicate2 = ResourceFactory.createProperty(NS + TARGET_PREDICATE + "2");

    /** Constant <code>targetResource</code> */
    public static final Resource targetResource = ResourceFactory.createResource(NS + "targetResource");

    /** Constant <code>template</code> */
    public static final Property template = ResourceFactory.createProperty(NS + "template");

    /** Constant <code>type</code> */
    public static final Property type = ResourceFactory.createProperty(NS + "type");

    /** Constant <code>value</code> */
    public static final Property value = ResourceFactory.createProperty(NS + "value");

    /** Constant <code>value1</code> */
    public static final Property value1 = ResourceFactory.createProperty(NS + "value1");

    /** Constant <code>value2</code> */
    public static final Property value2 = ResourceFactory.createProperty(NS + "value2");
    
    /** Constant <code>condition</code> */
    public static final Property condition = ResourceFactory.createProperty(NS + "condition");
    
    /**
     * <p>exists.</p>
     *
     * @param model a {@link org.apache.jena.rdf.model.Model} object.
     * @return a boolean.
     */
    public static boolean exists(Model model) {
    	return model.contains(model.getResource(SPINMAP.BASE_URI), RDF.type, OWL.Ontology);
    }
    
    
    /**
     * <p>getURI.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public static String getURI() {
        return NS;
    }
}
