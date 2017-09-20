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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.jena.enhanced.EnhGraph;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.RDFList;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.engine.binding.BindingHashMap;
import org.spinrdf.model.Values;
import org.spinrdf.model.print.PrintContext;
import org.spinrdf.model.visitor.ElementVisitor;
import org.spinrdf.util.JenaUtil;
import org.spinrdf.vocabulary.SP;

/**
 * <p>ValuesImpl class.</p>
 *

 * @version $Id: $Id
 */
public class ValuesImpl extends ElementImpl implements Values {
	
	/**
	 * <p>Constructor for ValuesImpl.</p>
	 *
	 * @param node a {@link org.apache.jena.graph.Node} object.
	 * @param graph a {@link org.apache.jena.enhanced.EnhGraph} object.
	 */
	public ValuesImpl(Node node, EnhGraph graph) {
		super(node, graph);
	}

	
	/** {@inheritDoc} */
	@Override
	public List<Binding> getBindings() {
		List<String> varNames = getVarNames();
		List<Binding> bindings = new LinkedList<Binding>();
		Resource outerList = JenaUtil.getResourceProperty(this, SP.bindings);
		if(outerList != null) {
			for(RDFNode innerList : outerList.as(RDFList.class).iterator().toList()) {
				BindingHashMap binding = new BindingHashMap();
				bindings.add(binding);
				Iterator<String> vars = varNames.iterator();
				Iterator<RDFNode> values = innerList.as(RDFList.class).iterator();
				while(vars.hasNext()) {
					String varName = vars.next();
					RDFNode value = values.next();
					if(!SP.undef.equals(value)) {
						binding.add(Var.alloc(varName), value.asNode());
					}
				}
			}
		}
		return bindings;
	}

	
	/** {@inheritDoc} */
	@Override
	public List<String> getVarNames() {
		List<String> results = new LinkedList<String>();
		RDFList list = getProperty(SP.varNames).getResource().as(RDFList.class);
		for(RDFNode member : list.iterator().toList()) {
			results.add(member.asLiteral().getLexicalForm());
		}
		return results;
	}

	
	/** {@inheritDoc} */
	@Override
	public void print(PrintContext p) {
		p.printKeyword("VALUES");
		p.print(" ");
		List<String> varNames = getVarNames();
		if(varNames.size() == 1) {
			p.printVariable(varNames.get(0));
		}
		else {
			p.print("(");
			Iterator<String> vit = varNames.iterator();
			while(vit.hasNext()) {
				p.printVariable(vit.next());
				if(vit.hasNext()) {
					p.print(" ");
				}
			}
			p.print(")");
		}
		p.print(" {");
		p.println();
		for(Binding binding : getBindings()) {
			p.printIndentation(p.getIndentation() + 1);
			if(varNames.size() != 1) {
				p.print("(");
			}
			Iterator<String> vit = varNames.iterator();
			while(vit.hasNext()) {
				String varName = vit.next();
				Node value = binding.get(Var.alloc(varName));
				if(value == null) {
					p.printKeyword("UNDEF");
				}
				else if(value.isURI()) {
					p.printURIResource((Resource)getModel().asRDFNode(value)); 
				}
				else {
					TupleImpl.print(getModel(), getModel().asRDFNode(value), p);
				}
				if(vit.hasNext()) {
					p.print(" ");
				}
			}
			if(varNames.size() != 1) {
				p.print(")");
			}
			p.println();
		}
		p.printIndentation(p.getIndentation());
		p.print("}");
	}

	
	/** {@inheritDoc} */
	@Override
	public void visit(ElementVisitor visitor) {
		visitor.visit(this);
	}
}
