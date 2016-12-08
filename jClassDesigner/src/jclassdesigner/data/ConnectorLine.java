/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jclassdesigner.data;

import java.util.ArrayList;

/**
 *
 * @author thisi
 */
public class ConnectorLine {
    private ArrayList<Endpoint> endpoints;
    private boolean highlighted;
    
    private Endpoint fromPt;
    private Endpoint toPt;
    
    private Class fromClass;
    private Class toClass;
    
    
    public ConnectorLine(Class or, Class en) {
        endpoints = new ArrayList();
        highlighted = false;
        fromClass = or;
        toClass = en;
        
    }

    

    /**
     * @return the highlighted
     */
    public boolean isHighlighted() {
        return highlighted;
    }

    /**
     * @param highlighted the highlighted to set
     */
    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    /**
     * @return the endpoints
     */
    public ArrayList<Endpoint> getEndpoints() {
        return endpoints;
    }

    /**
     * @param endpoints the endpoints to set
     */
    public void setEndpoints(ArrayList<Endpoint> endpoints) {
        this.endpoints = endpoints;
    }

    /**
     * @return the fromClass
     */
    public Class getFromClass() {
        return fromClass;
    }

    /**
     * @param fromClass the fromClass to set
     */
    public void setFromClass(Class fromClass) {
        this.fromClass = fromClass;
    }

    /**
     * @return the toClass
     */
    public Class getToClass() {
        return toClass;
    }

    /**
     * @param toClass the toClass to set
     */
    public void setToClass(Class toClass) {
        this.toClass = toClass;
    }
}
