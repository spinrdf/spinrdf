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
 * Vocabulary of the SPIN Modeling Vocabulary.
 *

 * @version $Id: $Id
 */
public class SPIN {

	/** Constant <code>BASE_URI="http://spinrdf.org/spin"</code> */
	public final static String BASE_URI = "http://spinrdf.org/spin";
	
	/** Constant <code>NS="BASE_URI + #"</code> */
	public final static String NS = BASE_URI + "#";
	
	/** Constant <code>PREFIX="spin"</code> */
	public final static String PREFIX = "spin";
	
	
	/** Constant <code>THIS_VAR_NAME="this"</code> */
	public final static String THIS_VAR_NAME = "this";


	/** Constant <code>ask</code> */
	public final static Resource ask = ResourceFactory.createResource(NS + "ask");

	/** Constant <code>AskTemplate</code> */
	public final static Resource AskTemplate = ResourceFactory.createProperty(NS + "AskTemplate");

	/** Constant <code>Column</code> */
	public final static Resource Column = ResourceFactory.createProperty(NS + "Column");

	/** Constant <code>ConstraintViolation</code> */
	public final static Resource ConstraintViolation = ResourceFactory.createProperty(NS + "ConstraintViolation");

	/** Constant <code>ConstraintViolationLevel</code> */
	public final static Resource ConstraintViolationLevel = ResourceFactory.createProperty(NS + "ConstraintViolationLevel");

	/** Constant <code>construct</code> */
	public final static Resource construct = ResourceFactory.createResource(NS + "construct");

	/** Constant <code>constructViolations</code> */
	public final static Resource constructViolations = ResourceFactory.createResource(NS + "constructViolations");

	/** Constant <code>ConstructTemplate</code> */
	public final static Resource ConstructTemplate = ResourceFactory.createProperty(NS + "ConstructTemplate");

	/** Constant <code>Error</code> */
	public final static Resource Error = ResourceFactory.createProperty(NS + "Error");

	/** Constant <code>eval</code> */
	public final static Resource eval = ResourceFactory.createResource(NS + "eval");

	/** Constant <code>Fatal</code> */
	public final static Resource Fatal = ResourceFactory.createProperty(NS + "Fatal");

	/** Constant <code>Function</code> */
	public final static Resource Function = ResourceFactory.createResource(NS + "Function");

	/** Constant <code>Functions</code> */
	public final static Resource Functions = ResourceFactory.createResource(NS + "Functions");

	/** Constant <code>Info</code> */
	public final static Resource Info = ResourceFactory.createProperty(NS + "Info");

	/** Constant <code>LibraryOntology</code> */
	public final static Resource LibraryOntology = ResourceFactory.createResource(NS + "LibraryOntology");

	/** Constant <code>MagicProperties</code> */
	public final static Resource MagicProperties = ResourceFactory.createResource(NS + "MagicProperties");

	/** Constant <code>MagicProperty</code> */
	public final static Resource MagicProperty = ResourceFactory.createResource(NS + "MagicProperty");

	/** Constant <code>Module</code> */
	public final static Resource Module = ResourceFactory.createResource(NS + "Module");

	/** Constant <code>Modules</code> */
	public final static Resource Modules = ResourceFactory.createResource(NS + "Modules");

	/** Constant <code>Rule</code> */
	public final static Resource Rule = ResourceFactory.createProperty(NS + "Rule");

	/** Constant <code>RuleProperty</code> */
	public final static Resource RuleProperty = ResourceFactory.createProperty(NS + "RuleProperty");

	/** Constant <code>select</code> */
	public final static Resource select = ResourceFactory.createResource(NS + "select");

	/** Constant <code>SelectTemplate</code> */
	public final static Resource SelectTemplate = ResourceFactory.createProperty(NS + "SelectTemplate");

	/** Constant <code>TableDataProvider</code> */
	public final static Resource TableDataProvider = ResourceFactory.createProperty(NS + "TableDataProvider");

	/** Constant <code>Template</code> */
	public final static Resource Template = ResourceFactory.createProperty(NS + "Template");

	/** Constant <code>Templates</code> */
	public final static Resource Templates = ResourceFactory.createProperty(NS + "Templates");

	/** Constant <code>UpdateTemplate</code> */
	public final static Resource UpdateTemplate = ResourceFactory.createProperty(NS + "UpdateTemplate");

	/** Constant <code>violatesConstraints</code> */
	public final static Resource violatesConstraints = ResourceFactory.createResource(NS + "violatesConstraints");

	/** Constant <code>Warning</code> */
	public final static Resource Warning = ResourceFactory.createProperty(NS + "Warning");

	
	/** Constant <code>abstract_</code> */
	public final static Property abstract_ = ResourceFactory.createProperty(NS + "abstract");
	
	/** Constant <code>body</code> */
	public final static Property body = ResourceFactory.createProperty(NS + "body");

