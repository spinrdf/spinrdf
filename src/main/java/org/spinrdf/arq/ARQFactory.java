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

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.apache.jena.graph.Node;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.riot.web.HttpOp;
import org.apache.jena.shared.PrefixMapping;
import org.apache.jena.shared.impl.PrefixMappingImpl;
import org.apache.jena.sparql.core.DatasetImpl;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.apache.jena.sparql.syntax.ElementNamedGraph;
import org.apache.jena.sparql.syntax.ElementVisitorBase;
import org.apache.jena.sparql.syntax.ElementWalker;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;
import org.spinrdf.model.print.StringPrintContext;
import org.spinrdf.model.update.Update;
import org.spinrdf.system.ExtraPrefixes;
import org.spinrdf.util.JenaUtil;
import org.spinrdf.util.SPINExpressions;


/**
 * A singleton that can create ARQ SPARQL Queries and QueryExecution
 * objects.  SPIN API users should use the provided methods here.
 */
public class ARQFactory {

	private static ARQFactory singleton = new ARQFactory();
	
	/**
	 * Caches parsable query strings for each SPIN Command or expression Node.
	 */
	private Map<Node,String> node2String = new ConcurrentHashMap<Node,String>();
	
	/**
	 * Caches Jena query objects for each SPIN Command or expression String.
	 */
	private Map<String,Query> string2Query = new ConcurrentHashMap<String,Query>();
	
	/**
	 * Caches Jena query objects for each SPIN Command or expression String.
	 */
	private Map<String,UpdateRequest> string2Update = new ConcurrentHashMap<String,UpdateRequest>();
	
	private boolean useCaches = true;
	

	/**
	 * Gets the singleton instance of this class.
	 * @return the singleton
	 */
	public static ARQFactory get() {
		return singleton;
	}
	

	/**
	 * Convenience method to get a named graph from the current ARQFactory's Dataset.
	 * @param graphURI  the URI of the graph to get
	 * @return the named graph or null
	 */
	public static Model getNamedModel(String graphURI) {
		return ARQFactory.get().getDataset(null).getNamedModel(graphURI);
	}
	
	
	/**
	 * Changes the singleton to some subclass.
	 * @param value  the new ARQFactory (not null)
	 */
	public static void set(ARQFactory value) {
		ARQFactory.singleton = value;
	}
	
	
	/**
	 * Can be overloaded to install extra things such as Lucene indices to all
	 * local QueryExecutions generated by this factory.
	 * Does nothing by default.
	 * @param qexec  the QueryExecution to modify
	 */
	protected void adjustQueryExecution(QueryExecution qexec) {
	}
	

	/**
	 * Programmatically resets any cached queries.
	 */
	public void clearCaches() {
		node2String.clear();
		string2Query.clear();
		string2Update.clear();
	}
	

	/**
	 * Converts a given SPIN Command (Query or Update) into a parsable String.
	 * This method is the recommended way of doing this task as it uses a cache
	 * to prevent duplicate computations.
	 * @param spinCommand  the SPIN Command to convert to String
	 * @return the String
	 */
	public String createCommandString(org.spinrdf.model.Command spinCommand) {
		String result = node2String.get(spinCommand.asNode());
		if(result == null) {
			String text = ARQ2SPIN.getTextOnly(spinCommand);
			if(text != null) {
				result = ARQFactory.get().createPrefixDeclarations(spinCommand.getModel()) + text;
			}
			else {
				StringPrintContext p = new StringPrintContext();
				p.setUsePrefixes(false);
				p.setPrintPrefixes(false);
				spinCommand.print(p);
				result = p.getString();
			}
			if(useCaches) {
				node2String.put(spinCommand.asNode(), result);
			}
		}
		return result;
	}
	
	
	public String createExpressionString(RDFNode expression) {
		String result = node2String.get(expression.asNode());
		if(result == null) {
			StringPrintContext p = new StringPrintContext();
			p.setUsePrefixes(false);
			SPINExpressions.printExpressionString(p, expression, false, false, expression.getModel().getGraph().getPrefixMapping());
			result = p.getString();
			if(useCaches) {
				node2String.put(expression.asNode(), result);
			}
		}
		return result;
	}
	
	
	public Query createExpressionQuery(RDFNode expression) {
		String queryString = createExpressionString(expression);
		return createExpressionQuery(queryString);
	}
	
	
	public Query createExpressionQuery(String expression) {
		Query result = string2Query.get(expression);
		if(result == null) {
			String queryString = "SELECT (" + expression + ") WHERE {}";
			result = doCreateQuery(queryString);
			if(useCaches) {
				string2Query.put(expression, result);
			}
		}
		return result;
	}
	
	
	/**
	 * Same as <code>createPrefixDeclarations(model, true)</code>.
	 * @param model  the Model to create prefix declarations for
	 * @return the prefix declarations
	 */
	public String createPrefixDeclarations(Model model) {
		return createPrefixDeclarations(model, true);
	}
	

