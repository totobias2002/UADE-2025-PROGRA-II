package org.uade.algorithm;

import org.uade.structure.implementation.dynamic.DynamicPriorityQueueADT;
import org.uade.structure.implementation.dynamic.DynamicStackADT;
import org.uade.structure.implementation.fixed.StaticGraphADT;

import java.util.Scanner;

public class ControladorRestaurante {

    private DynamicPriorityQueueADT colaPedidos;
    private Pedido[] pedidosRegistrados;
    private int totalPedidos;

    private StaticGraphADT mapaZonas;
    private Repartidor[] repartidores;

    private String[] nombresClientes;
    private int[] pedidosPorCliente;
    private int cantidadClientesEstadistica;

    private int[] contadorPlatos;

    private final double COSTO_POR_KM = 300;

    public ControladorRestaurante() {
        colaPedidos = new DynamicPriorityQueueADT();
        pedidosRegistrados = new Pedido[100];

        totalPedidos = 0;
        mapaZonas = inicializarMapa();
        repartidores = inicializarRepartidores();

        nombresClientes = new String[100];
        pedidosPorCliente = new int[100];
        cantidadClientesEstadistica = 0;

        contadorPlatos = new int[5];
    }

    private StaticGraphADT inicializarMapa() {
        StaticGraphADT g = new StaticGraphADT();

        for (int i = 0; i <= 5; i++) g.addVertx(i);

        g.addEdge(0, 1, 3);
        g.addEdge(0, 2, 5);
        g.addEdge(0, 3, 6);
        g.addEdge(0, 4, 4);
        g.addEdge(0, 5, 5);

        g.addEdge(1, 2, 4);
        g.addEdge(2, 5, 2);
        g.addEdge(3, 2, 3);
        g.addEdge(4, 1, 3);

        return g;
    }

    private Repartidor[] inicializarRepartidores() {
        Repartidor[] reps = new Repartidor[10];
        reps[0] = new Repartidor(1, "Juan", "Moto");
        reps[1] = new Repartidor(2, "Carlos", "Bici");
        reps[2] = new Repartidor(3, "Ana", "Moto");
        reps[3] = new Repartidor(4, "Lucía", "Bici");
        reps[4] = new Repartidor(5, "Nico", "Moto");
        reps[5] = new Repartidor(6, "María", "Moto");
        reps[6] = new Repartidor(7, "Pedro", "Bici");
        reps[7] = new Repartidor(8, "Diego", "Moto");
        reps[8] = new Repartidor(9, "Sofía", "Bici");
        reps[9] = new Repartidor(10, "Tomás", "Moto");
        return reps;
    }

    private Repartidor obtenerRepartidorDisponible() {
        for (Repartidor r : repartidores) {
            if (r.estaDisponible()) return r;
        }
        return null;
    }

