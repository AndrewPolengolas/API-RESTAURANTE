package com.restaurante.service;

import com.restaurante.helper.DataHelper;
import com.restaurante.helper.FormatadorHelper;
import com.restaurante.model.Cliente;
import com.restaurante.model.Role;
import com.restaurante.model.Usuario;
import com.restaurante.model.dto.ClienteDto;
import com.restaurante.model.dto.UserDto;
import com.restaurante.model.type.Status;
import com.restaurante.repository.ClienteRepository;
import com.restaurante.repository.RoleRepository;
import com.restaurante.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private DataHelper dataHelper;

    public ResponseEntity<?> adicionar(UserDto userDto){

        Optional<Role> userRole = roleRepository.findByName("CLIENTE");
        List<Role> roles = new ArrayList<>();
        roles.add(userRole.get());

        Optional<Usuario> validaUsuario = usuarioRepository.findByLogin(userDto.getLogin());

        if (!validaUsuario.isPresent()){
            Usuario usuario = new Usuario();

            usuario.setRoles(roles);
            usuario.setLogin(userDto.getLogin());
            usuario.setPassword(encoder.encode(userDto.getPassword()));
            usuario.setStatus(Status.ATIVO);
            usuario.setDataIni(dataHelper.getDataHora());

            usuarioRepository.save(usuario);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Login já cadastrado", HttpStatus.UNAUTHORIZED);

    }
}
