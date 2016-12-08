/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jclassdesigner.controller;

import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javax.imageio.ImageIO;
import jclassdesigner.JClassDesigner;
import jclassdesigner.data.DataManager;
import jclassdesigner.data.JClassState;
import jclassdesigner.gui.Workspace;
import jclassdesigner.data.Class;
import saf.AppTemplate;

/**
 *
 * @author thisi
 */
public class ProjectEditController {
    AppTemplate app;
    private boolean enabled;
    
    DataManager dataManager;
    
    public ProjectEditController(AppTemplate initApp) {
        app = initApp;
        dataManager = (DataManager)app.getDataComponent();
    }
    
    public void enable(boolean enableSetting) {
        enabled = enableSetting;
    }
    
    
    public void processSelectSelectionTool() {
        Scene scene = app.getGUI().getPrimaryScene();
        Pane canvas = ((Workspace)app.getWorkspaceComponent()).getCanvas();
        canvas.setCursor(Cursor.DEFAULT);
        dataManager.setState(JClassState.SELECTING_CLASS);
        
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        workspace.reloadWorkspace();
    }
    
    public void processSelectSizeTool() {
        Scene scene = app.getGUI().getPrimaryScene();
        Pane canvas = ((Workspace)app.getWorkspaceComponent()).getCanvas();
        canvas.setCursor(Cursor.SE_RESIZE);
        dataManager.setState(JClassState.SIZING_CLASS);
        
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        workspace.reloadWorkspace();
    }

    public void processSelectAddClass() {
        Scene scene = app.getGUI().getPrimaryScene();
        Pane canvas = ((Workspace)app.getWorkspaceComponent()).getCanvas();
        canvas.setCursor(Cursor.CROSSHAIR);
        
        if (dataManager.getSelectedClass() != null) dataManager.getSelectedClass().setHighlighted(false);
        dataManager.setSelectedClass(null);
        dataManager.setState(JClassState.ADDING_CLASS);
        
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        workspace.clearInformation();
        workspace.reloadWorkspace();
    }
    
    public void handleAddParent(String name) {
        dataManager.createUndoAction();
        dataManager.clearRedo();
        
        Class toEdit = dataManager.getSelectedClass();
        Class parent = dataManager.lookupClass(name);
        if (parent.getType().equals(Class.INTERFACE)) {
            if (!toEdit.getImplementList().contains(parent))
                toEdit.getImplementList().add(parent);
        }
        else {
            if (!toEdit.getExtendsList().contains(parent))
                toEdit.getExtendsList().add(parent);
        }
        dataManager.createInheritance(toEdit, parent);
        
    }
    public void handleRemoveParent(String name) {
        dataManager.createUndoAction();
        dataManager.clearRedo();
        Class toEdit = dataManager.getSelectedClass();
        Class parent = dataManager.lookupClass(name);
        if (parent.getType().equals(Class.INTERFACE)) {
            if (toEdit.getImplementList().contains(parent))
                toEdit.getImplementList().remove(parent);
        }
        else {
            if (toEdit.getExtendsList().contains(parent))
                toEdit.getExtendsList().remove(parent);
        }
        dataManager.removeInheritance(toEdit,parent);
    }
    
    public void processSelectAddInterface() {
        Scene scene = app.getGUI().getPrimaryScene();
        Pane canvas = ((Workspace)app.getWorkspaceComponent()).getCanvas();
        canvas.setCursor(Cursor.CROSSHAIR);
        
        if (dataManager.getSelectedClass() != null) dataManager.getSelectedClass().setHighlighted(false);
        dataManager.setSelectedClass(null);
        dataManager.setState(JClassState.ADDING_INTERFACE);
        
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        workspace.clearInformation();
        workspace.reloadWorkspace();
    }
    
    public void processRemoveClass() {
        //TODO REMOVING ARROWS
        if (dataManager.isInState(JClassState.SELECTING_CLASS) && dataManager.getSelectedClass() != null) {
            dataManager.removeSelectedClass();
            Workspace workspace = (Workspace) app.getWorkspaceComponent();
            workspace.clearInformation();
            workspace.reloadWorkspace();
        }
    }
    
    public void processSnapshot() {
	Workspace workspace = (Workspace)app.getWorkspaceComponent();
	Pane canvas = workspace.getCanvas();
	WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
	File file = new File("Snapshot.png");
	try {
	    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
	}
	catch(IOException ioe) {
	    ioe.printStackTrace();
	}
    }
}
