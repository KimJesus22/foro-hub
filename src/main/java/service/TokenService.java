package com.KimJesus.forohub.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private Algorithm algorithm() {
        return Algorithm.HMAC256(secret);
    }

    /** Genera un JWT con roles/authorities dentro del claim "roles" */
    public String generarToken(UserDetails user) {
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + expiration);

        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority) // p.ej. ROLE_ADMIN, TOPICOS_WRITE
                .collect(Collectors.toList());

        return JWT.create()
                .withIssuer("foro-hub")
                .withSubject(user.getUsername())
                .withArrayClaim("roles", roles.toArray(new String[0])) // 👈 claim de roles
                .withIssuedAt(ahora)
                .withExpiresAt(expiracion)
                .sign(algorithm());
    }

    /** Devuelve el username (sub) si el token es válido; lanza excepción si no lo es */
    public String validarToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm())
                .withIssuer("foro-hub")
                .build();
        DecodedJWT decoded = verifier.verify(token);
        return decoded.getSubject();
    }

    /** Extrae los roles como lista de String (para el filtro) */
    public List<String> extraerRoles(String token) {
        JWTVerifier verifier = JWT.require(algorithm())
                .withIssuer("foro-hub")
                .build();
        DecodedJWT decoded = verifier.verify(token);
        List<String> roles = decoded.getClaim("roles").asList(String.class);
        return roles == null ? List.of() : roles;
    }
}
