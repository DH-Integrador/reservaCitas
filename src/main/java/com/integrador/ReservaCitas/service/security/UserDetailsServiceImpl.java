package com.integrador.ReservaCitas.service.security;

import com.integrador.ReservaCitas.entity.Security.Usuario;
import com.integrador.ReservaCitas.repository.Security.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Buscar usuario de DB

        Usuario usuario = usuarioRepository.findUsuarioByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario  no encontrado"));

        //Lista de Permisos/Roles. En NUESTRO caso solo tienen un ROL.
        Set<GrantedAuthority> grantList = new HashSet<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(usuario.getRol().toString());
        grantList.add(grantedAuthority);


        //Devuelvo un usuario de Spring
        UserDetails user = new User(usuario.getUsername(), usuario.getPassword(), grantList);

        return user;
    }



}
