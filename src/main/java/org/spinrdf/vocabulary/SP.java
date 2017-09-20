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

package org.spinrdf.vocabulary;

import java.io.InputStream;

import org.apache.jena.enhanced.BuiltinPersonalities;
import org.apache.jena.enhanced.Personality;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFList;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.util.FileUtils;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.spinrdf.model.*;
import org.spinrdf.model.impl.*;
import org.spinrdf.model.update.*;
import org.spinrdf.model.update.impl.*;
import org.spinrdf.util.SimpleImplementation;
import org.spinrdf.util.SimpleImplementation2;

/**
 * Vocabulary of the SPIN SPARQL Syntax schema.
 *

 * @version $Id: $Id
 */
public class SP {

    /** Constant <code>BASE_URI="http://spinrdf.org/sp"</code> */
    public final static String BASE_URI = "http://spinrdf.org/sp";

    /** Constant <code>NS="BASE_URI + #"</code> */
    public final static String NS = BASE_URI + "#";

    /** Constant <code>PREFIX="sp"</code> */
    public final static String PREFIX = "sp";
    
    /** Constant <code>VAR_NS="http://spinrdf.org/var#"</code> */
    public final static String VAR_NS = "http://spinrdf.org/var#";

    /** Constant <code>VAR_PREFIX="var"</code> */
    public final static String VAR_PREFIX = "var";


    /** Constant <code>Aggregation</code> */
    public final static Resource Aggregation = ResourceFactory.createResource(NS + "Aggregation");

    /** Constant <code>AltPath</code> */
    public final static Resource AltPath = ResourceFactory.createResource(NS + "AltPath");

    /** Constant <code>Asc</code> */
    public final static Resource Asc = ResourceFactory.createResource(NS + "Asc");

    /** Constant <code>Ask</code> */
    public final static Resource Ask = ResourceFactory.createResource(NS + "Ask");

    /** Constant <code>Avg</code> */
    public final static Resource Avg = ResourceFactory.createResource(NS + "Avg");

    /** Constant <code>Bind</code> */
    public final static Resource Bind = ResourceFactory.createResource(NS + "Bind");

    /** Constant <code>Clear</code> */
    public final static Resource Clear = ResourceFactory.createResource(NS + "Clear");

    /** Constant <code>Command</code> */
    public final static Resource Command = ResourceFactory.createResource(NS + "Command");

    /** Constant <code>Construct</code> */
    public final static Resource Construct = ResourceFactory.createResource(NS + "Construct");

    /** Constant <code>Count</code> */
    public final static Resource Count = ResourceFactory.createResource(NS + "Count");

    /** Constant <code>Create</code> */
    public final static Resource Create = ResourceFactory.createResource(NS + "Create");

    /** Constant <code>Delete</code> */
    @Deprecated
    public final static Resource Delete = ResourceFactory.createResource(NS + "Delete");

    /** Constant <code>DeleteData</code> */
    public final static Resource DeleteData = ResourceFactory.createResource(NS + "DeleteData");

    /** Constant <code>DeleteWhere</code> */
    public final static Resource DeleteWhere = ResourceFactory.createResource(NS + "DeleteWhere");

    /** Constant <code>Desc</code> */
    public final static Resource Desc = ResourceFactory.createResource(NS + "Desc");

    /** Constant <code>Describe</code> */
    public final static Resource Describe = ResourceFactory.createResource(NS + "Describe");

    /** Constant <code>Drop</code> */
    public final static Resource Drop = ResourceFactory.createResource(NS + "Drop");

    /** Constant <code>exists</code> */
    public final static Resource exists = ResourceFactory.createResource(NS + "exists");

    /** Constant <code>Exists</code> */
    public final static Resource Exists = ResourceFactory.createResource(NS + "Exists");

    /** Constant <code>Expression</code> */
    public final static Resource Expression = ResourceFactory.createResource(NS + "Expression");