	/**
	 * Creates SPARQL prefix declarations for a given Model.
	 * @param model  the Model to get the prefixes from
	 * @param includeExtraPrefixes  true to also include implicit prefixes like afn
	 * @return the prefix declarations
	 */
	public String createPrefixDeclarations(Model model, boolean includeExtraPrefixes) {
	    StringBuffer queryString = new StringBuffer();
	    String defaultNamespace = JenaUtil.getNsPrefixURI(model, "");
	    if(defaultNamespace != null) {
	        queryString.append("PREFIX :   <" + defaultNamespace + ">\n");
	    }
	    if(includeExtraPrefixes) {
	    	Map<String,String> extraPrefixes = ExtraPrefixes.getExtraPrefixes();
	    	for(String prefix : extraPrefixes.keySet()) {
	    		String ns = extraPrefixes.get(prefix);
		    	perhapsAppend(queryString, prefix, ns, model);
	    	}
	    }
	    Iterator<String> prefixes = model.getNsPrefixMap().keySet().iterator();
	    while (prefixes.hasNext()) {
	        String prefix = prefixes.next();
	    	String namespace = JenaUtil.getNsPrefixURI(model, prefix);
	        if(prefix.length() > 0 && namespace != null) {
	        	queryString.append("PREFIX " + prefix + ": <" + namespace + ">\n");
	        }
	    }
	    return queryString.toString();
	}
	
	
	/**
	 * Converts a SPIN Query object into a ARQ Query.
	 * This method is the recommended way for this conversion -
	 * it uses a cache to retrieve queries that it has already seen before.
	 * @param spinQuery  the SPIN query
	 * @return the ARQ Query
	 */
	public Query createQuery(org.spinrdf.model.Query spinQuery) {
		String queryString = createCommandString(spinQuery);
		return createQuery(queryString);
	}
	
	
	public Query createQuery(String queryString) {
		Query result = string2Query.get(queryString);
		if(result == null) {
			result = doCreateQuery(queryString);
			if(useCaches) {
				string2Query.put(queryString, result);
			}
		}
		return result;
	}

	
	public Query doCreateQuery(String queryString) {
		return doCreateQuery(queryString, null);
	}
	

