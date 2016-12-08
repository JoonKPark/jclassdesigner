/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jclassdesigner.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import jclassdesigner.data.Attribute;
import jclassdesigner.data.DataManager;
import jclassdesigner.data.Class;
import jclassdesigner.data.ConnectorLine;
import jclassdesigner.data.Draggable;
import jclassdesigner.data.Endpoint;
import jclassdesigner.data.Interface;
import jclassdesigner.data.Method;
import saf.components.AppDataComponent;
import saf.components.AppFileComponent;

/**
 *
 * @author thisi
 */
public class FileManager implements AppFileComponent {
    static final String JSON_OBJECTS = "objects";
    static final String JSON_ATTRIBUTES = "attributes";
    static final String JSON_AGGREGATE_OF = "aggregate_of";
    static final String JSON_AGGREGATES = "aggregates";
    static final String JSON_EXTENDS = "extends";
    static final String JSON_IMPLEMENTS = "implements";
    static final String JSON_ATTR_NAME = "attribute_name";
    static final String JSON_ATTR_VISIBILITY = "attribute_visibility";
    static final String JSON_ATTR_STATIC = "attribute_is_static";
    static final String JSON_ATTR_TYPE = "attribute_type";
    static final String JSON_TYPE = "type";
    static final String JSON_NAME = "name";
    static final String JSON_PACKAGE_NAME = "package";
    static final String JSON_METHODS = "methods";
    static final String JSON_METHOD_NAME = "method_name";
    static final String JSON_METHOD_PARAMS = "parameters";
    static final String JSON_RETURN_TYPE = "return_type";
    static final String JSON_X = "x";
    static final String JSON_Y = "y";
    static final String JSON_WIDTH = "width";
    static final String JSON_HEIGHT = "height";
    
    static final String JSON_CONNECTORS = "connectors";
    static final String JSON_ENDPOINTS = "endpoints";
    static final String JSON_ENDPOINT_X = "endpoint_x";
    static final String JSON_ENDPOINT_Y = "endpoint_y";
    static final String JSON_ARROW_TYPE = "arrow_type";
    static final String JSON_CLASS_FROM = "class_from";
    static final String JSON_CLASS_TO = "class_to";
    
    @Override
    public void saveData (AppDataComponent data, String filePath) throws IOException {
        
	DataManager dataManager = (DataManager)data;
        ArrayList<Class> classes = dataManager.getClasses();
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Class c: classes) {
            String type = c.getType();
            String name = c.getName();
            String pkg = c.getPackageName();
            double x = c.getX();
            double y = c.getY();
            double width = c.getWidth();
            double height = c.getHeight();
            
            JsonObject classJson = Json.createObjectBuilder()
                    .add(JSON_NAME, name)
                    .add(JSON_TYPE, type)
                    .add(JSON_PACKAGE_NAME, pkg)
                    .add(JSON_AGGREGATE_OF, makeJsonClassArray(c.getAggregateOfList()))
                    .add(JSON_AGGREGATES, makeJsonClassArray(c.getAggregatesList()))
                    .add(JSON_IMPLEMENTS, makeJsonClassArray(c.getImplementList()))
                    .add(JSON_EXTENDS, makeJsonClassArray(c.getExtendsList()))
                    .add(JSON_X, x)
                    .add(JSON_Y, y)
                    .add(JSON_WIDTH, width)
                    .add(JSON_HEIGHT, height)
                    .add(JSON_ATTRIBUTES,makeJsonAttrArray(c.getAttributes()))
                    .add(JSON_METHODS,makeJsonMethodArray(c.getMethods()))
                    .build();
            arrayBuilder.add(classJson);
        }
        JsonArray classesArray = arrayBuilder.build();
        
