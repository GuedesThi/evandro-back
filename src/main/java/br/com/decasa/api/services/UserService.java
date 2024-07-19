package br.com.decasa.api.services;

import br.com.decasa.api.DTO.UserDTO;
import br.com.decasa.api.DTO.UserLoginDTO;
import br.com.decasa.api.DTO.UserUpdateProfileDTO;
import br.com.decasa.api.configs.jwt.JwtService;
import br.com.decasa.api.entities.UserEntity;
import br.com.decasa.api.entities.UserRole;
import br.com.decasa.api.enums.RoleName;
import br.com.decasa.api.repositories.UserRepository;
import br.com.decasa.api.repositories.UserRoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    final UserRepository repository;
    final UserRoleRepository roleRepository;
    final PasswordEncoder passwordEncoder;
    final JwtService jwtService;

    public UserService(UserRepository repository, UserRoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserEntity criar_usuario(UserDTO dados_requisicao) throws Exception {

        try {
            UserEntity user = new UserEntity();
            BeanUtils.copyProperties(dados_requisicao, user);

            user.setPassword(passwordEncoder.encode(dados_requisicao.password()));
            user.setCreatedAt(LocalDateTime.now(ZoneOffset.ofHours(-3)));

            UserRole userRole = roleRepository.findByRole(RoleName.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Role not found."));
            user.setRoles(Collections.singletonList(userRole));

            return repository.save(user);
        } catch (Exception e) {
            throw new Exception("Erro ao criar o usuário.");
        }
    }

    public String login_user(UserLoginDTO dados_requisicao) {
        Optional<UserEntity> user = repository.findByEmail(dados_requisicao.email());

        if (user.isPresent() && passwordEncoder.matches(dados_requisicao.password(), user.get().getPassword())) {

            UserEntity usuario = user.get();

            System.out.println("-------------------------------------------------------------");
            System.out.println("Usuário encontrado: " + dados_requisicao.email() + " | " + dados_requisicao.password());
            System.out.println("-------------------------------------------------------------");

            return jwtService.generateToken(usuario);

        } else {
            System.out.println("-------------------------------------------------------------");
            System.out.println("Usuário não foi encontrado pelos dados: " + dados_requisicao.email() + " | " + dados_requisicao.password());
            System.out.println("-------------------------------------------------------------");

            return null;
        }
    }

    public Map<String, String> atualizar_user(UserUpdateProfileDTO dados_requisicao) {
        Optional<UserEntity> user = repository.findByEmail(dados_requisicao.email());

        if (!user.isPresent()) {
            return null;

        } else {
            UserEntity newUser = user.get();
            BeanUtils.copyProperties(dados_requisicao, newUser);
            newUser.setUpdatedAt(LocalDateTime.now(ZoneOffset.ofHours(-3)));
            repository.save(newUser);

            Map<String, String> responseNewUserData = new HashMap<>();

            responseNewUserData.put("name", newUser.getRealUsername());
            responseNewUserData.put("email", newUser.getEmail());
            responseNewUserData.put("address", newUser.getAddress());
            responseNewUserData.put("cep", newUser.getCep());
            responseNewUserData.put("urlImage", newUser.getUrlImage());

            return responseNewUserData;
        }
    }

    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }
}