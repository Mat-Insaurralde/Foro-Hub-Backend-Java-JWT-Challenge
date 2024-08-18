-- Crear tabla intermedia permisos de roles
CREATE TABLE IF NOT EXISTS role_permisos (
    role_id BIGINT NOT NULL,
    permiso_id BIGINT NOT NULL,

    PRIMARY KEY (role_id,permiso_id),

    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (permiso_id) REFERENCES permisos(id)
);