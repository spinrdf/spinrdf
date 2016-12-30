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

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.update.Update;
import org.spinrdf.model.Command;


/**
 * A CommandWrapper that wraps SPARQL UPDATE requests
 * (in contrast to QueryWrapper for SPARQL queries).
 */
public class UpdateWrapper extends CommandWrapper {
	
	private Update update;
	
	private org.spinrdf.model.update.Update spinUpdate;
	
	
	public UpdateWrapper(Update update, Resource source, String text, org.spinrdf.model.update.Update spinUpdate, String label, Statement statement, boolean thisUnbound, Integer thisDepth) {
		super(source, text, label, statement, thisUnbound, thisDepth);
		this.update = update;
		this.spinUpdate = spinUpdate;
	}
	
	
	public Update getUpdate() {
		return update;
	}
	
	
	@Override
	public Command getSPINCommand() {
		return getSPINUpdate();
	}


	public org.spinrdf.model.update.Update getSPINUpdate() {
		return spinUpdate;
	}
}
