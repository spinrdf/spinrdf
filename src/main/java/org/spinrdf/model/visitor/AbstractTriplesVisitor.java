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

package org.spinrdf.model.visitor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.spinrdf.model.*;
import org.spinrdf.system.SPINModuleRegistry;
import org.spinrdf.util.SPINUtil;
import org.spinrdf.vocabulary.SPIN;


/**
 * A utility that can be used to traverse all TriplePatterns under a given
 * root Element.  This also traverses function calls and simulates the
 * bindings of those function calls if a Function has a registered body.
 *

 * @version $Id: $Id
 */
public abstract class AbstractTriplesVisitor {
	
	// Needed to remember the bindings of a FunctionCall so that they can be substituted
	private Map<Property,RDFNode> bindings;
	
	private Element element;
	
	
	/**
	 * <p>Constructor for AbstractTriplesVisitor.</p>
	 *
	 * @param element a {@link org.spinrdf.model.Element} object.
	 * @param initialBindings a {@link java.util.Map} object.
	 */
	public AbstractTriplesVisitor(Element element, Map<Property,RDFNode> initialBindings) {
		this.bindings = initialBindings;
		this.element = element;
	}
	
	
	/**
	 * <p>run.</p>
	 */
	public void run() {
		ElementWalker walker = new ElementWalker(new MyElementVisitor(), new MyExpressionVisitor());
		element.visit(walker);
	}
	

	/**
	 * Will be called on each TriplePattern.
	 *
	 * @param triplePattern  the TriplePattern
	 * @param bindings a {@link java.util.Map} object.
	 */
	protected abstract void handleTriplePattern(TriplePattern triplePattern, Map<Property,RDFNode> bindings);

	
	// This visitor collects the relevant predicates
	private class MyElementVisitor extends AbstractElementVisitor {

		@Override
		public void visit(TriplePattern triplePattern) {
			handleTriplePattern(triplePattern, bindings);
		}
	};

	
	// This visitor walks into SPIN Function calls 
	private class MyExpressionVisitor extends AbstractExpressionVisitor {
		
		private Set<FunctionCall> reachedFunctionCalls = new HashSet<FunctionCall>();

		@Override
		public void visit(FunctionCall functionCall) {
			Resource function = functionCall.getFunction();
			if(function != null && function.isURIResource() && !reachedFunctionCalls.contains(functionCall)) {
				reachedFunctionCalls.add(functionCall);
				Resource f = SPINModuleRegistry.get().getFunction(function.getURI(), null);
				if(f != null) {
					Statement bodyS = f.getProperty(SPIN.body);
					if(bodyS != null && bodyS.getObject().isResource()) {
						
						Map<Property,RDFNode> oldBindings = bindings;
						bindings = functionCall.getArgumentsMap();
						if(oldBindings != null) {
							Map<String,RDFNode> varNamesBindings = SPINUtil.mapProperty2VarNames(oldBindings);
							SPINUtil.applyBindings(bindings, varNamesBindings);
						}
						
						Query spinQuery = SPINFactory.asQuery(bodyS.getResource());
						ElementList where = spinQuery.getWhere();
						if(where != null) {
							ElementWalker walker = new ElementWalker(new MyElementVisitor(), this);
							where.visit(walker);
						}
						
						bindings = oldBindings;
					}
				}
			}
		}
	};
}
