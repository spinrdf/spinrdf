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

package org.spinrdf.arq;

import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementAssign;
import org.apache.jena.sparql.syntax.ElementBind;
import org.apache.jena.sparql.syntax.ElementData;
import org.apache.jena.sparql.syntax.ElementDataset;
import org.apache.jena.sparql.syntax.ElementExists;
import org.apache.jena.sparql.syntax.ElementFilter;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.ElementMinus;
import org.apache.jena.sparql.syntax.ElementNamedGraph;
import org.apache.jena.sparql.syntax.ElementNotExists;
import org.apache.jena.sparql.syntax.ElementOptional;
import org.apache.jena.sparql.syntax.ElementPathBlock;
import org.apache.jena.sparql.syntax.ElementService;
import org.apache.jena.sparql.syntax.ElementSubQuery;
import org.apache.jena.sparql.syntax.ElementTriplesBlock;
import org.apache.jena.sparql.syntax.ElementUnion;
import org.apache.jena.sparql.syntax.ElementVisitor;


/**
 * A basic implementation of ElementVisitor that has handling of
 * ElementGroups so that they are recursively walked in.
 *

 * @version $Id: $Id
 */
public abstract class AbstractElementVisitor implements ElementVisitor {


	/**
	 * <p>visit.</p>
	 *
	 * @param el a {@link org.apache.jena.sparql.syntax.ElementBind} object.
	 */
	public void visit(ElementBind el) {
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param el a {@link org.apache.jena.sparql.syntax.ElementData} object.
	 */
	public void visit(ElementData el) {
	}


	/** {@inheritDoc} */
	public void visit(ElementExists arg0) {
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param arg0 a {@link org.apache.jena.sparql.syntax.ElementNotExists} object.
	 */
	public void visit(ElementNotExists arg0) {
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param arg0 a {@link org.apache.jena.sparql.syntax.ElementAssign} object.
	 */
	public void visit(ElementAssign arg0) {
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param el a {@link org.apache.jena.sparql.syntax.ElementMinus} object.
	 */
	public void visit(ElementMinus el) {
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param arg0 a {@link org.apache.jena.sparql.syntax.ElementSubQuery} object.
	 */
	public void visit(ElementSubQuery arg0) {
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param arg0 a {@link org.apache.jena.sparql.syntax.ElementPathBlock} object.
	 */
	public void visit(ElementPathBlock arg0) {
	}


	/**
	 * <p>visit.</p>
	 *
	 * @param el a {@link org.apache.jena.sparql.syntax.ElementTriplesBlock} object.
	 */
	public void visit(ElementTriplesBlock el) {
	}

	
	/**
	 * <p>visit.</p>
	 *
	 * @param dataset a {@link org.apache.jena.sparql.syntax.ElementDataset} object.
	 */
	public void visit(ElementDataset dataset) {
	}
	
	
	/**
	 * <p>visit.</p>
	 *
	 * @param filter a {@link org.apache.jena.sparql.syntax.ElementFilter} object.
	 */
	public void visit(ElementFilter filter) {
	}

	
	/**
	 * <p>visit.</p>
	 *
	 * @param group a {@link org.apache.jena.sparql.syntax.ElementGroup} object.
	 */
	public void visit(ElementGroup group) {
		for(Element element : group.getElements()) {
			element.visit(this);
		}
	}

	
	/**
	 * <p>visit.</p>
	 *
	 * @param arg0 a {@link org.apache.jena.sparql.syntax.ElementNamedGraph} object.
	 */
	public void visit(ElementNamedGraph arg0) {
	}

	
	/**
	 * <p>visit.</p>
	 *
	 * @param arg0 a {@link org.apache.jena.sparql.syntax.ElementOptional} object.
	 */
	public void visit(ElementOptional arg0) {
	}

	
	/**
	 * <p>visit.</p>
	 *
	 * @param service a {@link org.apache.jena.sparql.syntax.ElementService} object.
	 */
	public void visit(ElementService service) {
	}

	
	/**
	 * <p>visit.</p>
	 *
	 * @param arg0 a {@link org.apache.jena.sparql.syntax.ElementUnion} object.
	 */
	public void visit(ElementUnion arg0) {
	}
}
