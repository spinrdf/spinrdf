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
 */
public abstract class AbstractElementVisitor implements ElementVisitor {


	public void visit(ElementBind el) {
	}


	public void visit(ElementData el) {
	}


	public void visit(ElementExists arg0) {
	}


	public void visit(ElementNotExists arg0) {
	}


	public void visit(ElementAssign arg0) {
	}


	public void visit(ElementMinus el) {
	}


	public void visit(ElementSubQuery arg0) {
	}


	public void visit(ElementPathBlock arg0) {
	}


	public void visit(ElementTriplesBlock el) {
	}

	
	public void visit(ElementDataset dataset) {
	}
	
	
	public void visit(ElementFilter filter) {
	}

	
	public void visit(ElementGroup group) {
		for(Element element : group.getElements()) {
			element.visit(this);
		}
	}

	
	public void visit(ElementNamedGraph arg0) {
	}

	
	public void visit(ElementOptional arg0) {
	}

	
	public void visit(ElementService service) {
	}

	
	public void visit(ElementUnion arg0) {
	}
}
