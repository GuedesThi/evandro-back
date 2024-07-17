package br.com.decasa.api.configs.jwt;

import br.com.decasa.api.entities.UserEntity;
import br.com.decasa.api.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    final JwtService service;
    final UserRepository repository;

    public JwtAuthenticationFilter(JwtService service, UserRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = get_token(request);
        String email = service.validateToken(token);

        if (email != null) {
            UserEntity user = repository.findByEmail(email).orElse(null);

            if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User userDetails = new User(user.getUsername(), user.getPassword(), user.getAuthorities());

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);


    }

    private String get_token(HttpServletRequest request) {
        var token_header = request.getHeader("Authorization");

        if (token_header != null && token_header.startsWith("Bearer ")) {
            return token_header.replace("Bearer ", "");

        } else {
            return null;
        }
    }
}