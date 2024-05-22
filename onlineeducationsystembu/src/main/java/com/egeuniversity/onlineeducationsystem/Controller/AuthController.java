package com.egeuniversity.onlineeducationsystem.Controller;

import com.egeuniversity.onlineeducationsystem.Service.AuthService;
import com.egeuniversity.onlineeducationsystem.payload.requests.LoginRequest;
import com.egeuniversity.onlineeducationsystem.payload.requests.SignupRequest;
import com.egeuniversity.onlineeducationsystem.payload.responses.JwtResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return authService.registerUser(signUpRequest);
    }

    @PostMapping("/token")
    public ResponseEntity<JwtResponse> generateNewToken(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.generateNewToken(loginRequest));
    }
}