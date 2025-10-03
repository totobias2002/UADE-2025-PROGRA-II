package org.uade.structure.implementation.common;

public class EdgeNode {
    public int weight;
    public VertexNode destination;
    public EdgeNode nextEdge;

    public EdgeNode(int weight, VertexNode destination) {
        this.weight = weight;
        this.destination = destination;
        this.nextEdge = null;
    }
}
