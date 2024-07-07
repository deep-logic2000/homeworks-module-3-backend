package com.example.homework_module3.Homework04.Service;

import com.example.homework_module3.Homework04.domain.*;
import com.example.homework_module3.Homework04.domain.JwtResponse;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CustomerService customerService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final Map<String, List<String>> accessStorage = new HashMap<>();
    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    public List<String> getTokensByUser(String userName) {
        List<String> accessTokens = accessStorage.get(userName);
        return accessTokens;
    }

    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException {
        final Customer customer = customerService.getByLogin(authRequest.getLogin())
                .orElseThrow(() -> new AuthException("User not found"));

        if (passwordEncoder.matches(authRequest.getPassword(), customer.getEncryptedPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(customer);
            final String refreshToken = jwtProvider.generateRefreshToken(customer);
            refreshStorage.put(customer.getName(), refreshToken);

            List<String> accessTokens = accessStorage.computeIfAbsent(customer.getName(), k -> new ArrayList<>());
            accessTokens.add(accessToken);
            accessStorage.put(customer.getName(), accessTokens);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Password is incorrect");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final Customer customer = customerService.getByLogin(login)
                        .orElseThrow(() -> new AuthException("User not found"));
                final String accessToken = jwtProvider.generateAccessToken(customer);

                List<String> accessTokens = accessStorage.computeIfAbsent(login, k -> new ArrayList<>());
                accessTokens.add(accessToken);
                accessStorage.put(login, accessTokens);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final Customer customer = customerService.getByLogin(login)
                        .orElseThrow(() -> new AuthException("User not found"));
                final String accessToken = jwtProvider.generateAccessToken(customer);
                final String newRefreshToken = jwtProvider.generateRefreshToken(customer);
                refreshStorage.put(customer.getName(), newRefreshToken);

                List<String> accessTokens = accessStorage.computeIfAbsent(login, k -> new ArrayList<>());
                accessTokens.add(accessToken);
                accessStorage.put(login, accessTokens);

                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("JWT token is invalid");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

    public boolean revokeToken(@NonNull String accessToken) {
        if (jwtProvider.validateAccessToken(accessToken)) {
            final Claims claims = jwtProvider.getAccessClaims(accessToken);
            final String login = claims.getSubject();
            List<String> tokens = accessStorage.get(login);
            if (tokens != null) {
                tokens.remove(accessToken);
                if (tokens.isEmpty()) {
                    accessStorage.remove(login);
                } else {
                    accessStorage.put(login, tokens);
                }
                return true;
            }
        }
        return false;
    }
}
