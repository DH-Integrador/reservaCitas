package com.integrador.ReservaCitas.repository.Security;

import com.integrador.ReservaCitas.entity.Security.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findUsuarioByUsername(String username);
}
