package com.KimJesus.forohub.config;

import com.KimJesus.forohub.model.Role;
import com.KimJesus.forohub.model.Usuario;
import com.KimJesus.forohub.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("!production") // Este componente solo se activará si el perfil NO es 'production'
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Override
    public void run(String... args) {
        String login = "miusuario";     // usuario para login
        String rawPassword = "123456";  // contraseña que usarás en Postman

        if (usuarioRepository.findByLogin(login).isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setLogin(login);
            usuario.setPassword(passwordEncoder.encode(rawPassword)); // encripta con BCrypt
            usuario.setRole(Role.USER);

            usuarioRepository.save(usuario);
            log.info("✅ Usuario de prueba creado: {} / contraseña: {}", login, rawPassword);
        } else {
            log.info("ℹ Usuario de prueba ya existe: {}", login);
        }
    }
}
