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

package org.spinrdf.spr;

import java.util.LinkedList;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolutionMap;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.spinrdf.arq.ARQFactory;
import org.spinrdf.util.JenaDatatypes;
import org.spinrdf.vocabulary.SPR;


/**
 * Static utilities on SPR tables.
 */
public class SPRResultSets {

    private static Query cellQuery = ARQFactory.get().doCreateQuery(
            "SELECT (<" + SPR.cell.getURI() + ">(?table, ?row, ?col) AS ?result)\n" +
            "WHERE {\n" +
            "}");

    private static Query colCountQuery = ARQFactory.get().doCreateQuery(
            "SELECT (<" + SPR.colCount.getURI() + ">(?table) AS ?result)\n" +
            "WHERE {\n" +
            "}");

    private static Query colNameQuery = ARQFactory.get().doCreateQuery(
            "SELECT (<" + SPR.colName.getURI() + ">(?table, ?col) AS ?result)\n" +
            "WHERE {\n" +
            "}");

    private static Query rowCountQuery = ARQFactory.get().doCreateQuery(
            "SELECT (<" + SPR.rowCount.getURI() + ">(?table) AS ?result)\n" +
            "WHERE {\n" +
            "}");


    public static RDFNode getCell(Resource table, int row, int col) {
        Model model = table.getModel();
        QuerySolutionMap bindings = new QuerySolutionMap();
        bindings.add("table", table);
        bindings.add("row", JenaDatatypes.createInteger(row));
        bindings.add("col", JenaDatatypes.createInteger(col));
        QueryExecution qexec = ARQFactory.get().createQueryExecution(cellQuery, model)
                .substitution(bindings)
                .build();
        try {
            ResultSet rs = qexec.execSelect();
            if(rs.hasNext()) {
                RDFNode result = rs.next().get("result");
                return result;
            }
            else {
                return null;
            }
        }
        finally {
            qexec.close();
        }
    }


    public static int getColCount(Resource table) {
        return getIntFromFunction(table, colCountQuery);
    }


    public static String getColName(Resource table, int col) {
        Model model = table.getModel();
        QuerySolutionMap bindings = new QuerySolutionMap();
        bindings.add("table", table);
        bindings.add("col", JenaDatatypes.createInteger(col));
        QueryExecution qexec = ARQFactory.get().createQueryExecution(colNameQuery, model)
                .substitution(bindings)
                .build();
        try {
            ResultSet rs = qexec.execSelect();
            if(rs.hasNext()) {
                RDFNode result = rs.next().get("result");
                if(result.isLiteral()) {
                    return ((Literal)result).getString();
                }
            }
            return null;
        }
        finally {
            qexec.close();
        }
    }


    public static List<String> getColNames(Resource table) {
        List<String> results = new LinkedList<String>();
        int colCount = getColCount(table);
        for(int i = 0; i < colCount; i++) {
            results.add(getColName(table, i));
        }
        return results;
    }


    private static int getIntFromFunction(Resource table, Query query) {
        Model model = table.getModel();
        QuerySolutionMap bindings = new QuerySolutionMap();
        bindings.add("table", table);
        QueryExecution qexec = ARQFactory.get().createQueryExecution(query, model)
                .substitution(bindings)
                .build();
        try {
            ResultSet rs = qexec.execSelect();
            if(rs.hasNext()) {
                RDFNode result = rs.next().get("result");
                if(result.isLiteral()) {
                    return ((Literal)result).getInt();
                }
            }
            return 0;
        }
        catch(Exception ex) {
            throw new IllegalArgumentException("Error trying to query spr: result set " + table, ex);
        }
        finally {
            qexec.close();
        }
    }


    public static int getRowCount(Resource table) {
        return getIntFromFunction(table, rowCountQuery);
    }
}
