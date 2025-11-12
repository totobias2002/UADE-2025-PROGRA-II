package org.uade.algorithm;

import org.uade.structure.implementation.dynamic.DynamicPriorityQueueADT;
import java.util.Scanner;

public class ControladorRestaurante {

    private DynamicPriorityQueueADT colaPedidos;
    private Pedido[] pedidosRegistrados;
    private int totalPedidos;

    public ControladorRestaurante() {
        colaPedidos = new DynamicPriorityQueueADT();
        pedidosRegistrados = new Pedido[100];  //Limite de 100 pedidos
        totalPedidos = 0;
    }


    // Registra los nuevos pedidos
    public void registrarPedido(Scanner scanner) {
        System.out.print("Ingrese nombre del cliente: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese dirección del cliente: ");
        String direccion = scanner.nextLine();

        System.out.print("Ingrese teléfono del cliente: ");
        String telefono = scanner.nextLine();

        System.out.print("¿Es cliente VIP? (s/n): ");
        boolean vip = scanner.nextLine().trim().equalsIgnoreCase("s");

        Cliente cliente = new Cliente(nombre, direccion, telefono, vip);

        int tipoNum;
        String tipoPedido = "";

        //  Menú para elegir tipo de pedido con validación
        while (true) {
            System.out.println("Seleccione el tipo de pedido:");
            System.out.println("1. Para llevar");
            System.out.println("2. A domicilio");
            System.out.print("Opción: ");
            tipoNum = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            if (tipoNum == 1) {
                tipoPedido = "Para llevar";
                break;
            } else if (tipoNum == 2) {
                tipoPedido = "A domicilio";
                break;
            } else {
                System.out.println("Opción inválida. Ingrese 1 o 2.\n");
            }
        }

        Pedido pedido = new Pedido(totalPedidos + 1, cliente, tipoPedido);
        mostrarPlatos();

        while (true) {
            System.out.print("Elija el menu entre 1 y 5   (0 para finalizar): ");
            int idPlato = scanner.nextInt();
            scanner.nextLine();

            if (idPlato == 0) break;

            Plato plato = buscarPlatoPorId(idPlato);
            if (plato != null) {
                pedido.agregarPlato(plato);
                System.out.println(" Plato agregado: " + plato.getNombre());
            } else {
                System.out.println(" plato inválido.");
            }
        }

        agregarPedido(pedido);
    }
    // Toma el pedido
    private void agregarPedido(Pedido pedido) {
        int prioridad = pedido.getCliente().isVip() ? 0 : 1;
        pedido.setEstado(" Pedido tomado");
        pedidosRegistrados[totalPedidos++] = pedido;
        colaPedidos.add(pedido.getId(), prioridad);
        System.out.println(" Pedido agregado correctamente #" + pedido.getId());
    }


    // Procesa todos los pedidos

    // Sino hay pedidos lo avisa
    public void procesarTodosLosPedidos() {
        if (colaPedidos.isEmpty()) {
            System.out.println("️ No hay pedidos pendientes.");
            return;
        }
        // Envia los pedidos a cocina
        System.out.println("\n Enviando pedidos a cocina...");
        while (!colaPedidos.isEmpty()) {
            int id = colaPedidos.getElement();
            Pedido p = buscarPedidoPorId(id);
            colaPedidos.remove();
            // muestra el numero de pedido,  marca que esta en preparacion
            // lo envia a a cocina
            if (p != null && p.getEstado().equals(" Pedido tomado")) {
                p.setEstado(" En cocina");
                System.out.println(" Pedido #" + p.getId() + " en preparación.");
                colaPedidos.add(p.getId(), p.esVip() ? 0 : 1);
            }
        }
        System.out.println(" Todos los pedidos fueron enviados a cocina.\n");
    }


    // Prepara todos los pedios
    public void prepararTodosLosPedidos() {
        boolean algunoPreparado = false;
        for (int i = 0; i < totalPedidos; i++) {
            Pedido p = pedidosRegistrados[i];
            if (p != null && p.getEstado().equals(" En cocina")) {
                for (int j = 0; j < p.cantidadDePlatos(); j++) {
                    Plato plato = p.obtenerPlato(j);
                    plato.setEstado("listo");
                }
                p.setEstado(" Listo para entregar");
                System.out.println(" Pedido #" + p.getId() + " listo para entregar.");
                algunoPreparado = true;
            }
        }
        //identifica si hay pedidos en cocina
        if (!algunoPreparado)
            System.out.println(" No hay pedidos en cocina para preparar.\n");
    }


