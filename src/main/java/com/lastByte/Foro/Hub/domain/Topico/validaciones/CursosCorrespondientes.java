package com.lastByte.Foro.Hub.domain.Topico.validaciones;


import com.lastByte.Foro.Hub.domain.Curso.Curso;
import com.lastByte.Foro.Hub.domain.Curso.CursoRepository;
import com.lastByte.Foro.Hub.infra.exceptions.ValidacionDeIntegridad;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CursosCorrespondientes {

    @Autowired
    private CursoRepository cursoRepository;


    public List<Curso> validar(List<String> cursos) {

        if (cursos.isEmpty()) {
            throw new ValidationException("Debe ingresar los cursos del topico");
        }


        List<Curso> cursosResponse = new ArrayList<>();

        for (String nombre : cursos) {
            var cursoBuscado = cursoRepository.findByNombreIgnoreCase(nombre);

            if (cursoBuscado.isPresent()) {
                cursosResponse.add(cursoBuscado.get());
            }
        }

        if (cursosResponse.isEmpty()) {
            throw new ValidacionDeIntegridad("Los cursos ingresados no existen!");
        }


        return cursosResponse;
    }
}
