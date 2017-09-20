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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.jena.enhanced.EnhGraph;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFList;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.spinrdf.model.Element;
import org.spinrdf.model.ElementList;
import org.spinrdf.model.SPINFactory;
import org.spinrdf.model.Variable;
import org.spinrdf.model.print.PrintContext;
import org.spinrdf.model.print.Printable;
import org.spinrdf.model.print.StringPrintContext;
import org.spinrdf.system.ExtraPrefixes;
import org.spinrdf.util.SPINExpressions;
import org.spinrdf.util.SPINUtil;
import org.spinrdf.vocabulary.SP;


/**
 * <p>Abstract AbstractSPINResourceImpl class.</p>
 *

 * @version $Id: $Id
 */
public abstract class AbstractSPINResourceImpl extends org.spinrdf.model.SPINResourceImpl implements Printable {
	
	/**
	 * One level of indentation (four spaces), used by toString methods
	 */
	public static final String INDENTATION = " ";

	
	/**
	 * <p>Constructor for AbstractSPINResourceImpl.</p>
	 *
	 * @param node a {@link org.apache.jena.graph.Node} object.
	 * @param graph a {@link org.apache.jena.enhanced.EnhGraph} object.
	 */
	public AbstractSPINResourceImpl(Node node, EnhGraph graph) {
		super(node, graph);
	}
	
	
	/**
	 * <p>getComment.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getComment() {
		return getString(RDFS.comment);
	}

	
	/**
	 * <p>getElements.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<Element> getElements() {
		return getElements(SP.elements);
	}
	

	/**
	 * <p>getElements.</p>
	 *
	 * @param predicate a {@link org.apache.jena.rdf.model.Property} object.
	 * @return a {@link java.util.List} object.
	 */
	public List<Element> getElements(Property predicate) {
		List<Element> results = new LinkedList<Element>();
		for(RDFNode node : getList(predicate)) {
			if(node != null && node.isResource()) {
				results.add(SPINFactory.asElement((Resource)node));
			}
		}
		return results;
	}

	
	/**
	 * <p>getList.</p>
	 *
	 * @param predicate a {@link org.apache.jena.rdf.model.Property} object.
	 * @return a {@link java.util.List} object.
	 */
	public List<RDFNode> getList(Property predicate) {
		Resource rawList = getResource(predicate);
		List<RDFNode> results = new LinkedList<RDFNode>();
		if(rawList != null) {
			RDFList list = rawList.as(RDFList.class);
			for(ExtendedIterator<RDFNode> it = list.iterator(); it.hasNext(); ) {
				RDFNode node = it.next();
				results.add(node);
			}
		}
		return results;
	}
	
	
	private String getPrefix(String namespace, PrintContext context) {
		String prefix = getModel().getNsURIPrefix(namespace);
		if(prefix == null && context.getUseExtraPrefixes()) {
			Map<String,String> extras = ExtraPrefixes.getExtraPrefixes();
			for(String extraPrefix : extras.keySet()) {
				String ns = extras.get(extraPrefix);
				if(namespace.equals(ns)) {
					return extraPrefix;
				}
			}
		}
		return prefix;
	}

	
	/**
	 * <p>hasRDFType.</p>
	 *
	 * @param node a {@link org.apache.jena.graph.Node} object.
	 * @param graph a {@link org.apache.jena.enhanced.EnhGraph} object.
	 * @param type a {@link org.apache.jena.rdf.model.Resource} object.
	 * @return a boolean.
	 */
	public static boolean hasRDFType(Node node, EnhGraph graph, Resource type) {
		return graph.asGraph().contains(node, RDF.type.asNode(), type.asNode());
	}
	
	
	/**
	 * <p>printComment.</p>
	 *
	 * @param context a {@link org.spinrdf.model.print.PrintContext} object.
	 */
	protected void printComment(PrintContext context) {
		String str = getComment();
		if(str != null) {
			String[] rows = str.split("\n");
			for(int i = 0; i < rows.length; i++) {
				context.print("# ");
				context.print(rows[i]);
				context.println();
			}
		}
	}


