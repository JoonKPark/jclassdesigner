/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jclassdesigner.data;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;
import static java.lang.Math.min;
import java.util.ArrayList;
/*
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
*/

/**
 *
 * @author thisi
 */
public class Class implements Draggable {
    private ArrayList<Class> implementList;
    private ArrayList<Class> aggregateOfList;
    private ArrayList<Class> aggregatesList;
    private ArrayList<Class> extendsList;
    
    public static final String SIDE_LEFT = "left";
    public static final String SIDE_RIGHT = "right";
    public static final String SIDE_UP = "up";
    public static final String SIDE_DOWN = "down";
    
    
    private ArrayList <Attribute> attributes;
    private ArrayList <Method> methods;
    private String name;
    private String packageName;
    private String type;
    
    private boolean highlighted;
    private boolean customSized;
    
    /*
    private Pane namePane;
    private Label nameLbl;
    private Pane attributePane;
    private Label attrLbl;
    private Pane methodPane;
    private Label mtdLbl;
    */
    
    private int x;
    private int y;
    private int startX;
    private int startY;
    private double height;
    private double width;
    
    public Class () {
        implementList = new ArrayList();
        aggregateOfList = new ArrayList();
        aggregatesList = new ArrayList();
        extendsList = new ArrayList();
        attributes = new ArrayList<Attribute>();
        methods = new ArrayList<Method>();
        name = "";
        packageName = "";
        
        startX = 0;
        startY = 0;
        height = 100;
        width = 200;
        type = this.CLASS;
        highlighted = false;
        customSized = false;
    }
    public Class (String intorabs) {
        implementList = new ArrayList();
        aggregateOfList = new ArrayList();
        aggregatesList = new ArrayList();
        extendsList = new ArrayList();
        attributes = new ArrayList<Attribute>();
        methods = new ArrayList<Method>();
        name = "<<" + intorabs.toLowerCase() + ">>";
        packageName = "";
        
        startX = 0;
        startY = 0;
        height = 100;
        width = 200;
        type = intorabs;
        highlighted = false;
    }
    
    
    /**
     * @return the attributes
     */
    public ArrayList <Attribute> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(ArrayList <Attribute> attributes) {
        this.attributes = attributes;
    }
    
    public void addAttribute(Attribute a) {
        attributes.add(a);
    }
    public void addAttributes(Attribute... as) {
        for (Attribute a : as) {
            attributes.add(a);
        }
    }
    
    public void addNewAttribute() {
        Attribute a = new Attribute();
        attributes.add(a);
    }
    public void removeAttribute(Attribute a) {
        attributes.remove(a);
    }
    
    /**
     * @return the methods
     */
    public ArrayList <Method> getMethods() {
        return methods;
    }
    
    /**
     * @param methods the methods to set
     */
    public void setMethods(ArrayList <Method> methods) {
        this.methods = methods;
    }
    
    public void addNewMethod() {
        Method a = new Method();
        methods.add(a);
    }
    
    public void removeMethod(Method m) {
        methods.remove(m);
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

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }
    
    /**
     * @return the packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @param packageName the packageName to set
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    boolean contains(int x, int y) {
        return this.x <= x && 
                this.y <= y &&
                this.x + this.getWidth() >= x &&
                this.y + this.getHeight() >= y;
    }

    @Override
    public void start(int x, int y) {
	startX = x;
	startY = y;
	//relocate(x,y);
    }

    @Override
    public void drag(int x, int y) {
        
	double diffX = x - (getX() + (getWidth()/2));
	double diffY = y - (getY() + (getHeight()/2));
	double newX = getX() + diffX;
	double newY = getY() + diffY;
        this.x = (int) newX;
        this.y = (int) newY;
	startX = x;
	startY = y;
    }
    
    public void drag(int x, int y, boolean snap) {
        if (snap) {
      
            double diffX = x - (getX() + (getWidth() / 2));
            double diffY = y - (getY() + (getHeight() / 2));
            double newX = getX() + diffX;
            double newY = getY() + diffY;
            
            
            int xFloor = (int) floor(newX / 20.0);
            int xCeiling = (int) ceil(newX / 20.0);
            int yFloor = (int) floor(newY / 20.0);
            int yCeiling = (int) ceil(newY / 20.0);

            int closerX = min(xFloor, xCeiling) * 20;
            int closerY = min(yFloor, yCeiling) * 20;
            
            
            this.x = (int) closerX;
            this.y = (int) closerY;
            startX = x;
            startY = y;

        }
        else {
            double diffX = x - (getX() + (getWidth() / 2));
            double diffY = y - (getY() + (getHeight() / 2));
            double newX = getX() + diffX;
            double newY = getY() + diffY;
            this.x = (int) newX;
            this.y = (int) newY;
            startX = x;
            startY = y;
        }
    }
    @Override
    public void size(int x, int y) {
        
        double nwidth = x - getX();
        double nheight = y - getY();
        setWidth((int) nwidth);
        setHeight((int) nheight);

        //setMinSize(nwidth, nheight);
        setCustomSized(true);
        
    }
    
    public void size(int x, int y, boolean snap) {
        if (snap) {
            int xFloor = (int) floor(x / 20.0);
            int xCeiling = (int) ceil(x / 20.0);
            int yFloor = (int) floor(y / 20.0);
            int yCeiling = (int) ceil(y / 20.0);

            int closerX = min(xFloor, xCeiling) * 20;
            int closerY = min(yFloor, yCeiling) * 20;
            
            double nwidth = closerX - getX();
            double nheight = closerY - getY();
            setWidth((int) nwidth);
            setHeight((int) nheight);

        } else {
            double nwidth = x - getX();
            double nheight = y - getY();
            setWidth((int) nwidth);
            setHeight((int) nheight);
        }
        
        //setMinSize(nwidth, nheight);
        setCustomSized(true);
        
    }

    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
        //setMinSize(initWidth,initHeight);
        //relocate(initX,initY);
        x = (int) initX;
        y = (int) initY;
        setWidth((int) initWidth);
        setHeight((int) initHeight);
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(double width) {
        this.width = width;
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
     * @return the type
     */
    @Override
    public String getType() {
        return type;
    }
    public void setType(String t) {
        type = t;
    }

