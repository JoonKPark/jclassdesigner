package jclassdesigner.data;

/**
 * This interface represents a family of draggable shapes.
 * 
 * @author McKillaGorilla
 * @version 1.0
 */
public interface Draggable {
    public static final String CLASS = "CLASS";
    public static final String INTERFACE = "INTERFACE";
    public static final String ABSTRACT = "ABSTRACT";
    //public PoseMakerState getStartingState();
    public void start(int x, int y);
    public void drag(int x, int y);
    public void size(int x, int y);
    public double getX();
    public double getY();
    public double getWidth();
    public double getHeight();
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight);

    public String getType();
}
