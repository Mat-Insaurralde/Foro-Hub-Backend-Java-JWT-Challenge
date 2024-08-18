package com.lastByte.Foro.Hub.domain.Curso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CursoRepository  extends JpaRepository<Curso,Long> {

    boolean existsByNombreIgnoreCase(String nombre);

    Optional<Curso> findByNombreIgnoreCase(String nombre);

    @Query(value = "SELECT * FROM cursos WHERE cursos.id IN (SELECT tc.curso_id FROM topico_cursos tc WHERE tc.topico_id =:topicoId)" ,nativeQuery = true)
    List<Curso> findCursosByTopicoId(Long topicoId);


}

