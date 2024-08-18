package com.lastByte.Foro.Hub.domain.Role;


import com.lastByte.Foro.Hub.domain.Permiso.PermisoRepository;
import com.lastByte.Foro.Hub.infra.exceptions.CollectionEmptyException;
import com.lastByte.Foro.Hub.infra.exceptions.ValidacionDeIntegridad;
import com.lastByte.Foro.Hub.presentation.dto.rol.RequestCreateRolDTO;
import com.lastByte.Foro.Hub.presentation.dto.rol.ResponseFindAllRolesDTO;
import com.lastByte.Foro.Hub.presentation.dto.rol.ResponseRolDTO;
import com.lastByte.Foro.Hub.presentation.dto.rol.RolDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class RoleService {


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermisoRepository permisoRepository;


    public ResponseRolDTO createRol(RequestCreateRolDTO createRolDTO) {

        var rolEnum = RoleEnum.fromStringToEnumRole(createRolDTO.nombre());

        if (roleRepository.findByRoleEnum(rolEnum).isPresent()) {
            throw new ValidacionDeIntegridad("Ya existe un rol con ese nombre!");
        }

        var listPermisos = permisoRepository.findPermisoEntitiesByPermisoEnumIn(createRolDTO.listPermisos()).stream().collect(Collectors.toSet());

        if (listPermisos.isEmpty()) {
            throw new IllegalArgumentException("Los permisos ingresados no existen");
        }

        var rol = new Role(rolEnum, listPermisos);

        roleRepository.save(rol);

        return new ResponseRolDTO("Rol creado con exito!", new RolDTO(rol));
    }


    public ResponseFindAllRolesDTO findAllRoles() {

        var listRoles = roleRepository.findAll().stream().map(RolDTO::new).toList();


        if (listRoles.isEmpty()) {
            throw new CollectionEmptyException("No hay roles registrados!");
        }

        return new ResponseFindAllRolesDTO("Lista de roles", listRoles);
    }


    public String deleteRolById(Long id) {

        var rol = roleRepository.findById(id);

        if (rol.isPresent()){
         roleRepository.deleteById(id);
        return "Rol eliminado con exito";
        }

        return "Rol no encontrado!";
    }
}