    /** Constant <code>Filter</code> */
    public final static Resource Filter = ResourceFactory.createResource(NS + "Filter");

    /** Constant <code>Insert</code> */
    @Deprecated
    public final static Resource Insert = ResourceFactory.createResource(NS + "Insert");

    /** Constant <code>InsertData</code> */
    public final static Resource InsertData = ResourceFactory.createResource(NS + "InsertData");

    /** Constant <code>Let</code> */
    @Deprecated
    public final static Resource Let = ResourceFactory.createResource(NS + "Let");

    /** Constant <code>Load</code> */
    public final static Resource Load = ResourceFactory.createResource(NS + "Load");

    /** Constant <code>Max</code> */
    public final static Resource Max = ResourceFactory.createResource(NS + "Max");

    /** Constant <code>Min</code> */
    public final static Resource Min = ResourceFactory.createResource(NS + "Min");

    /** Constant <code>Modify</code> */
    public final static Resource Modify = ResourceFactory.createResource(NS + "Modify");

    /** Constant <code>ModPath</code> */
    public final static Resource ModPath = ResourceFactory.createResource(NS + "ModPath");

    /** Constant <code>Minus</code> */
    public final static Resource Minus = ResourceFactory.createResource(NS + "Minus");

    /** Constant <code>NamedGraph</code> */
    public final static Resource NamedGraph = ResourceFactory.createResource(NS + "NamedGraph");

    /** Constant <code>notExists</code> */
    public final static Resource notExists = ResourceFactory.createResource(NS + "notExists");

    /** Constant <code>NotExists</code> */
    public final static Resource NotExists = ResourceFactory.createResource(NS + "NotExists");

    /** Constant <code>Optional</code> */
    public final static Resource Optional = ResourceFactory.createResource(NS + "Optional");

    /** Constant <code>Query</code> */
    public final static Resource Query = ResourceFactory.createResource(NS + "Query");

    /** Constant <code>ReverseLinkPath</code> */
    public final static Resource ReverseLinkPath = ResourceFactory.createResource(NS + "ReverseLinkPath");

    /** Constant <code>ReversePath</code> */
    public final static Resource ReversePath = ResourceFactory.createResource(NS + "ReversePath");

    /** Constant <code>Select</code> */
    public final static Resource Select = ResourceFactory.createResource(NS + "Select");

    /** Constant <code>Service</code> */
    public final static Resource Service = ResourceFactory.createResource(NS + "Service");

    /** Constant <code>SeqPath</code> */
    public final static Resource SeqPath = ResourceFactory.createResource(NS + "SeqPath");

    /** Constant <code>SubQuery</code> */
    public final static Resource SubQuery = ResourceFactory.createResource(NS + "SubQuery");

    /** Constant <code>Sum</code> */
    public final static Resource Sum = ResourceFactory.createResource(NS + "Sum");

    /** Constant <code>Triple</code> */
    public final static Resource Triple = ResourceFactory.createResource(NS + "Triple");

    /** Constant <code>TriplePath</code> */
    public final static Resource TriplePath = ResourceFactory.createResource(NS + "TriplePath");

    /** Constant <code>TriplePattern</code> */
    public final static Resource TriplePattern = ResourceFactory.createResource(NS + "TriplePattern");

    /** Constant <code>TripleTemplate</code> */
    public final static Resource TripleTemplate = ResourceFactory.createResource(NS + "TripleTemplate");

    /** Constant <code>undef</code> */
    public final static Resource undef = ResourceFactory.createResource(NS + "undef");

    /** Constant <code>Union</code> */
    public final static Resource Union = ResourceFactory.createResource(NS + "Union");

    /** Constant <code>Update</code> */
    public final static Resource Update = ResourceFactory.createResource(NS + "Update");

    /** Constant <code>Values</code> */
    public final static Resource Values = ResourceFactory.createResource(NS + "Values");

