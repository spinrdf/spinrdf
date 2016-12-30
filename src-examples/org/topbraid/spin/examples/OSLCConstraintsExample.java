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

import org.topbraid.spin.constraints.ConstraintViolation;
import org.topbraid.spin.constraints.SPINConstraints;
import org.topbraid.spin.system.SPINLabels;
import org.topbraid.spin.system.SPINModuleRegistry;
import org.topbraid.spin.vocabulary.SP;
import org.topbraid.spin.vocabulary.SPIN;
import org.topbraid.spin.vocabulary.SPL;

import org.apache.jena.graph.Graph;
import org.apache.jena.graph.compose.MultiUnion;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileUtils;


/**
 * Loads the SPIN-based OSLC Resource Shapes implementation and runs constraint checks
 * on an example bug database.
 * 
 * @author Holger Knublauch
 */
public class OSLCConstraintsExample {

	public static void main(String[] args) {
		
		// Initialize system functions and templates
		SPINModuleRegistry.get().init();

		// Load example file
		Model exampleModel = ModelFactory.createDefaultModel();
		exampleModel.read("http://knublauch.com/oslc/oslc_cm1.ttl", FileUtils.langTurtle);

		// Load OSLC SPIN model
		Model oslcModel = ModelFactory.createDefaultModel();
		oslcModel.read("http://knublauch.com/oslc/oslc.spin.ttl", FileUtils.langTurtle);
		
		// Create Model for the base graph with its imports
		MultiUnion union = new MultiUnion(new Graph[] {
				exampleModel.getGraph(),
				oslcModel.getGraph(),
				SPL.getModel().getGraph(),
				SPIN.getModel().getGraph(),
				SP.getModel().getGraph()
		});
		Model unionModel = ModelFactory.createModelForGraph(union);

		// Register locally defined functions (none exist, but may in the future)
		SPINModuleRegistry.get().registerAll(unionModel, null);

		// Run all constraints
		List<ConstraintViolation> cvs = SPINConstraints.check(unionModel, null);
		System.out.println("All constraint violations:");
		for(ConstraintViolation cv : cvs) {
			System.out.println(" - at " + SPINLabels.get().getLabel(cv.getRoot()) + ": " + cv.getMessage());
		}

		// Run constraints on a single instance only
		Resource bug = cvs.get(0).getRoot();
		List<ConstraintViolation> localCVS = SPINConstraints.check(bug, null);
		System.out.println("Constraint violations for " + SPINLabels.get().getLabel(bug) + ": " + localCVS.size());
	}
}
