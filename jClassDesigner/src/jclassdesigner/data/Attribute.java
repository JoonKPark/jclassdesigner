/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jclassdesigner.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author thisi
 */
public class Attribute {
    public static final String PRIVATE = "private";
    public static final String PUBLIC = "public";
    public static final String PROTECTED = "protected";
    public static final String STATIC = "static";
    public static final String NOT_STATIC = "";
    
    private String name;
    private String type;
    private String visibility;
    private String isStatic;
    
    public Attribute() {
        name = "";
        type = "";
        visibility = PUBLIC;
        isStatic = NOT_STATIC;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public StringProperty nameProperty() {
        SimpleStringProperty toReturn = new SimpleStringProperty(name);
        return toReturn;
    }
    
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
    public StringProperty typeProperty() {
        SimpleStringProperty toReturn = new SimpleStringProperty(type);
        return toReturn;
    }
    
    /**
     * @return the visibility
     */
    public String getVisibility() {
        return visibility;
    }

    /**
     * @param visibility the visibility to set
     */
    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
    
    public StringProperty visibilityProperty() {
        SimpleStringProperty toReturn = new SimpleStringProperty(visibility);
        return toReturn;
    }
    public String toString() {
        return name + " : " + type;
    }

    /**
     * @return the isStatic
     */
    public Boolean isStatic() {
        return isStatic.equals(Attribute.STATIC);
    }
    
    public String getStatic() {
        return isStatic;
    }
    
    /**
     * @param isStatic the isStatic to set
     */
    public void setStatic(String isStatic) {
        this.isStatic = isStatic;
    }
    
    public StringProperty staticProperty() {
        return new SimpleStringProperty(isStatic);
    }
}
