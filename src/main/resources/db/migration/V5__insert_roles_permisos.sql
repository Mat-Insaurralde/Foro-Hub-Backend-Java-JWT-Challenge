-- Insertar roles
INSERT INTO roles (role_name) VALUES ('ADMIN');
INSERT INTO roles (role_name) VALUES ('DEVELOPER');
INSERT INTO roles (role_name) VALUES ('INVITED');
INSERT INTO roles (role_name) VALUES ('USER');






-- Inserción de permisos iniciales
INSERT INTO permisos (permiso_name) VALUES ('READ');
INSERT INTO permisos (permiso_name) VALUES ('WRITE');
INSERT INTO permisos (permiso_name) VALUES ('DELETE');
INSERT INTO permisos (permiso_name) VALUES ('UPDATE');
INSERT INTO permisos (permiso_name) VALUES ('CREATE');


-- Asignar permisos a roles (ejemplo)
INSERT INTO role_permisos (role_id, permiso_id) VALUES (1, 1); -- ADMIN tiene READ
INSERT INTO role_permisos (role_id, permiso_id) VALUES (1, 2); -- ADMIN tiene WRITE
