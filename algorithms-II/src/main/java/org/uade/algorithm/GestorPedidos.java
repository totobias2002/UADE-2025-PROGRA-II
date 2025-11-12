package org.uade.algorithm;

import org.uade.structure.implementation.dynamic.DynamicPriorityQueueADT;

public class GestorPedidos {

    private DynamicPriorityQueueADT colaPedidos;
    private Pedido[] pedidosRegistrados;
    private int totalPedidos;
    private int pedidosDespachados;
// Cola con prioridad por cliente VIP o normal
    public GestorPedidos() {
        colaPedidos = new DynamicPriorityQueueADT();
        pedidosRegistrados = new Pedido[100];
        totalPedidos = 0;
        pedidosDespachados = 0;
    }

 //Marca el estado del pedido
    public void agregarPedido(Pedido pedido) {
        int prioridad = pedido.getCliente().isVip() ? 0 : 1;
        pedido.setEstado(" Pedido tomado");
        pedidosRegistrados[totalPedidos++] = pedido;
        colaPedidos.add(pedido.getId(), prioridad);
        System.out.println(" Pedido agregado: " + pedido);
    }

    private Pedido buscarPedidoPorId(int id) {
        for (int i = 0; i < totalPedidos; i++) {
            if (pedidosRegistrados[i].getId() == id) {
                return pedidosRegistrados[i];
            }
        }
        return null;
    }
// Muestra si hay pedidos pendientes
    public void procesarSiguientePedido() {
        if (colaPedidos.isEmpty()) {
            System.out.println(" No hay pedidos pendientes para procesar.");
            return;
        }

        int idPedido = colaPedidos.getElement();
        Pedido pedido = buscarPedidoPorId(idPedido);

        if (pedido == null) {
            System.out.println("X Error interno: pedido no encontrado.");
            colaPedidos.remove();
            return;
        }

        pedido.setEstado(" En cocina");
        System.out.println("\n Procesando pedido #" + pedido.getId() +
                " del cliente: " + pedido.getCliente().getNombre());

        for (int i = 0; i < pedido.cantidadDePlatos(); i++) {
            Plato plato = pedido.obtenerPlato(i);
            System.out.println(" Preparando plato: " + plato.getNombre());
        }

        System.out.println(" Pedido #" + pedido.getId() + " en cocina.\n");
    }

    public void prepararSiguientePlato() {
        if (colaPedidos.isEmpty()) {
            System.out.println(" No hay pedidos en preparación.");
            return;
        }

        int idPedido = colaPedidos.getElement();
        Pedido pedido = buscarPedidoPorId(idPedido);

        if (pedido == null) return;

        Plato siguiente = pedido.obtenerPlatoPendiente();
        if (siguiente == null) {
            System.out.println("️ Todos los platos del pedido #" + pedido.getId() + " ya están listos.");
            return;
        }

        siguiente.setEstado("listo");
        System.out.println(" Plato preparado: " + siguiente.getNombre());

        if (pedido.todosPlatosListos()) {
            pedido.setEstado(" Listo para entregar");
            System.out.println(" Pedido #" + pedido.getId() + " completo.\n");
        }
    }

    public void entregarPlato() {
        if (colaPedidos.isEmpty()) {
            System.out.println(" No hay pedidos listos para entregar.");
            return;
        }

        int idPedido = colaPedidos.getElement();
        Pedido pedido = buscarPedidoPorId(idPedido);

        if (pedido == null) return;

        if (!pedido.todosPlatosListos()) {
            System.out.println(" El pedido #" + pedido.getId() + " aún no está completo.");
            return;
        }

        colaPedidos.remove();
        pedido.setEstado(" Entregado");
        pedidosDespachados++;

        System.out.println(" Pedido #" + pedido.getId() + " entregado a " +
                pedido.getCliente().getNombre() + ".");
        System.out.println(" Total de pedidos despachados: " + pedidosDespachados);
        System.out.println(" Pedidos restantes en cola: " + colaPedidos.size() + "\n");
    }

    public void mostrarEstadoPedidos() {
        if (totalPedidos == 0) {
            System.out.println(" No hay pedidos registrados.");
            return;
        }

        System.out.println("\nESTADO DE PEDIDOS (ordenados por prioridad)");
        System.out.println("-------------------------------------");


        Pedido[] ordenados = new Pedido[totalPedidos];
        int count = 0;

        // Primero los VIP
        for (int i = 0; i < totalPedidos; i++) {
            if (pedidosRegistrados[i] != null && pedidosRegistrados[i].getCliente().isVip()) {
                ordenados[count++] = pedidosRegistrados[i];
            }
        }
        // Luego los normales
        for (int i = 0; i < totalPedidos; i++) {
            if (pedidosRegistrados[i] != null && !pedidosRegistrados[i].getCliente().isVip()) {
                ordenados[count++] = pedidosRegistrados[i];
            }
        }

        int prioridadPos = 1;
        int despachados = 0;

        for (int i = 0; i < count; i++) {
            Pedido p = ordenados[i];
            if (p != null) {
                String tipoPrioridad = p.getCliente().isVip() ? "VIP" : "Normal";
                System.out.println(prioridadPos + "° en prioridad | Pedido N°" + p.getId() +
                        " | Cliente: " + p.getCliente().getNombre() +
                        " | Prioridad: " + tipoPrioridad +
                        " | Estado: " + p.getEstado());
                prioridadPos++;
                if (p.getEstado().equals("Entregado") || p.getEstado().equals("Listo para entregar")) {
                    despachados++;
                }
            }
        }

        System.out.println("-------------------------------------");
        System.out.println("Pedidos totales: " + totalPedidos);
        System.out.println("Pedidos despachados: " + despachados);
    }



}
