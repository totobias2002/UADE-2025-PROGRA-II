package org.uade.algorithm;

import java.util.Scanner;

public class Validador {

    public static int leerEnteroEnRango(Scanner scanner, int min, int max, String mensajeError) {
        while (true) {
            if (!scanner.hasNextInt()) {
                System.out.println("X " + mensajeError);
                scanner.next();
                continue;
            }

            int valor = scanner.nextInt();
            scanner.nextLine();

            if (valor < min || valor > max) {
                System.out.println("X " + mensajeError);
                continue;
            }

            return valor;
        }
    }

    public static String leerTelefono(Scanner scanner) {
        while (true) {
            String tel = scanner.nextLine().trim();
            if (tel.matches("[0-9]+")) return tel;
            System.out.print("X Teléfono inválido. Solo números: ");
        }
    }

    public static String leerNombre(Scanner scanner) {
        while (true) {
            String nombre = scanner.nextLine().trim();
            if (nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) return nombre;
            System.out.print("X Nombre inválido. Solo letras: ");
        }
    }

    public static boolean leerSiNo(Scanner scanner) {
        while (true) {
            String r = scanner.nextLine().trim().toLowerCase();
            if (r.equals("s")) return true;
            if (r.equals("n")) return false;
            System.out.print("X Ingrese s/n: ");
        }
    }
}
