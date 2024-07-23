package br.com.decasa.api.controllers;

import br.com.decasa.api.DTO.UserDTO;
import br.com.decasa.api.DTO.UserLoginDTO;
import br.com.decasa.api.DTO.UserUpdateProfileDTO;
import br.com.decasa.api.entities.UserEntity;
import br.com.decasa.api.repositories.UserRepository;
import br.com.decasa.api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserController {

    final UserService service;
    final UserRepository repository;
    public UserController(UserService service, UserRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping("/create")
    public ResponseEntity create_user(@RequestBody @Valid UserDTO data) {

        if (service.existsByEmail(data.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nome de usuário, ou, Email já cadastrado.");

        } else if (service.existsByUsername(data.username())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nome de usuário, ou, Email já cadastrado.");

        } else {

            try {
                service.criar_usuario(data);
                return ResponseEntity.status(HttpStatus.CREATED).build();

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
    }

    @PostMapping()
    public ResponseEntity<Map<String, String>> login_user(@RequestBody @Valid UserLoginDTO data) {

        String token = service.login_user(data);
        Map<String, String> response_backend = new HashMap<>();

        if (token == null) {
            response_backend.put("situation", "no");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response_backend);

        } else {
            response_backend.put("situation", "yes");
            response_backend.put("token", token);
            return ResponseEntity.status(HttpStatus.OK).body(response_backend);
        }
    }

    @GetMapping("/account")
    public ResponseEntity<Map<String, String>> userSendToken(Authentication authentication) {

        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = authentication.getName();
        UserEntity user = repository.findByEmail(email).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Map<String, String> userInfo = new HashMap<>();

        userInfo.put("name", user.getRealUsername());
        userInfo.put("email", user.getEmail());
        userInfo.put("urlImage", user.getUrlImage());
        userInfo.put("address", user.getAddress());
        userInfo.put("cep", user.getCep());

        return ResponseEntity.ok(userInfo);

    }

    @PutMapping("/profile")
    public ResponseEntity<Map<String, String>> updateProfile(Authentication authentication, @RequestBody UserUpdateProfileDTO data) {

        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = authentication.getName();
        UserEntity user = repository.findByEmail(email).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        System.out.println("--------------------------------\nDados recebidos do Front: " + data + "\n--------------------------------");

        Map<String, String> response = service.atualizar_user(data);

        System.out.println("--------------------------------\nDados enviados para o front: " + response + "\n--------------------------------");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}