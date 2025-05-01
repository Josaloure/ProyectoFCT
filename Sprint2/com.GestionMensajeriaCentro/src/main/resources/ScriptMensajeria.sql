-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS guzpasen;
USE guzpasen;

-- Tabla USUARIO
CREATE TABLE usuario (
    id_usuario BIGINT PRIMARY KEY AUTO_INCREMENT,
    nick VARCHAR(30) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    clave VARCHAR(255) NOT NULL,
    rol ENUM('ADMIN', 'PROFESOR', 'GESTOR_INCIDENCIAS') NOT NULL,
    usuario_movil BOOLEAN DEFAULT FALSE
);

-- Tabla TABLON_CENTRO
CREATE TABLE tablon_centro (
    id_tablon_centro BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    id_usuario BIGINT NOT NULL, -- Admin que gestiona este tablón
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- Tabla TABLON_PROFESOR
CREATE TABLE tablon_profesor (
    id_tablon_profesor BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    id_usuario BIGINT NOT NULL, -- Profesor que gestiona este tablón
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- Tabla MENSAJE
CREATE TABLE mensaje (
    id_mensaje BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(150) NOT NULL,
    contenido VARCHAR(350) NOT NULL,
    fecha_creacion DATE NOT NULL,
    fecha_publicacion DATE,
    publicado BOOLEAN DEFAULT FALSE,
    visible_para_todos BOOLEAN DEFAULT TRUE,
    id_publicador BIGINT NOT NULL,
    id_tablon_centro BIGINT NOT NULL,
    FOREIGN KEY (id_publicador) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_tablon_centro) REFERENCES tablon_centro(id_tablon_centro)
);

-- Tabla TAREA
CREATE TABLE tarea (
    id_tarea BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(150) NOT NULL,                         -- Nuevo campo que no estaba en el diseño original
    asignatura VARCHAR(50),                               -- Conservado del diseño original (si es útil)
    descripcion VARCHAR(350),                             -- Ampliado de 100 a 350 caracteres
    fecha_creacion DATE NOT NULL,                         -- Equivale a fecha_tarea
    prioridad ENUM('Alta', 'Media', 'Baja') NOT NULL,     -- Nuevo campo
    estado ENUM('Pendiente', 'En curso', 'Finalizada') NOT NULL,  -- Adaptado de COMPLETADA/PENDIENTE
    categoria ENUM('Tutorías', 'Reuniones', 'Exámenes', 'Evaluaciones', 'Expulsiones') NOT NULL, -- Nuevo
    id_emisor BIGINT NOT NULL,
    id_receptor BIGINT NOT NULL,
    id_tablon_profesor BIGINT NOT NULL,
    id_sancion BIGINT,                                    -- Conservado, opcional
    id_tutoria BIGINT,                                    -- Conservado, opcional
    FOREIGN KEY (id_emisor) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_receptor) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_tablon_profesor) REFERENCES tablon_profesor(id_tablon_profesor),
    FOREIGN KEY (id_sancion) REFERENCES sancion(id_sancion),
    FOREIGN KEY (id_tutoria) REFERENCES tutoria(id_tutoria)
);

CREATE TABLE IF NOT EXISTS Sancion (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    alumno_sancionado VARCHAR(9) NOT NULL,
    fecha DATE NOT NULL,
    tipo_sancion ENUM('CON_EXPULSION_DENTRO', 'CON_EXPULSION_FUERA', 'SIN_EXPULSION') NOT NULL,
    duracion VARCHAR(100),
    descripcion VARCHAR(200),
    id_parte BIGINT,
    FOREIGN KEY (alumno_sancionado) REFERENCES Alumno(dni)
);
CREATE TABLE IF NOT EXISTS Alumno (
    dni VARCHAR(9) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    nombre_tutor_legal VARCHAR(30),
    apellidos_tutor_legal VARCHAR(50),
    email_tutor_legal VARCHAR(50)
);


-- Tabla Tutoria
CREATE TABLE tutoria (
    id_tutoria BIGINT PRIMARY KEY AUTO_INCREMENT,
    motivo VARCHAR(100),
    urgencia VARCHAR(20),
    asignatura VARCHAR(50),
    fecha DATE,
    estado ENUM('PENDIENTE', 'REALIZADA'),
    observaciones VARCHAR(100),
    id_usuario BIGINT,
    dni_alumno VARCHAR(20),
    id_aula BIGINT,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (dni_alumno) REFERENCES alumno(dni),
    FOREIGN KEY (id_aula) REFERENCES aula(id_aula)
);
CREATE TABLE IF NOT EXISTS Sancion (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    alumno_sancionado VARCHAR(9) NOT NULL,
    fecha DATE NOT NULL,
    tipo_sancion ENUM('CON_EXPULSION_DENTRO', 'CON_EXPULSION_FUERA', 'SIN_EXPULSION') NOT NULL,
    duracion VARCHAR(100),
    descripcion VARCHAR(200),
    id_parte BIGINT,
    FOREIGN KEY (alumno_sancionado) REFERENCES Alumno(dni)
);


-- Tabla COMENTARIO_TAREA
CREATE TABLE comentario_tarea (
    id_comentario BIGINT PRIMARY KEY AUTO_INCREMENT,
    contenido VARCHAR(350) NOT NULL,
    fecha_comentario DATETIME NOT NULL,
    id_tarea BIGINT NOT NULL,
    id_usuario BIGINT NOT NULL,
    FOREIGN KEY (id_tarea) REFERENCES tarea(id_tarea),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- Tabla HISTORIAL_CORREO
CREATE TABLE historial_correo (
    id_historial BIGINT PRIMARY KEY AUTO_INCREMENT,
    fecha_envio DATETIME NOT NULL,
    destinatario_email VARCHAR(150) NOT NULL,
    tipo_notificacion ENUM('TAREA', 'MENSAJE') NOT NULL,
    id_tarea BIGINT,
    id_mensaje BIGINT,
    FOREIGN KEY (id_tarea) REFERENCES tarea(id_tarea),
    FOREIGN KEY (id_mensaje) REFERENCES mensaje(id_mensaje)
);
