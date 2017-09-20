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

package org.spinrdf.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDFS;
import org.spinrdf.model.Argument;
import org.spinrdf.model.Module;
import org.spinrdf.vocabulary.SPIN;


/**
 * Utilities on SPIN modules.
 *

 * @version $Id: $Id
 */
public class ModulesUtil {
	

	/**
	 * Gets the spin:body of a module, including inherited ones if the
	 * direct body is null.
	 *
	 * @param module  the module to get the body of
	 * @return the body or null
	 */
	public static RDFNode getBody(Resource module) {
		Statement s = module.getProperty(SPIN.body);
		if(s == null) {
			return getSuperClassesBody(module, new HashSet<Resource>());
		}
		else {
			return s.getObject();
		}
	}

	
	/**
	 * Attempts to find "good" default bindings for a collection of RDFNode values
	 * at a given module.  For each argument, this algorithm checks whether each
	 * value would match the argument's type.
	 *
	 * @param module  the module Resource to check
	 * @param values  the potential values
	 * @return a Map of argProperty properties to a subset of the values
	 */
	public static Map<Property,RDFNode> getPotentialBindings(Module module, RDFNode[] values) {
		Map<Property,RDFNode> results = new HashMap<Property,RDFNode>();
		for(Argument argument : module.getArguments(false)) {
			Property argProperty = argument.getPredicate();
			if(argProperty != null) {
				Resource argType = argument.getValueType();
				if(argType != null) {
					for(RDFNode value : values) {
						if(value instanceof Resource) {
							Resource resource = (Resource) value;
							if(JenaUtil.hasIndirectType(resource, argType)) {
								results.put(argProperty, resource);
							}
						}
						else {
							Literal literal = (Literal) value;
							if(argType.getURI().equals(literal.getDatatypeURI())) {
								results.put(argProperty, literal);
							}
						}
					}
				}
			}
		}
		return results;
	}
	
	
	private static RDFNode getSuperClassesBody(Resource module, Set<Resource> reached) {
		StmtIterator it = module.listProperties(RDFS.subClassOf);
		while(it.hasNext()) {
			Statement next = it.nextStatement();
			if(next.getObject().isResource()) {
				Resource superClass = next.getResource();
				if(!reached.contains(superClass)) {
					reached.add(superClass);
					Statement s = superClass.getProperty(SPIN.body);
					if(s != null) {
						it.close();
						return s.getObject();
					}
					else {
						RDFNode body = getSuperClassesBody(module, reached);
						if(body != null) {
							it.close();
							return body;
						}
					}
				}
			}
		}
		return null;
	}
}
