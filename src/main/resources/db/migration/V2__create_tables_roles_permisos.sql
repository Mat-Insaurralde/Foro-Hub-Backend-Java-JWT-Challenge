-- Crear tabla de roles
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT NOT NULL AUTO_INCREMENT,
    role_name ENUM('ADMIN', 'DEVELOPER', 'INVITED', 'USER') NULL DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX UK_role_name (role_name)
);



-- Crear tabla de permisos
CREATE TABLE IF NOT EXISTS permisos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    permiso_name ENUM('READ', 'UPDATE', 'DELETE','CREATE','WRITE' ) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX UK_permiso_name (permiso_name)
);