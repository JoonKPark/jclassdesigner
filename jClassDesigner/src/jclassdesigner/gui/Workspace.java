/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jclassdesigner.gui;

import com.sun.javafx.tk.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import static jclassdesigner.PropertyType.ADD_CLASS_ICON;
import static jclassdesigner.PropertyType.ADD_CLASS_TOOLTIP;
import static jclassdesigner.PropertyType.ADD_INTERFACE_ICON;
import static jclassdesigner.PropertyType.ADD_INTERFACE_TOOLTIP;
import static jclassdesigner.PropertyType.CLASS_NAME_LABEL;
import static jclassdesigner.PropertyType.CODE_ICON;
import static jclassdesigner.PropertyType.CODE_TOOLTIP;
import static jclassdesigner.PropertyType.EDIT_PARAM_ICON;
import static jclassdesigner.PropertyType.EDIT_PARAM_TOOLTIP;
import static jclassdesigner.PropertyType.EXPORT_ERROR_MESSAGE;
import static jclassdesigner.PropertyType.EXPORT_ERROR_TITLE;
import static jclassdesigner.PropertyType.GRID_LABEL;
import static jclassdesigner.PropertyType.LOAD_ICON;
import static jclassdesigner.PropertyType.LOAD_TOOLTIP;
import static jclassdesigner.PropertyType.METHODS_LABEL;
import static jclassdesigner.PropertyType.MINUS_ICON;
import static jclassdesigner.PropertyType.NAME_LABEL;
import static jclassdesigner.PropertyType.PACKAGE_LABEL;
import static jclassdesigner.PropertyType.PARAMETERS_LABEL;
import static jclassdesigner.PropertyType.PARENT_LABEL;
import static jclassdesigner.PropertyType.PLUS_ICON;
import static jclassdesigner.PropertyType.REDO_ICON;
import static jclassdesigner.PropertyType.REDO_TOOLTIP;
import static jclassdesigner.PropertyType.REMOVE_ICON;
import static jclassdesigner.PropertyType.REMOVE_TOOLTIP;
import static jclassdesigner.PropertyType.RESIZE_ICON;
import static jclassdesigner.PropertyType.RESIZE_TOOLTIP;
import static jclassdesigner.PropertyType.RETURN_TYPE_LABEL;
import static jclassdesigner.PropertyType.SELECT_ICON;
import static jclassdesigner.PropertyType.SELECT_TOOLTIP;
import static jclassdesigner.PropertyType.SNAPSHOT_ICON;
import static jclassdesigner.PropertyType.SNAPSHOT_TOOLTIP;
import static jclassdesigner.PropertyType.SNAP_LABEL;
import static jclassdesigner.PropertyType.STATIC_LABEL;
import static jclassdesigner.PropertyType.TYPE_LABEL;
import static jclassdesigner.PropertyType.UNDO_ICON;
import static jclassdesigner.PropertyType.UNDO_TOOLTIP;
import static jclassdesigner.PropertyType.VARIABLES_LABEL;
import static jclassdesigner.PropertyType.VISIBILITY_LABEL;
import static jclassdesigner.PropertyType.X_ICON;
import static jclassdesigner.PropertyType.ZOOM_IN_ICON;
import static jclassdesigner.PropertyType.ZOOM_IN_TOOLTIP;
import static jclassdesigner.PropertyType.ZOOM_OUT_ICON;
import static jclassdesigner.PropertyType.ZOOM_OUT_TOOLTIP;
import jclassdesigner.controller.CanvasController;
import jclassdesigner.controller.ProjectEditController;
import jclassdesigner.data.Attribute;
import jclassdesigner.data.DataManager;
import jclassdesigner.data.Class;
import jclassdesigner.data.ConnectorLine;
import jclassdesigner.data.Endpoint;
import jclassdesigner.data.JClassState;
import jclassdesigner.data.Method;
import jclassdesigner.file.FileManager;
import properties_manager.PropertiesManager;
import saf.AppTemplate;
import saf.components.AppWorkspaceComponent;
import static saf.settings.AppPropertyType.SAVE_WORK_TITLE;
import static saf.settings.AppStartupConstants.FILE_PROTOCOL;
import static saf.settings.AppStartupConstants.PATH_IMAGES;
import static saf.settings.AppStartupConstants.PATH_WORK;
import saf.ui.AppGUI;
import saf.ui.AppMessageDialogSingleton;

/**
 *
 * @author thisi
 */
public class Workspace extends AppWorkspaceComponent {
    
    static final String CLASS_EDIT_TOOLBAR_ROW = "edit_toolbar_row";
    
    AppTemplate app;
    AppGUI gui;
    ProjectEditController pec;
    Button loadButton;
    
    VBox exportVBox;
    Button photoButton;
    Button codeButton;
    
    HBox classToolbar;
    Button selectButton;
    Button resizeButton;
    Button addClassButton;
    Button addInterfaceButton;
    Button removeButton;
    Button undoButton;
    Button redoButton;
    
    HBox viewToolbar;
    Button zoomInButton;
    Button zoomOutButton;
    
    VBox gridVBox;
    CheckBox gridCheckBox;
    CheckBox snapCheckBox;
    
    ScrollPane canvasContainer;
    Pane canvas;
    
    ScrollPane componentTBSP;
    VBox componentToolbar;
    
    HBox row1Box;
    Label classNameLabel;
    TextField classNameTextBox;
    
