/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jclassdesigner.data;

/**
 *
 * @author thisi
 */
public class Endpoint {
    public static final String ARROW = "arrow";
    public static final String DIAMOND = "diamond";
    public static final String NONE = "none";
    
    private int x;
    private int y;
    private String endpointType;
    public Endpoint(int x, int y) {
        this.x = x;
        this.y = y;
        endpointType = NONE;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the endpointType
     */
    public String getEndpointType() {
        return endpointType;
    }

    /**
     * @param endpointType the endpointType to set
     */
    public void setEndpointType(String endpointType) {
        this.endpointType = endpointType;
    }
    
}
