package com.lastByte.Foro.Hub.domain.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    List<Role> findRoleEntitiesByRoleEnumIn(List<String> roleNames);

    Optional<Role> findByRoleEnum(RoleEnum name);

}
