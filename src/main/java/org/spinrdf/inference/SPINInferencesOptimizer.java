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

package org.spinrdf.inference;

import java.util.List;
import java.util.Map;

import org.apache.jena.rdf.model.Resource;
import org.spinrdf.util.CommandWrapper;

/**
 * An interface for objects that can pre-process a set of rules, usually to optimize
 * the performance of rule execution.
 *

 * @version $Id: $Id
 */
public interface SPINInferencesOptimizer {

	/**
	 * Takes a rule set and either returns the same rule set unchanged or a new
	 * one with refactored rules.
	 *
	 * @param class2Query  the rules to execute
	 * @return a new rule set or class2Query unchanged
	 */
	Map<Resource, List<CommandWrapper>> optimize(Map<Resource, List<CommandWrapper>> class2Query);
}