	/**
	 * Creates the "physical" Jena Query instance.
	 * Can be overloaded to create engine-specific Query objects such as those
	 * for AllegroGraph.
	 * @param queryString  the parsable query string
	 * @param prefixMapping  an optional PrefixMapping to initialize the Query with
	 *                       (this object may be modified)
	 * @return the ARQ Query object
	 */
	protected Query doCreateQuery(String queryString, PrefixMapping prefixMapping) {
		Query query = new Query();
		if(prefixMapping != null) {
			query.setPrefixMapping(prefixMapping);
		}
	    return QueryFactory.parse(query, queryString, null, getSyntax());
	}

	
	/**
	 * Creates a new Query from a partial query (possibly lacking
	 * PREFIX declarations), using the ARQ syntax specified by <code>getSyntax</code>.
	 * @param model  the Model to operate on
	 * @param partialQuery  the (partial) query string
	 * @return the Query
	 */
	public Query createQuery(Model model, String partialQuery) {
		PrefixMapping pm = new PrefixMappingImpl();
	    String defaultNamespace = JenaUtil.getNsPrefixURI(model, "");
	    if(defaultNamespace != null) {
	        pm.setNsPrefix("", defaultNamespace);
	    }
	    Map<String,String> extraPrefixes = ExtraPrefixes.getExtraPrefixes();
	    for(String prefix : extraPrefixes.keySet()) {
	    	String ns = extraPrefixes.get(prefix);
	    	if(ns != null && pm.getNsPrefixURI(prefix) == null) {
	    		pm.setNsPrefix(prefix, ns);
	    	}
	    }
	    for(String prefix : model.getNsPrefixMap().keySet()) {
	    	String namespace = JenaUtil.getNsPrefixURI(model, prefix);
	        if(prefix.length() > 0 && namespace != null) {
	        	pm.setNsPrefix(prefix, namespace);
	        }
	    }
		return doCreateQuery(partialQuery, pm);
	}

	
	/**
	 * Creates a QueryExecution for a given Query in a given Model,
	 * with no initial bindings.
	 * The implementation basically uses Jena's QueryExecutionFactory
	 * but with the option to use different Dataset as specified by
	 * <code>getDataset(model)</code>.
	 * @param query  the Query
	 * @param model  the Model to query
	 * @return a QueryExecution
	 */
	public QueryExecution createQueryExecution(Query query, Model model) {
		return createQueryExecution(query, model, null);
	}
	
	
	/**
	 * Creates a QueryExecution for a given Query in a given Model, with
	 * some given initial bindings.
	 * The implementation basically uses Jena's QueryExecutionFactory
	 * but with the option to use different Dataset as specified by
	 * <code>getDataset(model)</code>.
	 * @param query  the Query
	 * @param model  the Model to query
	 * @param initialBinding  the initial variable bindings or null
	 * @return a QueryExecution
	 */
	public QueryExecution createQueryExecution(Query query, Model model, QuerySolution initialBinding) {
		Dataset dataset = getDataset(model);
		if(dataset != null) {
			return createQueryExecution(query, dataset, initialBinding);
		}
		else {
			QueryExecution qexec = QueryExecutionFactory.create(query, model, initialBinding);
			adjustQueryExecution(qexec);
			return qexec;
		}
	}
	
	
	public QueryExecution createQueryExecution(Query query, Dataset dataset) {
		return createQueryExecution(query, dataset, null);
	}
	
	
	public QueryExecution createQueryExecution(Query query, Dataset dataset, QuerySolution initialBinding) {
		if(!query.getGraphURIs().isEmpty() || !query.getNamedGraphURIs().isEmpty()) {
			dataset = new FromDataset(dataset, query);
		}
		QueryExecution qexec = QueryExecutionFactory.create(query, dataset, initialBinding);
		adjustQueryExecution(qexec);
		return qexec;
	}

	
	/**
	 * Creates a remote QueryExecution on a given Query.
	 * @param query  the Query to execute
	 * @return a remote QueryExecution
	 */
	public QueryEngineHTTP createRemoteQueryExecution(Query query) {
		List<String> graphURIs = query.getGraphURIs();
		return createRemoteQueryExecution(query, graphURIs);
	}
	
	
	public QueryEngineHTTP createRemoteQueryExecution(Query query, List<String> graphURIs) {
		String service = graphURIs.get(0);
		String serviceAsURI = service;
		if(service.endsWith("/sparql")) {
			serviceAsURI = service.substring(0, service.lastIndexOf('/'));
		}
		return createRemoteQueryExecution(service, query, Collections.singletonList(serviceAsURI), graphURIs, null, null);
	}
	

