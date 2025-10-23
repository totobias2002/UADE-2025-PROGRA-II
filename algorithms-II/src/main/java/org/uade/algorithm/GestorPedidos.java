package org.uade.algorithm;

import org.uade.structure.implementation.dynamic.DynamicPriorityQueueADT;

public class GestorPedidos {
    private Pedido[] pedidos;
    private int contador;
    private DynamicPriorityQueueADT colaPrioridad;

    public GestorPedidos(int capacidadMaxima) {
        pedidos = new Pedido[capacidadMaxima];
        contador = 0;
        colaPrioridad = new DynamicPriorityQueueADT();
    }

    public void registrarPedido(Pedido pedido) {
        if (contador >= pedidos.length) {
            System.out.println("❌ No se pueden registrar más pedidos.");
            return;
        }

        pedidos[contador] = pedido;
        int prioridad = pedido.esVip() ? 1 : 2;
        colaPrioridad.add(contador, prioridad); // guardamos el índice del pedido
        contador++;

        System.out.println("✅ Pedido registrado con prioridad " + (pedido.esVip() ? "VIP" : "Normal"));
    }

    public void mostrarPedidosPendientes() {
        System.out.println("\n=== PEDIDOS PENDIENTES ===");
        if (colaPrioridad.isEmpty()) {
            System.out.println("No hay pedidos pendientes.");
            return;
        }

        for (int i = 0; i < contador; i++) {
            if (pedidos[i] != null) {
                System.out.println(pedidos[i]);
            }
        }
        System.out.println();
    }

    public Pedido obtenerSiguientePedido() {
        if (colaPrioridad.isEmpty()) {
            System.out.println("❌ No hay pedidos en la cola.");
            return null;
        }
        int indice = colaPrioridad.getElement();
        colaPrioridad.remove();
        return pedidos[indice];
    }
}
