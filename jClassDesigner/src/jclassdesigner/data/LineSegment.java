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
public class LineSegment {
    private Endpoint[] endpoints = new Endpoint[2];
    public LineSegment(Endpoint e1, Endpoint e2) {
        endpoints[0] = e1;
        endpoints[1] = e2;
    }

    /**
     * @return the endpoints
     */
    public Endpoint[] getEndpoints() {
        return endpoints;
    }

    /**
     * @param endpoints the endpoints to set
     */
    public void setEndpoints(Endpoint[] endpoints) {
        this.endpoints = endpoints;
    }
    
    public void setEndpoint(int i, Endpoint e) {
        if (i == 1 || i == 0) endpoints[i] = e; 
    }
}
