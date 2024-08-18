package com.lastByte.Foro.Hub.domain.Permiso;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso,Long> {


    List<Permiso> findPermisoEntitiesByPermisoEnumIn(List<String> permisosName);


    Optional<Permiso> findByPermisoEnum(PermisoEnum name);



}