    HBox row2Box;
    Label packageLabel;
    TextField packageNameTextBox;
    
    HBox row3Box;
    Label parentLabel;
    ComboBox<String> parentComboBox;
    Button addParentButton;
    Button removeParentButton;
    
    
    VBox row4Box;
    Label variablesLabel;
    Button addVarButton;
    Button removeVarButton;
    TableView<Attribute> varTable;
    TableColumn<Attribute,String> varTypeColumn;
    TableColumn<Attribute,String> varNameColumn;
    TableColumn<Attribute,String> varVisibilityColumn;
    TableColumn<Attribute,String> varStaticColumn;
    
    Pane row5Box;
    
    VBox methodsEditBox;
    Label methodsLabel;
    Button addMethodButton;
    Button removeMethodButton;
    Button editParameterButton;
    TableView<Method> methodTable;
    TableColumn<Method,String> methodStaticColumn;
    TableColumn<Method,String> methodVisibilityColumn;
    TableColumn<Method,String> methodReturnTypeColumn;
    TableColumn<Method,String> methodNameColumn;
    
    VBox parameterEditBox;
    Label parametersLabel;
    ImageView exitButton;
    Button addParameterButton;
    Button removeParameterButton;
    TableView<Attribute> parameterTable;
    TableColumn<Attribute,String> parameterTypeColumn;
    TableColumn<Attribute,String> parameterNameColumn;
    
    
    
    private CanvasController canvasController;
    
    Pane grid;
    
    public Workspace (AppTemplate initApp) throws IOException {
        app = initApp;
        gui = app.getGUI();
        layoutGUI();
    }
    
    private void layoutGUI() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        DataManager data = (DataManager)app.getDataComponent();
        FlowPane topPane = (FlowPane)gui.getAppPane().getTop();
        
        FlowPane ftbp = (FlowPane)topPane.getChildren().get(0);
        
        
        exportVBox = new VBox();
        photoButton = gui.initChildButton(exportVBox, SNAPSHOT_ICON.toString(), SNAPSHOT_TOOLTIP.toString(), true);
        photoButton.setPadding(new Insets(0.5,10,0,10));
        codeButton = gui.initChildButton(exportVBox, CODE_ICON.toString(), CODE_TOOLTIP.toString(), true);
        codeButton.setPadding(new Insets(0,10,0.5,10));
        exportVBox.setAlignment(Pos.CENTER);
        
        
        int last = ftbp.getChildren().size() - 1;
        Button exit = (Button)ftbp.getChildren().get(last);
        ftbp.getChildren().set(last, exportVBox);
        ftbp.getChildren().add(exit);
        
        classToolbar = new HBox();
        selectButton = gui.initChildButton(classToolbar, SELECT_ICON.toString(), SELECT_TOOLTIP.toString(), true);
        resizeButton = gui.initChildButton(classToolbar, RESIZE_ICON.toString(), RESIZE_TOOLTIP.toString(), true);
        addClassButton = gui.initChildButton(classToolbar, ADD_CLASS_ICON.toString(), ADD_CLASS_TOOLTIP.toString(), true);
        addInterfaceButton = gui.initChildButton(classToolbar, ADD_INTERFACE_ICON.toString(), ADD_INTERFACE_TOOLTIP.toString(), true);
        removeButton = gui.initChildButton(classToolbar, REMOVE_ICON.toString(), REMOVE_TOOLTIP.toString(), true);
        undoButton = gui.initChildButton(classToolbar, UNDO_ICON.toString(), UNDO_TOOLTIP.toString(), true);
        redoButton = gui.initChildButton(classToolbar, REDO_ICON.toString(), REDO_TOOLTIP.toString(), true);
        
        
        topPane.getChildren().add(classToolbar);
        
        
        viewToolbar = new HBox();
        
        zoomInButton = gui.initChildButton(viewToolbar, ZOOM_IN_ICON.toString(), ZOOM_IN_TOOLTIP.toString(), true);
        zoomOutButton = gui.initChildButton(viewToolbar, ZOOM_OUT_ICON.toString(), ZOOM_OUT_TOOLTIP.toString(), true);
        
        //For spacing only
        Pane emptyPane = new Pane();
        emptyPane.setMinWidth(20);
        
        viewToolbar.getChildren().add(emptyPane);
        
        VBox gridVBox = new VBox();
        gridCheckBox = new CheckBox();
        gridCheckBox.setText(props.getProperty(GRID_LABEL.toString()));
        snapCheckBox = new CheckBox();
        snapCheckBox.setText(props.getProperty(SNAP_LABEL.toString()));
        gridVBox.getChildren().addAll(gridCheckBox, snapCheckBox);
        
        viewToolbar.getChildren().add(gridVBox);
        topPane.getChildren().add(viewToolbar);
        
        
        canvas = new Pane();
        
        componentToolbar = new VBox();
        
        row1Box = new HBox();
        //HBox row1Container = new HBox();
        classNameLabel = new Label(props.getProperty(CLASS_NAME_LABEL.toString()));
        classNameTextBox = new TextField();
        row1Box.getChildren().addAll(classNameLabel, classNameTextBox);
        row1Box.setSpacing(20);
        row1Box.alignmentProperty().set(Pos.CENTER);
        //row1Box.setCenter(row1Container);
       
        
        //row1Box.setLeft(classNameLabel);
        //row1Box.setRight(classNameTextBox);
        
