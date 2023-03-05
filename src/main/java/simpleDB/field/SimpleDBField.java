/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpleDB.field;

/**
 *
 * @author johanbergman
 */

public class SimpleDBField {
    String Name = "";
    String Type = "";
    String DefaultValue = "";
    boolean NotNull = false;
    boolean PrimaryKey = false;

    public SimpleDBField(String Name, String Type, String DefaultValue, boolean NotNull, boolean PrimaryKey) {
        this.Name = Name;
        this.Type = Type;
        this.DefaultValue = DefaultValue;
        this.NotNull = NotNull;
        this.PrimaryKey = PrimaryKey;
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }

    public String getDefaultValue() {
        return DefaultValue;
    }

    public boolean isNotNull() {
        return NotNull;
    }

    public boolean isPrimaryKey() {
        return PrimaryKey;
    }

}
