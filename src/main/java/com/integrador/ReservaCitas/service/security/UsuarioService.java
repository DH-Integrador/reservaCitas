package com.integrador.ReservaCitas.service.security;

import com.integrador.ReservaCitas.entity.Security.Usuario;
import com.integrador.ReservaCitas.repository.Security.IUsuarioRepository;
import com.integrador.ReservaCitas.util.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UsuarioService {
    private final IUsuarioRepository usuarioRepository;


    public Usuario guardar(Usuario d) {
        return usuarioRepository.save(d);
    }

    public Usuario buscar(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public void eliminar(Integer id) {
        if (!usuarioRepository.existsById(id)) throw new ResourceNotFoundException(id.toString(), "Usuario Id");

        usuarioRepository.deleteById(id);
    }

    public Usuario actualizar(Usuario d) {
        return usuarioRepository.save(d);
    }

}
