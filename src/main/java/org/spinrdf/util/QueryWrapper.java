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

package org.spinrdf.util;

import org.apache.jena.query.Query;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.spinrdf.model.Command;


/**
 * A CommandWrapper that wraps a SPARQL query
 * (in contrast to UpdateWrapper for UPDATE requests).
 *

 * @version $Id: $Id
 */
public class QueryWrapper extends CommandWrapper {
	
	private Query query;
	
	private org.spinrdf.model.Query spinQuery;
	
	
	/**
	 * <p>Constructor for QueryWrapper.</p>
	 *
	 * @param query a {@link org.apache.jena.query.Query} object.
	 * @param source a {@link org.apache.jena.rdf.model.Resource} object.
	 * @param text a {@link java.lang.String} object.
	 * @param spinQuery a {@link org.spinrdf.model.Query} object.
	 * @param label a {@link java.lang.String} object.
	 * @param statement a {@link org.apache.jena.rdf.model.Statement} object.
	 * @param thisUnbound a boolean.
	 * @param thisDepth a {@link java.lang.Integer} object.
	 */
	public QueryWrapper(Query query, Resource source, String text, org.spinrdf.model.Query spinQuery, String label, Statement statement, boolean thisUnbound, Integer thisDepth) {
		super(source, text, label, statement, thisUnbound, thisDepth);
		this.query = query;
		this.spinQuery = spinQuery;
	}
	
	
	/**
	 * <p>Getter for the field <code>query</code>.</p>
	 *
	 * @return a {@link org.apache.jena.query.Query} object.
	 */
	public Query getQuery() {
		return query;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public Command getSPINCommand() {
		return getSPINQuery();
	}


	/**
	 * <p>getSPINQuery.</p>
	 *
	 * @return a {@link org.spinrdf.model.Query} object.
	 */
	public org.spinrdf.model.Query getSPINQuery() {
		return spinQuery;
	}
}
