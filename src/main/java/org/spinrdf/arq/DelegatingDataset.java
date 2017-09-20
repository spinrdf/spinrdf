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

import java.util.Iterator;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.LabelExistsException;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.shared.Lock;
import org.apache.jena.sparql.core.DatasetGraph;
import org.apache.jena.sparql.util.Context;
import org.spinrdf.util.DatasetWrappingDatasetGraph;

/**
 * A Dataset that simply delegates all its calls, allowing to wrap an existing
 * Dataset (e.g. the TopBraid Dataset).
 *

 * @version $Id: $Id
 */
public abstract class DelegatingDataset implements Dataset {

	private Dataset delegate;
	
	/**
	 * <p>Constructor for DelegatingDataset.</p>
	 *
	 * @param delegate a {@link org.apache.jena.query.Dataset} object.
	 */
	public DelegatingDataset(Dataset delegate) {
		this.delegate = delegate;
	}

	/** {@inheritDoc} */
	@Override
	public DatasetGraph asDatasetGraph() {
		return new DatasetWrappingDatasetGraph(this);
	}

	
	/** {@inheritDoc} */
	@Override
	public void close() {
		delegate.close();
	}

	
	/** {@inheritDoc} */
	@Override
	public boolean containsNamedModel(String uri) {
		return delegate.containsNamedModel(uri);
	}

	
	/** {@inheritDoc} */
	@Override
	public Model getDefaultModel() {
		return delegate.getDefaultModel();
	}
	
	
	/**
	 * <p>Getter for the field <code>delegate</code>.</p>
	 *
	 * @return a {@link org.apache.jena.query.Dataset} object.
	 */
	public Dataset getDelegate() {
		return delegate;
	}

	
	/** {@inheritDoc} */
	@Override
	public Lock getLock() {
		return delegate.getLock();
	}

	
	/** {@inheritDoc} */
	@Override
	public Model getNamedModel(String uri) {
		return delegate.getNamedModel(uri);
	}

	
	/** {@inheritDoc} */
	@Override
	public Iterator<String> listNames() {
		return delegate.listNames();
	}

	
	/** {@inheritDoc} */
	@Override
	public void setDefaultModel(Model model) {
		delegate.setDefaultModel(model);
	}

	
	/** {@inheritDoc} */
	@Override
	public void addNamedModel(String uri, Model model)
			throws LabelExistsException {
		delegate.addNamedModel(uri, model);
	}

	
	/** {@inheritDoc} */
	@Override
	public void removeNamedModel(String uri) {
		delegate.removeNamedModel(uri);
	}

	
	/** {@inheritDoc} */
	@Override
	public void replaceNamedModel(String uri, Model model) {
		delegate.replaceNamedModel(uri, model);
	}

	
	/** {@inheritDoc} */
	@Override
	public Context getContext() {
		return delegate.getContext();
	}

	
	/** {@inheritDoc} */
	@Override
	public boolean supportsTransactions() {
		return delegate.supportsTransactions();
	}

	/** {@inheritDoc} */
	@Override
	public void begin(ReadWrite readWrite) {
		delegate.begin(readWrite);
	}

	
	/** {@inheritDoc} */
	@Override
	public void commit() {
		delegate.commit();
	}

	
	/** {@inheritDoc} */
	@Override
	public void abort() {
		delegate.abort();
	}

	
	/** {@inheritDoc} */
	@Override
	public boolean isInTransaction() {
		return delegate.isInTransaction();
	}

	
	/** {@inheritDoc} */
	@Override
	public void end() {
		delegate.end();
	}
}
