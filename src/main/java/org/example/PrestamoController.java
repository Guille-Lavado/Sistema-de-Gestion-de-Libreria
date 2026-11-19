package org.example;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PrestamoController {
    private static LibreriaBBDD bbdd;
    private static Scanner scanner = new Scanner(System.in);

    public static void setBbdd(LibreriaBBDD conexionBbdd) {
        bbdd = conexionBbdd;
    }

    public static void prestar() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Inserta el ID del libro: ");
            int idLibro = Integer.parseInt(scanner.nextLine());
            System.out.print("Inserta el ID del socio: ");
            int idSocio = Integer.parseInt(scanner.nextLine());
            System.out.print("Inserta la fecha de inicio (YYYY-MM-DD): ");
            String fechaIni = scanner.nextLine();
            System.out.print("Inserta la fecha de fin (YYYY-MM-DD o vacío): ");
            String fechaFin = scanner.nextLine();

            Date sqlFechaIni = fechaIni.isBlank() ? null : Date.valueOf(fechaIni.trim());
            Date sqlFechaFin = fechaFin.isBlank() ? null : Date.valueOf(fechaFin.trim());
            int nFilas = bbdd.realizarPrestamo(idLibro, idSocio, sqlFechaIni, sqlFechaFin);
            if (nFilas > 0) {
                System.out.println("Préstamo realizado correctamente.");
            } else {
                System.out.println("No se pudo realizar el préstamo.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Los IDs deben ser números enteros.");
        }
    }

    public static void leer() {
        try (ResultSet rs = bbdd.leerPrestamos()) {
            if (rs == null) return;

            boolean hayPrestamos = false;
            while (rs.next()) {
                hayPrestamos = true;
                int idLibro = rs.getInt("id_libros");
                String titulo = rs.getString("titulo");
                int idSocio = rs.getInt("id_socios");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                Date fechaIni = rs.getDate("fecha_ini");
                Date fechaFin = rs.getDate("fecha_fin");

                System.out.println("Libro [" + idLibro + "] " + titulo +
                        " -> Socio [" + idSocio + "] " + nombre + " " + apellidos +
                        " | Inicio: " + fechaIni +
                        " | Fin: " + (fechaFin != null ? fechaFin : "Pendiente"));
            }
            if (!hayPrestamos) {
                System.out.println("No hay préstamos registrados.");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void contarLibrosPorSocio() {
        try {
            System.out.print("Inserta el ID del socio: ");
            int idSocio = Integer.parseInt(scanner.nextLine());

            try (ResultSet rs = bbdd.contarLibrosPrestadosSocio(idSocio)) {
                if (rs == null) return;

                if (rs.next()) {
                    int total = rs.getInt("total_libros");
                    System.out.println("El socio con ID " + idSocio + " tiene " + total + " libro(s) en préstamos registrados.");
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: El ID del socio debe ser un número entero.");
        }
    }

    public static void leerLibrosVencidos() {
        try (ResultSet rs = bbdd.librosFueraPlazo()) {
            if (rs == null) return;

            boolean hayResultados = false;
            while (rs.next()) {
                hayResultados = true;
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                Date fechaFin = rs.getDate("fecha_fin");
                System.out.println("Libro [" + id + "] " + titulo + " (" + autor + ") - Fecha fin: " + fechaFin);
            }
            if (!hayResultados) {
                System.out.println("No hay libros que hayan superado la fecha de fin.");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void leerSociosLibrosVencidos() {
        try (ResultSet rs = bbdd.sociosLibrosFueraPlazo()) {
            if (rs == null) return;

            boolean hayResultados = false;
            while (rs.next()) {
                hayResultados = true;
                int idSocio = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                int telefono = rs.getInt("telefono");
                String titulo = rs.getString("titulo");
                Date fechaFin = rs.getDate("fecha_fin");
                System.out.println("Socio [" + idSocio + "] " + nombre + " " + apellidos +
                        " (Tel: " + telefono + ") -> Libro: " + titulo + " | Fecha fin: " + fechaFin);
            }
            if (!hayResultados) {
                System.out.println("No hay socios con libros que hayan superado la fecha de fin.");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
