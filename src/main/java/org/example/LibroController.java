package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LibroController {

    private static LibreriaBBDD bbdd;
    private static Scanner scanner = new Scanner(System.in);

    public static void setBbdd(LibreriaBBDD conexionBbdd) {
        bbdd = conexionBbdd;
    }

    public static void leer() {
        try (ResultSet rs = bbdd.leerLibro()) {
            if (rs == null) return;

            while (rs.next()) {
                pintarLibro(rs);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void buscar(String columna) {
        if (columna.equals("t")) {
            System.out.print("Inserta el título de la obra: ");
        } else {
            System.out.print("Inserta el autor de la obra: ");
        }
        String dato = scanner.nextLine();

        try (ResultSet rs = bbdd.buscarLibro(columna, dato)) {
            if (rs == null) return;

            boolean encontrado = false;
            while (rs.next()) {
                encontrado = true;
                pintarLibro(rs);
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
            Libro l = pedirDatosLibro();
            int nFilas = bbdd.crearLibro(l.titulo, l.autor, l.numeroEjemplares, l.editorial, l.numeroPaginas, l.annoEdicion);
            System.out.println(nFilas > 0 ? "Inserción correcta." : "No se insertó ningún registro.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Valor numérico inválido.");
        }
    }

    public static void modificar() {
        try {
            System.out.print("Inserta el ID del libro a modificar: ");
            int id = Integer.parseInt(scanner.nextLine());
            Libro l = pedirDatosLibro();
            int nFilas = bbdd.modificarLibro(id, l.titulo, l.autor, l.numeroEjemplares, l.editorial, l.numeroPaginas, l.annoEdicion);
            if (nFilas > 0) {
                System.out.println("Libro modificado correctamente.");
            } else if (nFilas == 0) {
                System.out.println("No existe ningún libro con ese ID.");
            } else {
                System.out.println("Error al modificar el libro.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Valor numérico inválido.");
        }
    }

    public static void eliminar() {
        try {
            System.out.print("Inserta el ID del libro a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine());
            int nFilas = bbdd.eliminarLibro(id);
            if (nFilas > 0) {
                System.out.println("Libro eliminado correctamente.");
            } else if (nFilas == 0) {
                System.out.println("No existe ningún libro con ese ID.");
            } else {
                System.out.println("Error al eliminar el libro.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: El ID debe ser un número entero.");
        }
    }

    private static Libro pedirDatosLibro() {
        System.out.print("Inserta el título de la obra: ");
        String titulo = scanner.nextLine();
        System.out.print("Inserta el autor de la obra: ");
        String autor = scanner.nextLine();
        System.out.print("Inserta el número de ejemplares: ");
        int ejemplares = Integer.parseInt(scanner.nextLine());
        System.out.print("Inserta el nombre de la editorial: ");
        String editorial = scanner.nextLine();
        System.out.print("Inserta el número de páginas: ");
        int paginas = Integer.parseInt(scanner.nextLine());
        System.out.print("Inserta el año de edición: ");
        int anno = Integer.parseInt(scanner.nextLine());
        return new Libro(titulo, autor, ejemplares, editorial, paginas, anno);
    }

    private static void pintarLibro(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String autor = rs.getString("autor");
        String titulo = rs.getString("titulo");
        System.out.println(id + " - " + autor + " - " + titulo);
    }
}