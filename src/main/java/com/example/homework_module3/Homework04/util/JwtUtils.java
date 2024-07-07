package com.example.homework_module3.Homework04.util;

import com.example.homework_module3.Homework04.domain.JwtAuthentication;
import com.example.homework_module3.Homework04.domain.Roles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {
    private static String secret = "LejjnLZua6SlR7eZXByD2+9M5P+dYxK3IlfA6XgPksuXijiXMAcpulI03o2Vq+PjYENhgTJGXLNm7YS4f1+IMw==";

    public static String generate(String userName) {
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());

        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(userName)
                .setExpiration(accessExpiration)
                .signWith(hmacKey)
                .claim("roles", "USER")
                .claim("firstName", userName)
                .compact();
    }

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoles(getRoles(claims));
        jwtInfoToken.setFirstName(claims.get("firstName", String.class));
        jwtInfoToken.setUsername(claims.getSubject());
        return jwtInfoToken;
    }

    private static Set<Roles> getRoles(Claims claims) {
        final List<Map<String, String>> roles = claims.get("roles", List.class);
        return roles.stream()
                .map(r -> Roles.valueOf(r.get("roleName")))
                .collect(Collectors.toSet());
    }

}