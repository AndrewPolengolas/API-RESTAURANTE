package com.restaurante.controller;

import com.restaurante.model.dto.UserDto;
import com.restaurante.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cadastro")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> adicionar(@RequestBody UserDto userDto){
        return userService.adicionar(userDto);
    }
}
