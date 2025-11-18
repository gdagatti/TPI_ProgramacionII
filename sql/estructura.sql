DROP DATABASE IF EXISTS empresa;
CREATE DATABASE empresa; -- Creamos la base --
USE empresa; -- Trabajar sobre la base --

-- Configuracion-- 
SET default_storage_engine = INNODB;
SET NAMES 'utf8mb4';
SET CHARACTER SET utf8mb4;

-- Idempotencia --
DROP TABLE IF EXISTS empleado;
DROP TABLE IF EXISTS legajo;

-- Creacion tabla legajo --
CREATE TABLE legajo (
  id            BIGINT NOT NULL AUTO_INCREMENT,
  eliminado     TINYINT(1) NOT NULL DEFAULT 0,
  nro_legajo    VARCHAR(20) NOT NULL,
  categoria     VARCHAR(30),
  estado        ENUM('ACTIVO','INACTIVO') NOT NULL DEFAULT 'ACTIVO',
  fecha_alta    DATE,
  observaciones VARCHAR(255),
  CONSTRAINT pk_legajo PRIMARY KEY (id), -- Restriccion -- 
  CONSTRAINT uq_legajo_nro UNIQUE (nro_legajo),  -- Restriccion -- 
  CONSTRAINT chk_legajo_eliminado CHECK (eliminado IN (0,1))  -- Restriccion -- 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- Creacion de tabla empleado --
CREATE TABLE empleado (
  id             BIGINT NOT NULL AUTO_INCREMENT,
  eliminado      TINYINT(1) NOT NULL DEFAULT 0,
  nombre         VARCHAR(80)  NOT NULL,
  apellido       VARCHAR(80)  NOT NULL,
  dni            VARCHAR(15)  NOT NULL,
  email          VARCHAR(120),
  fecha_ingreso  DATE,
  area           VARCHAR(50),
  legajo_id      BIGINT,
  CONSTRAINT pk_empleado PRIMARY KEY (id), -- Restriccion --
  CONSTRAINT uq_empleado_dni UNIQUE (dni), -- Restriccion --
  CONSTRAINT uq_empleado_legajo UNIQUE (legajo_id), -- Restriccion --
  CONSTRAINT fk_empleado_legajo -- Restriccion --
    FOREIGN KEY (legajo_id) REFERENCES legajo(id)
    ON UPDATE RESTRICT
    ON DELETE SET NULL,
  CONSTRAINT chk_empleado_eliminado CHECK (eliminado IN (0,1)), -- Restriccion --
  CONSTRAINT chk_empleado_email_fmt -- Restriccion --
    CHECK (email IS NULL OR email REGEXP '^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
