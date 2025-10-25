package org.uade.algorithm;

import java.util.Scanner;

public class Menu {
    private GestorPedidos gestor;
    private int contadorPedidos = 1;

    public Menu() {
        gestor = new GestorPedidos();
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        while (true) {
            mostrarMenuPrincipal();
            System.out.print("Seleccione una opción: ");

            if (!scanner.hasNextInt()) {
                System.out.println("❌ Entrada inválida.\n");
                scanner.next();
                continue;
            }

            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> registrarPedido(scanner);
                case 2 -> gestor.mostrarPlatos();
                case 3 -> gestor.procesarSiguientePedido();
                case 4 -> gestor.prepararSiguientePlato();
                case 5 -> gestor.entregarPlato();
                case 6 -> mostrarReporteInteractivo(scanner);
                case 0 -> {
                    System.out.println("\n👋 Saliendo del sistema... ¡Hasta luego!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("⚠️ Opción inválida. Intente nuevamente.\n");
            }
        }
    }

    // ------------------- MENÚ PRINCIPAL -------------------

    private void mostrarMenuPrincipal() {
        System.out.println("\n=====================================");
        System.out.println("        🍽️  SISTEMA DE RESTAURANTE");
        System.out.println("=====================================");
        System.out.println("📋  PEDIDOS");
        System.out.println("   1. Registrar nuevo pedido");
        System.out.println("   2. Mostrar platos disponibles");
        System.out.println("   3. Procesar siguiente pedido");
        System.out.println("-------------------------------------");
        System.out.println("🍳  COCINA");
        System.out.println("   4. Preparar siguiente plato");
        System.out.println("-------------------------------------");
        System.out.println("🚚  ENTREGAS");
        System.out.println("   5. Entregar plato / pedido listo");
        System.out.println("-------------------------------------");
        System.out.println("📊  REPORTES Y ANÁLISIS");
        System.out.println("   6. Ver reporte general del sistema");
        System.out.println("-------------------------------------");
        System.out.println("0. Salir");
        System.out.println("=====================================");
    }

    // ------------------- REGISTRO DE PEDIDO -------------------

    private void registrarPedido(Scanner scanner) {
        System.out.println("\n=== 📝 REGISTRAR NUEVO PEDIDO ===");

        System.out.print("Nombre del cliente: ");
        String nombre = scanner.nextLine();

        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        System.out.print("¿Es cliente VIP? (s/n): ");
        boolean vip = scanner.nextLine().equalsIgnoreCase("s");

        System.out.print("Tipo de pedido (1 = Para llevar / 2 = A domicilio): ");
        String tipo = scanner.nextLine().equals("1") ? "Para llevar" : "A domicilio";

        Cliente cliente = new Cliente(nombre, direccion, telefono, vip);
        Pedido pedido = new Pedido(contadorPedidos++, cliente, tipo);

        // Elegir platos
        boolean agregando = true;
        while (agregando) {
            gestor.mostrarPlatos();
            System.out.print("Ingrese el número del plato a agregar (0 para terminar): ");

            if (!scanner.hasNextInt()) {
                System.out.println("❌ Entrada inválida.\n");
                scanner.next();
                continue;
            }

            int idPlato = scanner.nextInt();
            scanner.nextLine();

            if (idPlato == 0) {
                agregando = false;
            } else {
                Plato plato = gestor.buscarPlatoPorId(idPlato);
                if (plato != null) {
                    pedido.agregarPlato(new Plato(plato.getId(), plato.getNombre(), plato.getPrecio()));
                    System.out.println("✅ Plato agregado: " + plato.getNombre());
                } else {
                    System.out.println("⚠️ Plato no encontrado.");
                }
            }
        }

        gestor.agregarPedido(pedido);
        System.out.println("\n✅ Pedido registrado exitosamente:");
        System.out.println(pedido + "\n");
    }

    // ------------------- NUEVA FUNCIÓN: REPORTE INTERACTIVO -------------------

    private void mostrarReporteInteractivo(Scanner scanner) {
        while (true) {
            System.out.println("\n=== 📊 REPORTE GENERAL DEL SISTEMA ===");
            gestor.generarReportes(); // muestra el resumen actual
            System.out.println("-------------------------------------");
            System.out.print("Presione 0 para volver al menú principal: ");
            String entrada = scanner.nextLine();
            if (entrada.equals("0")) {
                System.out.println();
                break;
            } else {
                System.out.println("⚠️ Entrada inválida. Intente nuevamente.\n");
            }
        }
    }
}
