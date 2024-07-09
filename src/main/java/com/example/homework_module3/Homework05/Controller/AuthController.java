package com.example.homework_module3.Homework05.Controller;

import com.example.homework_module3.Homework05.Service.AuthService;
import com.example.homework_module3.Homework05.domain.JwtRequest;
import com.example.homework_module3.Homework05.domain.JwtResponse;
import com.example.homework_module3.Homework05.domain.RefreshJwtRequest;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) throws AuthException {
        log.info("login method in AuthController is triggered.");
        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        log.info("getNewAccessToken method in AuthController is triggered.");
        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        log.info("getNewRefreshToken method in AuthController is triggered.");
        final JwtResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/revoke")
    public ResponseEntity<?> revokeToken(@RequestParam String accessToken) {
        log.info("revokeToken method in AuthController is triggered.");
        boolean isRevokeSuccess = authService.revokeToken(accessToken);
        if (isRevokeSuccess) {
            return ResponseEntity.ok("Token was revoked successfully");
        }
        return ResponseEntity.badRequest().body("Token was not revoked");
    }
}