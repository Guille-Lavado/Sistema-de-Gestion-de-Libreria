package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n--- GESTIÓN DE LIBRERÍA ---");
        System.out.println("ll  - Leer libro              | ls  - Leer socio");
        System.out.println("cl  - Crear libro             | cs  - Crear socio");
        System.out.println("ml  - Modificar libro         | ms  - Modificar socio");
        System.out.println("el  - Eliminar libro          | es  - Eliminar socio");
        System.out.println("blt - Buscar libro por título | bsn - Buscar socio por nombre");
        System.out.println("bla - Buscar libro por autor  | bsa - Buscar socio por apellido");
        System.out.println("p   - Prestar libro a socio   | lp  - Leer préstamos");
        System.out.println("ps  - Préstamos por socio     | lv  - Libros fuera de plazo ");
        System.out.println("sv  - Socios fuera de plazo   | q   - Salir");

        Scanner scanner = new Scanner(System.in);
        String opcion = "";
        LibreriaBBDD conexion = new LibreriaBBDD();

        if (!conexion.conectar()) {
            System.out.println("Error: No se pudo conectar con la base de datos.");
            scanner.close();
            return;
        }

        LibroController.setBbdd(conexion);
        SocioController.setBbdd(conexion);
        PrestamoController.setBbdd(conexion);

        while (!opcion.equals("q")) {
            System.out.print("Escribe una opción: ");
            opcion = scanner.nextLine().toLowerCase();

            switch (opcion) {
                case "ll":
                    LibroController.leer();
                    break;
                case "lc":
                    LibroController.crear();
                    break;
                case "lm":
                    LibroController.modificar();
                    break;
                case "le":
                    LibroController.eliminar();
                    break;
                case "blt":
                    LibroController.buscar("t");
                    break;
                case "bla":
                    LibroController.buscar("a");
                    break;
                case "ls":
                    SocioController.leer();
                    break;
                case "cs":
                    SocioController.crear();
                    break;
                case "ms":
                    SocioController.modificar();
                    break;
                case "es":
                    SocioController.eliminar();
                    break;
                case "bsn":
                    SocioController.buscar("n");
                    break;
                case "bsa":
                    SocioController.buscar("a");
                case "p":
                    PrestamoController.prestar();
                    break;
                case "lp":
                    PrestamoController.leer();
                    break;
                case "ps":
                    PrestamoController.contarLibrosPorSocio();
                    break;
                case "lv":
                    PrestamoController.leerLibrosVencidos();
                    break;
                case "sv":
                    PrestamoController.leerSociosLibrosVencidos();
                    break;
                case "q":
                    System.out.println("Hasta pronto!!");
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }

        if (!conexion.desconectar()) {
            System.out.println("Aviso: Hubo un problema al cerrar la conexión.");
        }
        scanner.close();
    }
}