	public QueryEngineHTTP createRemoteQueryExecution(String service,
	                                                  Query query, 
	                                                  List<String> defaultGraphURIs, 
	                                                  List<String> namedGraphURIs, 
	                                                  String user, 
	                                                  String password) {
	    HttpClient httpClient = buildHttpClient(service, user, password);
	    QueryEngineHTTP qexec = (QueryEngineHTTP) QueryExecutionFactory.sparqlService(service, query, httpClient);
	    if( defaultGraphURIs.size() > 0 ) {
	        qexec.setDefaultGraphURIs(defaultGraphURIs);
	    }
	    if( namedGraphURIs.size() > 0 ) {
	        qexec.setNamedGraphURIs(namedGraphURIs);
	    }
	    return qexec;
	}

	public static HttpClient buildHttpClient(String serviceURI, String user, String password) {
	    if ( user == null )
	        return HttpOp.getDefaultHttpClient();
	    CredentialsProvider credsProvider = new BasicCredentialsProvider();
	    Credentials credentials = new UsernamePasswordCredentials(user, password);
	    credsProvider.setCredentials(AuthScope.ANY, credentials);
	    return HttpClients.custom()
	        .setDefaultCredentialsProvider(credsProvider)
	        .build();
	}

	public UpdateRequest createUpdateRequest(String parsableString) {
	    UpdateRequest result = string2Update.get(parsableString);
	    if(result == null) {
	        result = UpdateFactory.create(parsableString);
	        if(useCaches) {
	            string2Update.put(parsableString, result);
	        }
	    }
	    return result;
	}
	
	
	public UpdateRequest createUpdateRequest(Update spinUpdate) {
		String string = createCommandString(spinUpdate);
		return createUpdateRequest(string);
	}
	
	
	/**
	 * Specifies a Dataset that shall be used for query execution.
	 * Returns a new DatasetImpl by default but may be overloaded in subclasses.
	 * For example, TopBraid delegates this to the currently open Graphs.
	 * @param defaultModel  the default Model of the Dataset
	 * @return the Dataset or null
	 */
	public Dataset getDataset(Model defaultModel) {
		return new DatasetImpl(defaultModel);
	}
	
	
	/**
	 * Gets a list of named graphs (GRAPH elements) mentioned in a given
	 * Query.
	 * @param query  the Query to traverse
	 * @return a List of those GRAPHs
	 */
	public static List<String> getNamedGraphURIs(Query query) {
		final List<String> results = new LinkedList<String>();
		ElementWalker.walk(query.getQueryPattern(), new ElementVisitorBase() {
			@Override
			public void visit(ElementNamedGraph el) {
				Node node = el.getGraphNameNode();
				if(node != null && node.isURI()) {
					String uri = node.getURI();
					if(!results.contains(uri)) {
						results.add(uri);
					}
				}
			}
		});
		return results;
	}

	
	/**
	 * The ARQ Syntax used by default: Syntax.syntaxARQ.
	 * @return the default syntax
	 */
	public Syntax getSyntax() {
		return Syntax.syntaxARQ;
	}
	
	
	public boolean isUsingCaches() {
		return useCaches;
	}


	private static void perhapsAppend(StringBuffer queryString, String prefix, String namespace, Model model) {
		if(model.getNsPrefixURI(prefix) == null && namespace != null) {
	    	queryString.append("PREFIX ");
	    	queryString.append(prefix);
	    	queryString.append(": <");
	    	queryString.append(namespace);
	    	queryString.append(">\n");
		}
	}
	
	
	/**
	 * Tells the ARQFactory whether to use caches for the various createXY functions.
	 * These are on by default.
	 * Warning: there may be memory leaks if the first executed query of its kind keeps a reference to a
	 * E_Function which keeps a reference to a Function, and FunctionBase to a FunctionEnv with an active graph.
	 * @param value  false to switch caches off
	 */
	public void setUseCaches(boolean value) {
		this.useCaches = value;
	}
}
