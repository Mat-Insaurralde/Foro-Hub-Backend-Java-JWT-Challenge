
package com.lastByte.Foro.Hub.domain.Curso;


import com.lastByte.Foro.Hub.presentation.dto.curso.RequestRegistroCursoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "cursos")
@Entity(name = "curso")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id") //crea automaticamente los metodos equals y hashcode con el id
public class Curso {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private LocalDateTime fechaDeCreacion;
    private boolean status;



    public Curso(RequestRegistroCursoDTO registroCursoDTO) {
        this.nombre= registroCursoDTO.nombre();
        this.descripcion=registroCursoDTO.descripcion();
        this.fechaDeCreacion= LocalDateTime.now();
        this.status=true;
    }


    public void desactivarCurso() {
        this.status=false;
    }





}

