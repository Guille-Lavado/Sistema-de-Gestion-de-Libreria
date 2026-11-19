# Sistema de Gestión de Librería

Aplicación de consola desarrollada en Java utilizando JDBC y MySQL para administrar el catálogo de libros, el registro de socios y el control de préstamos de una librería.

### Descripción de los Componentes

* **Modelos (`Libro`, `Socio`)**: Clases que encapsulan los atributos de cada entidad para facilitar el transporte de datos desde la entrada del usuario hasta la base de datos.
* **Acceso a Datos (`LibreriaBBDD`)**: Administra la conexión persistente con MySQL mediante `DriverManager`. Ejecuta sentencias parametrizadas (`PreparedStatement`) para prevenir inyección SQL, devolviendo `ResultSet` en operaciones de consulta y enteros con el número de filas afectadas en inserciones, modificaciones y borrados.
* **Controladores (`LibroController`, `SocioController`, `PrestamoController`)**: Clases con métodos estáticos que invocan los métodos correspondientes de `LibreriaBBDD` y formatean la salida de los resultados (`ResultSet`) por pantalla.

## Base de Datos

El sistema trabaja sobre una base de datos MySQL llamada `libreria` compuesta por tres tablas:

1. **`libros`**: Almacena los datos de las obras (`id`, `autor`, `titulo`, `numero_ejemplares`, `editorial`, `numero_paginas`, `anno_edicion`).
2. **`socios`**: Almacena los datos de los usuarios (`id`, `nombre`, `apellidos`, `edad`, `direccion`, `telefono`).
3. **`libros_socios`**: Tabla intermedia que registra los préstamos (`id_libros`, `id_socios`, `fecha_ini`, `fecha_fin`) con clave primaria compuesta `(id_libros, id_socios)` y claves foráneas configuradas con `ON DELETE CASCADE`.
