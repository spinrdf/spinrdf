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

import java.util.List;
import java.util.Map;

import org.topbraid.spin.inference.DefaultSPINRuleComparator;
import org.topbraid.spin.inference.SPINInferences;
import org.topbraid.spin.inference.SPINRuleComparator;
import org.topbraid.spin.system.SPINModuleRegistry;
import org.topbraid.spin.util.CommandWrapper;
import org.topbraid.spin.util.JenaUtil;
import org.topbraid.spin.util.SPINQueryFinder;
import org.topbraid.spin.vocabulary.SPIN;

import org.apache.jena.graph.Graph;
import org.apache.jena.graph.compose.MultiUnion;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;


/**
 * Demonstrates how to efficiently use an external SPIN library, such as OWL RL
 * to run inferences on a given Jena model.
 * 
 * The main trick is that the Query maps are constructed beforehand, so that the
 * actual query model does not need to include the OWL RL model at execution time.
 * 
 * @author Holger Knublauch
 */
public class OWLRLExample {

	public static void main(String[] args) {
		
		// Initialize system functions and templates
		SPINModuleRegistry.get().init();

		// Load domain model with imports
		System.out.println("Loading domain ontology...");
		OntModel queryModel = loadModelWithImports("http://owl.cs.manchester.ac.uk/co-ode-files/ontologies/pizza.owl");
		
		// Create and add Model for inferred triples
		Model newTriples = ModelFactory.createDefaultModel();
		queryModel.addSubModel(newTriples);
		
		// Load OWL RL library from the web
		System.out.println("Loading OWL RL ontology...");
		OntModel owlrlModel = loadModelWithImports("http://topbraid.org/spin/owlrl-all");

		// Register any new functions defined in OWL RL
		SPINModuleRegistry.get().registerAll(owlrlModel, null);
		
		// Build one big union Model of everything
		MultiUnion multiUnion = JenaUtil.createMultiUnion(new Graph[] {
			queryModel.getGraph(),
			owlrlModel.getGraph()
		});
		Model unionModel = ModelFactory.createModelForGraph(multiUnion);
		
		// Collect rules (and template calls) defined in OWL RL
		Map<Resource,List<CommandWrapper>> cls2Query = SPINQueryFinder.getClass2QueryMap(unionModel, queryModel, SPIN.rule, true, false);
		Map<Resource,List<CommandWrapper>> cls2Constructor = SPINQueryFinder.getClass2QueryMap(queryModel, queryModel, SPIN.constructor, true, false);
		SPINRuleComparator comparator = new DefaultSPINRuleComparator(queryModel);

		// Run all inferences
		System.out.println("Running SPIN inferences...");
		SPINInferences.run(queryModel, newTriples, cls2Query, cls2Constructor, null, null, false, SPIN.rule, comparator, null);
		System.out.println("Inferred triples: " + newTriples.size());
	}

	
	private static OntModel loadModelWithImports(String url) {
		Model baseModel = ModelFactory.createDefaultModel();
		baseModel.read(url);
		return JenaUtil.createOntologyModel(OntModelSpec.OWL_MEM,baseModel);
	}
}
