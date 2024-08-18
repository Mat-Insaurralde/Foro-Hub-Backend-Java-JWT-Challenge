package com.lastByte.Foro.Hub.presentation.controller;


import com.lastByte.Foro.Hub.domain.Respuesta.RespuestaService;
import com.lastByte.Foro.Hub.presentation.dto.respuesta.RequestCreateRespuestaDTO;
import com.lastByte.Foro.Hub.presentation.dto.respuesta.RequestUpdateRespuestaDTO;
import com.lastByte.Foro.Hub.presentation.dto.respuesta.ResponseCreateRespuestaDTO;
import com.lastByte.Foro.Hub.presentation.dto.respuesta.RespuestaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/respuesta")
@Tag(name = "Respuesta", description = "Endpoints de respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;


    //REGISTRAR RESPUESTA
    @PostMapping
    @Transactional
    @Operation(
            summary = "Registrar una nueva respuesta",
            description = "Este endpoint permite registrar una nueva respuesta a un topico.",
            tags = {"Respuesta"}
    )
    public ResponseEntity<ResponseCreateRespuestaDTO> createRespuesta(@RequestBody @Valid RequestCreateRespuestaDTO createRespuesta) {
        var response = respuestaService.createRespuesta(createRespuesta);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    ///BUSCAR RESPUESTA POR ID
    @GetMapping("{id}")
    @Operation(
            summary = "Buscar respuesta por ID",
            description = "Este endpoint permite buscar una respuesta.",
            tags = {"Respuesta"}
    )
    public ResponseEntity<RespuestaDTO> findRespuestaById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(respuestaService.findRespuestaById(id));
    }


    ///ELIMINAR RESPUESTA POR ID
    @DeleteMapping("{id}")
    @Operation(
            summary = "Eliminar respuesta por ID",
            description = "Este endpoint permite eliminar una respuesta.",
            tags = {"Respuesta"}
    )
    public ResponseEntity<String> deleteRespuestaById(@PathVariable Long id){
    return ResponseEntity.status(HttpStatus.OK).body(respuestaService.deleteRespuestaById(id));
    }





///ACTUALIZAR RESPUESTA POR ID
@PutMapping("{id}")
@Operation(
        summary = "Actualizar respuesta por ID",
        description = "Este endpoint permite actualizar una respuesta.",
        tags = {"Respuesta"}
)
public ResponseEntity<RespuestaDTO> actualizarRespuestaById(@PathVariable Long id, @Valid @RequestBody  RequestUpdateRespuestaDTO updateRespuestaDTO) {
    return ResponseEntity.status(HttpStatus.OK).body(respuestaService.actualizarRespuestaById(id , updateRespuestaDTO));
}



}
