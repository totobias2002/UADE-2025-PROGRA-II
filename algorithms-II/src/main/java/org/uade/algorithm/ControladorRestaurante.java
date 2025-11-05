package org.uade.algorithm;

import org.uade.structure.implementation.dynamic.DynamicPriorityQueueADT;
import java.util.Scanner;

public class ControladorRestaurante {

    private DynamicPriorityQueueADT colaPedidos;
    private Pedido[] pedidosRegistrados;
    private int totalPedidos;

    public ControladorRestaurante() {
        colaPedidos = new DynamicPriorityQueueADT();
        pedidosRegistrados = new Pedido[100];
        totalPedidos = 0;
    }

    // ===============================
    // 📋 REGISTRO DE NUEVOS PEDIDOS
    // ===============================
    public void registrarPedido(Scanner scanner) {
        System.out.print("Ingrese nombre del cliente: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese apellido del cliente: ");
        String apellido = scanner.nextLine();

        System.out.print("Ingrese correo electrónico: ");
        String email = scanner.nextLine();

        System.out.print("¿Es cliente VIP? (s/n): ");
        boolean vip = scanner.nextLine().trim().equalsIgnoreCase("s");

        // ✅ Ahora usa el constructor correcto
        Cliente cliente = new Cliente(nombre, apellido, email, vip);

        System.out.print("Tipo de pedido (Para llevar / A domicilio): ");
        String tipo = scanner.nextLine();

        Pedido pedido = new Pedido(totalPedidos + 1, cliente, tipo);
        mostrarPlatos();

        while (true) {
            System.out.print("Ingrese ID del plato (0 para finalizar): ");
            int idPlato = scanner.nextInt();
            scanner.nextLine();

            if (idPlato == 0) break;

            Plato plato = buscarPlatoPorId(idPlato);
            if (plato != null) {
                pedido.agregarPlato(plato);
                System.out.println("🍽️ Plato agregado: " + plato.getNombre());
            } else {
                System.out.println("⚠️ ID inválido.");
            }
        }

        agregarPedido(pedido);
    }

    private void agregarPedido(Pedido pedido) {
        int prioridad = pedido.getCliente().isVip() ? 0 : 1;
        pedido.setEstado("📝 Pedido tomado");
        pedidosRegistrados[totalPedidos++] = pedido;
        colaPedidos.add(pedido.getId(), prioridad);
        System.out.println("✅ Pedido agregado correctamente #" + pedido.getId());
    }

    // ===============================
    // 🧾 PROCESAR TODOS LOS PEDIDOS
    // ===============================
    public void procesarTodosLosPedidos() {
        if (colaPedidos.isEmpty()) {
            System.out.println("⚠️ No hay pedidos pendientes.");
            return;
        }

        System.out.println("\n🍳 Enviando pedidos a cocina...");
        while (!colaPedidos.isEmpty()) {
            int id = colaPedidos.getElement();
            Pedido p = buscarPedidoPorId(id);
            colaPedidos.remove();

            if (p != null && p.getEstado().equals("📝 Pedido tomado")) {
                p.setEstado("🍳 En cocina");
                System.out.println("👨‍🍳 Pedido #" + p.getId() + " en preparación.");
                colaPedidos.add(p.getId(), p.esVip() ? 0 : 1);
            }
        }
        System.out.println("✅ Todos los pedidos fueron enviados a cocina.\n");
    }

    // ===============================
    // 🍳 PREPARAR TODOS LOS PEDIDOS
    // ===============================
    public void prepararTodosLosPedidos() {
        boolean algunoPreparado = false;
        for (int i = 0; i < totalPedidos; i++) {
            Pedido p = pedidosRegistrados[i];
            if (p != null && p.getEstado().equals("🍳 En cocina")) {
                for (int j = 0; j < p.cantidadDePlatos(); j++) {
                    Plato plato = p.obtenerPlato(j);
                    plato.setEstado("listo");
                }
                p.setEstado("🚚 Listo para entregar");
                System.out.println("✅ Pedido #" + p.getId() + " listo para entregar.");
                algunoPreparado = true;
            }
        }
        if (!algunoPreparado)
            System.out.println("⚠️ No hay pedidos en cocina para preparar.\n");
    }