    //  Entrega el pedido cuando viene a retirar al local

    public void entregarTodosLosPedidos() {
        boolean algunoEntregado = false;
        for (int i = 0; i < totalPedidos; i++) {
            Pedido p = pedidosRegistrados[i];
            if (p != null && p.getEstado().equals(" Listo para entregar")) {
                p.setEstado(" Entregado");
                System.out.println(" Pedido #" + p.getId() + " entregado a " + p.getCliente().getNombre());
                algunoEntregado = true;
            }
        }
        if (!algunoEntregado)
            System.out.println(" No hay pedidos listos para entregar.\n");
    }


    // ️ Elimina el pedido por numero

    public void eliminarPedido(Scanner scanner) {
        System.out.print("Ingrese el número del pedido a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < totalPedidos; i++) {
            if (pedidosRegistrados[i] != null && pedidosRegistrados[i].getId() == id) {
                pedidosRegistrados[i] = null;
                System.out.println("️ Pedido #" + id + " eliminado correctamente.\n");
                return;
            }
        }
        System.out.println("️No se encontró el pedido #" + id + ".\n");
    }


    //  Metodo de apoyo
    private Pedido buscarPedidoPorId(int id) {
        for (int i = 0; i < totalPedidos; i++) {
            if (pedidosRegistrados[i] != null && pedidosRegistrados[i].getId() == id) {
                return pedidosRegistrados[i];
            }
        }
        return null;
    }
 // Reporta el estado de los pedidos
    public void mostrarReporteInteractivo(Scanner scanner) {
        System.out.println("\n Reporte general del sistema:");
        int pendientes = 0, cocina = 0, listos = 0, entregados = 0;

        for (int i = 0; i < totalPedidos; i++) {
            Pedido p = pedidosRegistrados[i];
            if (p == null) continue;
            switch (p.getEstado()) {
                case " Pedido tomado" -> pendientes++;
                case " En cocina" -> cocina++;
                case " Listo para entregar" -> listos++;
                case " Entregado" -> entregados++;
            }
        }

        System.out.println("Pendientes: " + pendientes);
        System.out.println("En cocina: " + cocina);
        System.out.println("Listos: " + listos);
        System.out.println("Entregados: " + entregados + "\n");
    }

    public void mostrarEstadoPedidos() {
        if (totalPedidos == 0) {
            System.out.println(" No hay pedidos registrados.");
            return;
        }

        System.out.println("\n ESTADO DE PEDIDOS (ordenados por prioridad)");
        System.out.println("-------------------------------------");

        // Creamos un arreglo auxiliar para mostrar según prioridad
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
                System.out.println(prioridadPos + "° en prioridad | #" + p.getId() +
                        " | Cliente: " + p.getCliente().getNombre() +
                        " | Prioridad: " + tipoPrioridad +
                        " | Estado: " + p.getEstado());
                prioridadPos++;
                if (p.getEstado().equals(" Entregado") || p.getEstado().equals(" Listo para entregar")) {
                    despachados++;
                }
            }
        }

        System.out.println("-------------------------------------");
        System.out.println(" Pedidos totales: " + totalPedidos);
        System.out.println(" Pedidos despachados: " + despachados);
    }


    public void mostrarPlatos() {
        System.out.println("\n MENÚ DISPONIBLE:");
        for (Plato p : obtenerMenuEjemplo()) {
            System.out.println(p.getId() + ". " + p.getNombre() + " - $" + p.getPrecio());
        }
        System.out.println();
    }

    public Plato buscarPlatoPorId(int id) {
        for (Plato p : obtenerMenuEjemplo()) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    private Plato[] obtenerMenuEjemplo() {
        return new Plato[]{
                new Plato(1, "Milanesa con papas", 10000),
                new Plato(2, "Hamburguesa completa", 12000),
                new Plato(3, "Pizza muzzarella", 15000),
                new Plato(4, "Empanadas (3 unidades)", 5500),
                new Plato(5, "Ravioles con salsa", 12000)
        };
    }
}
