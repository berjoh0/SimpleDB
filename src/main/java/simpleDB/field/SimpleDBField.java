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
interface FieldInfo {

    String Name = "";
    String Type = "";
    String DefaultValue = "";

    public String getName();

    public String getType();

    public String getDefaultValue();
}

public class SimpleDBField implements FieldInfo {

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDefaultValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
