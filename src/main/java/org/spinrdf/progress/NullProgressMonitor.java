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

package org.spinrdf.progress;



/**
 * A ProgressMonitor that doesn't "do" anything.
 * Support for canceling is provided via <code>setCanceled</code>.
 *
 * @author Holger Knublauch
 * @version $Id: $Id
 */
public class NullProgressMonitor implements ProgressMonitor {
	
	private boolean canceled;

	/** {@inheritDoc} */
	@Override
	public boolean isCanceled() {
		return canceled;
	}

	/** {@inheritDoc} */
	@Override
	public void beginTask(String label, int totalWork) {
	}

	/** {@inheritDoc} */
	@Override
	public void done() {
	}

	/** {@inheritDoc} */
	@Override
	public void setCanceled(boolean value) {
		this.canceled = value;
	}

	/** {@inheritDoc} */
	@Override
	public void setTaskName(String value) {
	}

	/** {@inheritDoc} */
	@Override
	public void subTask(String label) {
	}

	/** {@inheritDoc} */
	@Override
	public void worked(int amount) {
	}
}
