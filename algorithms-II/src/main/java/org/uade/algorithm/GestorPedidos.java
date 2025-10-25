package org.uade.algorithm;

import org.uade.structure.implementation.dynamic.DynamicQueueADT;
import org.uade.structure.implementation.dynamic.DynamicPriorityQueueADT;

public class GestorPedidos {
    private DynamicPriorityQueueADT colaPrioridad; // VIP primero
    private DynamicQueueADT colaCocina;            // Platos a preparar
    private DynamicQueueADT colaListos;            // Platos listos
    private DynamicQueueADT colaEntregas;          // Pedidos listos para enviar
    private Pedido[] pedidosRegistrados;
    private Plato[] platosDisponibles;
    private Repartidor[] repartidores;

    private int cantidadPedidos;
    private int cantidadRepartidores;

    public GestorPedidos() {
        colaPrioridad = new DynamicPriorityQueueADT();
        colaCocina = new DynamicQueueADT();
        colaListos = new DynamicQueueADT();
        colaEntregas = new DynamicQueueADT();
        pedidosRegistrados = new Pedido[100];
        cantidadPedidos = 0;

        cargarPlatos();
        cargarRepartidores();
    }

    // ------------------- PLATOS -------------------

    private void cargarPlatos() {
        platosDisponibles = new Plato[5];
        platosDisponibles[0] = new Plato(1, "Milanesa con papas", 2500);
        platosDisponibles[1] = new Plato(2, "Pizza muzzarella", 3000);
        platosDisponibles[2] = new Plato(3, "Empanadas (docena)", 2800);
        platosDisponibles[3] = new Plato(4, "Ensalada César", 2400);
        platosDisponibles[4] = new Plato(5, "Hamburguesa completa", 3200);
    }

    public void mostrarPlatos() {
        System.out.println("\n=== PLATOS DISPONIBLES ===");
        for (Plato p : platosDisponibles) {
            System.out.println(p);
        }
        System.out.println();
    }

    // ------------------- REPARTIDORES -------------------

    private void cargarRepartidores() {
        repartidores = new Repartidor[10];
        for (int i = 0; i < 10; i++) {
            repartidores[i] = new Repartidor(i + 1, "Repartidor " + (i + 1));
        }
        cantidadRepartidores = 10;
    }

    private Repartidor obtenerRepartidorDisponible() {
        for (Repartidor r : repartidores) {
            if (r.estaDisponible())
                return r;
        }
        return null;
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
            colaCocina.add(plato.getId());
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

        Pedido pedido = buscarPedidoPorPlato(plato);
        if (pedido == null) return;

        if (pedido.getTipo().equals("A domicilio")) {
            colaEntregas.add(pedido.getId());
            asignarRepartidor(pedido);
        } else {
            pedido.setEstado("finalizado");
            System.out.println("🏠 Pedido #" + pedido.getId() + " retirado en mostrador.\n");
        }
    }

    private Pedido buscarPedidoPorPlato(Plato plato) {
        for (int i = 0; i < cantidadPedidos; i++) {
            for (Plato p : pedidosRegistrados[i].getPlatos()) {
                if (p.getId() == plato.getId()) return pedidosRegistrados[i];
            }
        }
        return null;
    }

    private void asignarRepartidor(Pedido pedido) {
        Repartidor r = obtenerRepartidorDisponible();
        if (r == null) {
            System.out.println("🚫 No hay repartidores disponibles. Pedido #" + pedido.getId() + " en cola de entrega.\n");
            return;
        }

        r.asignarPedido(pedido);
        pedido.setEstado("en reparto");
        System.out.println("🚴 Pedido #" + pedido.getId() + " asignado a " + r.getNombre());
        r.entregarPedido(pedido);
    }

    // ------------------- REPORTES -------------------

    public void generarReportes() {
        System.out.println("\n=== REPORTES ===");
        int pendientes = 0, finalizados = 0;

        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidosRegistrados[i].getEstado().equals("finalizado"))
                finalizados++;
            else
                pendientes++;
        }

        System.out.println("📦 Pedidos pendientes: " + pendientes);
        System.out.println("✅ Pedidos finalizados: " + finalizados);

        System.out.println("\n🚴 Pedidos entregados por repartidor:");
        for (Repartidor r : repartidores) {
            System.out.println("   " + r.getNombre() + ": " + r.getCantidadEntregas() + " entregas");
        }

        Cliente mejor = clienteConMasPedidos();
        if (mejor != null) {
            System.out.println("\n⭐ Cliente con más pedidos: " + mejor.getNombre());
        }
        System.out.println();
    }

    private Cliente clienteConMasPedidos() {
        Cliente top = null;
        int max = 0;
        for (int i = 0; i < cantidadPedidos; i++) {
            Cliente c = pedidosRegistrados[i].getCliente();
            int cantidad = contarPedidosCliente(c);
            if (cantidad > max) {
                max = cantidad;
                top = c;
            }
        }
        return top;
    }

    private int contarPedidosCliente(Cliente c) {
        int contador = 0;
        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidosRegistrados[i].getCliente().getNombre().equals(c.getNombre()))
                contador++;
        }
        return contador;
    }
}
