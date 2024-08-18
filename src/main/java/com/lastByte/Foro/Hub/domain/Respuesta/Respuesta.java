package com.lastByte.Foro.Hub.domain.Respuesta;


import com.lastByte.Foro.Hub.domain.Topico.Topico;
import com.lastByte.Foro.Hub.domain.Usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "respuestas")
@Entity(name = "respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id") //crea automaticamente los metodos equals y hashcode con el id
public class Respuesta {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String mensaje;

    @Column(name = "fecha_de_creacion")
    private LocalDateTime fechaDeCreacion;


    @Setter
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    private Topico topico;

    @Setter
    @ManyToOne
    @JoinColumn(name = "respuesta_padre_id")
    private Respuesta respuesta;  // Relación a la respuesta "padre"

    @OneToMany(mappedBy = "respuesta", cascade = CascadeType.ALL)
    private List<Respuesta> respuestas;  // Respuestas hijas


    public Respuesta(String mensaje, Usuario usuario, Topico topico) {
    this.mensaje= mensaje;
    this.fechaDeCreacion=LocalDateTime.now();
    this.autor=usuario;
    this.topico=topico;
    }
}
