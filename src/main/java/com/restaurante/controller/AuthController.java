package com.restaurante.controller;

import com.restaurante.helper.UserHelper;
import com.restaurante.model.Role;
import com.restaurante.model.Usuario;
import com.restaurante.model.dto.DadosAuthDto;
import com.restaurante.model.dto.UserDataDto;
import com.restaurante.repository.RoleRepository;
import com.restaurante.repository.UsuarioRepository;
import com.restaurante.security.UserPrincipal;
import com.restaurante.security.jwt.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UserHelper userHelper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody DadosAuthDto dados) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                dados.getLogin(),
                dados.getPassword()
        );

        Authentication authentication = manager.authenticate(authenticationToken);

        if (!userHelper.userAtivo(authentication)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (authentication.isAuthenticated()) {
            String tokenJWT = tokenService.gerarToken(authentication);

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            Optional<Usuario> usuario = usuarioRepository.findByLogin(userPrincipal.getUsername());

            UserDataDto user = new UserDataDto(
                    usuario.get().getId(),
                    tokenJWT,
                    usuario.get().getLogin()
            );

            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/cadastrar-role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> cadastrarRole(@RequestBody Role role) {

        Optional<Role> userRole = roleRepository.findByName(role.getName());

        if (userRole.isPresent()){
            throw new RuntimeException("Role já exixtente!");
        }

        Role retorno = roleRepository.save(role);

        return new ResponseEntity<>(retorno, HttpStatus.OK);
    }
}