    /**
     * @return the implementList
     */
    public ArrayList<Class> getImplementList() {
        return implementList;
    }

    /**
     * @param implementList the implementList to set
     */
    public void setImplementList(ArrayList<Class> implementList) {
        this.implementList = implementList;
    }

    /**
     * @return the aggregateOfList
     */
    public ArrayList<Class> getAggregateOfList() {
        return aggregateOfList;
    }

    /**
     * @param aggregateOfList the aggregateOfList to set
     */
    public void setAggregateOfList(ArrayList<Class> aggregateOfList) {
        this.aggregateOfList = aggregateOfList;
    }

    /**
     * @return the aggregatesList
     */
    public ArrayList<Class> getAggregatesList() {
        return aggregatesList;
    }

    /**
     * @param aggregatesList the aggregatesList to set
     */
    public void setAggregatesList(ArrayList<Class> aggregatesList) {
        this.aggregatesList = aggregatesList;
    }

    /**
     * @return the extendsList
     */
    public ArrayList<Class> getExtendsList() {
        return extendsList;
    }

    /**
     * @param extendsList the extendsList to set
     */
    public void setExtendsList(ArrayList<Class> extendsList) {
        this.extendsList = extendsList;
    }

    public void addExtension (Class c) {
        extendsList.add(c);
    }
    
    public Class copy() {
        Class clone = new Class();
        clone.setImplementList(implementList);
        clone.setAggregateOfList(aggregateOfList);
        clone.setAttributes(attributes);
        clone.setAggregatesList(aggregatesList);
        clone.setExtendsList(extendsList);
        clone.setLocationAndSize(x, y, width, height);
        clone.setMethods(methods);
        clone.setName(name);
        clone.setPackageName(packageName);
        clone.setType(type);
        clone.setCustomSized(isCustomSized());
        
        return clone;
    }
    
    public String sideToRenderLine(Class c) {
        Endpoint upMp = c.upMidpoint();
        Endpoint downMp = c.downMidpoint();
        Endpoint leftMp = c.leftMidpoint();
        Endpoint rightMp = c.rightMidpoint();
        
        double downToUp = Math.sqrt(Math.pow(downMidpoint().getX() - upMp.getX(), 2) 
                                  + Math.pow(downMidpoint().getY() - upMp.getY(), 2));
        double upToDown = Math.sqrt(Math.pow(upMidpoint().getX() - downMp.getX(), 2) 
                                  + Math.pow(upMidpoint().getY() - downMp.getY(), 2));
        double leftToRight = Math.sqrt(Math.pow(leftMidpoint().getX() - rightMp.getX(), 2) 
                                  + Math.pow(leftMidpoint().getY() - rightMp.getY(), 2));
        double rightToLeft = Math.sqrt(Math.pow(rightMidpoint().getX() - leftMp.getX(), 2) 
                                  + Math.pow(rightMidpoint().getY() - leftMp.getY(), 2));
        
        if (downToUp < upToDown && downToUp < leftToRight && downToUp < rightToLeft) return SIDE_UP;
        else if (upToDown < downToUp && upToDown < leftToRight && upToDown < rightToLeft) return SIDE_DOWN;
        else if (leftToRight < upToDown && leftToRight < downToUp && leftToRight < rightToLeft) return SIDE_RIGHT;
        else return SIDE_LEFT;
        
    }
    
    public Endpoint upMidpoint() {
        return new Endpoint((int)((x+width/2)), y);
    }
    public Endpoint downMidpoint() {
        return new Endpoint((int)((x+width/2)), (int)(y+height));
    }
    public Endpoint leftMidpoint() {
        return new Endpoint((int)x, (int)((y+height/2)));
    }
    public Endpoint rightMidpoint() {
        return new Endpoint((int)(x+width), (int)(y+height/2));
    }

    /**
     * @return the customSized
     */
    public boolean isCustomSized() {
        return customSized;
    }

    /**
     * @param customSized the customSized to set
     */
    public void setCustomSized(boolean customSized) {
        this.customSized = customSized;
    }
}
