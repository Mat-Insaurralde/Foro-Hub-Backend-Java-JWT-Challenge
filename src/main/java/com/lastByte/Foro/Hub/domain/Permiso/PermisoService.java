package com.lastByte.Foro.Hub.domain.Permiso;



import com.lastByte.Foro.Hub.infra.exceptions.CollectionEmptyException;
import com.lastByte.Foro.Hub.infra.exceptions.ValidacionDeIntegridad;
import com.lastByte.Foro.Hub.presentation.dto.permiso.PermisoDTO;
import com.lastByte.Foro.Hub.presentation.dto.permiso.RequestCreatePermisoDTO;
import com.lastByte.Foro.Hub.presentation.dto.permiso.ResponseFindAllPermisosDTO;
import com.lastByte.Foro.Hub.presentation.dto.permiso.ResponsePermisoDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;



@Service
public class PermisoService {


    private final PermisoRepository permisoRepository;


    @Autowired
    public PermisoService(PermisoRepository permisoRepository) {
        this.permisoRepository = permisoRepository;
    }


    public ResponsePermisoDTO createPermiso(RequestCreatePermisoDTO createPermisoDTO) {

        var permisoEnum = PermisoEnum.fromStringToEnumPermiso(createPermisoDTO.permiso());

        if (permisoRepository.findByPermisoEnum(permisoEnum).isPresent()){
            throw new ValidacionDeIntegridad("Ya existe el permiso ingresado!");
        }

        var permiso = new Permiso(permisoEnum);

        permisoRepository.save(permiso);

        return new ResponsePermisoDTO("Permiso creado con exito!",new PermisoDTO(permiso));
    }


    public ResponseFindAllPermisosDTO findAllPermisos() {

        var listPermisos = permisoRepository.findAll().stream().map(PermisoDTO::new).toList();

        if (listPermisos.isEmpty()){
            throw new CollectionEmptyException("No hay permisos registrados!");
        }

    return new ResponseFindAllPermisosDTO("Lista de permisos",listPermisos);
    }






    public String deletePermisoById(Long id) {

        var permiso = permisoRepository.findById(id);

        if (permiso.isPresent()){
            permisoRepository.deleteById(id);
            return "Permiso eliminado con exito!";
        }

        return "Permiso no encontrado!";
    }
}
