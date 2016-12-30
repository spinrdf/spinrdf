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

package org.topbraid.spin.examples;

import org.topbraid.spin.arq.ARQ2SPIN;
import org.topbraid.spin.arq.ARQFactory;
import org.topbraid.spin.model.Query;
import org.topbraid.spin.model.Select;
import org.topbraid.spin.model.Template;
import org.topbraid.spin.system.SPINModuleRegistry;
import org.topbraid.spin.util.JenaUtil;
import org.topbraid.spin.util.SystemTriples;
import org.topbraid.spin.vocabulary.ARG;
import org.topbraid.spin.vocabulary.SPIN;
import org.topbraid.spin.vocabulary.SPL;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolutionMap;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;


/**
 * Creates a SPIN template and "calls" it.
 * 
 * @author Holger Knublauch
 */
public class TemplateExample {
	
	private static final String NS = "http://example.org/model#";
	
	private static final String PREFIX = "ex";
	
	// Query of the template - argument will be arg:predicate
	private static final String QUERY =
		"SELECT *\n" +
		"WHERE {\n" +
		"    owl:Thing ?predicate ?object .\n" +
		"}";

	
	public static void main(String[] args) {
		
		// Initialize system functions and templates
		SPINModuleRegistry.get().init();

		// Create main model
		Model model = JenaUtil.createDefaultModel();
		JenaUtil.initNamespaces(model.getGraph());
		model.add(SystemTriples.getVocabularyModel()); // Add some queryable triples
		model.setNsPrefix(PREFIX, NS);
		model.setNsPrefix(ARG.PREFIX, ARG.NS);
		
		// Create template
		Template template = createTemplate(model);

		// Now call the template
		org.apache.jena.query.Query arq = ARQFactory.get().createQuery((Select)template.getBody());
		QueryExecution qexec = ARQFactory.get().createQueryExecution(arq, model);
		QuerySolutionMap arqBindings = new QuerySolutionMap();
		arqBindings.add("predicate", RDFS.label);
		qexec.setInitialBinding(arqBindings); // Pre-assign the arguments
		ResultSet rs = qexec.execSelect();
		RDFNode object = rs.next().get("object");
		System.out.println("Label is " + object);
	}


	private static Template createTemplate(Model model) {
		
		// Create a template
		org.apache.jena.query.Query arqQuery = ARQFactory.get().createQuery(model, QUERY);
		Query spinQuery = new ARQ2SPIN(model).createQuery(arqQuery, null);
		Template template = model.createResource(NS + "MyTemplate", SPIN.Template).as(Template.class);
		template.addProperty(SPIN.body, spinQuery);
		
		// Define spl:Argument at the template
		Resource argument = model.createResource(SPL.Argument);
		argument.addProperty(SPL.predicate, model.getProperty(ARG.NS + "predicate"));
		argument.addProperty(SPL.valueType, RDF.Property);
		argument.addProperty(RDFS.comment, "The predicate to get the value of.");
		template.addProperty(SPIN.constraint, argument);
		
		return template;
	}
}
