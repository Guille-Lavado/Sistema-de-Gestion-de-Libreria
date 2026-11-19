package org.example;

public class Libro {
    String titulo;
    String autor;
    int numeroEjemplares;
    String editorial;
    int numeroPaginas;
    int annoEdicion;

    Libro(String titulo, String autor, int numeroEjemplares,
          String editorial, int numeroPaginas, int annoEdicion) {
        this.titulo = titulo;
        this.autor = autor;
        this.numeroEjemplares = numeroEjemplares;
        this.editorial = editorial;
        this.numeroPaginas = numeroPaginas;
        this.annoEdicion = annoEdicion;
    }
}
