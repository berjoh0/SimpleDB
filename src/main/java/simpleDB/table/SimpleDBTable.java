/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpleDB.table;

import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import simpleDB.database.SimpleDBDatabase;
import simpleDB.field.SimpleDBField;

/**
 *
 * @author johanbergman
 */
public class SimpleDBTable {
    private SimpleDBField[] tableFields;
    private String tableName;
    private SimpleDBDatabase database;
    private HashMap<String, Object> values = new HashMap<String, Object>();

    public SimpleDBTable(SimpleDBDatabase database, SimpleDBField[] tableFields, String tableName) {
        this.tableFields = tableFields;
        this.tableName = tableName;
        this.database = database;
    }

    public boolean insertValues() {
        database.insertValues(tableName, tableFields, values);
        return true;
    }

    protected JsonArray selectValues(String sql) {
        return database.selectValues(sql);
    }

    public void setValue(String field, Object value) {
        values.put(field, value);
    }

    public SimpleDBField[] getTableFields() {
        return tableFields;
    }

    public String getTableName() {
        return tableName;
    }

}
