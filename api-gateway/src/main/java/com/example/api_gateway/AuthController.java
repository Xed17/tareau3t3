package com.example.api_gateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final WebClient.Builder webClientBuilder;
    private final JwtTokenProvider tokenProvider;

    public AuthController(WebClient.Builder webClientBuilder, JwtTokenProvider tokenProvider) {
        this.webClientBuilder = webClientBuilder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<Object>> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if (username == null || password == null) {
            return Mono.just(ResponseEntity.badRequest().body((Object) "Username and password are required"));
        }

        // Call ms-reserva to validate credentials
        return webClientBuilder.build()
                .post()
                .uri("http://ms-reserva/usuarios/auth")
                .bodyValue(Map.of("username", username, "password", password))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    Boolean authenticated = (Boolean) response.get("authenticated");
                    if (Boolean.TRUE.equals(authenticated)) {
                        String role = (String) response.get("role");
                        String token = tokenProvider.generateToken(username, role);
                        return ResponseEntity.ok((Object) Map.of("token", token, "username", username, "role", role));
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body((Object) "Invalid username or password");
                    }
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body((Object) ("Error communicating with authentication service: " + e.getMessage()))));
    }
}
