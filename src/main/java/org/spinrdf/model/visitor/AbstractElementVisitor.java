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
 * Basic, "empty" implementation of ElementVisitor.
 */
public abstract class AbstractElementVisitor implements ElementVisitor {


    public void visit(Bind let) {
    }


    public void visit(ElementList elementList) {
    }


    public void visit(Exists exists) {
    }


    public void visit(Filter filter) {
    }


    @Override
    public void visit(Minus minus) {
    }


    public void visit(NamedGraph namedGraph) {
    }


    public void visit(NotExists notExists) {
    }


    public void visit(Optional optional) {
    }


    public void visit(Service service) {
    }


    public void visit(SubQuery subQuery) {
    }


    public void visit(TriplePath triplePath) {
    }


    public void visit(TriplePattern triplePattern) {
    }


    public void visit(Union union) {
    }


    @Override
    public void visit(Values values) {
    }


    @Override
    public void visit(Lateral lateral) {
    }
}
