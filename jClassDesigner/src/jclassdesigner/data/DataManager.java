package jclassdesigner.data;


import java.util.ArrayList;
import java.util.Stack;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import jclassdesigner.action.Action;
import static jclassdesigner.data.JClassState.SELECTING_CLASS;
import jclassdesigner.gui.Workspace;
import saf.AppTemplate;
import saf.components.AppDataComponent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thisi
 */
public class DataManager implements AppDataComponent {
    AppTemplate app;
    
    private ArrayList<Class> classes;
    private ArrayList<ConnectorLine> lines;
    
    private ArrayList<Class> snapClasses;
    private ArrayList<ConnectorLine> snapLines;
    
    JClassState state;
    
    Stack<Action> undoQueue;
    Stack<Action> redoQueue;
    
    
    private Class selectedClass;
    private Class resizingClass;
    
    
    public DataManager (AppTemplate initApp) throws Exception {
        app = initApp;
        selectedClass = null;
        resizingClass = null;
        
        
        classes = new ArrayList<Class>();
        lines = new ArrayList();
        
        undoQueue = new Stack();
        redoQueue = new Stack();
        
    }
    
    private Class lookupClassSnap(String name) {
        for (Class c: snapClasses) {
            if (c.getName().equals(name)) return c;
        }
        return null;
    }
    
    public void clearRedo() {
        redoQueue.clear();
    }
    
    public void setSnapshots() {
        snapClasses = new ArrayList();
        snapLines = new ArrayList();
        
        
        
        for (Class c : classes) {
            Class copy = c.copy();
            copy.setHighlighted(false);
            snapClasses.add(copy); 
        }
        
        for (ConnectorLine c : lines) {
            Class fromClass = lookupClassSnap(c.getFromClass().getName());
            Class toClass = lookupClassSnap(c.getToClass().getName());
            
            ConnectorLine copy = new ConnectorLine(fromClass, toClass);
            for (Endpoint e : c.getEndpoints()) {
                Endpoint copyEP = new Endpoint (e.getX(), e.getY());
                copyEP.setEndpointType(e.getEndpointType());
                copy.getEndpoints().add(copyEP);
            }
            snapLines.add(copy);
        }
        
        
    }
    
    
    public void createUndoAction() {
        setSnapshots();
        Action toAdd = new Action(snapClasses, snapLines);
        undoQueue.push(toAdd);
        snapClasses = null;
        snapLines = null;
    }
    
    public void createRedoAction () {
        setSnapshots();
        Action toAdd = new Action(snapClasses, snapLines);
        redoQueue.push(toAdd);
        snapClasses = null;
        snapLines = null;
    }
    
    
    public ArrayList<Class> getClasses() {
        return classes;
    }
    public void setClasses(ArrayList<Class> ol) {
        classes = ol;
    }
    public void addClass(int x, int y) {
        createUndoAction();
        Class newClass = new Class();
        newClass.setX(x);
        newClass.setY(y);
        classes.add(newClass);
        
        
    }
    public void addInterface(int x, int y) {
        createUndoAction();
        Class newClass = new Class();
        newClass.setType(Class.INTERFACE);
        newClass.setX(x);
        newClass.setY(y);
        classes.add(newClass);
        
    }
    
    public boolean undoEmpty() {
        return undoQueue.isEmpty();
    }
    public boolean redoEmpty() {
        return redoQueue.isEmpty();
    }
    
    public void undo() {
        if (!undoEmpty()) {
            createRedoAction();
            selectedClass = null;
            Action toUndo = undoQueue.pop();
            
            classes.clear();
            for (Class c : toUndo.getSnapshotClass()) {
                classes.add(c);
            }
            lines.clear();
            for (ConnectorLine c : toUndo.getSnapshotLines()) {
                lines.add(c);
            }
        }
    }
    
    public void redo() {
        if (!redoEmpty()) {
            createUndoAction();
            selectedClass = null;
            Action toRedo = redoQueue.pop();

            classes.clear();
            for (Class c : toRedo.getSnapshotClass()) {
                classes.add(c);
            }
            lines.clear();
            for (ConnectorLine c : toRedo.getSnapshotLines()) {
                lines.add(c);
            }
        }
    }
    
