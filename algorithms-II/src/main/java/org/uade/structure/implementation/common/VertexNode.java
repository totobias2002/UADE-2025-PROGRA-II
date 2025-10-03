package org.uade.structure.implementation.common;

public class VertexNode {
    public int value;
    public EdgeNode firstEdge;
    public VertexNode nextVertex;

    public VertexNode(int value) {
        this.value = value;
        this.firstEdge = null;
        this.nextVertex = null;
    }
}