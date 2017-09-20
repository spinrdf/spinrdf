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

package org.spinrdf.model.impl;

import org.apache.jena.enhanced.EnhGraph;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Statement;
import org.spinrdf.model.Template;
import org.spinrdf.vocabulary.SPIN;


/**
 * <p>TemplateImpl class.</p>
 *

 * @version $Id: $Id
 */
public class TemplateImpl extends ModuleImpl implements Template {

	/**
	 * <p>Constructor for TemplateImpl.</p>
	 *
	 * @param node a {@link org.apache.jena.graph.Node} object.
	 * @param eg a {@link org.apache.jena.enhanced.EnhGraph} object.
	 */
	public TemplateImpl(Node node, EnhGraph eg) {
		super(node, eg);
	}

	
	/**
	 * <p>getLabelTemplate.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getLabelTemplate() {
		return getString(SPIN.labelTemplate);
	}


	/** {@inheritDoc} */
	@Override
	public String getLabelTemplate(String matchLang) {
		
		if(matchLang == null || matchLang.equals("")) {
			return getLabelTemplate();
		}
		
		String label = null;
		for(Statement s : listProperties(SPIN.labelTemplate).toList()) {
			RDFNode object = s.getObject();
			if(object.isLiteral()) {
				Literal literal = (Literal)object;
				String lang = literal.getLanguage();
				if((lang.length() == 0 && label == null) || matchLang.equals(lang)) {
					label = literal.getString();
				}
			}
		}
		return label;
	}
}