    // ======================================================
    // REGISTRAR PEDIDO
    // ======================================================
    public void registrarPedido(Scanner scanner) {

        System.out.print("Ingrese nombre del cliente: ");
        String nombre = Validador.leerNombre(scanner);

        System.out.print("Ingrese teléfono del cliente: ");
        String telefono = Validador.leerTelefono(scanner);

        System.out.print("¿Es cliente VIP? (s/n): ");
        boolean vip = Validador.leerSiNo(scanner);

        Cliente cliente = new Cliente(nombre, "-", telefono, vip);
        Pedido pedido = new Pedido(totalPedidos + 1, cliente);

        System.out.println("\nSeleccione el método de entrega:");
        System.out.println("1. Retira en el local");
        System.out.println("2. Envío con repartidor");
        int tipo = Validador.leerEnteroEnRango(scanner, 1, 2, "Opción inválida.");

        if (tipo == 1) {
            pedido.setMetodoEntrega("Retiro en el local");
            pedido.setZona("Local");
            pedido.setDistanciaKm(0);
            pedido.setCostoDelivery(0);
            pedido.setRepartidor(null);

        } else {
            pedido.setMetodoEntrega("Delivery");

            System.out.println("\nSeleccione la zona de entrega:");
            System.out.println("1. Constitución");
            System.out.println("2. Obelisco");
            System.out.println("3. Plaza de Mayo");
            System.out.println("4. Hospital Garrahan");
            System.out.println("5. Congreso");

            int zona = Validador.leerEnteroEnRango(scanner, 1, 5, "Zona inválida.");

            String zonaNombre = switch (zona) {
                case 1 -> "Constitución";
                case 2 -> "Obelisco";
                case 3 -> "Plaza de Mayo";
                case 4 -> "Hospital Garrahan";
                case 5 -> "Congreso";
                default -> "N/A";
            };

            pedido.setZona(zonaNombre);

            double km = mapaZonas.edgeWeight(0, zona);
            pedido.setDistanciaKm(km);
            pedido.setCostoDelivery(km * COSTO_POR_KM);

            Repartidor r = obtenerRepartidorDisponible();
            if (r == null) {
                System.out.println("X No hay repartidores disponibles.");
                pedido.setRepartidor(null);
            } else {
                r.asignarPedido();
                pedido.setRepartidor(r);
                System.out.println("Repartidor asignado: " + r);
            }
        }

        mostrarPlatos();

        int platosAgregados = 0;
        while (true) {
            System.out.print("Elija el menú entre 1 y 5 (0 para finalizar): ");
            int id = Validador.leerEnteroEnRango(scanner, 0, 5, "Opción inválida.");

            if (id == 0) {
                if (platosAgregados == 0) {
                    System.out.println("X El pedido debe contener al menos 1 plato.");
                    continue;
                }
                break;
            }

            Plato p = buscarPlatoPorId(id);
            pedido.agregarPlato(p);
            contadorPlatos[p.getId() - 1]++;
            platosAgregados++;

            System.out.println("Plato agregado: " + p.getNombre());
        }

        System.out.println("\nSubtotal: $" + pedido.getSubtotal());
        if (pedido.getCostoDelivery() > 0)
            System.out.println("Delivery (" + pedido.getDistanciaKm() + " km): $" + pedido.getCostoDelivery());
        System.out.println("TOTAL A PAGAR: $" + pedido.getTotal());

        agregarPedido(pedido);
        actualizarEstadisticasCliente(nombre);
    }

    private void agregarPedido(Pedido pedido) {
        pedidosRegistrados[totalPedidos++] = pedido;

        int prioridad = pedido.getCliente().isVip() ? 0 : 1;
        colaPedidos.add(pedido.getId(), prioridad);

        System.out.println("Pedido #" + pedido.getId() + " agregado correctamente.");
    }

    // ======================================================
    // PROCESOS DE PEDIDO
    // ======================================================
    public void procesarSiguientePedido() {
        Pedido p = obtenerPorEstado(" Pedido tomado");

        if (p == null) {
            System.out.println("No hay pedidos pendientes.");
            return;
        }

        p.setEstado(" En cocina");
        System.out.println("Pedido #" + p.getId() + " enviado a cocina.");
    }

    public void prepararSiguientePedido() {
        Pedido p = obtenerPorEstado(" En cocina");

        if (p == null) {
            System.out.println("No hay pedidos en cocina.");
            return;
        }

        for (int i = 0; i < p.cantidadDePlatos(); i++)
            p.obtenerPlato(i).setEstado("listo");

        p.setEstado(" Listo para entregar");
        System.out.println("Pedido #" + p.getId() + " listo.");
    }

    public void entregarSiguientePedido() {
        Pedido p = obtenerPorEstado(" Listo para entregar");

        if (p == null) {
            System.out.println("No hay pedidos listos.");
            return;
        }

        if (p.getRepartidor() != null)
            p.getRepartidor().registrarEntregaRealizada();

        p.setEstado(" Entregado");
        System.out.println("Pedido #" + p.getId() + " ENTREGADO.");
    }

    private Pedido obtenerPorEstado(String estado) {
        for (int i = 0; i < totalPedidos; i++) {
            if (pedidosRegistrados[i] != null &&
                    pedidosRegistrados[i].getEstado().equals(estado))
                return pedidosRegistrados[i];
        }
        return null;
    }

