package com.KimJesus.forohub.controller;

import com.KimJesus.forohub.controller.dto.LoginDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.KimJesus.forohub.service.TokenService;

import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AutenticacionController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AutenticacionController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.password())
        );

        // El principal es el usuario autenticado
        var usuarioAutenticado = (UserDetails) authentication.getPrincipal();
        String token = tokenService.generarToken(usuarioAutenticado);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
