/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jclassdesigner.data;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author thisi
 */
public class Method {
    public static final String PRIVATE = "private";
    public static final String PUBLIC = "public";
    public static final String PROTECTED = "protected";
    public static final String STATIC = "static";
    public static final String NOT_STATIC = "";
    
    
    private String name;
    private ArrayList<Attribute> parameters;
    private String returnType;
    private String visibility;
    private String isStatic;
    
    public Method() {
        name = "";
        returnType = "";
        parameters = new ArrayList();
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
        return new SimpleStringProperty(name);
    }
    
    /**
     * @return the parameters
     */
    public ArrayList<Attribute> getParameters() {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(ArrayList<Attribute> parameters) {
        this.parameters = parameters;
    }
    
    public void addParameter() {
        Attribute p = new Attribute();
        parameters.add(p);
    }
    
    public void removeParameter(Attribute p) {
        parameters.remove(p);
    }
    
    public void addParameter(Attribute p) {
        parameters.add(p);
    }

    /**
     * @return the returnType
     */
    public String getReturnType() {
        return returnType;
    }
    
    public StringProperty returnTypeProperty() {
        return new SimpleStringProperty(returnType);
    }
    
    /**
     * @param returnType the returnType to set
     */
    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
    
    public String toString() {
        String toReturn = "";
        toReturn += name + "(";
        for (Attribute p : parameters) {
            if (parameters.indexOf(p) != parameters.size()-1)
                toReturn += p.toString() + ", ";
            else
                toReturn += p.toString();
        }
        toReturn += ") : " + returnType;
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
        return new SimpleStringProperty(visibility);
    }
    
    /**
     * @return the isStatic
     */
    public boolean isStatic() {
        return isStatic.equals(STATIC);
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