    /** Constant <code>Variable</code> */
    public final static Resource Variable = ResourceFactory.createResource(NS + "Variable");


	/** Constant <code>all</code> */
	public final static Property all = ResourceFactory.createProperty(NS + "all");

	/** Constant <code>arg</code> */
	public final static Property arg = ResourceFactory.createProperty(NS + "arg");

	/** Constant <code>arg1</code> */
	public final static Property arg1 = ResourceFactory.createProperty(NS + "arg1");

	/** Constant <code>arg2</code> */
	public final static Property arg2 = ResourceFactory.createProperty(NS + "arg2");

	/** Constant <code>arg3</code> */
	public final static Property arg3 = ResourceFactory.createProperty(NS + "arg3");

	/** Constant <code>arg4</code> */
	public final static Property arg4 = ResourceFactory.createProperty(NS + "arg4");

	/** Constant <code>arg5</code> */
	public final static Property arg5 = ResourceFactory.createProperty(NS + "arg5");
    
    /** Constant <code>as</code> */
    public final static Property as = ResourceFactory.createProperty(NS + "as");
    
    /** Constant <code>bindings</code> */
    public final static Property bindings = ResourceFactory.createProperty(NS + "bindings");

    /** Constant <code>data</code> */
    public final static Property data = ResourceFactory.createProperty(NS + "data");

	/** Constant <code>default_</code> */
	public final static Property default_ = ResourceFactory.createProperty(NS + "default");
    
    /** Constant <code>deletePattern</code> */
    public final static Property deletePattern = ResourceFactory.createProperty(NS + "deletePattern");
    
    /** Constant <code>distinct</code> */
    public final static Property distinct = ResourceFactory.createProperty(NS + "distinct");
    
    /** Constant <code>document</code> */
    public final static Property document = ResourceFactory.createProperty(NS + "document");
    
    /** Constant <code>elements</code> */
    public final static Property elements = ResourceFactory.createProperty(NS + "elements");
    
    /** Constant <code>expression</code> */
    public final static Property expression = ResourceFactory.createProperty(NS + "expression");
    
    /** Constant <code>from</code> */
    public final static Property from = ResourceFactory.createProperty(NS + "from");
    
    /** Constant <code>fromNamed</code> */
    public final static Property fromNamed = ResourceFactory.createProperty(NS + "fromNamed");

    /** Constant <code>graphIRI</code> */
    public final static Property graphIRI = ResourceFactory.createProperty(NS + "graphIRI");
    
    /** Constant <code>graphNameNode</code> */
    public final static Property graphNameNode = ResourceFactory.createProperty(NS + "graphNameNode");
    
    /** Constant <code>groupBy</code> */
    public final static Property groupBy = ResourceFactory.createProperty(NS + "groupBy");
    
    /** Constant <code>having</code> */
    public final static Property having = ResourceFactory.createProperty(NS + "having");
    
    /** Constant <code>insertPattern</code> */
    public final static Property insertPattern = ResourceFactory.createProperty(NS + "insertPattern");
    
    /** Constant <code>into</code> */
    public final static Property into = ResourceFactory.createProperty(NS + "into");
    
    /** Constant <code>limit</code> */
    public final static Property limit = ResourceFactory.createProperty(NS + "limit");
    
    /** Constant <code>modMax</code> */
    public final static Property modMax = ResourceFactory.createProperty(NS + "modMax");
    
    /** Constant <code>modMin</code> */
    public final static Property modMin = ResourceFactory.createProperty(NS + "modMin");

	/** Constant <code>named</code> */
	public final static Property named = ResourceFactory.createProperty(NS + "named");
    
    /** Constant <code>node</code> */
    public final static Property node = ResourceFactory.createProperty(NS + "node");
    
    /** Constant <code>object</code> */
    public final static Property object = ResourceFactory.createProperty(NS + "object");
    
    /** Constant <code>offset</code> */
    public final static Property offset = ResourceFactory.createProperty(NS + "offset");
    