        row2Box = new HBox();
        packageLabel = new Label(props.getProperty(PACKAGE_LABEL.toString()));
        packageNameTextBox = new TextField();
        row2Box.getChildren().addAll(packageLabel, packageNameTextBox);
        row2Box.setAlignment(Pos.CENTER);
        row2Box.setSpacing(40);
        
        row3Box = new HBox();
        parentLabel = new Label(props.getProperty(PARENT_LABEL.toString()));
        parentComboBox = new ComboBox();
        parentComboBox.setItems(FXCollections.observableArrayList());
        parentComboBox.setMinWidth(200);
        row3Box.getChildren().addAll(parentLabel, parentComboBox);
        addParentButton = gui.initChildButton(row3Box, PLUS_ICON.toString(), ZOOM_OUT_TOOLTIP.toString(), false);
        removeParentButton = gui.initChildButton(row3Box, MINUS_ICON.toString(), ZOOM_OUT_TOOLTIP.toString(), false);
        row3Box.setAlignment(Pos.CENTER);
        
        row3Box.setSpacing(20);
        
        row4Box = new VBox();
        HBox r4LabelsHBox = new HBox();
        variablesLabel = new Label(props.getProperty(VARIABLES_LABEL.toString()));
        r4LabelsHBox.getChildren().add(variablesLabel);
        addVarButton = gui.initChildButton(r4LabelsHBox, PLUS_ICON.toString(), ZOOM_OUT_TOOLTIP.toString(), true);
        removeVarButton = gui.initChildButton(r4LabelsHBox, MINUS_ICON.toString(), ZOOM_OUT_TOOLTIP.toString(), true);
        
        varTable = new TableView();
        
        varTypeColumn = new TableColumn(props.getProperty(TYPE_LABEL));
        varNameColumn = new TableColumn(props.getProperty(NAME_LABEL));
        varVisibilityColumn = new TableColumn(props.getProperty(VISIBILITY_LABEL));
        varStaticColumn = new TableColumn(props.getProperty(STATIC_LABEL));
        
        varTypeColumn.setCellValueFactory(new PropertyValueFactory("type"));
        varNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        varVisibilityColumn.setCellValueFactory(new PropertyValueFactory("visibility"));
        varStaticColumn.setCellValueFactory(new PropertyValueFactory("static"));
        
        varTypeColumn.setPrefWidth(150);
        varNameColumn.setPrefWidth(150);

        varTable.getColumns().add(varVisibilityColumn);
        varTable.getColumns().add(varStaticColumn);
        varTable.getColumns().add(varTypeColumn);
        varTable.getColumns().add(varNameColumn);
        
        row4Box.getChildren().addAll(r4LabelsHBox, varTable);
        row4Box.setSpacing(10);


        
        methodsEditBox = new VBox();
        BorderPane r5LabelsFormatter = new BorderPane();
        HBox r5LabelsHBox = new HBox();
        methodsLabel = new Label(props.getProperty(METHODS_LABEL.toString()));
        r5LabelsHBox.getChildren().add(methodsLabel);
        addMethodButton = gui.initChildButton(r5LabelsHBox, PLUS_ICON.toString(), ZOOM_OUT_TOOLTIP.toString(), true);
        removeMethodButton = gui.initChildButton(r5LabelsHBox, MINUS_ICON.toString(), ZOOM_OUT_TOOLTIP.toString(), true);
        Pane ePBPane = new Pane();
        editParameterButton = gui.initChildButton(ePBPane, EDIT_PARAM_ICON.toString(), EDIT_PARAM_TOOLTIP.toString(), true);
        
        r5LabelsFormatter.setLeft(r5LabelsHBox);
        r5LabelsFormatter.setRight(ePBPane);
        
        methodTable = new TableView();
        
        methodReturnTypeColumn = new TableColumn(props.getProperty(RETURN_TYPE_LABEL));
        methodNameColumn = new TableColumn(props.getProperty(NAME_LABEL));
        methodVisibilityColumn = new TableColumn(props.getProperty(VISIBILITY_LABEL));
        methodStaticColumn = new TableColumn(props.getProperty(STATIC_LABEL));
        
        methodReturnTypeColumn.setCellValueFactory(new PropertyValueFactory("returnType"));
        methodNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        methodVisibilityColumn.setCellValueFactory(new PropertyValueFactory("visibility"));
        methodStaticColumn.setCellValueFactory(new PropertyValueFactory("static"));
        
        methodReturnTypeColumn.setPrefWidth(150);
        methodNameColumn.setPrefWidth(150);

        methodTable.getColumns().add(methodVisibilityColumn);
        methodTable.getColumns().add(methodStaticColumn);
        methodTable.getColumns().add(methodReturnTypeColumn);
        methodTable.getColumns().add(methodNameColumn);
        
        methodsEditBox.getChildren().addAll(r5LabelsFormatter, methodTable);
        
        methodsEditBox.setSpacing(10);
        
        
        
