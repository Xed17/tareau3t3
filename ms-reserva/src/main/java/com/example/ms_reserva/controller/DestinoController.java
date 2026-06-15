package com.example.ms_reserva.controller;

import com.example.ms_reserva.model.Destino;
import com.example.ms_reserva.repository.DestinoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destinos")
public class DestinoController {

    private final DestinoRepository destinoRepository;

    public DestinoController(DestinoRepository destinoRepository) {
        this.destinoRepository = destinoRepository;
    }

    @PostMapping
    public ResponseEntity<?> crearDestino(@RequestBody Destino destino) {
        if (destino.getCodDest() == null || destino.getCodDest().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El código del destino es obligatorio");
        }
        destino.setCodDest(destino.getCodDest().trim());
        if (destinoRepository.existsById(destino.getCodDest())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El destino con ese código ya existe");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(destinoRepository.save(destino));
    }

    @GetMapping
    public ResponseEntity<List<Destino>> listarDestinos() {
        return ResponseEntity.ok(destinoRepository.findAll());
    }
}
