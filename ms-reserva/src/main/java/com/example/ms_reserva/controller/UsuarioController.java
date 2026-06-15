package com.example.ms_reserva.controller;

import com.example.ms_reserva.model.Usuario;
import com.example.ms_reserva.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("authenticated", false, "message", "Username and password are required"));
        }

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(username);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.ok(Map.of("authenticated", false, "message", "User not found"));
        }

        Usuario usuario = usuarioOpt.get();
        boolean matches = passwordEncoder.matches(password, usuario.getPassword());

        if (matches) {
            return ResponseEntity.ok(Map.of(
                    "authenticated", true,
                    "username", usuario.getUsername(),
                    "role", usuario.getRole()
            ));
        } else {
            return ResponseEntity.ok(Map.of("authenticated", false, "message", "Invalid password"));
        }
    }

    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        if (usuario.getUsername() == null || usuario.getPassword() == null || usuario.getRole() == null) {
            return ResponseEntity.badRequest().body("Username, password, and role are required");
        }

        if (usuarioRepository.existsById(usuario.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Usuario savedUser = usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "username", savedUser.getUsername(),
                "role", savedUser.getRole()
        ));
    }
}
