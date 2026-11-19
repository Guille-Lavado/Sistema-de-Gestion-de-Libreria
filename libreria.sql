CREATE DATABASE IF NOT EXISTS libreria;

USE libreria;

-- Borrar Tablas --
DROP TABLE IF EXISTS libros_socios;
DROP TABLE IF EXISTS libros;
DROP TABLE IF EXISTS socios;

-- Crear tabla libros --
CREATE TABLE libros (
    id int AUTO_INCREMENT PRIMARY KEY,
    autor varchar(100) NOT NULL,
    titulo varchar(255) NOT NULL,
    numero_ejemplares int,
    editorial varchar(255),
    numero_paginas int,
    anno_edicion int
);

-- Crear tabla socios --
CREATE TABLE socios (
    id int AUTO_INCREMENT PRIMARY KEY,
    nombre varchar(50) NOT NULL,
    apellidos varchar(100) NOT NULL,
    edad int,
    direccion varchar(255),
    telefono int
);

-- Crear tabla libros_socios --
CREATE TABLE libros_socios (
    id_libros int,
    id_socios int,
    fecha_ini date,
    fecha_fin date,
    PRIMARY KEY (id_libros, id_socios),
    CONSTRAINT fk_libros FOREIGN KEY (id_libros) REFERENCES libros(id) ON DELETE CASCADE,
    CONSTRAINT fk_socios FOREIGN KEY (id_socios) REFERENCES socios(id) ON DELETE CASCADE
);

-- Insertar Libros --
INSERT INTO libros (autor, titulo, numero_ejemplares, editorial, numero_paginas, anno_edicion) VALUES
('Gabriel García Márquez', 'Cien años de soledad', 5, 'Editorial Sudamericana', 417, 1967),
('Miguel de Cervantes', 'Don Quijote de la Mancha', 3, 'Real Academia Española', 1345, 1605),
('Antoine de Saint-Exupéry', 'El principito', 10, 'Reynal & Hitchcock', 96, 1943),
('George Orwell', '1984', 4, 'Secker & Warburg', 328, 1949),
('Fiódor Dostoyevski', 'Crimen y castigo', 2, 'The Russian Messenger', 671, 1866);

-- Insertar Socios --
INSERT INTO socios (nombre, apellidos, edad, direccion, telefono) VALUES
('Juan', 'Pérez Gómez', 28, 'Calle Mayor 12, Madrid', 600123456),
('María', 'López Ruiz', 34, 'Avenida de la Paz 45, Barcelona', 611234567),
('Carlos', 'García Sánchez', 42, 'Plaza España 8, Valencia', 622345678),
('Ana', 'Martínez Gil', 22, 'Calle Gran Vía 100, Sevilla', 633456789),
('Lucía', 'Fernández Torres', 39, 'Calle Luna 3, Bilbao', 644567890);

-- Relacionar Libros y Socios --
INSERT INTO libros_socios (id_libros, id_socios, fecha_ini, fecha_fin) VALUES
(1, 1, '2026-11-01', '2026-11-15'), -- Juan tuvo prestado 'Cien años de soledad'
(2, 2, '2026-11-05', NULL),         -- María tiene prestado 'Don Quijote de la Mancha' (pendiente de devolver)
(3, 1, '2026-11-10', NULL),         -- Juan tiene prestado 'El principito' (pendiente de devolver)
(4, 3, '2026-10-20', '2026-11-03'), -- Carlos tuvo prestado '1984'
(5, 4, '2026-11-12', NULL);         -- Ana tiene prestada 'Crimen y castigo' (pendiente de devolver)