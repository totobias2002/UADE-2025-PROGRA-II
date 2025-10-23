package org.uade.algorithm;

import org.uade.algorithm.Cliente;
import org.uade.algorithm.Pedido;
import org.uade.structure.implementation.dynamic.DynamicPriorityQueueADT;

import java.util.Scanner;

public class Menu {

    private DynamicPriorityQueueADT colaPedidos = new DynamicPriorityQueueADT();
    private Pedido[] pedidos = new Pedido[100]; // arreglo auxiliar para guardar los objetos
    private int contadorPedidos = 0;

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        while (true) {
            System.out.println("==== SISTEMA DE RESTAURANTE ====");
            System.out.println("1. Registrar pedido");
            System.out.println("2. Ver pedidos pendientes");
            System.out.println("3. Preparar siguiente pedido (según prioridad)");
            System.out.println("0. Salir");
            System.out.print("Ingrese una opción (0-3): ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // limpia buffer

                switch (opcion) {
                    case 1 -> registrarPedido(scanner);
                    case 2 -> mostrarPedidosPendientes();
                    case 3 -> prepararSiguientePedido();
                    case 0 -> {
                        System.out.println("👋 Saliendo del sistema...");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("❌ Opción fuera de rango.\n");
                }
            } else {
                System.out.println("❌ Entrada inválida. Intente nuevamente.\n");
                scanner.next();
            }
        }
    }

    // Registrar pedido
    private void registrarPedido(Scanner scanner) {
        System.out.println("\n=== REGISTRAR PEDIDO ===");
        System.out.print("Nombre del cliente: ");
        String nombre = scanner.nextLine();

        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        System.out.print("¿Es cliente VIP? (s/n): ");
        boolean vip = scanner.nextLine().equalsIgnoreCase("s");

        System.out.print("Tipo de pedido (para llevar / a domicilio): ");
        String tipo = scanner.nextLine();

        // Crear cliente y pedido
        Cliente cliente = new Cliente(nombre, direccion, telefono, vip);
        Pedido pedido = new Pedido(contadorPedidos + 1, cliente, tipo);

        // Guardar pedido en arreglo
        pedidos[contadorPedidos] = pedido;

        // Clasificar prioridad
        int prioridad = vip ? 1 : 2;

        // Agregar índice del pedido a la cola con prioridad
        colaPedidos.add(contadorPedidos, prioridad);

        contadorPedidos++;

        System.out.println("✅ Pedido registrado correctamente.");
        System.out.println(pedido + "\n");
    }

    // Mostrar pedidos pendientes
    private void mostrarPedidosPendientes() {
        System.out.println("\n=== PEDIDOS PENDIENTES ===");

        if (colaPedidos.isEmpty()) {
            System.out.println("No hay pedidos en espera.\n");
            return;
        }

        for (int i = 0; i < contadorPedidos; i++) {
            if (pedidos[i] != null) {
                System.out.println(pedidos[i]);
            }
        }
        System.out.println();
    }

    //  Preparar siguiente pedido (según prioridad)
    private void prepararSiguientePedido() {
        if (colaPedidos.isEmpty()) {
            System.out.println("📭 No hay pedidos para preparar.\n");
            return;
        }

        int indice = colaPedidos.getElement(); // índice del pedido con más prioridad
        colaPedidos.remove();

        Pedido pedido = pedidos[indice];
        pedido.setEstado("en preparación");

        System.out.println("🍳 Preparando: " + pedido);
        System.out.println();
    }
}
