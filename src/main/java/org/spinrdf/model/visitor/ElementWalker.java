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

import java.util.List;

import org.apache.jena.rdf.model.RDFNode;
import org.spinrdf.model.*;


/**
 * An object that can be used to recursively walk through an Element
 * and the embedded expressions.
 */
public class ElementWalker implements ElementVisitor {

    private ElementVisitor elementVisitor;

    private ExpressionVisitor expressionVisitor;


    public ElementWalker(ElementVisitor elementVisitor, ExpressionVisitor expressionVisitor) {
        this.elementVisitor = elementVisitor;
        this.expressionVisitor = expressionVisitor;
    }


    public void visit(Bind bind) {
        elementVisitor.visit(bind);
        visitExpression(bind.getExpression());
    }


    public void visit(ElementList elementList) {
        elementVisitor.visit(elementList);
        visitChildren(elementList);
    }


    public void visit(Exists exists) {
        elementVisitor.visit(exists);
        visitChildren(exists);
    }


    public void visit(Filter filter) {
        elementVisitor.visit(filter);
        visitExpression(filter.getExpression());
    }


    @Override
    public void visit(Minus minus) {
        elementVisitor.visit(minus);
        visitChildren(minus);
    }


    public void visit(NamedGraph namedGraph) {
        elementVisitor.visit(namedGraph);
        visitChildren(namedGraph);
    }


    public void visit(NotExists notExists) {
        elementVisitor.visit(notExists);
        visitChildren(notExists);
    }


    public void visit(Optional optional) {
        elementVisitor.visit(optional);
        visitChildren(optional);
    }


    public void visit(Service service) {
        elementVisitor.visit(service);
        visitChildren(service);
    }


    public void visit(SubQuery subQuery) {
        elementVisitor.visit(subQuery);
    }


    public void visit(TriplePath triplePath) {
        elementVisitor.visit(triplePath);
    }


    public void visit(TriplePattern triplePattern) {
        elementVisitor.visit(triplePattern);
    }


    public void visit(Union union) {
        elementVisitor.visit(union);
        visitChildren(union);
    }


    public void visit(Values values) {
        elementVisitor.visit(values);
    }


    protected void visitChildren(ElementGroup group) {
        List<Element> childElements = group.getElements();
        for(Element childElement : childElements) {
            childElement.visit(this);
        }
    }


    private void visitExpression(RDFNode node) {
        if(expressionVisitor != null) {
            ExpressionWalker expressionWalker = new ExpressionWalker(expressionVisitor);
            ExpressionVisitors.visit(node, expressionWalker);
        }
    }


    @Override
    public void visit(Lateral lateral) {
        elementVisitor.visit(lateral);
        visitChildren(lateral);
    }
}