        parameterEditBox = new VBox();
        BorderPane paramsLabelsFormatter = new BorderPane();
        HBox paramsLabelsHBox = new HBox();
        parametersLabel = new Label(props.getProperty(PARAMETERS_LABEL.toString()));
        paramsLabelsHBox.getChildren().add(parametersLabel);
        addParameterButton = gui.initChildButton(paramsLabelsHBox, PLUS_ICON.toString(), ZOOM_OUT_TOOLTIP.toString(), false);
        removeParameterButton = gui.initChildButton(paramsLabelsHBox, MINUS_ICON.toString(), ZOOM_OUT_TOOLTIP.toString(), false);
        
        
        Image buttonImage = new Image(FILE_PROTOCOL + PATH_IMAGES + props.getProperty(X_ICON.toString()));
        exitButton = new ImageView(buttonImage);
        
        paramsLabelsFormatter.setLeft(paramsLabelsHBox);
        paramsLabelsFormatter.setRight(exitButton);
        
        
        
        parameterTable = new TableView();
        
        parameterTypeColumn = new TableColumn(props.getProperty(TYPE_LABEL));
        parameterNameColumn = new TableColumn(props.getProperty(NAME_LABEL));
        
        parameterTypeColumn.setCellValueFactory(new PropertyValueFactory("type"));
        parameterNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        
        parameterTypeColumn.setPrefWidth(150);
        parameterNameColumn.setPrefWidth(150);

        parameterTable.getColumns().add(parameterTypeColumn);
        parameterTable.getColumns().add(parameterNameColumn);
        
        parameterTable.setEditable(true);
        
        parameterEditBox.getChildren().addAll(paramsLabelsFormatter, parameterTable);
        
        parameterEditBox.setSpacing(10);
        
        
        
        
        componentToolbar.getChildren().addAll(row1Box, row2Box, row3Box, row4Box, methodsEditBox);
        componentToolbar.setPrefWidth(300);
        componentToolbar.setMinWidth(490);
        componentTBSP = new ScrollPane(componentToolbar);
        componentTBSP.setFitToHeight(true);
        
        canvasContainer = new ScrollPane(canvas);
        canvasContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        canvasContainer.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        
        canvas.setMinHeight(gui.getWindow().getHeight()*2);
        canvas.setMinWidth(gui.getWindow().getWidth()*2);
        canvas.setOnMousePressed(e->{
            data.addClass((int)e.getX(),(int)e.getY());
        });
          
        grid = new Pane();
        grid.setMinSize(canvas.getMinWidth(), canvas.getMinHeight());
        grid.setMaxSize(canvas.getMinWidth(), canvas.getMinHeight());
        int gridsize = 20;
        int vLinesToDraw = (int) (canvas.getMinWidth()) / gridsize;
        int hLinesToDraw = (int) (canvas.getMinHeight()) / gridsize;
        
        for (int i = 0; i < vLinesToDraw; i++) {
            Line toDraw = new Line();
            toDraw.setStartX(i * gridsize);
            toDraw.setStartY(0);
            
            toDraw.setEndX(i * gridsize);
            toDraw.setEndY(canvas.getMinHeight());
            
            toDraw.setFill(Color.BLACK);
            toDraw.setStrokeWidth(1);
            toDraw.setOpacity(.4);
            
            grid.getChildren().add(toDraw);
        }
        for (int i = 0; i < hLinesToDraw; i++) {
            Line toDraw = new Line();
            toDraw.setStartX(0);
            toDraw.setStartY(i * gridsize);
            
            toDraw.setEndX(canvas.getMinWidth());
            toDraw.setEndY(i * gridsize);
            
            toDraw.setFill(Color.BLACK);
            toDraw.setStrokeWidth(1);
            toDraw.setOpacity(.4);
            
            grid.getChildren().add(toDraw);
        }
        
        
        workspace = new BorderPane();
        
        ((BorderPane)workspace).setRight(componentToolbar);
        ((BorderPane)workspace).setCenter(canvasContainer);

        
        photoButton.setDisable(true);
        codeButton.setDisable(true);
        resizeButton.setDisable(true);
        addInterfaceButton.setDisable(true);
        removeButton.setDisable(true);
        undoButton.setDisable(true);
        redoButton.setDisable(true);
        
