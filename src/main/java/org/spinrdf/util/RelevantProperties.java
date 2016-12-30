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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDFS;
import org.spinrdf.internal.ObjectPropertiesGetter;
import org.spinrdf.model.*;
import org.spinrdf.vocabulary.SP;
import org.spinrdf.vocabulary.SPIN;


/**
 * Control logic that determines "relevant" properties for given classes or instances.
 */
public class RelevantProperties {


	private static void addProperties(QueryOrTemplateCall qot, Set<Property> results) {
		Model model = qot.getCls().getModel();
		if(qot.getTemplateCall() != null) {
			TemplateCall templateCall = qot.getTemplateCall();
			Template template = templateCall.getTemplate();
			if(template != null) {
				Command spinQuery = template.getBody();
				if(spinQuery instanceof Ask || spinQuery instanceof Construct) {
					ElementList where = ((Query)spinQuery).getWhere();
					if(where != null) {  // Gracefully ignore queries that only have sp:text
						ObjectPropertiesGetter getter = new ObjectPropertiesGetter(model, where, templateCall.getArgumentsMapByProperties());
						getter.run();
						results.addAll(getter.getResults());
					}
				}
			}
		}
		else if(qot.getQuery() instanceof Ask || qot.getQuery() instanceof Construct) {
			ElementList where = qot.getQuery().getWhere();
			if(where != null) {
				ObjectPropertiesGetter getter = new ObjectPropertiesGetter(model, where, null);
				getter.run();
				results.addAll(getter.getResults());
			}				
		}
	}
	
	
	public static Set<Property> getRelevantPropertiesOfClass(Resource cls) {
		Set<Property> results = new HashSet<Property>();
		
		JenaUtil.setGraphReadOptimization(true);
		try {

			StmtIterator it = cls.getModel().listStatements(null, RDFS.domain, cls);
			while (it.hasNext()) {
				Resource subject = it.next().getSubject();
				if (subject.isURIResource()) {
					results.add(cls.getModel().getProperty(subject.getURI()));
					JenaUtil.addDomainlessSubProperties(subject, results, new HashSet<Resource>());
				}
			}

			for(Resource superClass : JenaUtil.getSuperClasses(cls)) {
				Statement s = superClass.getProperty(OWL.onProperty);
				if(s != null && s.getObject().isURIResource()) {
					results.add(cls.getModel().getProperty(s.getResource().getURI()));
				}
			}

			Set<Property> others = RelevantProperties.getRelevantSPINPropertiesOfClass(cls);
			if(others != null) {
				for(Property other : others) {
					results.add(other);
				}
			}
		}
		finally {
			JenaUtil.setGraphReadOptimization(false);
		}
		
		return results;
	}


	public static Set<Property> getRelevantSPINPropertiesOfInstance(Resource root) {
		if(SP.exists(root.getModel())) {
			SPINInstance instance = root.as(SPINInstance.class);
			Set<Property> results = new HashSet<Property>();
			for(QueryOrTemplateCall qot : instance.getQueriesAndTemplateCalls(SPIN.constraint)) {
				addProperties(qot, results);
			}
			return results;
		}
		else {
			return null;
		}
	}


	public static Set<Property> getRelevantSPINPropertiesOfClass(Resource cls) {
		if(SP.exists(cls.getModel())) {
			List<QueryOrTemplateCall> qots = new ArrayList<QueryOrTemplateCall>();
			SPINUtil.addQueryOrTemplateCalls(cls, SPIN.constraint, qots);
			Set<Property> results = new HashSet<Property>();
			for(QueryOrTemplateCall qot : qots) {
				addProperties(qot, results);
			}
			return results;
		}
		else {
			return null;
		}
	}
}
