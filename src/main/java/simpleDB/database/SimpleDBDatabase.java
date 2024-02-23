package simpleDB.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import simpleDB.field.SimpleDBField;
import simpleDB.table.SimpleDBTable;

public class SimpleDBDatabase {

    private JsonObject databaseProperties;
    private SimpleDBTable[] tables;
    private Connection conn;

    private static final Set<String> numericTypes = Set.of("INT", "INTEGER", "REAL");

    public SimpleDBDatabase(JsonObject databaseProperties, SimpleDBTable[] tables) {
        this.databaseProperties = databaseProperties;
        this.tables = tables;
    }

    public boolean openDatabase() {
        return openDatabase(false);
    }

    public boolean openDatabase(boolean checkTables) {

        try {
            String dbDriver = databaseProperties.get("driver").getAsString();
            String dbUrl = databaseProperties.get("url").getAsString();

            Class.forName(dbDriver);
            conn = DriverManager.getConnection(dbUrl);
            if (checkTables) {
                return checkTables();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Statement createStatement() {
        try {
            return conn.createStatement();
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    public boolean checkTables() {
        for (SimpleDBTable tbl : tables) {

            try {

                String SQL = "SELECT name, sql FROM sqlite_master WHERE name = '" + tbl.getTableName()
                        + "' and type='table'";
                Statement stmt = createStatement();
                ResultSet rs = stmt.executeQuery(SQL);
                if (rs.next()) {
                    // TODO check metadata
                } else {
                    // Create table
                    SQL = "CREATE TABLE " + tbl.getTableName() + " (";

                    for (SimpleDBField fld : tbl.getTableFields()) {
                        SQL += fld.getName() + " " + fld.getType() + (fld.isPrimaryKey() ? " PRIMARY KEY" : "")
                                + (fld.isNotNull() ? " NOT NULL" : "")
                                + (fld.getDefaultValue() != null ? " DEFAULT '" + fld.getDefaultValue() + "'" : "")
                                + ", ";
                    }
                    SQL = SQL.substring(0, SQL.length() - 2);
                    SQL += ");";
                    stmt.execute(SQL);
                }
                rs.close();
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
                // TODO: handle exception
                return false;
            }

        }
        return true;
    }

    public boolean insertValues(String tableName, SimpleDBField[] tableFields, HashMap<String, Object> values) {
        try {
            String fields = "";
            String val = "";
            for (SimpleDBField fld : tableFields) {
                // get value
                if (values.keySet().contains(fld.getName())) {
                    fields += fld.getName() + ", ";
                    if (numericTypes.contains(fld.getType())) {
                        val += values.get(fld.getName()).toString() + ", ";
                    } else {
                        val += "'" + values.get(fld.getName()).toString() + "', ";
                    }
                }
            }
            fields = fields.substring(0, fields.length() - 2);
            val = val.substring(0, val.length() - 2);

            String SQL = "INSERT INTO " + tableName + " (" + fields + ") VALUES (" + val + ");";
            int i = executeSQL(SQL);
            return (i == 1);

        } catch (Exception e) {
            // TODO: handle exception
        }
        return true;
    }

    private int executeSQL(String SQL) {
        try {
            Statement stmt = createStatement();
            int i = stmt.executeUpdate(SQL);
            stmt.close();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            // TODO Error fix
            return 0;
        }
    }

    public JsonArray selectValues(String sql) {
        return executeSelectSQL(sql);
    }

    private JsonArray executeSelectSQL(String SQL) {
        try {
            Statement stmt = createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            JsonArray retArray = resultSet2Json(rs);
            stmt.close();
            return retArray;
        } catch (Exception e) {
            e.printStackTrace();
            // TODO Error fix
            return null;
        }
    }

    private JsonArray resultSet2Json(ResultSet rs) {
        JsonArray retArray = new JsonArray();
        try {
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                JsonObject tRow = new JsonObject();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    tRow.addProperty(rsmd.getColumnName(i + 1), rs.getString(i + 1));
                }
                retArray.add(tRow);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }

        return retArray;
    }
}
