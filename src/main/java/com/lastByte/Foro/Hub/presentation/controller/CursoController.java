package com.lastByte.Foro.Hub.presentation.controller;


import com.lastByte.Foro.Hub.domain.Curso.CursoService;
import com.lastByte.Foro.Hub.presentation.dto.curso.ResponseDetalleCursoDTO;
import com.lastByte.Foro.Hub.presentation.dto.curso.RequestRegistroCursoDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cursos")
@Tag(name = "Curso", description = "Endpoints para la gestión de cursos")
public class CursoController {


    @Autowired
    private CursoService cursoService;



   //REGISTRAR CURSO
    @PostMapping
    @Transactional
    @Operation(
            summary = "Registrar un nuevo curso",
            description = "Este endpoint permite registrar un nuevo curso en el sistema.",
            tags = {"Curso"}
    )
    public ResponseEntity<ResponseDetalleCursoDTO> registrarCurso(@RequestBody RequestRegistroCursoDTO registroCursoDTO){
        var response = cursoService.registrarCurso(registroCursoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response) ;
    }




   //DELETE REAL
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(
            summary = "Eliminar un curso",
            description = "Este endpoint permite eliminar un curso del sistema mediante su ID.",
            tags = {"Curso"}
    )
    public ResponseEntity<String> eliminarCurso(@PathVariable Long id){
        var response = cursoService.eliminarCurso(id);
        return ResponseEntity.status(HttpStatus.OK).body(response) ;
    }


    ///LISTA DETALLE DEL CURSO POR ID
   @GetMapping("/{id}")
   @Operation(
           summary = "Buscar un curso por ID",
           description = "Este endpoint permite obtener los detalles de un curso específico mediante su ID.",
           tags = {"Curso"}
   )
    public  ResponseEntity<ResponseDetalleCursoDTO> buscarCursoPorId(@PathVariable Long id){
        var response = cursoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
   }


   //LISTA DETALLE DE TODOS LOS CURSOS
    @GetMapping()
    @Operation(
            summary = "Listar todos los cursos",
            description = "Este endpoint permite listar todos los cursos registrados en el sistema.",
            tags = {"Curso"}
    )
    public  ResponseEntity listadoCursos(){
        var response = cursoService.listadoCursos();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



}
