package org.uade.structure.implementation.dynamic;

import org.uade.structure.definition.GraphADT;
import org.uade.structure.definition.SetADT;
import org.uade.structure.implementation.common.EdgeNode;
import org.uade.structure.implementation.common.VertexNode;

public class DynamicGraphADT implements GraphADT {

    private VertexNode vertices;

    public DynamicGraphADT() {
        this.vertices = null;
    }

    @Override
    public SetADT getVertxs() {
        throw new UnsupportedOperationException("Falta implementar usando SetADT");
    }

    @Override
    public void addVertx(int vertex) {
        if (findVertex(vertex) == null) {
            VertexNode newVertex = new VertexNode(vertex);
            newVertex.nextVertex = vertices;
            vertices = newVertex;
        }
    }

    @Override
    public void removeVertx(int vertex) {
        if (vertices == null) return;

        if (vertices.value == vertex) {
            vertices = vertices.nextVertex;
            return;
        }

        // Buscar el vértice anterior
        VertexNode current = vertices;
        while (current.nextVertex != null && current.nextVertex.value != vertex) {
            current = current.nextVertex;
        }

        if (current.nextVertex != null) {
            current.nextVertex = current.nextVertex.nextVertex;
        }
    }

    @Override
    public void addEdge(int vertxOne, int vertxTwo, int weight) {
        VertexNode v1 = findVertex(vertxOne);
        VertexNode v2 = findVertex(vertxTwo);

        if (v1 != null && v2 != null) {
            EdgeNode newEdge = new EdgeNode(weight, v2);
            newEdge.nextEdge = v1.firstEdge;
            v1.firstEdge = newEdge;
        }
    }

    @Override
    public void removeEdge(int vertxOne, int vertxTwo) {
        VertexNode v1 = findVertex(vertxOne);
        if (v1 == null) return;

        EdgeNode current = v1.firstEdge;
        EdgeNode prev = null;

        while (current != null && current.destination.value != vertxTwo) {
            prev = current;
            current = current.nextEdge;
        }

        if (current != null) {
            if (prev == null) {
                v1.firstEdge = current.nextEdge;
            } else {
                prev.nextEdge = current.nextEdge;
            }
        }
    }

    @Override
    public boolean existsEdge(int vertxOne, int vertxTwo) {
        VertexNode v1 = findVertex(vertxOne);
        if (v1 == null) return false;

        EdgeNode current = v1.firstEdge;
        while (current != null) {
            if (current.destination.value == vertxTwo) {
                return true;
            }
            current = current.nextEdge;
        }
        return false;
    }

    @Override
    public int edgeWeight(int vertxOne, int vertxTwo) {
        VertexNode v1 = findVertex(vertxOne);
        if (v1 == null) {
            throw new IllegalArgumentException("El vértice no existe");
        }

        EdgeNode current = v1.firstEdge;
        while (current != null) {
            if (current.destination.value == vertxTwo) {
                return current.weight;
            }
            current = current.nextEdge;
        }

        throw new IllegalArgumentException("La arista no existe");
    }

    @Override
    public boolean isEmpty() {
        return vertices == null;
    }

    private VertexNode findVertex(int value) {
        VertexNode current = vertices;
        while (current != null) {
            if (current.value == value) {
                return current;
            }
            current = current.nextVertex;
        }
        return null;
    }
}

