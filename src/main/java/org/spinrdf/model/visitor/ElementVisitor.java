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

package org.spinrdf.model.visitor;

import org.spinrdf.model.*;


/**
 * An interface to visit the various kinds of Elements.
 * 
 * @author Holger Knublauch
 */
public interface ElementVisitor {
	
	void visit(Bind bind);
	
	
	void visit(ElementList elementList);
	
	
	void visit(Exists exists);
	
	
	void visit(Filter filter);
	
	
	void visit(Minus minus);
	
	
	void visit(NamedGraph namedGraph);
	
	
	void visit(NotExists notExists);
	
	
	void visit(Optional optional);

	
	void visit(Service service);
	
	
	void visit(SubQuery subQuery);
	
	
	void visit(TriplePath triplePath);

	
	void visit(TriplePattern triplePattern);
	
	
	void visit(Union union);
	
	
	void visit(Values values);
}
