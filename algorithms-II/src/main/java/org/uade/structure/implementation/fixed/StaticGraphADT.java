package org.uade.structure.implementation.fixed;

public class StaticGraphADT {

    private final int MAX_VERTICES;
    private boolean[][] adjMatrix;
    private int size;

    public StaticGraphADT(int maxVertices) {
        this.MAX_VERTICES = maxVertices;
        this.adjMatrix = new boolean[MAX_VERTICES][MAX_VERTICES];
        this.size = 0;
    }


    public void addVertex() {
        if (size >= MAX_VERTICES) {
            throw new IllegalStateException("Cantidad máxima de vértices alcanzada");
        }

        size++;
    }


    public void addEdge(int from, int to) {
        if (from < 0 || from >= size || to < 0 || to >= size) {
            throw new IndexOutOfBoundsException("Vértice inexistente");
        }
        adjMatrix[from][to] = true;
        adjMatrix[to][from] = true; // quitar esta línea si es grafo dirigido
    }


    public void removeEdge(int from, int to) {
        if (from < 0 || from >= size || to < 0 || to >= size) {
            throw new IndexOutOfBoundsException("Vértice inexistente");
        }
        adjMatrix[from][to] = false;
        adjMatrix[to][from] = false;
    }


    public boolean hasEdge(int from, int to) {
        if (from < 0 || from >= size || to < 0 || to >= size) {
            throw new IndexOutOfBoundsException("Vértice inexistente");
        }
        return adjMatrix[from][to];
    }


    public int getSize() {
        return size;
    }


    public boolean isEmpty() {
        return size == 0;
    }


    public void printGraph() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print((adjMatrix[i][j] ? 1 : 0) + " ");
            }
            System.out.println();
        }
    }
}