	/**
	 * <p>printNestedElementList.</p>
	 *
	 * @param p a {@link org.spinrdf.model.print.PrintContext} object.
	 */
	protected void printNestedElementList(PrintContext p) {
		printNestedElementList(p, SP.elements);
	}
	

	/**
	 * <p>printNestedElementList.</p>
	 *
	 * @param p a {@link org.spinrdf.model.print.PrintContext} object.
	 * @param predicate a {@link org.apache.jena.rdf.model.Property} object.
	 */
	protected void printNestedElementList(PrintContext p, Property predicate) {
		p.print(" {");
		p.println();
		Resource elementsRaw = getResource(predicate);
		if(elementsRaw != null) {
			ElementList elements = elementsRaw.as(ElementList.class);
			p.setIndentation(p.getIndentation() + 1);
			elements.print(p);
			p.setIndentation(p.getIndentation() - 1);
		}
		p.printIndentation(p.getIndentation());
		p.print("}");
	}

	
	/**
	 * <p>printNestedExpressionString.</p>
	 *
	 * @param context a {@link org.spinrdf.model.print.PrintContext} object.
	 * @param node a {@link org.apache.jena.rdf.model.RDFNode} object.
	 */
	protected void printNestedExpressionString(PrintContext context, RDFNode node) {
		printNestedExpressionString(context, node, false);
	}
	
	
	/**
	 * <p>printNestedExpressionString.</p>
	 *
	 * @param p a {@link org.spinrdf.model.print.PrintContext} object.
	 * @param node a {@link org.apache.jena.rdf.model.RDFNode} object.
	 * @param force a boolean.
	 */
	protected void printNestedExpressionString(PrintContext p, RDFNode node, boolean force) {
		SPINExpressions.printExpressionString(p, node, true, force, getModel().getGraph().getPrefixMapping());
	}
	
	
	/**
	 * <p>printPrefixes.</p>
	 *
	 * @param context a {@link org.spinrdf.model.print.PrintContext} object.
	 */
	protected void printPrefixes(PrintContext context) {
		if(context.getPrintPrefixes()) {
			Set<Resource> uriResources = SPINUtil.getURIResources(this);
			Set<String> namespaces = new HashSet<String>();
			for(Resource uriResource : uriResources) {
				String namespace = uriResource.getNameSpace();
				namespaces.add(namespace);
			}
			Map<String,String> prefix2Namespace = new HashMap<String,String>();
			for(String namespace : namespaces) {
				String prefix = getPrefix(namespace, context);
				if(prefix != null) {
					prefix2Namespace.put(prefix, namespace);
				}
			}
			List<String> prefixes = new ArrayList<String>(prefix2Namespace.keySet());
			Collections.sort(prefixes);
			for(String prefix : prefixes) {
				context.printKeyword("PREFIX");
				context.print(" ");
				context.print(prefix);
				context.print(": <");
				String ns = prefix2Namespace.get(prefix);
				context.print(ns);
				context.print(">");
				context.println();
			}
		}
	}

	
	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String toString() {
		StringPrintContext p = new StringPrintContext();
		print(p);
		return p.getString();
	}


	/**
	 * <p>printVarOrResource.</p>
	 *
	 * @param p a {@link org.spinrdf.model.print.PrintContext} object.
	 * @param resource a {@link org.apache.jena.rdf.model.Resource} object.
	 */
	public static void printVarOrResource(PrintContext p, Resource resource) {
		Variable variable = SPINFactory.asVariable(resource);
		if(variable != null) {
			variable.print(p);
		}
		else if(resource.isURIResource()) {
			p.printURIResource(resource);
		}
		else if(p.isNamedBNodeMode()) {
			p.print(p.getNodeToLabelMap().asString(resource.asNode()));
		}
		else {
			p.print("[]");
		}
	}
}
