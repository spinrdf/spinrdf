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

import java.util.Map;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDFS;
import org.spinrdf.util.JenaUtil;


/**
 * A singleton that is used to render resources into strings.
 * By default this displays qnames (if possible).
 * Can be changed, for example, to switch to displaying rdfs:labels
 * instead of qnames etc.
 *

 * @version $Id: $Id
 */
public class SPINLabels {
	
	private static SPINLabels singleton = new SPINLabels();
	

	/**
	 * Gets the singleton instance of this class.
	 *
	 * @return the singleton
	 */
	public static SPINLabels get() {
		return singleton;
	}
	
	
	/**
	 * Replaces the singleton to a subclass with different behavior.
	 * This is used by TopBraid, which has its own rendering engine.
	 *
	 * @param value  the new engine
	 */
	public static void set(SPINLabels value) {
		SPINLabels.singleton = value;
	}
	
	
	/**
	 * Gets a "human-readable" label for a given Resource.
	 * This checks for any existing rdfs:label, otherwise falls back to
	 * <code>getLabel()</code>.
	 *
	 * @param resource a {@link org.apache.jena.rdf.model.Resource} object.
	 * @return the label (never null)
	 */
	public String getCustomizedLabel(Resource resource) {
		String label = JenaUtil.getStringProperty(resource, RDFS.label);
		if(label != null) {
			return label;
		}
		else {
			return getLabel(resource);
		}
	}
	

	/**
	 * Gets the label for a given Resource.
	 *
	 * @param resource  the Resource to get the label of
	 * @return the label (never null)
	 */
	public String getLabel(Resource resource) {
		if(resource.isURIResource() && resource.getModel() != null) {
			String qname = resource.getModel().qnameFor(resource.getURI());
			if(qname != null) {
				return qname;
			}
			else {
				return "<" + resource.getURI() + ">";
			}
		}
		else {
			return resource.toString();
		}
	}


	/**
	 * Renders a template call's label template into a label by inserting the
	 * evaluated SPARQL expressions into appropriate spaces marked with {expression}.
	 * Currently only simple variables are supported, e.g. {?test }.
	 *
	 * @param buffer  the StringBuffer to write to
	 * @param labelTemplate  the labelTemplate
	 * @param args  the arguments  a Map of pre-bound variables (supplied arguments)
	 */
	public static void appendTemplateCallLabel(StringBuffer buffer, String labelTemplate, Map<String, RDFNode> args) {
		for(int i = 0; i < labelTemplate.length(); i++) {
			if(i < labelTemplate.length() - 3 && labelTemplate.charAt(i) == '{' && labelTemplate.charAt(i + 1) == '?') {
				int varEnd = i + 2;
				while(varEnd < labelTemplate.length()) {
					if(labelTemplate.charAt(varEnd) == '}') {
						String varName = labelTemplate.substring(i + 2, varEnd);
						RDFNode varValue = args.get(varName);
						if(varValue instanceof Resource) {
							buffer.append(get().getLabel((Resource)varValue));
						}
						else if(varValue instanceof Literal) {
							buffer.append(varValue.asNode().getLiteralLexicalForm());
						}
						break;
					}
					else {
						varEnd++;
					}
				}
				i = varEnd;
			}
			else {
				buffer.append(labelTemplate.charAt(i));
			}
		}
	}
}
