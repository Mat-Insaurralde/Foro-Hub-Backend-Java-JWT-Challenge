-- Crear la tabla "respuesta"
CREATE TABLE respuesta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mensaje VARCHAR(255) NOT NULL,
    fecha_de_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    autor_id BIGINT,
    topico_id BIGINT,
    respuesta_padre_id BIGINT,
    CONSTRAINT fk_respuesta_autor FOREIGN KEY (autor_id) REFERENCES usuarios(id) ON DELETE SET NULL,
    CONSTRAINT fk_topico FOREIGN KEY (topico_id) REFERENCES topicos(id) ON DELETE CASCADE,
    CONSTRAINT fk_respuesta_padre FOREIGN KEY (respuesta_padre_id) REFERENCES respuesta(id) ON DELETE CASCADE
);

-- Crear la relación de uno a muchos (respuestas hijas)
-- (Esta relación ya está definida en la tabla misma, no requiere otro paso)
