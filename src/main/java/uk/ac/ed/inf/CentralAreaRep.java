package uk.ac.ed.inf;

import java.util.List;

public class CentralAreaRep {
    private String name;
    private List<LocationRep> vertices;1

    // Constructor
    public CentralAreaRep(String name, List<LocationRep> vertices) {
        this.name = name;
        this.vertices = vertices;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LocationRep> getVertices() {
        return vertices;
    }

    public void setVertices(List<LocationRep> vertices) {
        this.vertices = vertices;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "CentralAreaRep{" +
                "name='" + name + '\'' +
                ", vertices=" + vertices +
                '}';
    }
}