    // ===============================
    // 🚚 ENTREGAR TODOS LOS PEDIDOS
    // ===============================
    public void entregarTodosLosPedidos() {
        boolean algunoEntregado = false;
        for (int i = 0; i < totalPedidos; i++) {
            Pedido p = pedidosRegistrados[i];
            if (p != null && p.getEstado().equals("🚚 Listo para entregar")) {
                p.setEstado("✅ Entregado");
                System.out.println("🚚 Pedido #" + p.getId() + " entregado a " + p.getCliente().getNombre());
                algunoEntregado = true;
            }
        }
        if (!algunoEntregado)
            System.out.println("⚠️ No hay pedidos listos para entregar.\n");
    }

    // ===============================
    // 🗑️ ELIMINAR PEDIDO POR NÚMERO
    // ===============================
    public void eliminarPedido(Scanner scanner) {
        System.out.print("Ingrese el número del pedido a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < totalPedidos; i++) {
            if (pedidosRegistrados[i] != null && pedidosRegistrados[i].getId() == id) {
                pedidosRegistrados[i] = null;
                System.out.println("🗑️ Pedido #" + id + " eliminado correctamente.\n");
                return;
            }
        }
        System.out.println("⚠️ No se encontró el pedido #" + id + ".\n");
    }

    // ===============================
    // 🔍 MÉTODOS DE APOYO
    // ===============================
    private Pedido buscarPedidoPorId(int id) {
        for (int i = 0; i < totalPedidos; i++) {
            if (pedidosRegistrados[i] != null && pedidosRegistrados[i].getId() == id) {
                return pedidosRegistrados[i];
            }
        }
        return null;
    }

    public void mostrarReporteInteractivo(Scanner scanner) {
        System.out.println("\n📊 Reporte general del sistema:");
        int pendientes = 0, cocina = 0, listos = 0, entregados = 0;

        for (int i = 0; i < totalPedidos; i++) {
            Pedido p = pedidosRegistrados[i];
            if (p == null) continue;
            switch (p.getEstado()) {
                case "📝 Pedido tomado" -> pendientes++;
                case "🍳 En cocina" -> cocina++;
                case "🚚 Listo para entregar" -> listos++;
                case "✅ Entregado" -> entregados++;
            }
        }

        System.out.println("Pendientes: " + pendientes);
        System.out.println("En cocina: " + cocina);
        System.out.println("Listos: " + listos);
        System.out.println("Entregados: " + entregados + "\n");
    }

    public void mostrarEstadoPedidos() {
        if (totalPedidos == 0) {
            System.out.println("⚠️ No hay pedidos registrados.");
            return;
        }

        System.out.println("\n📋 LISTADO DE PEDIDOS");
        System.out.println("-------------------------------------");
        for (int i = 0; i < totalPedidos; i++) {
            Pedido p = pedidosRegistrados[i];
            if (p != null) {
                System.out.println("Pedido #" + p.getId() +
                        " | Cliente: " + p.getCliente().getNombre() +
                        " | Estado: " + p.getEstado());
            }
        }
        System.out.println("-------------------------------------");
    }

    public void mostrarPlatos() {
        System.out.println("\n🍽️ MENÚ DISPONIBLE:");
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
                new Plato(1, "Milanesa con papas", 3500),
                new Plato(2, "Hamburguesa completa", 3000),
                new Plato(3, "Pizza muzzarella", 4200),
                new Plato(4, "Empanadas (3 unidades)", 2000),
                new Plato(5, "Ravioles con salsa", 3800)
        };
    }
}
