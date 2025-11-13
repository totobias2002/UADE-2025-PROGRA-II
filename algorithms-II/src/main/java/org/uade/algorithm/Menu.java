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
                System.out.println("X Entrada inválida.");
                scanner.next();
                continue;
            }

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> controlador.registrarPedido(scanner);
                case 2 -> controlador.procesarSiguientePedido();
                case 3 -> controlador.prepararSiguientePedido();
                case 4 -> controlador.entregarSiguientePedido();
                case 5 -> controlador.mostrarEstadoPedidos();
                case 6 -> controlador.mostrarMenuReportes(scanner);
                case 7 -> controlador.eliminarPedido(scanner);
                case 0 -> {
                    System.out.println("Saliendo del sistema...");
                    return;
                }
                default -> System.out.println("X Opción fuera de rango.");
            }
        }
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n===== SISTEMA DEL RESTAURANTE =====");
        System.out.println("1. Registrar pedido");
        System.out.println("2. Procesar siguiente pedido");
        System.out.println("3. Preparar siguiente pedido");
        System.out.println("4. Entregar siguiente pedido");
        System.out.println("5. Mostrar estado de pedidos");
        System.out.println("6. Reportes");
        System.out.println("7. Eliminar un pedido");
        System.out.println("0. Salir");
        System.out.println("===================================\n");
    }
}
