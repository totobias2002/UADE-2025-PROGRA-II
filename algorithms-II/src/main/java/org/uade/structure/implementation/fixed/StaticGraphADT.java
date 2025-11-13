package org.uade.structure.implementation.fixed;

import org.uade.structure.definition.GraphADT;
import org.uade.structure.definition.SetADT;

public class StaticGraphADT implements GraphADT {
    private final int MAX_VERTICES = 20;
    private int[][] matriz;
    private boolean[] verticesPresentes;
    private int cantidadVertices;

    public StaticGraphADT() {
        matriz = new int[MAX_VERTICES][MAX_VERTICES];
        verticesPresentes = new boolean[MAX_VERTICES];
        cantidadVertices = 0;
    }

    @Override
    public SetADT getVertxs() {
        throw new UnsupportedOperationException("No se implementó con SetADT todavía");
    }

    @Override
    public void addVertx(int vertex) {
        if (!verticesPresentes[vertex]) {
            verticesPresentes[vertex] = true;
            cantidadVertices++;
        }
    }

    @Override
    public void removeVertx(int vertex) {
        if (verticesPresentes[vertex]) {
            for (int i = 0; i < MAX_VERTICES; i++) {
                matriz[vertex][i] = 0;
                matriz[i][vertex] = 0;
            }
            verticesPresentes[vertex] = false;
            cantidadVertices--;
        }
    }

    @Override
    public void addEdge(int vertxOne, int vertxTwo, int weight) {
        if (verticesPresentes[vertxOne] && verticesPresentes[vertxTwo]) {
            matriz[vertxOne][vertxTwo] = weight;
            matriz[vertxTwo][vertxOne] = weight; // No dirigido
        }
    }

    @Override
    public void removeEdge(int vertxOne, int vertxTwo) {
        matriz[vertxOne][vertxTwo] = 0;
        matriz[vertxTwo][vertxOne] = 0;
    }

    @Override
    public boolean existsEdge(int vertxOne, int vertxTwo) {
        return matriz[vertxOne][vertxTwo] != 0;
    }

    @Override
    public int edgeWeight(int vertxOne, int vertxTwo) {
        return matriz[vertxOne][vertxTwo];
    }

    @Override
    public boolean isEmpty() {
        return cantidadVertices == 0;
    }
}
