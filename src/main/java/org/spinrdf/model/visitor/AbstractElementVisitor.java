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

import org.spinrdf.model.*;


/**
 * Basic, "empty" implementation of ElementVisitor.
 *

 * @version $Id: $Id
 */
public abstract class AbstractElementVisitor implements ElementVisitor {


	/**
	 * <p>visit.</p>
	 *
	 * @param let a {@link org.spinrdf.model.Bind} object.
	 */
	public void visit(Bind let) {
	}

	
	/**
	 * <p>visit.</p>
	 *
	 * @param elementList a {@link org.spinrdf.model.ElementList} object.
	 */
	public void visit(ElementList elementList) {
	}

	
	/**
	 * <p>visit.</p>
	 *
	 * @param exists a {@link org.spinrdf.model.Exists} object.
	 */
	public void visit(Exists exists) {
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param filter a {@link org.spinrdf.model.Filter} object.
	 */
	public void visit(Filter filter) {
	}


	/** {@inheritDoc} */
	@Override
	public void visit(Minus minus) {
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param namedGraph a {@link org.spinrdf.model.NamedGraph} object.
	 */
	public void visit(NamedGraph namedGraph) {
	}
	
	
	/**
	 * <p>visit.</p>
	 *
	 * @param notExists a {@link org.spinrdf.model.NotExists} object.
	 */
	public void visit(NotExists notExists) {
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param optional a {@link org.spinrdf.model.Optional} object.
	 */
	public void visit(Optional optional) {
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param service a {@link org.spinrdf.model.Service} object.
	 */
	public void visit(Service service) {
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param subQuery a {@link org.spinrdf.model.SubQuery} object.
	 */
	public void visit(SubQuery subQuery) {
	}


	/** {@inheritDoc} */
	public void visit(TriplePath triplePath) {
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param triplePattern a {@link org.spinrdf.model.TriplePattern} object.
	 */
	public void visit(TriplePattern triplePattern) {
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param union a {@link org.spinrdf.model.Union} object.
	 */
	public void visit(Union union) {
	}


	/** {@inheritDoc} */
	@Override
	public void visit(Values values) {
	}
}
