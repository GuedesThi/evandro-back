package br.com.decasa.api.configs.jwt;

import br.com.decasa.api.entities.UserEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {

    @Value("${token.secret.value}")
    private String secret;

    private Instant data_de_criacao() {
        return LocalDateTime.now().toInstant(ZoneOffset.ofHours(-3));
    }

    private Instant data_de_expiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.ofHours(-3));
    }

    public String generateToken(UserEntity user) {

        try {
            return JWT.create()
                    .withIssuer("decasa")
                    .withSubject(user.getEmail())
                    .withIssuedAt(data_de_criacao())
                    .withExpiresAt(data_de_expiracao())
                    .sign(Algorithm.HMAC256(secret));

        } catch (JWTCreationException e) {
            throw new RuntimeException("Houve um erro na geração do Token: " + e.getMessage());
        }
    }

    public String validateToken(String token) {

        try {
            return JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("decasa")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException e) {
            return null;
        }
    }
}