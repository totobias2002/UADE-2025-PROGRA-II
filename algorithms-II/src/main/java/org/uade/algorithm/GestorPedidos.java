package org.uade.algorithm;

import org.uade.structure.implementation.dynamic.DynamicPriorityQueueADT;

public class GestorPedidos {

    private DynamicPriorityQueueADT colaPedidos;
    private Pedido[] pedidosRegistrados;
    private int totalPedidos;
    private int pedidosDespachados; // 🔹 Nuevo contador de pedidos entregados

    public GestorPedidos() {
        colaPedidos = new DynamicPriorityQueueADT();
        pedidosRegistrados = new Pedido[100];
        totalPedidos = 0;
        pedidosDespachados = 0;
    }

    // ======================
    // 🔹 AGREGAR PEDIDO
    // ======================
    public void agregarPedido(Pedido pedido) {
        int prioridad = pedido.getCliente().isVip() ? 0 : 1;
        pedido.setEstado("📝 Pedido tomado");
        pedidosRegistrados[totalPedidos++] = pedido;
        colaPedidos.add(pedido.getId(), prioridad);
        System.out.println("✅ Pedido agregado: " + pedido);
    }

    // ======================
    // 🔹 BUSCAR PEDIDO POR ID
    // ======================
    private Pedido buscarPedidoPorId(int id) {
        for (int i = 0; i < totalPedidos; i++) {
            if (pedidosRegistrados[i].getId() == id) {
                return pedidosRegistrados[i];
            }
        }
        return null;
    }

    // ======================
    // 🔹 PROCESAR SIGUIENTE PEDIDO
    // ======================
    public void procesarSiguientePedido() {
        if (colaPedidos.isEmpty()) {
            System.out.println("⚠️ No hay pedidos pendientes para procesar.");
            return;
        }

        int idPedido = colaPedidos.getElement(); // devuelve un int
        Pedido pedido = buscarPedidoPorId(idPedido);

        if (pedido == null) {
            System.out.println("❌ Error interno: pedido no encontrado.");
            colaPedidos.remove();
            return;
        }

        pedido.setEstado("🍳 En cocina");
        System.out.println("\n🧾 Procesando pedido #" + pedido.getId() +
                " del cliente: " + pedido.getCliente().getNombre());

        for (int i = 0; i < pedido.cantidadDePlatos(); i++) {
            Plato plato = pedido.obtenerPlato(i);
            System.out.println("👨‍🍳 Preparando plato: " + plato.getNombre());
        }

        System.out.println("✅ Pedido #" + pedido.getId() + " en cocina.\n");
    }

    // ======================
    // 🔹 PREPARAR SIGUIENTE PLATO
    // ======================
    public void prepararSiguientePlato() {
        if (colaPedidos.isEmpty()) {
            System.out.println("⚠️ No hay pedidos en preparación.");
            return;
        }

        int idPedido = colaPedidos.getElement();
        Pedido pedido = buscarPedidoPorId(idPedido);

        if (pedido == null) return;

        Plato siguiente = pedido.obtenerPlatoPendiente();
        if (siguiente == null) {
            System.out.println("⚠️ Todos los platos del pedido #" + pedido.getId() + " ya están listos.");
            return;
        }

        siguiente.setEstado("listo");
        System.out.println("👨‍🍳 Plato preparado: " + siguiente.getNombre());

        if (pedido.todosPlatosListos()) {
            pedido.setEstado("🚚 Listo para entregar");
            System.out.println("✅ Pedido #" + pedido.getId() + " completo.\n");
        }
    }

    // ======================
    // 🔹 ENTREGAR PEDIDO
    // ======================
    public void entregarPlato() {
        if (colaPedidos.isEmpty()) {
            System.out.println("⚠️ No hay pedidos listos para entregar.");
            return;
        }

        int idPedido = colaPedidos.getElement();
        Pedido pedido = buscarPedidoPorId(idPedido);

        if (pedido == null) return;

        if (!pedido.todosPlatosListos()) {
            System.out.println("⚠️ El pedido #" + pedido.getId() + " aún no está completo.");
            return;
        }

        colaPedidos.remove();
        pedido.setEstado("✅ Entregado");
        pedidosDespachados++; // 🔹 Contador incrementa

        System.out.println("🚚 Pedido #" + pedido.getId() + " entregado a " +
                pedido.getCliente().getNombre() + ".");
        System.out.println("📦 Total de pedidos despachados: " + pedidosDespachados);
        System.out.println("📋 Pedidos restantes en cola: " + colaPedidos.size() + "\n");
    }

    // ======================
    // 🔹 REPORTES
    // ======================
    public void generarReportes() {
        System.out.println("📊 Reporte general del sistema:");
        System.out.println("- Pedidos registrados: " + totalPedidos);
        System.out.println("- Pedidos pendientes: " + colaPedidos.size());
        System.out.println("- Pedidos despachados: " + pedidosDespachados);
    }

    // ======================
    // 🔹 MOSTRAR PLATOS
    // ======================
    public void mostrarPlatos() {
        System.out.println("\n🍽️ LISTA DE PLATOS DISPONIBLES:");
        Plato[] menu = obtenerMenuEjemplo();
        for (Plato p : menu) {
            System.out.println(p.getId() + ". " + p.getNombre() + " - $" + p.getPrecio());
        }
        System.out.println();
    }

    // ======================
    // 🔹 BUSCAR PLATO POR ID
    // ======================
    public Plato buscarPlatoPorId(int id) {
        for (Plato p : obtenerMenuEjemplo()) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    // ======================
    // 🔹 MENÚ DE EJEMPLO
    // ======================
    private Plato[] obtenerMenuEjemplo() {
        return new Plato[]{
                new Plato(1, "Milanesa con papas", 3500),
                new Plato(2, "Hamburguesa completa", 3000),
                new Plato(3, "Pizza muzzarella", 4200),
                new Plato(4, "Empanadas (3 unidades)", 2000),
                new Plato(5, "Ravioles con salsa", 3800)
        };
    }

    // ======================
    // 🔹 MOSTRAR ESTADO DE PEDIDOS
    // ======================
    public void mostrarEstadoPedidos() {
        if (totalPedidos == 0) {
            System.out.println("⚠️ No hay pedidos registrados.");
            return;
        }

        System.out.println("\n📋 LISTADO DE PEDIDOS");
        System.out.println("-------------------------------------");
        for (int i = 0; i < totalPedidos; i++) {
            Pedido p = pedidosRegistrados[i];
            System.out.println("Pedido N°" + p.getId() +
                    " | Cliente: " + p.getCliente().getNombre() +
                    " | Tipo: " + p.getTipo() +
                    " | Estado actual: " + p.getEstado());
        }
        System.out.println("-------------------------------------");
        System.out.println("📦 Total de pedidos registrados: " + totalPedidos);
        System.out.println("🚚 Total de pedidos despachados: " + pedidosDespachados);
    }
}
