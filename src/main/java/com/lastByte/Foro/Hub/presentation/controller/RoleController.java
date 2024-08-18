package com.lastByte.Foro.Hub.presentation.controller;


import com.lastByte.Foro.Hub.domain.Role.RoleService;
import com.lastByte.Foro.Hub.presentation.dto.rol.RequestCreateRolDTO;
import com.lastByte.Foro.Hub.presentation.dto.rol.ResponseFindAllRolesDTO;
import com.lastByte.Foro.Hub.presentation.dto.rol.ResponseRolDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
@Tag(name = "Role", description = "Endpoints para la gestión los roles")
public class RoleController {



    @Autowired
    private RoleService roleService;



    //REGISTRAR CURSO
    @PostMapping
    @Transactional
    @Operation(
            summary = "Registrar un nuevo rol",
            description = "Este endpoint permite registrar un nuevo rol en el sistema.",
            tags = {"Rol"}
    )
    public ResponseEntity<ResponseRolDTO> createRol(@RequestBody RequestCreateRolDTO createRolDTO ){
        var response = roleService.createRol(createRolDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response) ;
    }



    //BUSCAR ROL
    @GetMapping
    @Operation(
            summary = "Buscar todos los roles",
            description = "Este endpoint permite buscar todos los roles del sistema",
            tags = {"Rol"}
    )
    public ResponseEntity<ResponseFindAllRolesDTO> findAllRoles(){
        var response = roleService.findAllRoles();
        return ResponseEntity.status(HttpStatus.OK).body(response) ;
    }



    //ELIMINAR ROL
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar rol por id",
            description = "Este endpoint permite eliminar un rol del sistema",
            tags = {"Rol"}
    )
    public ResponseEntity<String> deleteRolById(@PathVariable Long id){
        var response = roleService.deleteRolById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response) ;
    }






}
