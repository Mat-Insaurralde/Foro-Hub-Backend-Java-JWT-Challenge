package com.lastByte.Foro.Hub.presentation.dto.topico;

import java.util.List;

public record RequestActualizarTopicoDTO(

        String titulo,

        String mensaje,

        List<String> cursos
) {
}
