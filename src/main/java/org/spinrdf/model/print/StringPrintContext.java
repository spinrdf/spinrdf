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

package org.spinrdf.model.print;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.graph.Graph;
import org.apache.jena.graph.compose.MultiUnion;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.impl.Util;
import org.apache.jena.shared.PrefixMapping;
import org.apache.jena.shared.impl.PrefixMappingImpl;
import org.apache.jena.sparql.util.FmtUtils;
import org.apache.jena.sparql.util.NodeToLabelMap;
import org.spinrdf.system.ExtraPrefixes;


/**
 * A simple implementation of PrintContext that operates on a StringBuilder.
 *
 * By default this is using prefixes but not extra prefixes.
 *

 * @version $Id: $Id
 */
public class StringPrintContext implements PrintContext {
	
	private static final PrefixMapping noPrefixMapping = new PrefixMappingImpl();
	
	private int indentation;
	
	private Map<String,RDFNode> initialBindings;
	
    /**
     *
     */
    protected String indentationString = "    ";
	
	private boolean namedBNodeMode;
	
	private boolean nested;
	
	private NodeToLabelMap nodeToLabelMap;
	
	private boolean printPrefixes;
	
	private StringBuilder sb;
	
	private boolean useExtraPrefixes;
	
	private boolean usePrefixes = true;
	
	
	/**
	 * <p>Constructor for StringPrintContext.</p>
	 */
	public StringPrintContext() {
		this(new StringBuilder());
	}
	
	
	/**
	 * <p>Constructor for StringPrintContext.</p>
	 *
	 * @param sb a {@link java.lang.StringBuilder} object.
	 */
	public StringPrintContext(StringBuilder sb) {
		this(sb, new HashMap<String,RDFNode>());
	}
	
	
	/**
	 * <p>Constructor for StringPrintContext.</p>
	 *
	 * @param sb a {@link java.lang.StringBuilder} object.
	 * @param initialBindings a {@link java.util.Map} object.
	 */
	public StringPrintContext(StringBuilder sb, Map<String,RDFNode> initialBindings) {
		this.sb = sb;
		this.initialBindings = initialBindings;
	}
	
	
	/**
	 * <p>clone.</p>
	 *
	 * @return a {@link org.spinrdf.model.print.PrintContext} object.
	 */
	public PrintContext clone() {
		StringPrintContext cl = new StringPrintContext(sb);
		cl.setIndentation(getIndentation());
		cl.setNested(isNested());
		cl.setUseExtraPrefixes(getUseExtraPrefixes());
		cl.setUsePrefixes(getUsePrefixes());
		cl.initialBindings = initialBindings;
		cl.sb = this.sb;
		return cl;
	}
	