    /** Constant <code>orderBy</code> */
    public final static Property orderBy = ResourceFactory.createProperty(NS + "orderBy");
    
    /** Constant <code>path</code> */
    public final static Property path = ResourceFactory.createProperty(NS + "path");
    
    /** Constant <code>path1</code> */
    public final static Property path1 = ResourceFactory.createProperty(NS + "path1");
    
    /** Constant <code>path2</code> */
    public final static Property path2 = ResourceFactory.createProperty(NS + "path2");

    /** Constant <code>predicate</code> */
    public final static Property predicate = ResourceFactory.createProperty(NS + "predicate");
    
    /** Constant <code>query</code> */
    public final static Property query = ResourceFactory.createProperty(NS + "query");

    /** Constant <code>reduced</code> */
    public final static Property reduced = ResourceFactory.createProperty(NS + "reduced");

    /** Constant <code>resultNodes</code> */
    public final static Property resultNodes = ResourceFactory.createProperty(NS + "resultNodes");

    /** Constant <code>resultVariables</code> */
    public final static Property resultVariables = ResourceFactory.createProperty(NS + "resultVariables");
    
    /** Constant <code>separator</code> */
    public final static Property separator = ResourceFactory.createProperty(NS + "separator");
    
    /** Constant <code>serviceURI</code> */
    public final static Property serviceURI = ResourceFactory.createProperty(NS + "serviceURI");
    
    /** Constant <code>silent</code> */
    public final static Property silent = ResourceFactory.createProperty(NS + "silent");

	/** Constant <code>str</code> */
	public final static Property str = ResourceFactory.createProperty(NS + "str");

	/** Constant <code>strlang</code> */
	public final static Property strlang = ResourceFactory.createProperty(NS + "strlang");

    /** Constant <code>subject</code> */
    public final static Property subject = ResourceFactory.createProperty(NS + "subject");

    /** Constant <code>subPath</code> */
    public final static Property subPath = ResourceFactory.createProperty(NS + "subPath");

    /** Constant <code>templates</code> */
    public final static Property templates = ResourceFactory.createProperty(NS + "templates");

    /** Constant <code>text</code> */
    public final static Property text = ResourceFactory.createProperty(NS + "text");
    
    /** Constant <code>using</code> */
    public final static Property using = ResourceFactory.createProperty(NS + "using");
    
    /** Constant <code>usingNamed</code> */
    public final static Property usingNamed = ResourceFactory.createProperty(NS + "usingNamed");
    
    /** Constant <code>values</code> */
    public final static Property values = ResourceFactory.createProperty(NS + "values");

    /** Constant <code>variable</code> */
    public final static Property variable = ResourceFactory.createProperty(NS + "variable");
    
    /** Constant <code>varName</code> */
    public final static Property varName = ResourceFactory.createProperty(NS + "varName");
    
    /** Constant <code>varNames</code> */
    public final static Property varNames = ResourceFactory.createProperty(NS + "varNames");
    
    /** Constant <code>where</code> */
    public final static Property where = ResourceFactory.createProperty(NS + "where");
    
    /** Constant <code>with</code> */
    public final static Property with = ResourceFactory.createProperty(NS + "with");
    
    
    /** Constant <code>bound</code> */
    public final static Resource bound = ResourceFactory.createResource(NS + "bound");
    
    /** Constant <code>eq</code> */
    public final static Resource eq = ResourceFactory.createResource(NS + "eq");
    
    /** Constant <code>not</code> */
    public final static Resource not = ResourceFactory.createResource(NS + "not");

	/** Constant <code>regex</code> */
	public final static Resource regex = ResourceFactory.createResource(NS + "regex");

	/** Constant <code>sub</code> */
	public final static Resource sub = ResourceFactory.createResource(NS + "sub");

