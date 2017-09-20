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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.spinrdf.constraints.ObjectPropertyPath;
import org.spinrdf.constraints.SimplePropertyPath;
import org.spinrdf.constraints.SubjectPropertyPath;
import org.spinrdf.model.Element;
import org.spinrdf.model.SPINFactory;
import org.spinrdf.model.TriplePattern;
import org.spinrdf.model.Variable;
import org.spinrdf.model.visitor.AbstractTriplesVisitor;
import org.spinrdf.vocabulary.SP;
import org.spinrdf.vocabulary.SPIN;


/**
 * A utility that can be used to find all SimplePropertyPaths encoded in a
 * SPIN element where either subject or object is ?this.
 *

 * @version $Id: $Id
 */
public class PropertyPathsGetter extends AbstractTriplesVisitor {
	
	private Resource localThis;
	
	private Set<SimplePropertyPath> results = new HashSet<SimplePropertyPath>();
	
	private Model targetModel;
	
	
	/**
	 * <p>Constructor for PropertyPathsGetter.</p>
	 *
	 * @param element a {@link org.spinrdf.model.Element} object.
	 * @param initialBindings a {@link java.util.Map} object.
	 */
	public PropertyPathsGetter(Element element, Map<Property,RDFNode> initialBindings) {
		super(element, initialBindings);
		this.targetModel = element.getModel();
		this.localThis = SPIN._this.inModel(targetModel);
	}
	
	
	/**
	 * <p>Getter for the field <code>results</code>.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	public Set<SimplePropertyPath> getResults() {
		return results;
	}

	
	/** {@inheritDoc} */
	@Override
	protected void handleTriplePattern(TriplePattern triplePattern, Map<Property, RDFNode> bindings) {
		if(SPIN._this.equals(triplePattern.getSubject())) {
			Resource predicate = triplePattern.getPredicate();
			if(predicate != null && predicate.isURIResource()) {
				Variable variable = SPINFactory.asVariable(predicate);
				if(variable == null) {
					String uri = predicate.getURI();
					Property pred = targetModel.getProperty(uri);
					results.add(new ObjectPropertyPath(localThis, pred));
				}
				else if(bindings != null) {
					String varName = variable.getName();
					Property argProperty = targetModel.getProperty(SP.NS + varName);
					RDFNode b = bindings.get(argProperty);
					if(b != null && b.isURIResource()) {
						String uri = ((Resource)b).getURI();
						Property pred = targetModel.getProperty(uri);
						results.add(new ObjectPropertyPath(localThis, pred));
					}
				}
			}
		}
		if(SPIN._this.equals(triplePattern.getObject())) {
			Resource predicate = triplePattern.getPredicate();
			if(predicate != null && predicate.isURIResource()) {
				Variable variable = SPINFactory.asVariable(predicate);
				if(variable == null) {
					String uri = predicate.getURI();
					Property pred = targetModel.getProperty(uri);
					results.add(new SubjectPropertyPath(localThis, pred));
				}
				else if(bindings != null) {
					String varName = variable.getName();
					Property argProperty = targetModel.getProperty(SP.NS + varName);
					RDFNode b = bindings.get(argProperty);
					if(b != null && b.isURIResource()) {
						String uri = ((Resource)b).getURI();
						Property pred = targetModel.getProperty(uri);
						results.add(new SubjectPropertyPath(localThis, pred));
					}
				}
			}
		}
	}
}
