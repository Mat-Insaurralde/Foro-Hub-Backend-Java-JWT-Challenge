package com.lastByte.Foro.Hub.domain.Topico;


//import com.lastByte.Foro.Hub.domain.curso.Curso;

import com.lastByte.Foro.Hub.domain.Respuesta.Respuesta;
import com.lastByte.Foro.Hub.domain.Usuario.Usuario;
import com.lastByte.Foro.Hub.presentation.dto.topico.RequestActualizarTopicoDTO;
import com.lastByte.Foro.Hub.presentation.dto.topico.RequestRegistroTopicoDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Table(name = "topicos")
@Entity(name = "topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id") //crea automaticamente los metodos equals y hashcode con el id
public class Topico {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensaje;

    @Column(name = "fecha_de_creacion")
    private LocalDateTime fechaDeCreacion;


    private boolean status;

    @Setter
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;


    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL)
    private List<Respuesta> respuestas= new ArrayList<>();




    //No mapeo los cursos porque usare una tabla intermedia


    public Topico(RequestRegistroTopicoDTO registroTopicoDTO) {
        this.titulo = registroTopicoDTO.titulo();
        this.mensaje = registroTopicoDTO.mensaje();
        this.fechaDeCreacion = LocalDateTime.now();
        this.status = true;

    }

    public void actualizarDatos(RequestActualizarTopicoDTO datos) {
        if (datos.titulo() != null) {
            this.titulo = datos.titulo();
        }

        if (datos.mensaje() != null) {
            this.mensaje = datos.mensaje();
        }
    }

    public void desactivarTopico() {
        this.status=false;
    }


    @Override
    public String toString() {
        return "Topico: " +
               "Titulo: " + titulo + '\'' +
               "Mensaje: " + mensaje + '\'' +
               "Fecha De Creacion: " + fechaDeCreacion +
               "Autor: " + autor.getEmail();
    }


}
