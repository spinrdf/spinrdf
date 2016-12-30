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
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.impl.PropertyImpl;
import org.apache.jena.vocabulary.RDFS;
import org.spinrdf.model.AbstractAttribute;
import org.spinrdf.model.print.PrintContext;
import org.spinrdf.vocabulary.SPL;


public abstract class AbstractAttributeImpl extends AbstractSPINResourceImpl implements AbstractAttribute {
	
	public AbstractAttributeImpl(Node node, EnhGraph eg) {
		super(node, eg);
	}

	
	public Property getPredicate() {
		Resource r = getResource(SPL.predicate);
		if(r != null && r.isURIResource()) {
			return new PropertyImpl(r.asNode(), (EnhGraph)r.getModel());
		}
		else {
			return null;
		}
	}


	public Resource getValueType() {
		return getResource(SPL.valueType);
	}
	
	
	public String getComment() {
		return getString(RDFS.comment);
	}


	public void print(PrintContext p) {
		// TODO Auto-generated method stub

	}
}