    private Pedido buscarPedidoPorId(int id) {
        for (int i = 0; i < totalPedidos; i++) {
            if (pedidosRegistrados[i] != null && pedidosRegistrados[i].getId() == id)
                return pedidosRegistrados[i];
        }
        return null;
    }

    // ======================================================
    // ESTADO DE PEDIDOS
    // ======================================================
    public void mostrarEstadoPedidos() {
        if (totalPedidos == 0) {
            System.out.println("No hay pedidos.");
            return;
        }

        System.out.println("\nESTADO DE PEDIDOS:");
        System.out.println("(° = Prioridad | # = Número de pedido)");
        System.out.println("----------------------------------------------------");

        Pedido[] ordenados = new Pedido[totalPedidos];
        int idx = 0;

        for (int i = 0; i < totalPedidos; i++)
            if (pedidosRegistrados[i] != null && pedidosRegistrados[i].getCliente().isVip())
                ordenados[idx++] = pedidosRegistrados[i];

        for (int i = 0; i < totalPedidos; i++)
            if (pedidosRegistrados[i] != null && !pedidosRegistrados[i].getCliente().isVip())
                ordenados[idx++] = pedidosRegistrados[i];

        int prio = 1;
        for (int i = 0; i < idx; i++) {
            Pedido p = ordenados[i];

            System.out.println(prio + "° | #" + p.getId()
                    + " | Cliente: " + p.getCliente().getNombre()
                    + " | " + (p.getCliente().isVip() ? "VIP" : "Normal")
                    + " | Total $" + p.getTotal()
                    + " | Repartidor: " + (p.getRepartidor() != null ? p.getRepartidor().getNombre() : "N/A")
                    + " | Estado: " + p.getEstado());

            prio++;
        }

        System.out.println("----------------------------------------------------");
    }

    // ======================================================
    // REPORTES
    // ======================================================
    public void mostrarMenuReportes(Scanner scanner) {
        while (true) {
            System.out.println("\n===== REPORTES =====");
            System.out.println("1. Estado general de pedidos");
            System.out.println("2. Repartidores y cantidad de entregas");
            System.out.println("3. Cliente con más pedidos");
            System.out.println("4. Platos más pedidos");
            System.out.println("5. Mostrar distancias del grafo");
            System.out.println("6. Mostrar ruta hacia zona (usando PILA)");
            System.out.println("0. Volver");
            System.out.print("Seleccione opción: ");

            int opc = Validador.leerEnteroEnRango(scanner, 0, 6, "Opción inválida.");

            switch (opc) {
                case 1 -> mostrarEstadoPedidos();
                case 2 -> reporteRepartidores();
                case 3 -> reporteClienteMasPedidos();
                case 4 -> reportePlatosMasPedidos();
                case 5 -> mostrarDistancias();
                case 6 -> mostrarRutaConPila(scanner);
                case 0 -> { return; }
            }

            System.out.print("\nPresione 0 para volver: ");
            Validador.leerEnteroEnRango(scanner, 0, 0, "Debe ingresar 0.");
        }
    }

    private void reporteRepartidores() {
        System.out.println("\nREPARTIDORES:");
        for (Repartidor r : repartidores) {
            String est = r.estaDisponible() ? "Disponible" : "Ocupado";
            System.out.println(r.getId() + " | " + r.getNombre() + " (" + r.getVehiculo()
                    + ") | Entregas: " + r.getEntregasRealizadas()
                    + " | Estado: " + est);
        }
    }

    private void actualizarEstadisticasCliente(String nombre) {
        for (int i = 0; i < cantidadClientesEstadistica; i++) {
            if (nombresClientes[i].equalsIgnoreCase(nombre)) {
                pedidosPorCliente[i]++;
                return;
            }
        }
        nombresClientes[cantidadClientesEstadistica] = nombre;
        pedidosPorCliente[cantidadClientesEstadistica] = 1;
        cantidadClientesEstadistica++;
    }