        JsonArrayBuilder arrayBuilderLines = Json.createArrayBuilder();
        for (ConnectorLine cl: dataManager.getLines()) {
            JsonObject lineJson = Json.createObjectBuilder()
                    .add(JSON_ENDPOINTS, makeJsonEndpointsArray(cl.getEndpoints()))
                    .add(JSON_CLASS_FROM, cl.getFromClass().getName())
                    .add(JSON_CLASS_TO, cl.getToClass().getName())
                    .build();
            arrayBuilderLines.add(lineJson);
        }
        JsonArray linesArray = arrayBuilderLines.build();
        
        
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_OBJECTS, classesArray)
                .add(JSON_CONNECTORS, linesArray)
                .build();
        
        
 	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING       
        Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    private JsonArray makeJsonEndpointsArray(ArrayList<Endpoint> list) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Endpoint e : list) {
            JsonObject endpoint = Json.createObjectBuilder()
                    .add(JSON_ENDPOINT_X, e.getX())
                    .add(JSON_ENDPOINT_Y, e.getY())
                    .add(JSON_ARROW_TYPE, e.getEndpointType())
                    .build();
            arrayBuilder.add(endpoint);
            
        }
        JsonArray endpoints = arrayBuilder.build();
        return endpoints;
    }
    
    private JsonArray makeJsonClassArray(ArrayList<Class> list) {
	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Class c : list) {
            JsonObject attribute = Json.createObjectBuilder()
                    .add(JSON_NAME, c.getName())
                    .add(JSON_PACKAGE_NAME, c.getPackageName())
                    .build();
            arrayBuilder.add(attribute);
        }
        JsonArray attributes = arrayBuilder.build();
        return attributes;
    }
    
    private JsonArray makeJsonAttrArray(ArrayList<Attribute> list) {
	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Attribute a : list) {
            JsonObject attribute = Json.createObjectBuilder()
                    .add(JSON_ATTR_VISIBILITY, a.getVisibility())
                    .add(JSON_ATTR_STATIC, a.getStatic())
                    .add(JSON_ATTR_NAME, a.getName())
                    .add(JSON_ATTR_TYPE, a.getType())
                    .build();
            arrayBuilder.add(attribute);
        }
        JsonArray attributes = arrayBuilder.build();
        return attributes;
    }
    
    private JsonArray makeJsonMethodArray(ArrayList<Method> list) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Method m : list) {
            JsonObject method = Json.createObjectBuilder()
                    .add(JSON_METHOD_NAME, m.getName())
                    .add(JSON_METHOD_PARAMS, makeJsonAttrArray(m.getParameters()))
                    .add(JSON_RETURN_TYPE, m.getReturnType())
                    .build();
            arrayBuilder.add(method);
        }
        JsonArray methods = arrayBuilder.build();
        return methods;
    }
    
    @Override
    public void loadData (AppDataComponent data, String filePath) throws IOException {
 	// CLEAR THE OLD DATA OUT
	DataManager dataManager = (DataManager)data;
	dataManager.reset();
	
	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);
	
	// AND NOW LOAD ALL THE CLASSES
	JsonArray jsonClassArray = json.getJsonArray(JSON_OBJECTS);
	for (int i = 0; i < jsonClassArray.size(); i++) {
	    JsonObject jsonClass = jsonClassArray.getJsonObject(i);
	    Class tClass = loadClass(jsonClass);
	    dataManager.getClasses().add(tClass);
	}
        
        JsonArray jsonLinesArray = json.getJsonArray(JSON_CONNECTORS);
        for (int i = 0; i < jsonLinesArray.size(); i++) {
            JsonObject jsonLine = jsonLinesArray.getJsonObject(i);
            ConnectorLine tLine = loadLine(jsonLine,data);
            if (tLine != null)
                dataManager.getLines().add(tLine);
        }
    }
    
    public double getDataAsDouble(JsonObject json, String dataName) {
	JsonValue value = json.get(dataName);
	JsonNumber number = (JsonNumber)value;
	return number.bigDecimalValue().doubleValue();	
    }
    
    public Class loadClass(JsonObject jo) {
        String type = jo.getString(JSON_TYPE);
        String name = jo.getString(JSON_NAME);
        String pkg = jo.getString(JSON_PACKAGE_NAME);
        double x = getDataAsDouble(jo,JSON_X);
        double y = getDataAsDouble(jo,JSON_Y);
        double width = getDataAsDouble(jo,JSON_WIDTH);
        double height = getDataAsDouble(jo,JSON_HEIGHT);
        Class c = new Class();
        c.setType(type);
        c.setName(name);
        c.setPackageName(pkg);
        c.setX((int)x);
        c.setY((int)y);
        c.setWidth(width);
        c.setHeight(height);
        c.setMethods(loadMethods(jo));
        c.setAttributes(loadAttributes(jo,JSON_ATTRIBUTES));
        c.setExtendsList(loadClasses(jo,JSON_EXTENDS));
        c.setAggregateOfList(loadClasses(jo,JSON_AGGREGATE_OF));
        c.setAggregatesList(loadClasses(jo,JSON_AGGREGATES));
        c.setImplementList(loadClasses(jo,JSON_IMPLEMENTS));
        return c;
    }
    
    private ConnectorLine loadLine(JsonObject jo, AppDataComponent data) {
	DataManager dataManager = (DataManager)data;
        String fromClassName = jo.getString(JSON_CLASS_FROM);
        String toClassName = jo.getString(JSON_CLASS_TO);
        Class fromClass = dataManager.lookupClass(fromClassName);
        Class toClass = dataManager.lookupClass(toClassName);
        ConnectorLine l = null;
        if (fromClass != null && toClass != null ) {
             l = new ConnectorLine(fromClass,toClass);
            for (Endpoint ep : loadEndpoints(jo,JSON_ENDPOINTS)) {
                l.getEndpoints().add(ep);
            }
        }
        
        return l;
    }
    
    private ArrayList<Endpoint> loadEndpoints (JsonObject json, String toGet) {
        JsonArray jsonEndpointArray = json.getJsonArray(toGet);
        ArrayList<Endpoint> eps = new ArrayList();
        for (int i = 0; i < jsonEndpointArray.size(); i++) {
            JsonObject jsonEnd = jsonEndpointArray.getJsonObject(i);
            Endpoint e = new Endpoint((int)getDataAsDouble(jsonEnd,JSON_ENDPOINT_X),
                    (int)getDataAsDouble(jsonEnd,JSON_ENDPOINT_Y));
            e.setEndpointType(jsonEnd.getString(JSON_ARROW_TYPE));
            eps.add(e);
        }
        return eps;
    }
    private ArrayList<Class> loadClasses(JsonObject json, String toGet) {
        JsonArray jsonClassArray = json.getJsonArray(toGet);
        ArrayList<Class> classes = new ArrayList();
        for (int i = 0; i < jsonClassArray.size(); i++) {
            Class c = new Class();
            JsonObject jsonClass = jsonClassArray.getJsonObject(i);
            c.setName(jsonClass.getString(JSON_NAME));
            c.setPackageName(jsonClass.getString(JSON_PACKAGE_NAME));
            classes.add(c);
        }
        return classes;
    }
    
    private ArrayList<Attribute> loadAttributes(JsonObject json, String toGet) {
        JsonArray jsonAttributeArray = json.getJsonArray(toGet);
        ArrayList<Attribute> attribs = new ArrayList();
        for (int i = 0; i < jsonAttributeArray.size(); i++) {
            Attribute a = new Attribute();
            JsonObject jsonAttr = jsonAttributeArray.getJsonObject(i);
            a.setVisibility(jsonAttr.getString(JSON_ATTR_VISIBILITY));
            a.setStatic(jsonAttr.getString(JSON_ATTR_STATIC));
            a.setName(jsonAttr.getString(JSON_ATTR_NAME));
            a.setType(jsonAttr.getString(JSON_ATTR_TYPE));
            attribs.add(a);
        }
        return attribs;
    }
    
    private ArrayList<Method> loadMethods(JsonObject json) {
	JsonArray jsonMethodArray = json.getJsonArray(JSON_METHODS);
        ArrayList<Method> methods = new ArrayList();
	for (int i = 0; i < jsonMethodArray.size(); i++) {
            Method m = new Method();
	    JsonObject jsonMethod = jsonMethodArray.getJsonObject(i);
            m.setName(jsonMethod.getString(JSON_METHOD_NAME));
            m.setReturnType(jsonMethod.getString(JSON_RETURN_TYPE));
            m.setParameters(loadAttributes(jsonMethod,JSON_METHOD_PARAMS));
            methods.add(m);
        }
        return methods;
    }
    
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }
    
    
    @Override
    public void exportData (AppDataComponent data, String filePath) throws IOException {
        //String sourceDir = filePath + "/src";
        File srcdir = new File(filePath);
        srcdir.mkdir();
        ArrayList<Class> classes = ((DataManager)data).getClasses();
        
        for (Class c : classes) {
            String packageDirectory = c.getPackageName();
            packageDirectory = packageDirectory.replaceAll("\\.", "/");
            packageDirectory = filePath + "/" + packageDirectory + "/";
            
            File pkgDir = new File(packageDirectory);
            pkgDir.mkdirs();
            PrintWriter out = new PrintWriter(pkgDir + "/" + c.getName() + ".java");
            out.println("/*\n"
                      + " * This file has been automatically generated by jClassDesigner\n"
                      + " * export feature. You should change this header to include any\n"
                      + " * licenses and copyrights.\n"
                      + " */");
            out.println("package " + c.getPackageName() + ";\n");
            
            
            if (c.getType().equals(Class.CLASS)) {
                out.print("public class " + c.getName());
                if (!c.getImplementList().isEmpty()) {
                    for (Class ci : c.getImplementList()){
                        out.print(" implements " + ci.getName());
                    }
                }
                if (!c.getExtendsList().isEmpty()) {
                    for (Class ce : c.getExtendsList()) {
                        out.print(" extends " + ce.getName());
                    }
                }
                out.println(" {");
                
                for (Attribute a : c.getAttributes()) {
                    String isStatic = "";
                    if (a.isStatic()) isStatic = "static";
                    out.println("\t" + a.getVisibility() + " " + isStatic + " " + a.getType() + " " + a.getName() + ";");
                }
                
                for (Method m : c.getMethods()) {
                    out.print("\tpublic " + m.getReturnType() + " " + m.getName()
                              + "(");
                    for (Attribute p : m.getParameters()) {
                        if (m.getParameters().indexOf(p) < m.getParameters().size()-1)
                            out.print(p.getType() + " " + p.getName() + ", ");
                        else out.print(p.getType() + " " + p.getName());
                    }
                    out.println(") {\n"
                            + "\t\t// TODO WRITE METHOD FUNCTIONS\n");
                    if (!m.getReturnType().equals("void")) {
                        out.println("\t\t" + m.getReturnType() + " fixme;\n\t\treturn fixme;\n"
                                + "\t}");
                    }
                }
                out.println("}");
                out.close();
            }
            
            else if (c.getType().equals(Class.ABSTRACT)) {
                out.print("public abstract class " + c.getName());
                if (c.getImplementList().size() > 0) {
                    for (Class ci : c.getImplementList()){
                        out.print(" implements " + ci.getName());
                    }
                }
                if (c.getExtendsList().size() > 0) {
                    for (Class ce : c.getExtendsList()) {
                        out.print(" extends " + ce.getName());
                    }
                }
                out.println(" {");
                
                for (Attribute a : c.getAttributes()) {
                    //TODO ADD SUPPORT FOR PRIVATE PROTECTED ETC
                    
                    out.println("\t" + a.getType() + " " + a.getName() + ";");
                }
                
                for (Method m : c.getMethods()) {
                    out.print("\tpublic " + m.getReturnType() + " " + m.getName()
                              + "(");
                    for (Attribute p : m.getParameters()) {
                        if (m.getParameters().indexOf(p) < m.getParameters().size()-1)
                            out.print(p.getType() + " " + p.getName() + ", ");
                        else out.print(p.getType() + " " + p.getName());
                    }
                    out.println(") {}");
                }
                out.println("}");
                out.close();
            }
            
            else if (c.getType().equals(Class.INTERFACE)) {
                out.print("public interface " + c.getName());
                if (c.getExtendsList().size() > 0) {
                    for (Class ce : c.getExtendsList()) {
                        out.print(" extends " + ce.getName());
                    }
                }
                out.println(" {");
                
                for (Attribute a : c.getAttributes()) {
                    //TODO ADD SUPPORT FOR PRIVATE PROTECTED ETC
                    
                    out.println("\t" + a.getType() + " " + a.getName() + ";");
                }
                
                for (Method m : c.getMethods()) {
                    out.print("\tpublic " + m.getReturnType() + " " + m.getName()
                              + "(");
                    for (Attribute p : m.getParameters()) {
                        if (m.getParameters().indexOf(p) < m.getParameters().size()-1)
                            out.print(p.getType() + " " + p.getName() + ", ");
                        else out.print(p.getType() + " " + p.getName());
                    }
                    out.println(") {}");
                }
                out.println("}");
                out.close();
            }
        }
        
    }
    public void importData (AppDataComponent data, String filePath) throws IOException {
        
    }
}
