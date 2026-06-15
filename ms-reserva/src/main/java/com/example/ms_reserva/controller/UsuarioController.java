package com.example.ms_reserva.controller;

import com.example.ms_reserva.model.Usuario;
import com.example.ms_reserva.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        if (usuario.getUsername() == null || usuario.getPassword() == null) {
            return ResponseEntity.badRequest().body("Username and password are required");
        }

        if (usuario.getRole() == null || usuario.getRole().trim().isEmpty()) {
            usuario.setRole("ROLE_USER");
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

    @GetMapping
    public ResponseEntity<List<?>> listarUsuarios() {
        List<?> usuarios = usuarioRepository.findAll().stream().map(u -> Map.of(
                "username", u.getUsername(),
                "role", u.getRole()
        )).toList();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable String username) {
        return usuarioRepository.findById(username)
                .<ResponseEntity<?>>map(u -> ResponseEntity.ok(Map.of(
                        "username", u.getUsername(),
                        "role", u.getRole()
                )))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable String username, @RequestBody Usuario usuarioDetails) {
        return usuarioRepository.findById(username)
                .<ResponseEntity<?>>map(existingUser -> {
                    if (usuarioDetails.getPassword() != null && !usuarioDetails.getPassword().trim().isEmpty()) {
                        existingUser.setPassword(passwordEncoder.encode(usuarioDetails.getPassword()));
                    }
                    if (usuarioDetails.getRole() != null && !usuarioDetails.getRole().trim().isEmpty()) {
                        existingUser.setRole(usuarioDetails.getRole().trim());
                    }
                    Usuario updatedUser = usuarioRepository.save(existingUser);
                    return ResponseEntity.ok(Map.of(
                            "username", updatedUser.getUsername(),
                            "role", updatedUser.getRole()
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable String username) {
        return usuarioRepository.findById(username)
                .<ResponseEntity<?>>map(existingUser -> {
                    usuarioRepository.delete(existingUser);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
