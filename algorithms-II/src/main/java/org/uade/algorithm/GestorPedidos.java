package org.uade.algorithm;

import org.uade.structure.implementation.dynamic.DynamicQueueADT;
import org.uade.structure.implementation.dynamic.DynamicPriorityQueueADT;

public class GestorPedidos {
    private DynamicPriorityQueueADT colaPrioridad; // VIP primero
    private DynamicQueueADT colaCocina;            // Platos a preparar
    private DynamicQueueADT colaListos;            // Platos listos
    private Pedido[] pedidosRegistrados;
    private int cantidadPedidos;
    private Plato[] platosDisponibles;

    public GestorPedidos() {
        colaPrioridad = new DynamicPriorityQueueADT();
        colaCocina = new DynamicQueueADT();
        colaListos = new DynamicQueueADT();
        pedidosRegistrados = new Pedido[100];
        cantidadPedidos = 0;
        cargarPlatos();
    }

    private void cargarPlatos() {
        platosDisponibles = new Plato[4];
        platosDisponibles[0] = new Plato(1, "Milanesa con papas", 2500);
        platosDisponibles[1] = new Plato(2, "Pizza muzzarella", 3000);
        platosDisponibles[2] = new Plato(3, "Empanadas (docena)", 2800);
        platosDisponibles[3] = new Plato(4, "Ensalada César", 2400);
    }

    public void mostrarPlatos() {
        System.out.println("\n=== PLATOS DISPONIBLES ===");
        for (Plato p : platosDisponibles) {
            System.out.println(p);
        }
        System.out.println();
    }

    // ------------------- PEDIDOS -------------------

    public void agregarPedido(Pedido pedido) {
        pedidosRegistrados[cantidadPedidos++] = pedido;
        int prioridad = pedido.esVip() ? 0 : 1;
        colaPrioridad.add(pedido.getId(), prioridad);
        System.out.println("✅ Pedido agregado (Prioridad: " + (pedido.esVip() ? "VIP" : "Normal") + ")");
    }

    public void procesarSiguientePedido() {
        if (colaPrioridad.isEmpty()) {
            System.out.println("No hay pedidos pendientes.\n");
            return;
        }

        int id = colaPrioridad.getElement();
        colaPrioridad.remove();
        Pedido pedido = buscarPedidoPorId(id);

        if (pedido != null) {
            System.out.println("🍽️ Pedido #" + id + " en preparación: " + pedido.getCliente().getNombre());
            pedido.setEstado("en preparación");
            enviarPlatosACocina(pedido);
        }
    }

    private Pedido buscarPedidoPorId(int id) {
        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidosRegistrados[i].getId() == id)
                return pedidosRegistrados[i];
        }
        return null;
    }

    // ------------------- COCINA -------------------

    private void enviarPlatosACocina(Pedido pedido) {
        for (Plato plato : pedido.getPlatos()) {
            colaCocina.add(plato.getId()); // Guardamos solo el ID del plato
            System.out.println("   👨‍🍳 Enviando a cocina: " + plato.getNombre());
        }
        System.out.println("Todos los platos del pedido #" + pedido.getId() + " fueron enviados a cocina.\n");
    }

    public void prepararSiguientePlato() {
        if (colaCocina.isEmpty()) {
            System.out.println("No hay platos en cocina.\n");
            return;
        }

        int idPlato = colaCocina.getElement();
        colaCocina.remove();
        Plato plato = buscarPlatoPorId(idPlato);

        System.out.println("🔥 Cocinando plato: " + plato.getNombre());
        plato.setEstado("listo");
        colaListos.add(plato.getId());

        System.out.println("✅ Plato listo: " + plato.getNombre() + "\n");
    }

    Plato buscarPlatoPorId(int id) {
        for (Plato p : platosDisponibles) {
            if (p.getId() == id)
                return p;
        }
        return null;
    }

    // ------------------- ENTREGA -------------------

    public void entregarPlato() {
        if (colaListos.isEmpty()) {
            System.out.println("No hay platos listos para entregar.\n");
            return;
        }

        int idPlato = colaListos.getElement();
        colaListos.remove();
        Plato plato = buscarPlatoPorId(idPlato);

        System.out.println("🚗 Plato entregado al cliente/repartidor: " + plato.getNombre() + "\n");
    }
}