    public void addToUndo(Action a){
        undoQueue.push(a);
    }
    public JClassState getState() {
	return state;
    }

    public void setState(JClassState initState) {
	state = initState;
    }

    public boolean isInState(JClassState testState) {
	return state == testState;
    }
    @Override
    public void reset () {
        setState(SELECTING_CLASS);
        setSelectedClass(null);
        
        classes.clear();
        lines.clear();
        
        undoQueue.clear();
        redoQueue.clear();
        
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        workspace.clearInformation();
    }
    
    public void unhighlightClass(Class c) {
	c.setHighlighted(false);
    }
    
    public void highlightClass(Class c) {
	c.setHighlighted(true);
    }

    public Class selectClass(int x, int y) {
        Class thisClass = getTopClass(x, y);
        if (thisClass == getSelectedClass()) 
            return thisClass;
        if (getSelectedClass() != null) {
            unhighlightClass(getSelectedClass());
        }
        if (thisClass != null) {
            highlightClass(thisClass);
            Workspace workspace = (Workspace)app.getWorkspaceComponent();
            workspace.loadSelectedClassSettings(thisClass);
        }
        setSelectedClass(thisClass);
        if (thisClass != null) {
            
        }
        return thisClass;
    }
    
    public boolean hasClass(String name) {
        for (Class c: classes) {
            if (c.getName().equals(name)) return true;
        }
        return false;
    }
    
    public Class lookupClass(String name) {
        for (Class c: classes) {
            if (c.getName().equals(name)) return c;
        }
        return null;
    }
    
    public Class getTopClass (int x, int y) {
        for  (int i = classes.size() - 1; i >= 0; i--) {
            Class thisClass = classes.get(i);
            
            
            if (thisClass.contains(x,y)) {
                return thisClass;
            }
        }
        return null;
    }
    
    public void relocateEndpoints(Class c) {
        for (ConnectorLine cl : lines) {
            
        }
    }
    
    public ArrayList<ConnectorLine> linesThatContain(Class c) {
        ArrayList<ConnectorLine> toRet = new ArrayList();
        for (ConnectorLine cl : lines) {
            if (cl.getFromClass().equals(c) || cl.getToClass().equals(c)) {
                toRet.add(cl);
            }
        }
        return toRet;
    }
    
    public ConnectorLine lineConnecting(Class to, Class fro) {
        for (ConnectorLine cl : lines) {
            if (cl.getFromClass().equals(to) && cl.getToClass().equals(fro))
                return cl;
            else if (cl.getToClass().equals(to) && cl.getFromClass().equals(to))
                return cl;
        }
        return null;
    }
  
