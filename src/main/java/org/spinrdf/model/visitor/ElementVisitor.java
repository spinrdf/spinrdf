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
 * An interface to visit the various kinds of Elements.
 *
 * @author Holger Knublauch
 * @version $Id: $Id
 */
public interface ElementVisitor {
	
	/**
	 * <p>visit.</p>
	 *
	 * @param bind a {@link org.spinrdf.model.Bind} object.
	 */
	void visit(Bind bind);
	
	
	/**
	 * <p>visit.</p>
	 *
	 * @param elementList a {@link org.spinrdf.model.ElementList} object.
	 */
	void visit(ElementList elementList);
	
	
	/**
	 * <p>visit.</p>
	 *
	 * @param exists a {@link org.spinrdf.model.Exists} object.
	 */
	void visit(Exists exists);
	
	
	/**
	 * <p>visit.</p>
	 *
	 * @param filter a {@link org.spinrdf.model.Filter} object.
	 */
	void visit(Filter filter);
	
	
	/**
	 * <p>visit.</p>
	 *
	 * @param minus a {@link org.spinrdf.model.Minus} object.
	 */
	void visit(Minus minus);
	
	
	/**
	 * <p>visit.</p>
	 *
	 * @param namedGraph a {@link org.spinrdf.model.NamedGraph} object.
	 */
	void visit(NamedGraph namedGraph);
	
	
	/**
	 * <p>visit.</p>
	 *
	 * @param notExists a {@link org.spinrdf.model.NotExists} object.
	 */
	void visit(NotExists notExists);
	
	
	/**
	 * <p>visit.</p>
	 *
	 * @param optional a {@link org.spinrdf.model.Optional} object.
	 */
	void visit(Optional optional);

	
	/**
	 * <p>visit.</p>
	 *
	 * @param service a {@link org.spinrdf.model.Service} object.
	 */
	void visit(Service service);
	
	
	/**
	 * <p>visit.</p>
	 *
	 * @param subQuery a {@link org.spinrdf.model.SubQuery} object.
	 */
	void visit(SubQuery subQuery);
	
	
	/**
	 * <p>visit.</p>
	 *
	 * @param triplePath a {@link org.spinrdf.model.TriplePath} object.
	 */
	void visit(TriplePath triplePath);

	
	/**
	 * <p>visit.</p>
	 *
	 * @param triplePattern a {@link org.spinrdf.model.TriplePattern} object.
	 */
	void visit(TriplePattern triplePattern);
	
	
	/**
	 * <p>visit.</p>
	 *
	 * @param union a {@link org.spinrdf.model.Union} object.
	 */
	void visit(Union union);
	
	
	/**
	 * <p>visit.</p>
	 *
	 * @param values a {@link org.spinrdf.model.Values} object.
	 */
	void visit(Values values);
}
