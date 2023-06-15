package com.restaurante;

import com.restaurante.helper.DataHelper;
import com.restaurante.model.Role;
import com.restaurante.model.Usuario;
import com.restaurante.model.dto.UserDto;
import com.restaurante.model.type.Status;
import com.restaurante.repository.RoleRepository;
import com.restaurante.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class RestauranteApplication {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private DataHelper dataHelper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public static void main(String[] args) {
        SpringApplication.run(RestauranteApplication.class, args);
    }

    @PostConstruct
    private void criarUserADM(){


        Optional<Role> userRole = roleRepository.findByName("ADMIN");

        if (!userRole.isPresent()){
            userRole.get().setName("ADMIN");

            roleRepository.save(userRole.get());
        }

        List<Role> roles = new ArrayList<>();
        roles.add(userRole.get());

        Optional<Usuario> validaUsuario = usuarioRepository.findByLogin("admin");

        if (!validaUsuario.isPresent()){

            Usuario usuario = new Usuario();

            usuario.setRoles(roles);
            usuario.setLogin("admin");
            usuario.setPassword(encoder.encode("admin"));
            usuario.setStatus(Status.ATIVO);
            usuario.setDataIni(dataHelper.getDataHora());

            usuarioRepository.save(usuario);
        }
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
