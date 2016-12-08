/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jclassdesigner.action;

import java.util.ArrayList;
import jclassdesigner.data.Class;
import jclassdesigner.data.ConnectorLine;

/**
 *
 * @author thisi
 */
public class Action {
    /*
    public static final String ALTER_CLASS = "edit_class";
    public static final String REMOVE_CLASS = "remove_class";
    public static final String ADD_CLASS = "add_class";
    
    private String actionType;
    private Class original;
    private Class changed;
    */
    
    private ArrayList<Class> snapshotClass;
    private ArrayList<ConnectorLine> snapshotLines;
    
    public Action(ArrayList<Class> sC, ArrayList<ConnectorLine> sL){
        snapshotClass = new ArrayList();
        snapshotLines = new ArrayList();
        for (Class c : sC) {
            snapshotClass.add(c);
        }
        for (ConnectorLine c : sL) {
            snapshotLines.add(c);
        }
    }

    /**
     * @return the snapshotClass
     */
    public ArrayList<Class> getSnapshotClass() {
        return snapshotClass;
    }

    /**
     * @param snapshotClass the snapshotClass to set
     */
    public void setSnapshotClass(ArrayList<Class> snapshotClass) {
        this.snapshotClass = snapshotClass;
    }

    /**
     * @return the snapshotLines
     */
    public ArrayList<ConnectorLine> getSnapshotLines() {
        return snapshotLines;
    }

    /**
     * @param snapshotLines the snapshotLines to set
     */
    public void setSnapshotLines(ArrayList<ConnectorLine> snapshotLines) {
        this.snapshotLines = snapshotLines;
    }
    

}
