package org.uade.algorithm;

import org.uade.structure.implementation.staticgraph.StaticGraphADT;

public class Reparto {

    public static void main(String[] args) {

        StaticGraphADT grafo = new StaticGraphADT();

        // Agregar los vértices
        grafo.addVertx(0); // Restaurante (UADE)
        grafo.addVertx(1); // Constitución
        grafo.addVertx(2); // Obelisco
        grafo.addVertx(3); // Plaza de Mayo
        grafo.addVertx(4); // Hospital Garrahan
        grafo.addVertx(5); // Congreso

        // Agregar las aristas (pesos aproximados en km)
        grafo.addEdge(0, 1, 3); // Restaurante - Constitución
        grafo.addEdge(0, 2, 5); // Restaurante - Obelisco
        grafo.addEdge(0, 3, 6); // Restaurante - Plaza de Mayo
        grafo.addEdge(0, 4, 4); // Restaurante - Garrahan
        grafo.addEdge(0, 5, 5); // Restaurante - Congreso

        // Conexiones adicionales entre los puntos
        grafo.addEdge(1, 2, 4); // Constitución - Obelisco
        grafo.addEdge(2, 5, 2); // Obelisco - Congreso
        grafo.addEdge(3, 2, 3); // Plaza de Mayo - Obelisco
        grafo.addEdge(4, 1, 3); // Garrahan - Constitución

        System.out.println(" Grafo de reparto cargado correctamente");
    }
}
