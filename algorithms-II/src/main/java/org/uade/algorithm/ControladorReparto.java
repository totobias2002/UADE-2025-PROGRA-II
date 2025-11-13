package org.uade.algorithm;

import org.uade.structure.implementation.staticgraph.StaticGraphADT;

public class ControladorReparto {

    private StaticGraphADT mapa;
    private Repartidor[] repartidores;

    public ControladorReparto() {
        // Crear mapa
        mapa = new StaticGraphADT();
        cargarMapa();

        // Crear 10 repartidores
        repartidores = new Repartidor[10];
        for (int i = 0; i < repartidores.length; i++) {
            repartidores[i] = new Repartidor(i + 1, "Repartidor " + (i + 1));
        }
    }

    private void cargarMapa() {
        // 0 = Restaurante (UADE)
        for (int i = 0; i <= 5; i++) mapa.addVertx(i);

        mapa.addEdge(0, 1, 3); // Restaurante - Constitución
        mapa.addEdge(0, 2, 5); // Restaurante - Obelisco
        mapa.addEdge(0, 3, 6); // Restaurante - Plaza de Mayo
        mapa.addEdge(0, 4, 4); // Restaurante- Garrahan
        mapa.addEdge(0, 5, 5); // Restaurante - Congreso

        mapa.addEdge(1, 2, 4); //Constitucion-Obelisco
        mapa.addEdge(2, 5, 2); //Obelisco-Congreso
        mapa.addEdge(3, 2, 3); //Plaza de mayo-obelisco
        mapa.addEdge(4, 1, 3); //garrahan-constitucion
    }

    // Obtener un repartidor disponible
    public Repartidor obtenerRepartidorDisponible() {
        for (Repartidor r : repartidores) {
            if (r.estaDisponible()) {
                return r;
            }
        }
        return null; // no hay disponibles
    }

    // Simular entrega
    public void enviarPedido(int destino, Pedido pedido) {
        Repartidor repartidor = obtenerRepartidorDisponible();
        if (repartidor == null) {
            System.out.println(" No hay repartidores disponibles.");
            return;
        }

        repartidor.asignarPedido(pedido);
        System.out.println(" " + repartidor.getNombre() +
                " sale del Restaurante hacia destino #" + destino + "...");
        System.out.println("Distancia estimada: " + mapa.edgeWeight(0, destino) + " km.");
        repartidor.entregarPedido(pedido);
    }
}