	/** Constant <code>unaryMinus</code> */
	public final static Resource unaryMinus = ResourceFactory.createResource(NS + "unaryMinus");
	
	
	private static Model model;
	

	/**
	 * Gets a Model with the content of the SP namespace, from a file
	 * that is bundled with this API.
	 *
	 * @return the namespace Model
	 */
	public static synchronized Model getModel() {
		if(model == null) {
			model = ModelFactory.createDefaultModel();
			InputStream is = SP.class.getResourceAsStream("/etc/sp.ttl");
			if(is == null) {
				model.read(SP.BASE_URI);
			}
			else {
				model.read(is, "http://dummy", FileUtils.langTurtle);
			}
		}
		return model;
	}

    
    static {
		SP.init(BuiltinPersonalities.model);
    }

    
    @SuppressWarnings("deprecation")
	private static void init(Personality<RDFNode> p) {
    	p.add(Aggregation.class, new SimpleImplementation(SPL.Argument.asNode(), AggregationImpl.class));
    	p.add(Argument.class, new SimpleImplementation(SPL.Argument.asNode(), ArgumentImpl.class));
    	p.add(Attribute.class, new SimpleImplementation(SPL.Attribute.asNode(), AttributeImpl.class));
    	p.add(Ask.class, new SimpleImplementation(Ask.asNode(), AskImpl.class));
    	p.add(Bind.class, new SimpleImplementation2(Bind.asNode(), Let.asNode(), BindImpl.class));
    	p.add(Clear.class, new SimpleImplementation(Clear.asNode(), ClearImpl.class));
    	p.add(Construct.class, new SimpleImplementation(Construct.asNode(), ConstructImpl.class));
    	p.add(Create.class, new SimpleImplementation(Create.asNode(), CreateImpl.class));
    	p.add(org.spinrdf.model.update.Delete.class, new SimpleImplementation(Delete.asNode(), org.spinrdf.model.update.impl.DeleteImpl.class));
    	p.add(DeleteData.class, new SimpleImplementation(DeleteData.asNode(), DeleteDataImpl.class));
    	p.add(DeleteWhere.class, new SimpleImplementation(DeleteWhere.asNode(), DeleteWhereImpl.class));
    	p.add(Describe.class, new SimpleImplementation(Describe.asNode(), DescribeImpl.class));
    	p.add(Drop.class, new SimpleImplementation(Drop.asNode(), DropImpl.class));
    	p.add(ElementList.class, new SimpleImplementation(RDF.List.asNode(), ElementListImpl.class));
    	p.add(Exists.class, new SimpleImplementation(Exists.asNode(), ExistsImpl.class));
    	p.add(Function.class, new SimpleImplementation(SPIN.Function.asNode(), FunctionImpl.class));
    	p.add(FunctionCall.class, new SimpleImplementation(SPIN.Function.asNode(), FunctionCallImpl.class));
    	p.add(Filter.class, new SimpleImplementation(Filter.asNode(), FilterImpl.class));
    	p.add(org.spinrdf.model.update.Insert.class, new SimpleImplementation(Insert.asNode(), org.spinrdf.model.update.impl.InsertImpl.class));
    	p.add(InsertData.class, new SimpleImplementation(InsertData.asNode(), InsertDataImpl.class));
    	p.add(Load.class, new SimpleImplementation(Load.asNode(), LoadImpl.class));
    	p.add(Minus.class, new SimpleImplementation(Minus.asNode(), MinusImpl.class));
    	p.add(Modify.class, new SimpleImplementation(Modify.asNode(), ModifyImpl.class));
    	p.add(Module.class, new SimpleImplementation(SPIN.Module.asNode(), ModuleImpl.class));
    	p.add(NamedGraph.class, new SimpleImplementation(NamedGraph.asNode(), NamedGraphImpl.class));
    	p.add(NotExists.class, new SimpleImplementation(NotExists.asNode(), NotExistsImpl.class));
    	p.add(Optional.class, new SimpleImplementation(Optional.asNode(), OptionalImpl.class));
    	p.add(Service.class, new SimpleImplementation(Service.asNode(), ServiceImpl.class));
    	p.add(Select.class, new SimpleImplementation(Select.asNode(), SelectImpl.class));
    	p.add(SubQuery.class, new SimpleImplementation(SubQuery.asNode(), SubQueryImpl.class));
    	p.add(SPINInstance.class, new SimpleImplementation(RDFS.Resource.asNode(), SPINInstanceImpl.class));
    	p.add(Template.class, new SimpleImplementation(SPIN.Template.asNode(), TemplateImpl.class));
    	p.add(TemplateCall.class, new SimpleImplementation(RDFS.Resource.asNode(), TemplateCallImpl.class));
    	p.add(TriplePath.class, new SimpleImplementation(TriplePath.asNode(), TriplePathImpl.class));
    	p.add(TriplePattern.class, new SimpleImplementation(TriplePattern.asNode(), TriplePatternImpl.class));
    	p.add(TripleTemplate.class, new SimpleImplementation(TripleTemplate.asNode(), TripleTemplateImpl.class));
    	p.add(Union.class, new SimpleImplementation(Union.asNode(), UnionImpl.class));
    	p.add(Values.class, new SimpleImplementation(Values.asNode(), ValuesImpl.class));
    	p.add(Variable.class, new SimpleImplementation(Variable.asNode(), VariableImpl.class));
    }
    
    
    /**
     * Checks whether the SP ontology is used in a given Model.
     * This is true if the model defines the SP namespace prefix
     * and also has sp:Query defined with an rdf:type.
     * The goal of this call is to be very fast when SP is not
     * imported, i.e. it checks the namespace first and can then
     * omit the type query.
     *
     * @param model  the Model to check
     * @return true if SP exists in model
     */
    public static boolean exists(Model model) {
    	return model != null &&
    		SP.NS.equals(model.getNsPrefixURI(SP.PREFIX)) && 
    		model.contains(SP.Query, RDF.type, (RDFNode)null);
    }
    
    
    /**
     * <p>getArgProperty.</p>
     *
     * @param index a int.
     * @return a {@link org.apache.jena.rdf.model.Property} object.
     */
    public static Property getArgProperty(int index) {
    	return ResourceFactory.createProperty(NS + "arg" + index);
    }
    
    
    /**
     * <p>getArgProperty.</p>
     *
     * @param varName a {@link java.lang.String} object.
     * @return a {@link org.apache.jena.rdf.model.Property} object.
     */
    public static Property getArgProperty(String varName) {
    	return ResourceFactory.createProperty(NS + varName);
    }
    
    
    /**
     * <p>getArgPropertyIndex.</p>
     *
     * @param varName a {@link java.lang.String} object.
     * @return a {@link java.lang.Integer} object.
     */
    public static Integer getArgPropertyIndex(String varName) {
    	if(varName.startsWith("arg")) {
    		String subString = varName.substring(3);
    		try {
    			return Integer.getInteger(subString);
    		}
    		catch(Throwable t) {
    		}
    	}
    	return null;
    }
	
	
	/**
	 * <p>getURI.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public static String getURI() {
        return NS;
    }


	/**
	 * <p>toStringElementList.</p>
	 *
	 * @param buffer a {@link java.lang.StringBuffer} object.
	 * @param resource a {@link org.apache.jena.rdf.model.Resource} object.
	 */
	public static void toStringElementList(StringBuffer buffer, Resource resource) {
		RDFList list = resource.as(RDFList.class);
		for(ExtendedIterator<RDFNode> it = list.iterator(); it.hasNext(); ) {
			Resource item = (Resource) it.next();
			Element e = SPINFactory.asElement(item);
			buffer.append(e.toString());
			if(it.hasNext()) {
				buffer.append(" .\n");
			}
		}
	}
}