        initStyle();
        setupHandlers();
    }
    
    private void setupHandlers() {
        pec = new ProjectEditController(app);
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        DataManager data = (DataManager)app.getDataComponent();
        classNameTextBox.setOnKeyPressed(e->{
            data.updateSelectedName(classNameTextBox.getText());
        });
        classNameTextBox.setOnKeyReleased(e->{
            data.updateSelectedName(classNameTextBox.getText());
        });
        
        
        
        varNameColumn.setCellFactory(TextFieldTableCell.<Attribute>forTableColumn());
        varNameColumn.setOnEditCommit(
                (CellEditEvent<Attribute, String> t) -> {
                    data.createUndoAction();
                    data.clearRedo();
                    ((Attribute) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setName(t.getNewValue());
                    reloadCanvas();
                });
        varTypeColumn.setCellFactory(TextFieldTableCell.<Attribute>forTableColumn());
        varTypeColumn.setOnEditCommit(
                (CellEditEvent<Attribute, String> t) -> {
                    data.createUndoAction();
                    data.clearRedo();
                    ((Attribute) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setType(t.getNewValue());
                    data.checkAggregate(t.getTableView().getItems().get(
                            t.getTablePosition().getRow()), data.getSelectedClass());
                    reloadCanvas();
                });
        
        ObservableList<String> visibility = FXCollections.observableArrayList();
        visibility.add(Attribute.PRIVATE);
        visibility.add(Attribute.PUBLIC);
        visibility.add(Attribute.PROTECTED);
        varVisibilityColumn.setCellFactory(ComboBoxTableCell.<Attribute,String>forTableColumn(visibility));
        varVisibilityColumn.setOnEditCommit(
                (CellEditEvent<Attribute, String> t) -> {
                    data.createUndoAction();
                    data.clearRedo();
                    ((Attribute) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setVisibility(t.getNewValue());
                    reloadCanvas();
                });
        
        ObservableList<String> staticOrNot = FXCollections.observableArrayList(Attribute.STATIC,Attribute.NOT_STATIC);
        varStaticColumn.setCellFactory(ComboBoxTableCell.<Attribute,String>forTableColumn(staticOrNot));
        varStaticColumn.setOnEditCommit(
                (CellEditEvent<Attribute, String> t) -> {
                    data.createUndoAction();
                    data.clearRedo();
                    ((Attribute) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setStatic(t.getNewValue());
                    reloadCanvas();
                });
        
        
        
        methodTable.setOnMouseClicked(e-> {
            if (methodTable.getSelectionModel().getSelectedItem() != null) {
                editParameterButton.setDisable(false);
            }
            else editParameterButton.setDisable(true);
        });
        
        methodNameColumn.setCellFactory(TextFieldTableCell.<Method>forTableColumn());
        methodNameColumn.setOnEditCommit(
                (CellEditEvent<Method, String> t) -> {
                    data.createUndoAction();
                    data.clearRedo();
                    ((Method) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setName(t.getNewValue());
                    reloadCanvas();
                });
        methodReturnTypeColumn.setCellFactory(TextFieldTableCell.<Method>forTableColumn());
        methodReturnTypeColumn.setOnEditCommit(
                (CellEditEvent<Method, String> t) -> {
                    data.createUndoAction();
                    data.clearRedo();
                    ((Method) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setReturnType(t.getNewValue());
                    reloadCanvas();
                });
        
        methodVisibilityColumn.setCellFactory(ComboBoxTableCell.<Method,String>forTableColumn(visibility));
        methodVisibilityColumn.setOnEditCommit(
                (CellEditEvent<Method, String> t) -> {
                    data.createUndoAction();
                    data.clearRedo();
                    ((Method) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setVisibility(t.getNewValue());
                    reloadCanvas();
                });
        
        methodStaticColumn.setCellFactory(ComboBoxTableCell.<Method,String>forTableColumn(staticOrNot));
        methodStaticColumn.setOnEditCommit(
                (CellEditEvent<Method, String> t) -> {
                    data.createUndoAction();
                    data.clearRedo();
                    ((Method) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setStatic(t.getNewValue());
                    reloadCanvas();
                });
        
        parameterNameColumn.setCellFactory(TextFieldTableCell.<Attribute>forTableColumn());
        parameterNameColumn.setOnEditCommit(
                (CellEditEvent<Attribute, String> t) -> {
                    data.createUndoAction();
                    data.clearRedo();
                    ((Attribute) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setName(t.getNewValue());
                    reloadCanvas();
                });
        parameterTypeColumn.setCellFactory(TextFieldTableCell.<Attribute>forTableColumn());
        parameterTypeColumn.setOnEditCommit(
                (CellEditEvent<Attribute, String> t) -> {
                    data.createUndoAction();
                    data.clearRedo();
                    ((Attribute) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setType(t.getNewValue());
                    reloadCanvas();
                });
        
        packageNameTextBox.setOnKeyPressed(e->{
            data.updateSelectedPackageName(packageNameTextBox.getText());
        });
        packageNameTextBox.setOnKeyReleased(e->{
            data.updateSelectedPackageName(packageNameTextBox.getText());
        });
        
        codeButton.setOnAction(e->{
            FileManager fman = (FileManager)app.getFileComponent();
            DirectoryChooser fc = new DirectoryChooser();
            
            fc.setInitialDirectory(new File(PATH_WORK));
            fc.setTitle(props.getProperty(SAVE_WORK_TITLE));
            
            File selectedFile = fc.showDialog(app.getGUI().getWindow());
            if (selectedFile != null) {
                try {
                    fman.exportData(data, selectedFile.getPath());
                } catch (IOException ioex) {
                    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                    dialog.show(props.getProperty(EXPORT_ERROR_TITLE), props.getProperty(EXPORT_ERROR_MESSAGE));
                }
            }
        });
        
        gridCheckBox.setOnAction(e->{
            processGridToggle();
        });
        
        selectButton.setOnAction(e->{
            pec.processSelectSelectionTool();
        });
        resizeButton.setOnAction(e->{
            pec.processSelectSizeTool();
        });
        addClassButton.setOnAction(e->{
            pec.processSelectAddClass();
        });
        addInterfaceButton.setOnAction(e->{
            pec.processSelectAddInterface();
        });
        removeButton.setOnAction(e->{
            pec.processRemoveClass();
        });
        
        undoButton.setOnAction(e->{
            data.undo();
            clearInformation();
            reloadWorkspace();
        });
        redoButton.setOnAction(e->{
            data.redo();
            clearInformation();
            reloadWorkspace();
        });
        
        photoButton.setOnAction(e->{
            pec.processSnapshot();
        });
        
        
        addParentButton.setOnAction(e->{
            String name = parentComboBox.getSelectionModel().getSelectedItem();
            pec.handleAddParent(name);
        });
        removeParentButton.setOnAction(e->{
            String name = parentComboBox.getSelectionModel().getSelectedItem();
            pec.handleRemoveParent(name);
        });
        
        
        addVarButton.setOnAction(e->{
            data.getSelectedClass().addNewAttribute();
            varTable.setItems(FXCollections.observableArrayList(data.getSelectedClass().getAttributes()));
            reloadCanvas();
        });
        removeVarButton.setOnAction(e->{
            data.getSelectedClass().removeAttribute(varTable.getSelectionModel().getSelectedItem());
            varTable.setItems(FXCollections.observableArrayList(data.getSelectedClass().getAttributes()));
            reloadCanvas();
        });
        
        addMethodButton.setOnAction(e->{
            data.getSelectedClass().addNewMethod();
            methodTable.setItems(FXCollections.observableArrayList(data.getSelectedClass().getMethods()));
            reloadCanvas();
        });
        removeMethodButton.setOnAction(e->{
            data.getSelectedClass().removeMethod(methodTable.getSelectionModel().getSelectedItem());
            methodTable.setItems(FXCollections.observableArrayList(data.getSelectedClass().getMethods()));
            reloadCanvas();
        });
        
        addParameterButton.setOnAction(e->{
            Method toEdit = methodTable.getSelectionModel().getSelectedItem();
            toEdit.addParameter();
            parameterTable.setItems(FXCollections.observableArrayList(toEdit.getParameters()));
            reloadCanvas();
        });
        removeParameterButton.setOnAction(e->{
            Method toEdit = methodTable.getSelectionModel().getSelectedItem();
            toEdit.removeParameter(parameterTable.getSelectionModel().getSelectedItem());
            parameterTable.setItems(FXCollections.observableArrayList(toEdit.getParameters()));
            reloadCanvas();
        });
        
        editParameterButton.setOnAction(e->{
            componentToolbar.getChildren().remove(methodsEditBox);
            componentToolbar.getChildren().add(parameterEditBox);
            
            Method editing = methodTable.getSelectionModel().getSelectedItem();
            parametersLabel.setText(props.getProperty(PARAMETERS_LABEL.toString()) + editing.getName() + ": ");
            
            parameterTable.setItems(FXCollections.observableArrayList(editing.getParameters()));
            
        });
        exitButton.setOnMouseClicked(e->{
            componentToolbar.getChildren().remove(parameterEditBox);
            componentToolbar.getChildren().add(methodsEditBox);
            
        });
        
        canvasController = new CanvasController(app);
        canvas.setOnMousePressed(e->{
            canvasController.processCanvasMousePress((int)e.getX(), (int)e.getY());
        });
        canvas.setOnMouseReleased(e->{
	    canvasController.processCanvasMouseRelease((int)e.getX(), (int)e.getY());
	});
	canvas.setOnMouseDragged(e->{
	    canvasController.processCanvasMouseDragged((int)e.getX(), (int)e.getY());
	});
	canvas.setOnMouseExited(e->{
	    canvasController.processCanvasMouseExited((int)e.getX(), (int)e.getY());
	});
	canvas.setOnMouseMoved(e->{
	    canvasController.processCanvasMouseMoved((int)e.getX(), (int)e.getY());
	});
    }
    
    public Pane getCanvas() {
        return canvas;
    }
    
    @Override
    public void initStyle() {
        componentTBSP.getStyleClass().add(CLASS_BORDERED_PANE);
        classToolbar.getStyleClass().add(CLASS_BORDERED_PANE);
        viewToolbar.getStyleClass().add(CLASS_BORDERED_PANE);
        row1Box.getStyleClass().add(CLASS_BORDERED_PANE);
        row2Box.getStyleClass().add(CLASS_BORDERED_PANE);
        row3Box.getStyleClass().add(CLASS_BORDERED_PANE);
        row4Box.getStyleClass().add(CLASS_BORDERED_PANE);
        methodsEditBox.getStyleClass().add(CLASS_BORDERED_PANE);
        parameterEditBox.getStyleClass().add(CLASS_BORDERED_PANE);
    }
    
    public void clearInformation() {
        classNameTextBox.setText("");
        packageNameTextBox.setText("");
        parentComboBox.setItems(FXCollections.observableArrayList());
        varTable.setItems(FXCollections.observableArrayList());
        varTable.setEditable(false);
        methodTable.setItems(FXCollections.observableArrayList());
        methodTable.setEditable(false);
        addParentButton.setDisable(true);
        removeParentButton.setDisable(true);
        addVarButton.setDisable(true);
        removeVarButton.setDisable(true);
        addMethodButton.setDisable(true);
        removeMethodButton.setDisable(true);
        editParameterButton.setDisable(true);
        if (componentToolbar.getChildren().contains(parameterEditBox)) {
            componentToolbar.getChildren().remove(parameterEditBox);
            componentToolbar.getChildren().add(methodsEditBox);
        }
    }
    
    public void loadSelectedClassSettings(Class c) {
        classNameTextBox.setText(c.getName());
        packageNameTextBox.setText(c.getPackageName());
        ObservableList<String> parents = FXCollections.observableArrayList();
        for (Class names : ((DataManager)app.getDataComponent()).getClasses()) {
            if (!c.getName().equals(names.getName()))
                parents.add(names.getName());
        }
        
        parentComboBox.setItems(parents);
        
        varTable.setItems(FXCollections.observableArrayList(c.getAttributes()));
        methodTable.setItems(FXCollections.observableArrayList(c.getMethods()));
        varTable.setEditable(true);
        methodTable.setEditable(true);
        addParentButton.setDisable(false);
        removeParentButton.setDisable(false);
        addVarButton.setDisable(false);
        removeVarButton.setDisable(false);
        addMethodButton.setDisable(false);
        removeMethodButton.setDisable(false);
    }
    
    private void drawLines() {
        DataManager data = (DataManager)app.getDataComponent();
        for (ConnectorLine cl : data.getLines()) {
            for (int i = 0; i < cl.getEndpoints().size() -1; i++) {
                
                Line lineToDraw = new Line();
                
                canvas.getChildren().add(lineToDraw);
                
                
                lineToDraw.setStartX(cl.getEndpoints().get(i).getX());
                lineToDraw.setStartY(cl.getEndpoints().get(i).getY());
                
                lineToDraw.setEndX(cl.getEndpoints().get(i+1).getX());
                lineToDraw.setEndY(cl.getEndpoints().get(i+1).getY());
                
                double sX = lineToDraw.getStartX();
                double sY = lineToDraw.getStartY();
                double eX = lineToDraw.getEndX();
                double eY = lineToDraw.getEndY();

                Point2D firstPoint = new Point2D(sX, sY);
                Point2D secondPoint = new Point2D(eX, eY);
                Point2D vertical = new Point2D(sX, eY);

                double toTilt = firstPoint.angle(vertical, secondPoint);
                
                if (sX <= eX && sY <= eY) {
                    toTilt *= -1;
                    toTilt += 180;
                    if (Double.isNaN(toTilt)) toTilt = 90;
                } else if (eX <= sX && sY <= eY) {
                    toTilt += 180;
                    if (Double.isNaN(toTilt)) toTilt = 270;
                } else if (eX <= sX && eY <= sY) {
                    toTilt *= -1;
                    if (Double.isNaN(toTilt)) toTilt = 270;
                } else {
                    if (Double.isNaN(toTilt)) toTilt = 270;
                }
                
                if (cl.getEndpoints().get(i+1).getEndpointType().equals(Endpoint.ARROW)) {
                    double deltaX = lineToDraw.getStartX() - lineToDraw.getEndX();
                    double deltaY = lineToDraw.getStartY() - lineToDraw.getEndY();
                    
                    
                    
                    
                    Polygon arrowhead = new Polygon();
                    String side = cl.getFromClass().sideToRenderLine(cl.getToClass());
                    arrowhead.getPoints().addAll(new Double[]{
                            lineToDraw.getEndX(), lineToDraw.getEndY(),
                            lineToDraw.getEndX() + 7, lineToDraw.getEndY() + 15,
                            lineToDraw.getEndX() - 7, lineToDraw.getEndY() + 15
                    });
                    arrowhead.getTransforms().add(new Rotate(toTilt, lineToDraw.getEndX(), lineToDraw.getEndY()));
                    canvas.getChildren().add(arrowhead);
                }
                else if (cl.getEndpoints().get(i+1).getEndpointType().equals(Endpoint.DIAMOND)) {
                    Polygon arrowhead = new Polygon();
                    String side = cl.getFromClass().sideToRenderLine(cl.getToClass());
                    arrowhead.getPoints().addAll(new Double[]{
                            lineToDraw.getEndX(), lineToDraw.getEndY(),
                            lineToDraw.getEndX() + 7, lineToDraw.getEndY() + 15,
                            lineToDraw.getEndX(), lineToDraw.getEndY() + 30,
                            lineToDraw.getEndX() - 7, lineToDraw.getEndY() + 15
                    });arrowhead.getTransforms().add(new Rotate(toTilt, lineToDraw.getEndX(), lineToDraw.getEndY()));
                    canvas.getChildren().add(arrowhead);
                }
            }
            
        }
        
    }
    
    private VBox generateClass(Class c) {
        DropShadow dropShadowEffect = new DropShadow();
	dropShadowEffect.setOffsetX(0.0f);
	dropShadowEffect.setOffsetY(0.0f);
	dropShadowEffect.setSpread(1.0);
	dropShadowEffect.setColor(Color.AQUA);
	dropShadowEffect.setBlurType(BlurType.GAUSSIAN);
	dropShadowEffect.setRadius(3);
	Effect highlightedEffect = dropShadowEffect;
        
        VBox newClass = new VBox();
        
        newClass.setMinWidth(200);
        newClass.setMinHeight(100);
        
        boolean overrideSizing = false;
        boolean customSizing = c.isCustomSized();
        Pane namePane = new Pane();
        
        double height = 0;
        double largestWidth = c.getWidth();
        
        namePane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        Label nameLbl = new Label();
        nameLbl.setPadding(new Insets(5));
        Text nameTxt = new Text(c.getName());
        nameLbl.setText(nameTxt.getText());
        if (c.getType().equals(Class.INTERFACE)) {
            nameLbl.setText("<<interface>>\n" + c.getName());
            nameTxt = new Text("<<interface>>\n" + c.getName());
        }
        else if (c.getType().equals(Class.ABSTRACT)) {
            nameLbl.setText("{abtract}\n" + c.getName());
            nameTxt = new Text("{abtract}\n" + c.getName());
        }
        
        namePane.getChildren().add(nameLbl);
        namePane.getStyleClass().add(CLASS_ELEMENT);
        newClass.getChildren().add(namePane);
        
        if (nameTxt.getLayoutBounds().getWidth() > largestWidth) largestWidth = nameTxt.getLayoutBounds().getWidth();
        height+=nameTxt.getLayoutBounds().getHeight();
        
        
        if (c.getAttributes().size() > 0) {
            Pane attributePane = new Pane();
            attributePane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
            Label attrLbl = new Label();
            String attrString = "";
            for (Attribute a: c.getAttributes()) {
                Text attrtxt = new Text(a.toString());
                if (a.getVisibility().equals(Attribute.PUBLIC)) attrString+="+";
                else if (a.getVisibility().equals(Attribute.PRIVATE)) attrString+="-";
                else if (a.getVisibility().equals(Attribute.PROTECTED)) attrString+="#";
                
                if (a.isStatic()) attrString+="$";
                
                attrString+= a.toString() + "\n";
                if (attrtxt.getLayoutBounds().getWidth()+10 > largestWidth) largestWidth = attrtxt.getLayoutBounds().getWidth()+10;
                height+=attrtxt.getLayoutBounds().getHeight();
            }
            attrLbl.setText(attrString);
            attrLbl.setPadding(new Insets(5));
            overrideSizing = true;
            attributePane.getChildren().add(attrLbl);
            //attributePane.setMaxHeight(Double.MAX_VALUE);
            attributePane.getStyleClass().add(CLASS_ELEMENT);
            newClass.getChildren().add(attributePane);
        }
        if (c.getMethods().size() > 0) {
            
            if (c.getAttributes().isEmpty()) {
                Pane attributePane = new Pane();
                attributePane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
                Label attrLbl = new Label();
                String attrString = " ";
                attrLbl.setText(attrString);
                attrLbl.setPadding(new Insets(5));
                attributePane.getChildren().add(attrLbl);
                //attributePane.setMaxHeight(Double.MAX_VALUE);
                attributePane.getStyleClass().add(CLASS_ELEMENT);
                newClass.getChildren().add(attributePane);
            }
            Pane methodPane = new Pane();
            methodPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
            Label mtdLbl = new Label();
            String methodString = "";
            for (Method m: c.getMethods()) {
                Text methtxt = new Text(m.toString());
                if (m.getVisibility().equals(Attribute.PUBLIC)) methodString+="+";
                else if (m.getVisibility().equals(Attribute.PRIVATE)) methodString+="-";
                else if (m.getVisibility().equals(Attribute.PROTECTED)) methodString+="#";
                
                if (m.isStatic()) methodString+="$";
                
                methodString+= m.toString() + "\n";
                double methWidth = Toolkit.getToolkit().getFontLoader().computeStringWidth(m.toString(), mtdLbl.getFont());
                if (methtxt.getLayoutBounds().getWidth() > largestWidth) largestWidth = methtxt.getLayoutBounds().getWidth();
                height+=methtxt.getLayoutBounds().getHeight();
            }
            mtdLbl.setText(methodString);
            mtdLbl.setPadding(new Insets(5));
            overrideSizing = true;
            methodPane.getChildren().add(mtdLbl);
            //methodPane.setMaxHeight(Double.MAX_VALUE);
            methodPane.getStyleClass().add(CLASS_ELEMENT);
            newClass.getChildren().add(methodPane); 
        }
        
        if (c.getMethods().size() + c.getAttributes().size() == 0) {
            namePane.prefHeightProperty().bind(newClass.heightProperty());
            nameLbl.setTextAlignment(TextAlignment.CENTER);
            newClass.setAlignment(Pos.CENTER);
        }
        
        if (c.isHighlighted()) {
            newClass.setEffect(highlightedEffect);
        }
        
        
        newClass.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        if (customSizing) {
            newClass.setMinHeight(c.getHeight());
        }
        else if (height > c.getHeight() || overrideSizing == true) {
            newClass.setMinHeight(height);
        } else {
            newClass.setMinHeight(c.getHeight());
        }
        
        newClass.setMaxHeight(Double.MAX_VALUE);
        
        DataManager data = (DataManager)app.getDataComponent();
        
        c.setWidth(largestWidth);
        newClass.setMinWidth(largestWidth);
        
        newClass.setLayoutX(0);
        newClass.setLayoutY(0);
        
        
        return newClass;
    }
    
    public void reloadCanvas() {
        canvas.getChildren().clear();
        DataManager data = (DataManager)app.getDataComponent();
        if (gridCheckBox.isSelected()) {
            canvas.getChildren().add(grid);
        }
        
        for (Class c : data.getClasses()) {
            VBox cP = generateClass(c);
            canvas.getChildren().add(cP);
            cP.relocate(c.getX(), c.getY());
        }
        
        drawLines();
    }
    
    
    private void processGridToggle() {
        reloadCanvas();
    }
    
    public boolean isSnapSelected() {
        return snapCheckBox.isSelected();
    }
    
    @Override
    public void reloadWorkspace() {
        

        
        DataManager data = (DataManager)app.getDataComponent();
        
        boolean editingClass = (data.getSelectedClass() != null);
        
        redoButton.setDisable(data.redoEmpty());
        undoButton.setDisable(data.undoEmpty());
        
        classNameTextBox.setEditable(editingClass);
        packageNameTextBox.setEditable(editingClass);
        reloadCanvas();
        
    }
    
}
