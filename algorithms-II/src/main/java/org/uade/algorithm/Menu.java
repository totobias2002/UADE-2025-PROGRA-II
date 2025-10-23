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
            System.out.println("==== SISTEMA DE RESTAURANTE ====");
            System.out.println("1. Registrar pedido");
            System.out.println("2. Mostrar platos disponibles");
            System.out.println("3. Procesar siguiente pedido");
            System.out.println("4. Preparar siguiente plato");
            System.out.println("5. Entregar plato listo");
            System.out.println("0. Salir");
            System.out.print("Ingrese una opción: ");

            if (!scanner.hasNextInt()) {
                System.out.println("❌ Entrada inválida.\n");
                scanner.next();
                continue;
            }

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> registrarPedido(scanner);
                case 2 -> gestor.mostrarPlatos();
                case 3 -> gestor.procesarSiguientePedido();
                case 4 -> gestor.prepararSiguientePlato();
                case 5 -> gestor.entregarPlato();
                case 0 -> {
                    System.out.println("👋 Saliendo del sistema...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("❌ Opción inválida.\n");
            }
        }
    }

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

        System.out.print("Tipo de pedido (1 = Para llevar / 2 = A domicilio): ");
        String tipo = scanner.nextLine().equals("1") ? "Para llevar" : "A domicilio";

        Cliente cliente = new Cliente(nombre, direccion, telefono, vip);
        Pedido pedido = new Pedido(contadorPedidos++, cliente, tipo);

        // Elegir platos
        boolean agregando = true;
        while (agregando) {
            gestor.mostrarPlatos();
            System.out.print("Ingrese el ID del plato a agregar (0 para terminar): ");
            if (!scanner.hasNextInt()) {
                System.out.println("❌ Entrada inválida.");
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
                    System.out.println("❌ Plato no encontrado.");
                }
            }
        }

        gestor.agregarPedido(pedido);
        System.out.println("Pedido registrado: " + pedido + "\n");
    }
}
