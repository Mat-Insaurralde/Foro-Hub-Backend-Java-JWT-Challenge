package com.lastByte.Foro.Hub.domain.Topico;


import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository  extends JpaRepository<Topico,Long> {


    @Modifying
    @Transactional
     @Query(value = "INSERT INTO topico_cursos (topico_id,curso_id) VALUES (:topico_id,:curso_id);" , nativeQuery = true)
    void registrarCursoDelTopico(Long topico_id,Long curso_id);


    @Modifying
    @Transactional
    @Query(value = "DELETE  FROM topico_cursos t WHERE t.topico_id =:topico_id;" ,nativeQuery = true)
    void eliminarCursosDelTopico(Long topico_id);



    boolean existsByTituloAndMensajeIgnoreCase(String titulo, String mensaje);


    Page<Topico> findByOrderByFechaDeCreacionAsc(Pageable paginacion);



    @Query( value = """  
                        SELECT t.id , t.titulo , t.mensaje, t.fecha_de_creacion , t.status , t.autor_id FROM topico_cursos tc
                        JOIN cursos c ON tc.curso_id = c.id
                        JOIN topicos t ON tc.topico_id = t.id
                        WHERE c.nombre = :nombre
                        AND YEAR(t.fecha_de_creacion) = :anio ;"""  ,  nativeQuery = true)
    Page<Topico> findByCursosPorNombreYanio( String nombre , Integer anio, Pageable paginacion );


}
