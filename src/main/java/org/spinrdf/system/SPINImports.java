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

package org.spinrdf.system;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.jena.graph.Graph;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.apache.jena.graph.compose.MultiUnion;
import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.spinrdf.util.JenaUtil;
import org.spinrdf.vocabulary.SP;
import org.spinrdf.vocabulary.SPIN;
import org.spinrdf.vocabulary.SPL;


/**
 * A singleton managing spin:imports.
 *
 * Subclasses can be installed that implement different loaders or
 * otherwise change the default behavior.
 *

 * @version $Id: $Id
 */
public class SPINImports {
	
	private Set<String> registeredURIs = new HashSet<String>();

	/** Constant <code>singleton</code> */
	public static SPINImports singleton = new SPINImports();
	
	
	/**
	 * Gets the singleton instance of this class.
	 *
	 * @return the singleton
	 */
	public static SPINImports get() {
		return singleton;
	}
	
	
	/**
	 * Attempts to load a graph with a given URI.
	 * In the default implementation, this uses the Jena
	 * OntDocumentManager and default loading mechanisms.
	 * Subclasses can override this.
	 *
	 * @param uri  the base URI of the graph to load
	 * @return the Graph or null to ignore this
	 * @throws java.io.IOException if any.
	 */
	protected Graph getImportedGraph(String uri) throws IOException   {
		Model model = OntDocumentManager.getInstance().getModel(uri);
		if(model == null) {
			Model baseModel = JenaUtil.createDefaultModel();
			baseModel.read(uri);
			model = JenaUtil.createOntologyModel(OntModelSpec.OWL_MEM,baseModel);
			OntDocumentManager.getInstance().addModel(uri, model);
		}
		return model.getGraph();
	}
	
	
	/**
	 * Checks if spin:imports have been declared and adds them to a union model.
	 * Will also register any SPIN modules defined in those imports that haven't
	 * been loaded before.
	 *
	 * @param model  the base Model to operate on
	 * @return either model or the union of model and its spin:imports
	 * @throws java.io.IOException if any.
	 */
	public Model getImportsModel(Model model) throws IOException  {
		Set<String> uris = new HashSet<String>();
		StmtIterator it = model.listStatements(null, SPIN.imports, (RDFNode)null);
		while(it.hasNext()) {
			Statement s = it.nextStatement();
			if(s.getObject().isURIResource()) {
				uris.add(s.getResource().getURI());
			}
		}
		if(uris.isEmpty()) {
			return model;
		}
		else {
			Graph baseGraph = model.getGraph();
			
			MultiUnion union = JenaUtil.createMultiUnion();
			union.addGraph(baseGraph);
			union.setBaseGraph(baseGraph);
			
			boolean needsRegistration = false;
			for(String uri : uris) {
				Graph graph = getImportedGraph(uri);
				if(graph != null) {
					union.addGraph(graph);
					if(!registeredURIs.contains(uri)) {
						registeredURIs.add(uri);
						needsRegistration = true;
					}
				}
			}
			
			// Ensure that SP, SPIN and SPL are present
			ensureImported(union, SP.BASE_URI, SP.getModel());
			ensureImported(union, SPL.BASE_URI, SPL.getModel());
			ensureImported(union, SPIN.BASE_URI, SPIN.getModel());
			
			Model unionModel = ModelFactory.createModelForGraph(union);
			if(needsRegistration) {
				SPINModuleRegistry.get().registerAll(unionModel, null);
			}
			return unionModel;
		}
	}
	
	
	private void ensureImported(MultiUnion union, String baseURI, Model model) {
		if(!union.contains(Triple.create(NodeFactory.createURI(baseURI), RDF.type.asNode(), OWL.Ontology.asNode()))) {
			union.addGraph(model.getGraph());
		}
	}
	

	/**
	 * Installs a different SPINImports singleton.
	 *
	 * @param value  the new singleton
	 */
	public static void set(SPINImports value) {
		SPINImports.singleton = value;
	}
}
