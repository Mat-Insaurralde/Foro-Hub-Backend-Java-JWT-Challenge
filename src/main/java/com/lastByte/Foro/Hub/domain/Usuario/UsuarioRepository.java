package com.lastByte.Foro.Hub.domain.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario,Long> {

   Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByEmailAndUsername(String email, String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);


    boolean existsByEmailAndUsername(String email, String username);

}
