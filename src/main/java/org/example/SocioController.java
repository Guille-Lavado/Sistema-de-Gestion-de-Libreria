package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SocioController {
    private static LibreriaBBDD bbdd;
    private static Scanner scanner = new Scanner(System.in);

    public static void setBbdd(LibreriaBBDD conexionBbdd) {
        bbdd = conexionBbdd;
    }

    public static void leer() {
        try (ResultSet rs = bbdd.leerSocio()) {
            if (rs == null) return;

            while (rs.next()) {
                pintarSocio(rs);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void buscar(String columna) {
        if (columna.equals("n")) {
            System.out.print("Inserta el nombre del socio: ");
        } else {
            System.out.print("Inserta los apellidos del socio: ");
        }
        String dato = scanner.nextLine();

        try (ResultSet rs = bbdd.buscarSocio(columna, dato)) {
            if (rs == null) return;

            boolean encontrado = false;
            while (rs.next()) {
                encontrado = true;
                pintarSocio(rs);
            }
            if (!encontrado) {
                System.out.println("No se encontraron libros con ese título.");
            }
        } catch (SQLException e) {
            System.out.print(e);
        }
    }

    public static void crear() {
        try {
            Socio s = pedirDatosSocio();
            int nFilas = bbdd.crearSocio(s.nombre, s.apellidos, s.edad, s.direccion, s.telefono);
            System.out.println(nFilas > 0 ? "Inserción correcta." : "No se insertó ningún registro.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Valor numérico inválido.");
        }
    }

    public static void modificar() {
        try {
            System.out.print("Inserta el ID del socio a modificar: ");
            int id = Integer.parseInt(scanner.nextLine());
            Socio s = pedirDatosSocio();
            int nFilas = bbdd.modificarSocio(id, s.nombre, s.apellidos, s.edad, s.direccion, s.telefono);
            if (nFilas > 0) {
                System.out.println("Socio modificado correctamente.");
            } else if (nFilas == 0) {
                System.out.println("No existe ningún socio con ese ID.");
            } else {
                System.out.println("Error al modificar el socio.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Valor numérico inválido.");
        }
    }

    public static void eliminar() {
        try {
            System.out.print("Inserta el ID del socio a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine());
            int nFilas = bbdd.eliminarSocio(id);
            if (nFilas > 0) {
                System.out.println("Socio eliminado correctamente.");
            } else if (nFilas == 0) {
                System.out.println("No existe ningún socio con ese ID.");
            } else {
                System.out.println("Error al eliminar el socio.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: El ID debe ser un número entero.");
        }
    }

    private static Socio pedirDatosSocio() {
        System.out.print("Inserta el nombre del socio: ");
        String nombre = scanner.nextLine();
        System.out.print("Inserta los apellidos del socio: ");
        String apellidos = scanner.nextLine();
        System.out.print("Inserta la edad: ");
        int edad = Integer.parseInt(scanner.nextLine());
        System.out.print("Inserta la dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("Inserta el teléfono: ");
        int telefono = Integer.parseInt(scanner.nextLine());
        return new Socio(nombre, apellidos, edad, direccion, telefono);
    }

    private static void pintarSocio(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nombre = rs.getString("nombre");
        String apellidos = rs.getString("apellidos");
        int telefono = rs.getInt("telefono");
        System.out.println(id + " - " + nombre + " " + apellidos + " - Tel: " + telefono);
    }
}