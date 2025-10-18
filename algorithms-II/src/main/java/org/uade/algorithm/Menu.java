package org.uade.algorithm;

import java.util.Scanner;

public class Menu {

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        while (true) {
            System.out.println("==== SISTEMA DE RESTAURANTE ====");
            System.out.println("1. Registrar pedido");
            System.out.println("2. Preparar platos");
            System.out.println("3. Asignar pedido a repartidor");
            System.out.println("4. Generar reportes");
            System.out.println("0. Salir");
            System.out.print("Ingrese una opción (0-4): ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();

                if (opcion >= 0 && opcion <= 4) {
                    switch (opcion) {
                        case 1:
                            System.out.println("📋 Iría a la pantalla de *Registrar pedido* (pendiente de implementación)\n");
                            // RegistrarPedido registrar = new RegistrarPedido();
                            // registrar.mostrar();
                            break;
                        case 2:
                            System.out.println("🍳 Iría a la pantalla de *Preparar platos* (pendiente de implementación)\n");
                            // PrepararPlatos preparar = new PrepararPlatos();
                            // preparar.mostrar();
                            break;
                        case 3:
                            System.out.println("🚗 Iría a la pantalla de *Asignar pedido a repartidor* (pendiente de implementación)\n");
                            // AsignarPedido asignar = new AsignarPedido();
                            // asignar.mostrar();
                            break;
                        case 4:
                            System.out.println("📊 Iría a la pantalla de *Generar reportes* (pendiente de implementación)\n");
                            // GenerarReportes reportes = new GenerarReportes();
                            // reportes.mostrar();
                            break;
                        case 0:
                            System.out.println("👋 Saliendo del sistema...");
                            scanner.close();
                            return;
                    }
                } else {
                    System.out.println("❌ Opción fuera de rango. Ingrese un número entre 0 y 4.\n");
                }
            } else {
                System.out.println("❌ Entrada inválida. Ingrese un número entre 0 y 4.\n");
                scanner.next(); // limpia entrada inválida
            }
        }
    }
}
