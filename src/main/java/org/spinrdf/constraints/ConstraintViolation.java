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

package org.spinrdf.constraints;

import java.util.Collection;

import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.spinrdf.model.TemplateCall;
import org.spinrdf.vocabulary.SPIN;


/**
 * An object representing a failure of a SPIN constraint.
 *

 * @version $Id: $Id
 */
public class ConstraintViolation {
	
	private Collection<TemplateCall> fixes;
	
	private Resource level;
	
	private String message;
	
	private Collection<SimplePropertyPath> paths;
	
	private Resource root;
	
	private Resource source;
	
	private RDFNode value;
	
	
	/**
	 * Constructs a new ConstraintViolation.
	 *
	 * @param root  the root resource of the violation
	 * @param paths  the paths (may be empty)
	 * @param fixes  potential fixes for the violations (may be empty)
	 * @param message  the message explaining the error
	 * @param source  the SPIN Query or template call that has caused this violation
	 *                (may be null)
	 */
	public ConstraintViolation(Resource root, 
				Collection<SimplePropertyPath> paths,
				Collection<TemplateCall> fixes,
				String message,
				Resource source) {
		this.fixes = fixes;
		this.message = message;
		this.root = root;
		this.paths = paths;
		this.source = source;
	}
	
	
	/**
	 * <p>Getter for the field <code>fixes</code>.</p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	public Collection<TemplateCall> getFixes() {
		return fixes;
	}
	
	
	/**
	 * <p>Getter for the field <code>level</code>.</p>
	 *
	 * @return a {@link org.apache.jena.rdf.model.Resource} object.
	 */
	public Resource getLevel() {
		return level == null ? SPIN.Error : level;
	}
	
	
	/**
	 * <p>Getter for the field <code>message</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getMessage() {
		return message;
	}
	
	
	/**
	 * <p>Getter for the field <code>paths</code>.</p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	public Collection<SimplePropertyPath> getPaths() {
		return paths;
	}
	

	/**
	 * <p>Getter for the field <code>root</code>.</p>
	 *
	 * @return a {@link org.apache.jena.rdf.model.Resource} object.
	 */
	public Resource getRoot() {
		return root;
	}
	
	
	/**
	 * Gets the SPIN Query or template call that has caused this violation.
	 *
	 * @return the source (code should be robust against null values)
	 */
	public Resource getSource() {
		return source;
	}
	
	
	/**
	 * <p>Getter for the field <code>value</code>.</p>
	 *
	 * @return a {@link org.apache.jena.rdf.model.RDFNode} object.
	 */
	public RDFNode getValue() {
		return value;
	}
	

	/**
	 * Checks if this represents an Error or Fatal.
	 *
	 * @return true if Error or Fatal
	 */
	public boolean isError() {
		return SPIN.Error.equals(getLevel()) || SPIN.Fatal.equals(getLevel());
	}
	
	
	/**
	 * <p>Setter for the field <code>level</code>.</p>
	 *
	 * @param level a {@link org.apache.jena.rdf.model.Resource} object.
	 */
	public void setLevel(Resource level) {
		this.level = level;
	}
	
	
	/**
	 * <p>Setter for the field <code>value</code>.</p>
	 *
	 * @param value a {@link org.apache.jena.rdf.model.RDFNode} object.
	 */
	public void setValue(RDFNode value) {
		this.value = value;
	}
}
