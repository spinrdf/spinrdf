package org.topbraid.spin;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.spinrdf.arq.ARQ2SPIN;
import org.spinrdf.arq.ARQFactory;
import org.spinrdf.model.Select;
import org.spinrdf.model.Template;
import org.spinrdf.system.SPINModuleRegistry;
import org.spinrdf.util.JenaUtil;
import org.spinrdf.util.SystemTriples;
import org.spinrdf.vocabulary.SP;
import org.spinrdf.vocabulary.SPIN;

/**
 * Creates a SPIN template containing a negated property paht and "calls" it.
 * 
 * @author Robin Keskisärkkä
 */
public class NegatedPropertyPath {

	// Query of the template - argument will be arg:predicate
	private static final String QUERY = ""
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
			+ "PREFIX owl:  <http://www.w3.org/2002/07/owl#> "
			+ "SELECT * "
			+ "WHERE { "
			+ "   owl:Restriction !rdfs:label ?o . "
			+ "}";

	public static void main(String[] args) {
		SPINModuleRegistry.get().init();

		Model model = JenaUtil.createDefaultModel();
		model.setNsPrefix("sp", SP.getURI());
		model.setNsPrefix("spin", SPIN.NS);
		Template template = createTemplate(model);
		model.write(System.out, "TTL");

		// Query the system triples
		model.add(SystemTriples.getVocabularyModel());
		org.apache.jena.query.Query arq = ARQFactory.get().createQuery((Select) template.getBody());
		QueryExecution qexec = ARQFactory.get().createQueryExecution(arq, model);
		ResultSet rs = qexec.execSelect();
		while(rs.hasNext()){
			System.out.println(rs.next());
		}
		
		
	}

	private static Template createTemplate(Model model) {
		Query arqQuery = ARQFactory.get().createQuery(model, QUERY);
		org.spinrdf.model.Query spinQuery = new ARQ2SPIN(model).createQuery(arqQuery, null);
		Template template = model.createResource(null, SPIN.Template).as(Template.class);
		template.addProperty(SPIN.body, spinQuery);
		return template;
	}
}
