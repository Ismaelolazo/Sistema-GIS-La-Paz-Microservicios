-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2025-02-22 00:44:59.399

-- tables
-- Table: Historial_Cambios
CREATE TABLE Historial_Cambios (
    ID_cambio int  NOT NULL,
    ID_administrador int  NULL,
    CONSTRAINT Historial_Cambios_pk PRIMARY KEY (ID_cambio)
);

-- Table: Paradas
CREATE TABLE Paradas (
    ID_parada int  NOT NULL,
    ID_ruta int  NULL,
    Nombre varchar(255)  NULL,
    Latitud decimal(10,6)  NULL,
    Longitud decimal(10,6)  NULL,
    CONSTRAINT Paradas_pk PRIMARY KEY (ID_parada)
);

CREATE TABLE Tarifas_Transporte (
    ID_tarifa INT NOT NULL,
    ID_ruta INT NOT NULL,
    Tipo_vehiculo ENUM('Minibús', 'Bus', 'Teleférico') NOT NULL,
    Horario ENUM('Diurno', 'Nocturno') NOT NULL,
    Tramo ENUM('Corto', 'Largo') NOT NULL,
    Condicion_usuario ENUM('General', 'Tercera edad', 'Discapacidad') NOT NULL,
    Precio DECIMAL(5,2) NOT NULL,
    CONSTRAINT Tarifas_Transporte_pk PRIMARY KEY (ID_tarifa),
    CONSTRAINT FK_tarifa_ruta FOREIGN KEY (ID_ruta)
        REFERENCES Rutas_Transporte (ID_ruta)
        ON DELETE CASCADE
);


-- Table: Puntos_Interes
CREATE TABLE Puntos_Interes (
    ID_punto_interes int  NOT NULL,
    Nombre varchar(255)  NULL,
    Descripcion text  NULL,
    CONSTRAINT Puntos_Interes_pk PRIMARY KEY (ID_punto_interes)
);

-- Table: Puntos_Rutas_Turisticas
CREATE TABLE Puntos_Rutas_Turisticas (
    ID_punto_ruta int  NOT NULL,
    ID_ruta_turistica int  NULL,
    ID_punto_interes int  NULL,
    Orden int  NULL,
    CONSTRAINT Puntos_Rutas_Turisticas_pk PRIMARY KEY (ID_punto_ruta)
);

-- Table: Puntos_Rutas_Usuarios
CREATE TABLE Puntos_Rutas_Usuarios (
    ID_punto_ruta_usuario int  NOT NULL,
    ID_ruta_usuario int  NULL,
    ID_punto_interes int  NULL,
    Orden int  NULL,
    CONSTRAINT Puntos_Rutas_Usuarios_pk PRIMARY KEY (ID_punto_ruta_usuario)
);

-- Table: Rutas_Transporte
CREATE TABLE Rutas_Transporte (
    ID_ruta int  NOT NULL,
    Nombre varchar(255)  NULL,
    CONSTRAINT Rutas_Transporte_pk PRIMARY KEY (ID_ruta)
);

-- Table: Rutas_Turisticas
CREATE TABLE Rutas_Turisticas (
    ID_ruta_turistica int  NOT NULL,
    Nombre varchar(255)  NULL,
    Descripcion text  NULL,
    Creado_por int  NULL,
    Privada boolean  NULL DEFAULT false,
    CONSTRAINT Rutas_Turisticas_pk PRIMARY KEY (ID_ruta_turistica)
);

-- Table: Rutas_Usuarios
CREATE TABLE Rutas_Usuarios (
    ID_ruta_usuario int  NOT NULL,
    Nombre varchar(255)  NULL,
    ID_usuario int  NULL,
    Descripcion text  NULL,
    Fecha_creacion timestamp  NULL DEFAULT current_timestamp,
    CONSTRAINT Rutas_Usuarios_pk PRIMARY KEY (ID_ruta_usuario)
);

-- Table: Usuarios
CREATE TABLE Usuarios (
    ID_usuario int  NOT NULL,
    Nombre varchar(255)  NULL,
    Correo varchar(255)  NULL,
    Contra varchar(255)  NULL,
    CONSTRAINT AK_0 UNIQUE (Correo) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT Usuarios_pk PRIMARY KEY (ID_usuario)
);

-- foreign keys
-- Reference: FK_0 (table: Paradas)
ALTER TABLE Paradas ADD CONSTRAINT FK_0
    FOREIGN KEY (ID_ruta)
    REFERENCES Rutas_Transporte (ID_ruta)
    ON DELETE  CASCADE  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: FK_1 (table: Rutas_Turisticas)
ALTER TABLE Rutas_Turisticas ADD CONSTRAINT FK_1
    FOREIGN KEY (Creado_por)
    REFERENCES Usuarios (ID_usuario)
    ON DELETE  SET NULL  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: FK_2 (table: Puntos_Rutas_Turisticas)
ALTER TABLE Puntos_Rutas_Turisticas ADD CONSTRAINT FK_2
    FOREIGN KEY (ID_ruta_turistica)
    REFERENCES Rutas_Turisticas (ID_ruta_turistica)
    ON DELETE  CASCADE  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: FK_3 (table: Puntos_Rutas_Turisticas)
ALTER TABLE Puntos_Rutas_Turisticas ADD CONSTRAINT FK_3
    FOREIGN KEY (ID_punto_interes)
    REFERENCES Puntos_Interes (ID_punto_interes)
    ON DELETE  CASCADE  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: FK_4 (table: Rutas_Usuarios)
ALTER TABLE Rutas_Usuarios ADD CONSTRAINT FK_4
    FOREIGN KEY (ID_usuario)
    REFERENCES Usuarios (ID_usuario)
    ON DELETE  CASCADE  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: FK_5 (table: Puntos_Rutas_Usuarios)
ALTER TABLE Puntos_Rutas_Usuarios ADD CONSTRAINT FK_5
    FOREIGN KEY (ID_ruta_usuario)
    REFERENCES Rutas_Usuarios (ID_ruta_usuario)
    ON DELETE  CASCADE  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: FK_6 (table: Puntos_Rutas_Usuarios)
ALTER TABLE Puntos_Rutas_Usuarios ADD CONSTRAINT FK_6
    FOREIGN KEY (ID_punto_interes)
    REFERENCES Puntos_Interes (ID_punto_interes)
    ON DELETE  CASCADE  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- End of file.