    public void refactorLinesFor(Class c) {
        ArrayList<ConnectorLine> toRefactor = linesThatContain(c);

        for (ConnectorLine cl : toRefactor) {
            Class otherSide;
            int last = cl.getEndpoints().size() - 1;
            if (cl.getFromClass().equals(c)) {
                otherSide = cl.getToClass();
                String side = c.sideToRenderLine(otherSide);
                if (side.equals(Class.SIDE_DOWN)) {
                    Endpoint start = c.upMidpoint();
                    Endpoint end = otherSide.downMidpoint();
                    end.setEndpointType(cl.getEndpoints().get(last).getEndpointType());
                    cl.getEndpoints().set(0, start);
                    cl.getEndpoints().set(last, end);
                } else if (side.equals(Class.SIDE_UP)) {
                    Endpoint start = c.downMidpoint();
                    Endpoint end = otherSide.upMidpoint();
                    end.setEndpointType(cl.getEndpoints().get(last).getEndpointType());
                    cl.getEndpoints().set(0, start);
                    cl.getEndpoints().set(last, end);
                } else if (side.equals(Class.SIDE_LEFT)) {
                    Endpoint start = c.rightMidpoint();
                    Endpoint end = otherSide.leftMidpoint();
                    end.setEndpointType(cl.getEndpoints().get(last).getEndpointType());
                    cl.getEndpoints().set(0, start);
                    cl.getEndpoints().set(last, end);
                } else {
                    Endpoint start = c.leftMidpoint();
                    Endpoint end = otherSide.rightMidpoint();
                    end.setEndpointType(cl.getEndpoints().get(last).getEndpointType());
                    cl.getEndpoints().set(0, start);
                    cl.getEndpoints().set(last, end);
                }
            } 
            
            
            else {
                otherSide = cl.getFromClass();
                String side = otherSide.sideToRenderLine(c);
                if (side.equals(Class.SIDE_DOWN)) {
                    Endpoint start = otherSide.upMidpoint();
                    Endpoint end = c.downMidpoint();
                    end.setEndpointType(cl.getEndpoints().get(last).getEndpointType());
                    cl.getEndpoints().set(0, start);
                    cl.getEndpoints().set(last, end);
                } else if (side.equals(Class.SIDE_UP)) {
                    Endpoint start = otherSide.downMidpoint();
                    Endpoint end = c.upMidpoint();
                    end.setEndpointType(cl.getEndpoints().get(last).getEndpointType());
                    cl.getEndpoints().set(0, start);
                    cl.getEndpoints().set(last, end);
                } else if (side.equals(Class.SIDE_LEFT)) {
                    Endpoint start = otherSide.rightMidpoint();
                    Endpoint end = c.leftMidpoint();
                    end.setEndpointType(cl.getEndpoints().get(last).getEndpointType());
                    cl.getEndpoints().set(0, start);
                    cl.getEndpoints().set(last, end);
                } else {
                    Endpoint start = otherSide.leftMidpoint();
                    Endpoint end = c.rightMidpoint();
                    end.setEndpointType(cl.getEndpoints().get(last).getEndpointType());
                    cl.getEndpoints().set(0, start);
                    cl.getEndpoints().set(last, end);
                }
            }
        }
        
    }
     
    public void createInheritance(Class child, Class parent) {
        ConnectorLine newLine = new ConnectorLine(child, parent);
        String side = parent.sideToRenderLine(child);
        if (side.equals(Class.SIDE_DOWN)) {
            Endpoint start = parent.upMidpoint();
            Endpoint end = child.downMidpoint();
            end.setEndpointType(Endpoint.ARROW);
            newLine.getEndpoints().add(start);
            newLine.getEndpoints().add(end);
        } else if (side.equals(Class.SIDE_UP)) {
            Endpoint start = parent.downMidpoint();
            Endpoint end = child.upMidpoint();
            end.setEndpointType(Endpoint.ARROW);
            newLine.getEndpoints().add(start);
            newLine.getEndpoints().add(end);
        } else if (side.equals(Class.SIDE_LEFT)) {
            Endpoint start = parent.rightMidpoint();
            Endpoint end = child.leftMidpoint();
            end.setEndpointType(Endpoint.ARROW);
            newLine.getEndpoints().add(start);
            newLine.getEndpoints().add(end);
        } else {
            Endpoint start = parent.leftMidpoint();
            Endpoint end = child.rightMidpoint();
            end.setEndpointType(Endpoint.ARROW);
            newLine.getEndpoints().add(start);
            newLine.getEndpoints().add(end);
        }
        lines.add(newLine);
        Workspace w = (Workspace)app.getWorkspaceComponent();
        w.reloadWorkspace();
    }
    
    public void removeInheritance(Class child, Class parent) {
        ConnectorLine removing = lineConnecting(child, parent);
        if (removing != null) {
            lines.remove(removing);
        }
        Workspace w = (Workspace)app.getWorkspaceComponent();
        w.reloadWorkspace();
    }
    
    public void removeAggregate(Class used, Class user) {
        ConnectorLine removing = lineConnecting(used, user);
        if (removing != null) {
            lines.remove(removing);
        }
        Workspace w = (Workspace)app.getWorkspaceComponent();
        w.reloadWorkspace();
    }
    