	/** Constant <code>cachable</code> */
	public final static Property cachable = ResourceFactory.createProperty(NS + "cachable");

	/** Constant <code>column</code> */
	public final static Property column = ResourceFactory.createProperty(NS + "column");

	/** Constant <code>columnIndex</code> */
	public final static Property columnIndex = ResourceFactory.createProperty(NS + "columnIndex");

	/** Constant <code>columnWidth</code> */
	public final static Property columnWidth = ResourceFactory.createProperty(NS + "columnWidth");

	/** Constant <code>columnType</code> */
	public final static Property columnType = ResourceFactory.createProperty(NS + "columnType");
	
	/** Constant <code>command</code> */
	public final static Property command = ResourceFactory.createProperty(NS + "command");
	
	/** Constant <code>constraint</code> */
	public final static Property constraint = ResourceFactory.createProperty(NS + "constraint");
	
	/** Constant <code>constructor</code> */
	public final static Property constructor = ResourceFactory.createProperty(NS + "constructor");

	/** Constant <code>fix</code> */
	public final static Property fix = ResourceFactory.createProperty(NS + "fix");

	/** Constant <code>imports</code> */
	public final static Property imports = ResourceFactory.createProperty(NS + "imports");

	/** Constant <code>labelTemplate</code> */
	public final static Property labelTemplate = ResourceFactory.createProperty(NS + "labelTemplate");

	/** Constant <code>nextRuleProperty</code> */
	public final static Property nextRuleProperty = ResourceFactory.createProperty(NS + "nextRuleProperty");

	/** Constant <code>private_</code> */
	public final static Property private_ = ResourceFactory.createProperty(NS + "private");

	/** Constant <code>query</code> */
	public final static Property query = ResourceFactory.createProperty(NS + "query");

	/** Constant <code>returnType</code> */
	public final static Property returnType = ResourceFactory.createProperty(NS + "returnType");
	
	/** Constant <code>rule</code> */
	public final static Property rule = ResourceFactory.createProperty(NS + "rule");

	/** Constant <code>rulePropertyMaxIterationCount</code> */
	public final static Property rulePropertyMaxIterationCount = ResourceFactory.createProperty(NS + "rulePropertyMaxIterationCount");

	/** Constant <code>symbol</code> */
	public final static Property symbol = ResourceFactory.createProperty(NS + "symbol");

	/** Constant <code>thisUnbound</code> */
	public final static Property thisUnbound = ResourceFactory.createProperty(NS + "thisUnbound");
	
	/** Constant <code>violationDetail</code> */
	public final static Property violationDetail = ResourceFactory.createProperty(NS + "violationDetail");
	
	/** Constant <code>violationLevel</code> */
	public final static Property violationLevel = ResourceFactory.createProperty(NS + "violationLevel");
	
	/** Constant <code>violationPath</code> */
	public final static Property violationPath = ResourceFactory.createProperty(NS + "violationPath");
	
	/** Constant <code>violationRoot</code> */
	public final static Property violationRoot = ResourceFactory.createProperty(NS + "violationRoot");
	
	/** Constant <code>violationSource</code> */
	public final static Property violationSource = ResourceFactory.createProperty(NS + "violationSource");
	
	/** Constant <code>violationValue</code> */
	public final static Property violationValue = ResourceFactory.createProperty(NS + "violationValue");
	

	/** Constant <code>_arg1</code> */
	public final static Resource _arg1 = ResourceFactory.createProperty(NS + "_arg1");

	/** Constant <code>_arg2</code> */
	public final static Resource _arg2 = ResourceFactory.createProperty(NS + "_arg2");

	/** Constant <code>_arg3</code> */
	public final static Resource _arg3 = ResourceFactory.createProperty(NS + "_arg3");

	/** Constant <code>_arg4</code> */
	public final static Resource _arg4 = ResourceFactory.createProperty(NS + "_arg4");

	/** Constant <code>_arg5</code> */
	public final static Resource _arg5 = ResourceFactory.createProperty(NS + "_arg5");
	
	/** Constant <code>_this</code> */
	public final static Resource _this = ResourceFactory.createResource(NS + "_this");
	
	
	static {
		// Force initialization
		SP.getURI();
	}
	
	
	private static Model model;
	

	/**
	 * Gets a Model with the content of the SPIN namespace, from a file
	 * that is bundled with this API.
	 *
	 * @return the namespace Model
	 */
	public static synchronized Model getModel() {
		if(model == null) {
			model = ModelFactory.createDefaultModel();
			InputStream is = SPIN.class.getResourceAsStream("/etc/spin.ttl");
			if(is == null) {
				model.read(SPIN.BASE_URI);
			}
			else {
				model.read(is, "http://dummy", FileUtils.langTurtle);
			}
		}
		return model;
	}
}
