package org.example;

import java.sql.*;

public class LibreriaBBDD {
    private Connection conexion;

    // Abre la conexión si aún no existe o está cerrada
    public boolean conectar() {
        String USUARIO = "alumno";
        String URL = "jdbc:mysql://localhost:3306/libreria";
        String CONTRASENA = "alumno";
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    // Cierra la conexión al salir de la aplicación
    public boolean desconectar() {
        try {
            if (!conexion.isClosed() && conexion != null) {
                conexion.close();
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    // Rellena los 6 campos comunes de un libro (índices 1 a 6)
    public void asignarDatosLibro(PreparedStatement ps, String titulo, String autor,
                                  int numero_ejemplares, String editorial,
                                  int numero_paginas, int anno_edicion) throws SQLException {
        ps.setString(1, titulo);
        ps.setString(2, autor);
        ps.setInt(3, numero_ejemplares);
        ps.setString(4, editorial);
        ps.setInt(5, numero_paginas);
        ps.setInt(6, anno_edicion);
    }

    // Rellena los 5 campos comunes de un socio (índices 1 a 5)
    public void asignarDatosSocio(PreparedStatement ps, String nombre, String apellidos,
                                  int edad, String direccion, int telefono) throws SQLException {
        ps.setString(1, nombre);
        ps.setString(2, apellidos);
        ps.setInt(3, edad);
        ps.setString(4, direccion);
        ps.setInt(5, telefono);
    }

    public ResultSet leerLibro() {
        String sql = "SELECT id, titulo, autor FROM libros";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet buscarLibro(String columna, String dato) {
        String sql;
        if (columna.equals("t")) {
            sql = "SELECT id, titulo, autor FROM libros WHERE titulo LIKE ?";
        } else {
            sql = "SELECT id, titulo, autor FROM libros WHERE autor LIKE ?";
        }

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, "%" + dato + "%");
            return ps.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public int crearLibro(String titulo, String autor, int numero_ejemplares,
                           String editorial, int numero_paginas, int anno_edicion) {
        String sql = "INSERT INTO libros " +
                "(titulo, autor, numero_ejemplares, editorial, numero_paginas, anno_edicion) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            asignarDatosLibro(ps, titulo, autor, numero_ejemplares, editorial, numero_paginas, anno_edicion);
            return ps.executeUpdate();  // Devuelve el número de filas afectadas
        } catch (SQLException e) {
            System.out.println(e);
            return -1;
        }
    }

    public int modificarLibro(int id, String titulo, String autor, int numero_ejemplares,
                              String editorial, int numero_paginas, int anno_edicion) {
        String sql = "UPDATE libros SET " +
                "titulo = ?, autor = ?, " +
                "numero_ejemplares = ?, " +
                "editorial = ?, " +
                "numero_paginas = ?, " +
                "anno_edicion = ? " +
                "WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            asignarDatosLibro(ps, titulo, autor, numero_ejemplares, editorial, numero_paginas, anno_edicion);
            ps.setInt(7, id);
            return ps.executeUpdate();  // Devuelve el número de filas afectadas, 0 si no existe el id
        } catch (SQLException e) {
            System.out.println(e);
            return -1;
        }
    }

    public int eliminarLibro(int id) {
        String sql = "DELETE FROM libros WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate(); // Devuelve 1 si se borró, 0 si no existía el id
        } catch (SQLException e) {
            System.out.println(e);
            return -1;
        }
    }

    public ResultSet leerSocio() {
        String sql = "SELECT id, nombre, apellidos, telefono FROM socios";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet buscarSocio(String columna, String dato) {
        String sql;
        if (columna.equals("n")) {
            sql = "SELECT id, nombre, apellidos, telefono FROM socios WHERE nombre LIKE ?";
        } else {
            sql = "SELECT id, nombre, apellidos, telefono FROM socios WHERE apellidos LIKE ?";
        }

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, "%" + dato + "%");
            return ps.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public int crearSocio(String nombre, String apellidos, int edad, String direccion, int telefono) {
        String sql = "INSERT INTO socios " +
                "(nombre, apellidos, edad, direccion, telefono) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            asignarDatosSocio(ps, nombre, apellidos, edad, direccion, telefono);
            return ps.executeUpdate(); // Devuelve el número de filas afectadas
        } catch (SQLException e) {
            System.out.println(e);
            return -1;
        }
    }

    public int modificarSocio(int id, String nombre, String apellidos, int edad, String direccion, int telefono) {
        String sql = "UPDATE socios SET " +
                "nombre = ?, apellidos = ?, " +
                "edad = ?, " +
                "direccion = ?, " +
                "telefono = ? " +
                "WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            asignarDatosSocio(ps, nombre, apellidos, edad, direccion, telefono);
            ps.setInt(6, id);
            return ps.executeUpdate(); // Devuelve el número de filas afectadas, 0 si no existe el id
        } catch (SQLException e) {
            System.out.println(e);
            return -1;
        }
    }

    public int eliminarSocio(int id) {
        String sql = "DELETE FROM socios WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate(); // Devuelve 1 si se borró, 0 si no existía el id
        } catch (SQLException e) {
            System.out.println(e);
            return -1;
        }
    }

    public int realizarPrestamo(int idLibro, int idSocio, Date fechaIni, Date fechaFin) {
        String sql = "INSERT INTO libros_socios " +
                "(id_libros, id_socios, fecha_ini, fecha_fin) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setInt(1, idLibro);
            ps.setInt(2, idSocio);
            ps.setDate(3, fechaIni);
            ps.setDate(4, fechaFin);
            return ps.executeUpdate();
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e);
            return -1;
        }
    }

    public ResultSet leerPrestamos() {
        String sql = "SELECT ls.id_libros, l.titulo, ls.id_socios, s.nombre, s.apellidos, ls.fecha_ini, ls.fecha_fin " +
                "FROM libros_socios ls " +
                "JOIN libros l ON ls.id_libros = l.id " +
                "JOIN socios s ON ls.id_socios = s.id";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet contarLibrosPrestadosSocio(int idSocio) {
        String sql = "SELECT COUNT(*) AS total_libros " +
                "FROM libros_socios " +
                "WHERE id_socios = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idSocio);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet librosFueraPlazo() {
        String sql = "SELECT DISTINCT l.id, l.titulo, l.autor, ls.fecha_fin " +
                "FROM libros_socios ls " +
                "JOIN libros l ON ls.id_libros = l.id " +
                "WHERE ls.fecha_fin IS NOT NULL AND ls.fecha_fin < CURDATE()";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet sociosLibrosFueraPlazo() {
        String sql = "SELECT DISTINCT s.id, s.nombre, s.apellidos, s.telefono, l.titulo, ls.fecha_fin " +
                "FROM libros_socios ls " +
                "JOIN socios s ON ls.id_socios = s.id " +
                "JOIN libros l ON ls.id_libros = l.id " +
                "WHERE ls.fecha_fin IS NOT NULL AND ls.fecha_fin < CURDATE()";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}
