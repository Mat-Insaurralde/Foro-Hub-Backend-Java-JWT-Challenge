package com.lastByte.Foro.Hub.domain.Topico.validaciones;
import com.lastByte.Foro.Hub.domain.Curso.Curso;
import com.lastByte.Foro.Hub.domain.Curso.CursoRepository;
import com.lastByte.Foro.Hub.infra.exceptions.ValidacionDeIntegridad;
import com.lastByte.Foro.Hub.presentation.dto.curso.RequestRegistroCursoDTO;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CursosCorrespondientesTest {


        @Mock
        private CursoRepository cursoRepository;

        @InjectMocks
        private CursosCorrespondientes cursosCorrespondientes;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testValidarConCursosExistentes() {
            // Arrange
            List<String> nombresCursos = List.of("Curso1", "Curso2");
            Curso curso1 = new Curso(new RequestRegistroCursoDTO("Curso1","curso1"));
            Curso curso2 = new Curso(new RequestRegistroCursoDTO("Curso2","curso2"));

            when(cursoRepository.findByNombreIgnoreCase("Curso1")).thenReturn(Optional.of(curso1));
            when(cursoRepository.findByNombreIgnoreCase("Curso2")).thenReturn(Optional.of(curso2));

            // Act
            List<Curso> resultados = cursosCorrespondientes.validar(nombresCursos);

            // Assert
            assertNotNull(resultados);
            assertEquals(2, resultados.size());
            assertTrue(resultados.contains(curso1));
            assertTrue(resultados.contains(curso2));
        }

        @Test
        public void testValidarConCursosVacios() {
            // Arrange
            List<String> nombresCursos = new ArrayList<>();

            // Act & Assert
            ValidationException thrown = assertThrows(ValidationException.class, () -> {
                cursosCorrespondientes.validar(nombresCursos);
            });
            assertEquals("Debe ingresar los cursos del topico", thrown.getMessage());
        }

        @Test
        public void testValidarConCursosNoExistentes() {
            // Arrange
            List<String> nombresCursos = List.of("CursoInexistente");
            when(cursoRepository.findByNombreIgnoreCase("CursoInexistente")).thenReturn(Optional.empty());

            // Act & Assert
            ValidacionDeIntegridad thrown = assertThrows(ValidacionDeIntegridad.class, () -> {
                cursosCorrespondientes.validar(nombresCursos);
            });
            assertEquals("Los cursos ingresados no existen!", thrown.getMessage());
        }
}