package com.lastByte.Foro.Hub.presentation.controller;


import com.lastByte.Foro.Hub.domain.Permiso.PermisoService;
import com.lastByte.Foro.Hub.presentation.dto.permiso.RequestCreatePermisoDTO;
import com.lastByte.Foro.Hub.presentation.dto.permiso.ResponseFindAllPermisosDTO;
import com.lastByte.Foro.Hub.presentation.dto.permiso.ResponsePermisoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/permiso")
@Tag(name = "Permiso", description = "Endpoints para la gestión de los permisos de los roles")
public class PermisoController {


   private final PermisoService permisoService;


    @Autowired
    public PermisoController(PermisoService permisoService) {
        this.permisoService = permisoService;
    }


    //REGISTRAR PERMISO
    @PostMapping
    @Transactional
    @Operation(
            summary = "Registrar un nuevo permiso",
            description = "Este endpoint permite registrar un nuevo permiso para los roles en el sistema.",
            tags = {"Permiso"}
    )
    public ResponseEntity<ResponsePermisoDTO> createPermiso(@RequestBody RequestCreatePermisoDTO createPermisoDTO ){
        var response = permisoService.createPermiso(createPermisoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response) ;
    }



    //BUSCAR PERMISOS
    @GetMapping
    @Operation(
            summary = "Buscar todos los permisos para los roles",
            description = "Este endpoint permite buscar todos los permisos del sistema",
            tags = {"Permiso"}
    )
    public ResponseEntity<ResponseFindAllPermisosDTO> findAllPermisos(){
        var response = permisoService.findAllPermisos();
        return ResponseEntity.status(HttpStatus.OK).body(response) ;
    }



    //ELIMINAR PERMISOS
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar permiso por id",
            description = "Este endpoint permite eliminar un permiso del sistema",
            tags = {"Permiso"}
    )
    public ResponseEntity<String> deletePermisoById(@PathVariable Long id){
        var response = permisoService.deletePermisoById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response) ;
    }









}