    public void removeSelectedClass() {
        if (selectedClass != null) {
            if (!selectedClass.getImplementList().isEmpty()) {
                for (Class c : selectedClass.getImplementList()) {
                    removeInheritance(selectedClass, c);
                    selectedClass.getImplementList().remove(c);
                    
                }
            }
            if (!selectedClass.getAggregateOfList().isEmpty()) {
                for (Class c : selectedClass.getAggregateOfList()) {
                    removeAggregate(selectedClass, c);
                    selectedClass.getAggregateOfList().remove(c);
                }
            }
            for (ConnectorLine cl : linesThatContain(selectedClass)) {
                lines.remove(cl);
            }
            classes.remove(selectedClass);
            selectedClass = null;
        }
        Workspace w = (Workspace) app.getWorkspaceComponent();
        w.reloadWorkspace();
    }
    
    public void checkAggregate(Attribute a, Class c) {
        String name = a.getType();
        if (hasClass(name)) {
            // The class that c makes use of.
            Class checking = lookupClass(name);
            if (!checking.getAggregateOfList().contains(c)) {
                String side = c.sideToRenderLine(checking);
                ConnectorLine newLine;
                if (side.equals(Class.SIDE_DOWN)) {
                    newLine = new ConnectorLine(checking, c);
                    Endpoint start = checking.downMidpoint();
                    Endpoint end = c.upMidpoint();
                    end.setEndpointType(Endpoint.DIAMOND);
                    newLine.getEndpoints().add(start);
                    newLine.getEndpoints().add(end);
                } else if (side.equals(Class.SIDE_UP)) {
                    newLine = new ConnectorLine(checking, c);
                    Endpoint start = checking.upMidpoint();
                    Endpoint end = c.downMidpoint();
                    end.setEndpointType(Endpoint.DIAMOND);
                    newLine.getEndpoints().add(start);
                    newLine.getEndpoints().add(end);
                } else if (side.equals(Class.SIDE_LEFT)) {
                    newLine = new ConnectorLine(checking, c);
                    Endpoint start = checking.leftMidpoint();
                    Endpoint end = c.rightMidpoint();
                    end.setEndpointType(Endpoint.DIAMOND);
                    newLine.getEndpoints().add(start);
                    newLine.getEndpoints().add(end);
                } else {
                    newLine = new ConnectorLine(checking, c);
                    Endpoint start = checking.rightMidpoint();
                    Endpoint end = c.leftMidpoint();
                    end.setEndpointType(Endpoint.DIAMOND);
                    newLine.getEndpoints().add(start);
                    newLine.getEndpoints().add(end);
                }
                lines.add(newLine);
                checking.getAggregateOfList().add(c);
            }
        }
    }
    
    /**
     * @return the selectedClass
     */
    public Class getSelectedClass() {
        return selectedClass;
    }

    /**
     * @param selectedClass the selectedClass to set
     */
    public void setSelectedClass(Class selectedClass) {
        this.selectedClass = selectedClass;
    }
    
    public void updateSelectedName(String s) {
        createUndoAction();
        clearRedo();
        if (selectedClass != null) selectedClass.setName(s);
        Workspace w = (Workspace)app.getWorkspaceComponent();
        w.reloadWorkspace();
    }
    public void updateSelectedPackageName(String s) {
        createUndoAction();
        clearRedo();
        if (selectedClass != null) selectedClass.setPackageName(s);
        Workspace w = (Workspace)app.getWorkspaceComponent();
        w.reloadWorkspace();
    }

    /**
     * @return the resizingClass
     */
    public Class getResizingClass() {
        return resizingClass;
    }

    /**
     * @param resizingClass the resizingClass to set
     */
    public void setResizingClass(Class resizingClass) {
        this.resizingClass = resizingClass;
    }

    /**
     * @return the lines
     */
    public ArrayList<ConnectorLine> getLines() {
        return lines;
    }

    /**
     * @param lines the lines to set
     */
    public void setLines(ArrayList<ConnectorLine> lines) {
        this.lines = lines;
    }
}
