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
import org.apache.jena.rdf.model.Resource;
import org.spinrdf.model.Query;
import org.spinrdf.model.SPINFactory;
import org.spinrdf.model.SubQuery;
import org.spinrdf.model.print.PrintContext;
import org.spinrdf.model.visitor.ElementVisitor;
import org.spinrdf.vocabulary.SP;



public class SubQueryImpl extends ElementImpl implements SubQuery {
    
	public SubQueryImpl(Node node, EnhGraph graph) {
		super(node, graph);
	}

	
	public Query getQuery() {
		Resource r = getResource(SP.query);
		if(r != null) {
			return SPINFactory.asQuery(r);
		}
		else {
			return null;
		}
	}

	
	public void print(PrintContext p) {
		p.print("{");
		p.println();
		Query query = getQuery();
		if(query != null) {
			p.setIndentation(p.getIndentation() + 1);
			boolean oldPP = p.getPrintPrefixes();
			p.setPrintPrefixes(false);
			query.print(p);
			p.setIndentation(p.getIndentation() - 1);
			p.setPrintPrefixes(oldPP);
		}
		else {
			p.print("<Error: Missing sub-query>");
		}
		p.println();
		p.printIndentation(p.getIndentation());
		p.print("}");
	}

	
	public void visit(ElementVisitor visitor) {
		visitor.visit(this);
	}
}
