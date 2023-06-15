package com.restaurante.helper;

import com.restaurante.model.Usuario;
import com.restaurante.model.type.Status;
import com.restaurante.repository.UsuarioRepository;
import com.restaurante.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserHelper {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuario> getUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return usuarioRepository.findByLogin(userDetails.getUsername());
    }

    public Boolean userAtivo(Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Optional<Usuario> usuario = usuarioRepository.findByLogin(userPrincipal.getUsername());

        if (usuario.isPresent()){

            if (usuario.get() != null){
                if (usuario.get().getStatus().equals(Status.ATIVO)){
                    return true;
                }
            }
        }

        return false;
    }
}