	/**
	 * <p>Getter for the field <code>indentation</code>.</p>
	 *
	 * @return a int.
	 */
	public int getIndentation() {
		return indentation;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public RDFNode getInitialBinding(String varName) {
		return initialBindings.get(varName);
	}


	/**
	 * <p>Getter for the field <code>nodeToLabelMap</code>.</p>
	 *
	 * @return a {@link org.apache.jena.sparql.util.NodeToLabelMap} object.
	 */
	public NodeToLabelMap getNodeToLabelMap() {
		if(nodeToLabelMap == null) {
			nodeToLabelMap = new NodeToLabelMap();
		}
		return nodeToLabelMap;
	}


	/**
	 * <p>Getter for the field <code>printPrefixes</code>.</p>
	 *
	 * @return a boolean.
	 */
	public boolean getPrintPrefixes() {
		return printPrefixes;
	}


	/**
	 * <p>getString.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getString() {
		return sb.toString();
	}
	
	
	/**
	 * <p>getStringBuilder.</p>
	 *
	 * @return a {@link java.lang.StringBuilder} object.
	 */
	public StringBuilder getStringBuilder() {
		return sb;
	}


	/**
	 * <p>Getter for the field <code>useExtraPrefixes</code>.</p>
	 *
	 * @return a boolean.
	 */
	public boolean getUseExtraPrefixes() {
		return useExtraPrefixes;
	}
	
	
	/**
	 * <p>Getter for the field <code>usePrefixes</code>.</p>
	 *
	 * @return a boolean.
	 */
	public boolean getUsePrefixes() {
		return usePrefixes;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public boolean hasInitialBindings() {
		return initialBindings != null && !initialBindings.isEmpty();
	}


	/** {@inheritDoc} */
	@Override
	public boolean isNamedBNodeMode() {
		return namedBNodeMode;
	}


	/**
	 * <p>isNested.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isNested() {
		return nested;
	}
	
	
	/** {@inheritDoc} */
	public void print(String str) {
		sb.append(str.toString());
	}


	/** {@inheritDoc} */
	public void printIndentation(int depth) {
		for(int i = 0; i < depth; i++) {
			print(indentationString);
		}
	}


	/** {@inheritDoc} */
	public void printKeyword(String str) {
		print(str);
	}


	/**
	 * <p>println.</p>
	 */
	public void println() {
		print("\n");
	}


	/** {@inheritDoc} */
	public void printVariable(String str) {
		RDFNode binding = getInitialBinding(str);
		if(binding == null || binding.isAnon()) {
			print("?" + str);
		}
		else if(binding.isURIResource()) {
			printURIResource((Resource)binding);
		}
		else {
			String lit = FmtUtils.stringForNode(binding.asNode(), noPrefixMapping);
			print(lit);
		}
	}


	/** {@inheritDoc} */
	public void printURIResource(Resource resource) {
		if(getUsePrefixes()) {
			String qname = qnameFor(resource);
			if(qname != null) {
				print(qname);
				return;
			}
			else if(getUseExtraPrefixes()) {
				Map<String,String> extras = ExtraPrefixes.getExtraPrefixes();
				for(String prefix : extras.keySet()) {
					String ns = extras.get(prefix);
					if(resource.getURI().startsWith(ns)) {
						print(prefix);
						print(":");
						print(resource.getURI().substring(ns.length()));
						return;
					}
				}
			}
		}
		print("<");
		print(resource.getURI());
		print(">");
	}
	

	/**
	 * Work-around for a bug in Jena: Jena would use the default
	 * namespace of an imported Graph in a MultiUnion.
	 *
	 * @param resource  the Resource to get the qname for
	 * @return the qname or null
	 */
	public static String qnameFor(Resource resource) {
		Graph graph = resource.getModel().getGraph();
		if(graph instanceof MultiUnion) {
			String uri = resource.getURI();
	        int split = Util.splitNamespaceXML(uri);
	        String local = uri.substring(split);
	        if (local.length() == 0) {
	        	return null;
	        }
	        String ns = uri.substring(0, split);
	        
	        MultiUnion mu = (MultiUnion) graph;
			Graph baseGraph = mu.getBaseGraph();
			if(baseGraph != null) {
				String prefix = baseGraph.getPrefixMapping().getNsURIPrefix(ns);
				if(prefix != null) {
					return prefix + ":" + local;
				}
			}
            List<Graph> graphs = mu.getSubGraphs();
            for (int i = 0; i < graphs.size(); i++) {
            	Graph subGraph = graphs.get(i);
            	String prefix = subGraph.getPrefixMapping().getNsURIPrefix(ns);
            	if(prefix != null && prefix.length() > 0) {
                	return prefix + ":" + local;
                }
            }
            return null;
		}
		else {
			return resource.getModel().qnameFor(resource.getURI());
		}
	}


	/** {@inheritDoc} */
	public void setIndentation(int value) {
		this.indentation = value;
	}
	
	
	/**
	 * <p>Setter for the field <code>indentationString</code>.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	public void setIndentationString(String value) {
		this.indentationString = value;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setNamedBNodeMode(boolean value) {
		this.namedBNodeMode = value;
	}


	/** {@inheritDoc} */
	public void setNested(boolean value) {
		this.nested = value;
	}


	/** {@inheritDoc} */
	public void setPrintPrefixes(boolean value) {
		this.printPrefixes = value;
	}


	/** {@inheritDoc} */
	public void setUseExtraPrefixes(boolean value) {
		this.useExtraPrefixes = value;
	}
	
	
	/** {@inheritDoc} */
	public void setUsePrefixes(boolean value) {
		this.usePrefixes = value;
	}
}