    private void reporteClienteMasPedidos() {
        if (cantidadClientesEstadistica == 0) {
            System.out.println("Sin clientes registrados.");
            return;
        }

        int max = 0;
        int idxMax = 0;

        for (int i = 0; i < cantidadClientesEstadistica; i++) {
            if (pedidosPorCliente[i] > max) {
                max = pedidosPorCliente[i];
                idxMax = i;
            }
        }

        System.out.println("\nCLIENTE CON MÁS PEDIDOS:");
        System.out.println(nombresClientes[idxMax] + " → " + max + " pedidos");
    }

    private void reportePlatosMasPedidos() {
        System.out.println("\nPLATOS MÁS PEDIDOS:");
        String[] nombres = {
                "Milanesa con papas",
                "Hamburguesa completa",
                "Pizza muzzarella",
                "Empanadas (3 unidades)",
                "Ravioles con salsa"
        };

        for (int i = 0; i < 5; i++) {
            System.out.println(nombres[i] + ": " + contadorPlatos[i] + " pedidos");
        }
    }

    // ======================================================
    // GRAFO + PILA
    // ======================================================
    public void mostrarDistancias() {
        System.out.println("\nDISTANCIAS DESDE EL RESTAURANTE (UADE):");

        String[] zonas = {
                "Restaurante",
                "Constitución",
                "Obelisco",
                "Plaza de Mayo",
                "Hospital Garrahan",
                "Congreso"
        };

        for (int i = 1; i <= 5; i++) {
            System.out.println("0 → " + zonas[i] + ": " +
                    mapaZonas.edgeWeight(0, i) + " km");
        }
    }

    public void mostrarRutaConPila(Scanner scanner) {
        System.out.println("\nSeleccione zona:");
        System.out.println("1. Constitución");
        System.out.println("2. Obelisco");
        System.out.println("3. Plaza de Mayo");
        System.out.println("4. Hospital Garrahan");
        System.out.println("5. Congreso");

        int zona = Validador.leerEnteroEnRango(scanner, 1, 5, "Zona inválida.");

        String[] nombres = {
                "Restaurante",
                "Constitución",
                "Obelisco",
                "Plaza de Mayo",
                "Hospital Garrahan",
                "Congreso"
        };

        DynamicStackADT pila = new DynamicStackADT();

        // CORREGIDO — add() reemplaza push()
        pila.add(zona);
        pila.add(0);

        System.out.println("\nRuta (PILA → destino → origen):");

        // CORREGIDO — pop() se realiza con getElement() + remove()
        while (!pila.isEmpty()) {
            int nodo = pila.getElement();
            pila.remove();
            System.out.println("Nodo " + nodo + ": " + nombres[nodo]);
        }
    }

    // ======================================================
    // PLATOS
    // ======================================================
    public void mostrarPlatos() {
        System.out.println("\nMENÚ:");
        for (Plato p : obtenerMenuEjemplo()) {
            System.out.println(p.getId() + ". " + p.getNombre() + " - $" + p.getPrecio());
        }
        System.out.println();
    }

    public Plato buscarPlatoPorId(int id) {
        for (Plato p : obtenerMenuEjemplo())
            if (p.getId() == id) return p;
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

    // ======================================================
    // ELIMINAR PEDIDO
    // ======================================================
    public void eliminarPedido(Scanner scanner) {
        if (totalPedidos == 0) {
            System.out.println("No hay pedidos para eliminar.");
            return;
        }

        System.out.print("Ingrese ID del pedido: ");
        int id = Validador.leerEnteroEnRango(scanner, 1, totalPedidos, "ID inválido.");

        for (int i = 0; i < totalPedidos; i++) {
            if (pedidosRegistrados[i] != null && pedidosRegistrados[i].getId() == id) {
                pedidosRegistrados[i] = null;
                System.out.println("Pedido #" + id + " eliminado.");
                return;
            }
        }

        System.out.println("No existe ese pedido.");
    }
}
