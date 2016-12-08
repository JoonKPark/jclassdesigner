package jclassdesigner.controller;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;
import static java.lang.Math.min;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import jclassdesigner.action.Action;
import jclassdesigner.data.DataManager;
import jclassdesigner.data.JClassState;
import jclassdesigner.data.JClassState;
import jclassdesigner.data.Class;
import jclassdesigner.data.Draggable;
import static jclassdesigner.data.JClassState.ADDING_CLASS;
import static jclassdesigner.data.JClassState.ADDING_INTERFACE;
import static jclassdesigner.data.JClassState.DRAGGING_CLASS;
import static jclassdesigner.data.JClassState.DRAGGING_NOTHING;
import static jclassdesigner.data.JClassState.SELECTING_CLASS;
import static jclassdesigner.data.JClassState.SIZING_CLASS;
import jclassdesigner.gui.Workspace;
import saf.AppTemplate;

/**
 * This class responds to interactions with the rendering surface.
 * 
 * @author McKillaGorilla
 * @version 1.0
 */
public class CanvasController {
    AppTemplate app;
    
    public CanvasController(AppTemplate initApp) {
	app = initApp;
    }
    
    public void processCanvasMouseExited(int x, int y) {
	DataManager dataManager = (DataManager)app.getDataComponent();
	if (dataManager.isInState(JClassState.DRAGGING_CLASS)) {
	    
	}
	else if (dataManager.isInState(JClassState.SIZING_CLASS)) {
	    
	}
    }
    
    public void processCanvasMousePress(int x, int y) {
	DataManager dataManager = (DataManager)app.getDataComponent();
	
	Workspace workspace = (Workspace)app.getWorkspaceComponent();
        
        if (dataManager.isInState(SELECTING_CLASS)) {
	    Class thisClass = dataManager.selectClass(x,y);
            Pane canvas = ((Workspace)app.getWorkspaceComponent()).getCanvas();
            workspace.reloadWorkspace();
            
            if (thisClass != null) {
		canvas.setCursor(Cursor.MOVE);
		dataManager.setState(JClassState.DRAGGING_CLASS);
		app.getGUI().updateToolbarControls(false);
                workspace.reloadWorkspace();
	    }
	    else {
		canvas.setCursor(Cursor.DEFAULT);
		dataManager.setState(DRAGGING_NOTHING);
		app.getWorkspaceComponent().reloadWorkspace();
                workspace.clearInformation();
                workspace.reloadWorkspace();
	    }
	}
        else if (dataManager.isInState(ADDING_CLASS)) {
            dataManager.createUndoAction();
            dataManager.clearRedo();
            dataManager.addClass(x, y);
            workspace.reloadCanvas();
        }
        else if (dataManager.isInState(ADDING_INTERFACE)) {
            dataManager.createUndoAction();
            dataManager.clearRedo();
            dataManager.addInterface(x,y);
            workspace.reloadWorkspace();
        }
    }
    
    public void processCanvasMouseMoved(int x, int y) {
	//Workspace workspace = (Workspace)app.getWorkspaceComponent();
	//workspace.setDebugText("(" + x + "," + y + ")");
    }
    
    public void processCanvasMouseDragged(int x, int y) {
	DataManager dataManager = (DataManager)app.getDataComponent();
	Workspace workspace = (Workspace)app.getWorkspaceComponent();
	if (dataManager.isInState(SIZING_CLASS)) {
            if (dataManager.getResizingClass() != null) {
                dataManager.createUndoAction();
                dataManager.clearRedo();
                dataManager.getResizingClass().size(x, y, workspace.isSnapSelected());
                dataManager.refactorLinesFor(dataManager.getResizingClass());
                workspace.reloadCanvas();
            }
            else {
                dataManager.setResizingClass(dataManager.selectClass(x, y)); 
            }
	}
	else if (dataManager.isInState(DRAGGING_CLASS)) {
            dataManager.createUndoAction();
            dataManager.clearRedo();
            //Draggable selectedDraggableClass = (Draggable)dataManager.getSelectedClass();
            
            dataManager.getSelectedClass().drag(x, y, workspace.isSnapSelected());
            dataManager.refactorLinesFor(dataManager.getSelectedClass());
            
            
            app.getGUI().updateToolbarControls(false);
            workspace.reloadWorkspace();
	}
    }
    
    public void processCanvasMouseRelease(int x, int y) {
	Workspace workspace = (Workspace)app.getWorkspaceComponent();
	DataManager dataManager = (DataManager)app.getDataComponent();
        Pane canvas = ((Workspace)app.getWorkspaceComponent()).getCanvas();
	if (dataManager.isInState(SIZING_CLASS)) {
	    //dataManager.selectSizedShape();
            dataManager.setResizingClass(null);
            workspace.reloadCanvas();
	    app.getGUI().updateToolbarControls(false);
	}
	else if (dataManager.isInState(JClassState.DRAGGING_CLASS)) {
	    dataManager.setState(SELECTING_CLASS);
	    Scene scene = app.getGUI().getPrimaryScene();
	    canvas.setCursor(Cursor.DEFAULT);
            workspace.reloadCanvas();
	    app.getGUI().updateToolbarControls(false);
	}
	else if (dataManager.isInState(JClassState.DRAGGING_NOTHING)) {
	    dataManager.setState(SELECTING_CLASS);
	    Scene scene = app.getGUI().getPrimaryScene();
	    canvas.setCursor(Cursor.DEFAULT);
            workspace.reloadCanvas();
	}
    }
}
