package com.eventapp.backend.auth;

import com.eventapp.backend.models.User;
import com.eventapp.backend.repositories.UserRepository;
import com.eventapp.backend.security.GoogleTokenVerifierService;
import com.eventapp.backend.security.JwtService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final GoogleTokenVerifierService googleTokenVerifierService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            UserRepository userRepository,
            JwtService jwtService,
            GoogleTokenVerifierService googleTokenVerifierService,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.googleTokenVerifierService = googleTokenVerifierService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalStateException("Email is required");
        }

        if (request.getPassword() == null || request.getPassword().length() < 8) {
            throw new IllegalStateException("Password must contain at least 8 characters");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("Email is already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setProvider("local");
        user.setProviderUserId(request.getEmail());
        user.setEmailVerified(false);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setLastLoginAt(Instant.now());

        User savedUser = userRepository.save(user);
        return toAuthResponse(savedUser, "Registration successful");
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        if (request.getEmail() == null || request.getPassword() == null) {
            throw new IllegalStateException("Email and password are required");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("Invalid email or password"));

        if (user.getPasswordHash() == null || !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalStateException("Invalid email or password");
        }

        user.setLastLoginAt(Instant.now());
        User savedUser = userRepository.save(user);
        return toAuthResponse(savedUser, "Login successful");
    }

    @PostMapping("/google")
    public AuthResponse googleLogin(@RequestBody GoogleLoginRequest request) {
        if (request.getIdToken() == null || request.getIdToken().isBlank()) {
            throw new IllegalStateException("Google ID token is required");
        }

        GoogleIdToken.Payload payload = googleTokenVerifierService.verify(request.getIdToken());

        String provider = "google";
        String providerUserId = payload.getSubject(); // sub
        String email = payload.getEmail();
        Boolean emailVerified = payload.getEmailVerified();
        String name = (String) payload.get("name");

        User user = userRepository
                .findByProviderAndProviderUserId(provider, providerUserId)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setProvider(provider);
                    newUser.setProviderUserId(providerUserId);
                    newUser.setRole("ROLE_USER");
                    return newUser;
                });

        user.setEmail(email);
        user.setEmailVerified(Boolean.TRUE.equals(emailVerified));
        user.setName(name);
        user.setLastLoginAt(Instant.now());

        User savedUser = userRepository.save(user);

        return toAuthResponse(savedUser, "Google login successful");
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestHeader("X-Refresh-Token") String refreshToken) {
        Long userId = jwtService.extractUserId(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        return toAuthResponse(user, "Token refreshed");
    }

    @GetMapping("/me")
    public CurrentUserResponse me(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalStateException("Missing or invalid Authorization header");
        }

        String token = authorizationHeader.substring(7);
        Long userId = jwtService.extractUserId(token);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        return new CurrentUserResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole()
        );
    }

    private AuthResponse toAuthResponse(User user, String message) {
        return new AuthResponse(
                jwtService.generateAccessToken(user),
                jwtService.generateRefreshToken(user),
                message
        );
    }
}
