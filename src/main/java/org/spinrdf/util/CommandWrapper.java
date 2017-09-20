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

import java.util.Map;

import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.spinrdf.model.Command;


/**
 * Wraps a (pre-compiled) Jena Query or UpdateRequest with its source SPIN object and
 * a human-readable string representation.
 *
 * Also needed to work around the issue of Query.equals/hashCode: Otherwise
 * multiple distinct template calls will be merged into one in HashMaps.
 *

 * @version $Id: $Id
 */
public abstract class CommandWrapper {
	
	private String label;
	
	private Resource source;
	
	private Statement statement;
	
	// Used to store the arguments if this wraps a Template call
	private Map<String,RDFNode> templateBinding;
	
	private String text;
	
	private Integer thisDepth;
	
	private boolean thisUnbound;
	
	
	/**
	 * <p>Constructor for CommandWrapper.</p>
	 *
	 * @param source a {@link org.apache.jena.rdf.model.Resource} object.
	 * @param text a {@link java.lang.String} object.
	 * @param label a {@link java.lang.String} object.
	 * @param statement a {@link org.apache.jena.rdf.model.Statement} object.
	 * @param thisUnbound a boolean.
	 * @param thisDepth a {@link java.lang.Integer} object.
	 */
	public CommandWrapper(Resource source, String text, String label, Statement statement, boolean thisUnbound, Integer thisDepth) {
		this.label = label;
		this.statement = statement;
		this.source = source;
		this.text = text;
		this.thisDepth = thisDepth;
		this.thisUnbound = thisUnbound;
	}
	
	
	/**
	 * <p>Getter for the field <code>templateBinding</code>.</p>
	 *
	 * @return a {@link java.util.Map} object.
	 */
	public Map<String,RDFNode> getTemplateBinding() {
		return templateBinding;
	}
	
	
	/**
	 * <p>Getter for the field <code>label</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getLabel() {
		return label;
	}
	
	
	/**
	 * <p>getSPINCommand.</p>
	 *
	 * @return a {@link org.spinrdf.model.Command} object.
	 */
	public abstract Command getSPINCommand();
	
	
	/**
	 * <p>Getter for the field <code>statement</code>.</p>
	 *
	 * @return a {@link org.apache.jena.rdf.model.Statement} object.
	 */
	public Statement getStatement() {
		return statement;
	}
	
	
	/**
	 * Gets the SPIN Query or template call that has created this QueryWrapper.
	 *
	 * @return the source
	 */
	public Resource getSource() {
		return source;
	}
	
	
	/**
	 * <p>Getter for the field <code>text</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getText() {
		return text;
	}
	
	
	/**
	 * Gets the maximum depth of ?this in the element tree.
	 * May be null if either not computed (?thisUnbound) or ?this does not exist.
	 *
	 * @return the max depth of ?this or null
	 */
	public Integer getThisDepth() {
		return thisDepth;
	}
	
	
	/**
	 * <p>isThisDeep.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isThisDeep() {
		return thisDepth != null && thisDepth > 1;
	}
	
	
	/**
	 * <p>isThisUnbound.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isThisUnbound() {
		return thisUnbound;
	}
	
	
	/**
	 * <p>Setter for the field <code>templateBinding</code>.</p>
	 *
	 * @param value a {@link java.util.Map} object.
	 */
	public void setTemplateBinding(Map<String,RDFNode> value) {
		this.templateBinding = value;
	}
}
