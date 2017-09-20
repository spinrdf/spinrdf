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

package org.spinrdf.examples;

import org.spinrdf.arq.ARQ2SPIN;
import org.spinrdf.arq.ARQFactory;
import org.spinrdf.model.Select;
import org.spinrdf.system.SPINModuleRegistry;

import org.apache.jena.query.Query;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileUtils;
import org.apache.jena.vocabulary.RDF;
import org.spinrdf.system.SPINPreferences;


/**
 * Converts between textual SPARQL representation and SPIN RDF model.
 * 
 * @author Holger Knublauch
 */
public class SPINParsingExample {

	public static void main(String[] args) {
            SPINParsingExample.example1();
            SPINParsingExample.example2();
	}
        
	public static void example1() {
            // Register system functions (such as sp:gt (>))
            SPINModuleRegistry.get().init();

            // Create an empty OntModel importing SP
            Model model = ModelFactory.createDefaultModel();
            model.setNsPrefix("rdf", RDF.getURI());
            model.setNsPrefix("ex", "http://example.org/demo#");

            String query =
                    "SELECT ?person\n" +
                    "WHERE {\n" +
                    "    ?person a ex:Person .\n" +
                    "    ?person ex:age ?age .\n" +
                    "    FILTER (?age > 18) .\n" +
                    "}";

            Query arqQuery = ARQFactory.get().createQuery(model, query);
            ARQ2SPIN arq2SPIN = new ARQ2SPIN(model);
            Select spinQuery = (Select) arq2SPIN.createQuery(arqQuery, null);

            System.out.println("SPIN query in Turtle:");
            model.write(System.out, FileUtils.langTurtle);

            System.out.println("-----");
            String str = spinQuery.toString();
            System.out.println("SPIN query:\n" + str);

            // Now turn it back into a Jena Query
            Query parsedBack = ARQFactory.get().createQuery(spinQuery);
            System.out.println("Jena query:\n" + parsedBack);
        }
        
	public static void example2() {
            // Register system functions (such as sp:gt (>))
            SPINModuleRegistry.get().init();

            // Create an empty OntModel importing SP
            Model model = ModelFactory.createDefaultModel();
            model.setNsPrefix("rdf", RDF.getURI());
            model.setNsPrefix("ex", "http://example.org/demo#");

            String query =
                    "SELECT ?person\n" +
                    "WHERE {\n" +
                    "    ?person a ex:Person .\n" +
                    "    ?person ex:age ?age .\n" +
                    "    FILTER (?age > 18) .\n" +
                    "}";

            SPINPreferences.get().setCreateURIVariables(true);
            Query arqQuery = ARQFactory.get().createQuery(model, query);
            ARQ2SPIN arq2SPIN = new ARQ2SPIN(model);
            arq2SPIN.setVarNamespace("http://example.com/id/");
            Select spinQuery = (Select) arq2SPIN.createQuery(arqQuery, "http://example.com/id");

            System.out.println("SPIN query in Turtle:");
            model.write(System.out, FileUtils.langTurtle);

            System.out.println("-----");
            String str = spinQuery.toString();
            System.out.println("SPIN query:\n" + str);

            // Now turn it back into a Jena Query
            Query parsedBack = ARQFactory.get().createQuery(spinQuery);
            System.out.println("Jena query:\n" + parsedBack);
        }
}
