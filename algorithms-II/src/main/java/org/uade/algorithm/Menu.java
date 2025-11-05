package org.uade.algorithm;

import java.util.Scanner;

public class Menu {
    private ControladorRestaurante controlador;

    public Menu() {
        controlador = new ControladorRestaurante();
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
                case 1 -> controlador.registrarPedido(scanner);
                case 2 -> controlador.mostrarPlatos();
                case 3 -> controlador.procesarTodosLosPedidos();
                case 4 -> controlador.prepararTodosLosPedidos();
                case 5 -> controlador.entregarTodosLosPedidos();
                case 6 -> controlador.mostrarReporteInteractivo(scanner);
                case 7 -> controlador.mostrarEstadoPedidos();
                case 8 -> controlador.eliminarPedido(scanner);
                case 0 -> {
                    System.out.println("\n👋 Saliendo del sistema... ¡Hasta luego!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("⚠️ Opción inválida. Intente nuevamente.\n");
            }
        }
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n=====================================");
        System.out.println("        🍽️  SISTEMA DE RESTAURANTE");
        System.out.println("=====================================");
        System.out.println("📋  PEDIDOS");
        System.out.println("   1. Registrar nuevo pedido");
        System.out.println("   2. Mostrar platos disponibles");
        System.out.println("-------------------------------------");
        System.out.println("🧾  ORGANIZACIÓN DE PEDIDOS");
        System.out.println("   3. Procesar todos los pedidos tomados (pasar a cocina)");
        System.out.println("-------------------------------------");
        System.out.println("🍳  COCINA");
        System.out.println("   4. Preparar todos los pedidos en cocina");
        System.out.println("-------------------------------------");
        System.out.println("🚚  ENTREGAS");
        System.out.println("   5. Entregar todos los pedidos listos");
        System.out.println("-------------------------------------");
        System.out.println("📊  REPORTES Y ANÁLISIS");
        System.out.println("   6. Ver reporte general del sistema");
        System.out.println("-------------------------------------");
        System.out.println("📦  ESTADO DE PEDIDOS");
        System.out.println("   7. Ver pedidos y su estado actual");
        System.out.println("-------------------------------------");
        System.out.println("🗑️  GESTIÓN ADMINISTRATIVA");
        System.out.println("   8. Eliminar pedido por número");
        System.out.println("-------------------------------------");
        System.out.println("0. Salir");
        System.out.println("=====================================");
    }
}
