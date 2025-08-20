package com.KimJesus.forohub.config;

import com.KimJesus.forohub.security.JwtAuthenticationFilter;
import com.KimJesus.forohub.service.TokenService;
import com.KimJesus.forohub.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfigurations {

    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    public SecurityConfigurations(UsuarioService usuarioService, TokenService tokenService) {
        this.usuarioService = usuarioService;
        this.tokenService = tokenService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(tokenService, usuarioService);

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Permitir acceso a recursos estáticos, Swagger y rutas públicas
                        .requestMatchers(
                                "/", "/index.html", "/topicos.html", "/favicon.ico",
                                "/css/**", "/js/**", "/images/**",
                                "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers("/auth/**").permitAll() // Login/registro es público

                        // Reglas para Tópicos basadas en roles
                        .requestMatchers(HttpMethod.GET, "/topicos/**").permitAll() // Cualquiera puede leer tópicos
                        .requestMatchers(HttpMethod.POST, "/topicos").hasRole("ADMIN")      // Solo ADMIN puede crear
                        .requestMatchers(HttpMethod.PUT, "/topicos/**").hasRole("ADMIN")   // Solo ADMIN puede actualizar
                        .requestMatchers(HttpMethod.DELETE, "/topicos/**").hasRole("ADMIN") // Solo ADMIN puede eliminar

                        // Todas las demás peticiones requieren autenticación
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(usuarioService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
