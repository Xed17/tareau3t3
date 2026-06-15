package com.example.ms_reserva.controller;

import com.example.ms_reserva.model.Destino;
import com.example.ms_reserva.repository.DestinoRepository;
import com.example.ms_reserva.repository.ReservaRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destinos")
public class DestinoController {

    private final DestinoRepository destinoRepository;
    private final ReservaRepository reservaRepository;

    public DestinoController(DestinoRepository destinoRepository, ReservaRepository reservaRepository) {
        this.destinoRepository = destinoRepository;
        this.reservaRepository = reservaRepository;
    }

    @PostMapping
    public ResponseEntity<?> crearDestino(@Valid @RequestBody Destino destino) {
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
    @GetMapping("/{codDest}")
    public ResponseEntity<?> obtenerDestino(@PathVariable String codDest) {
        return destinoRepository.findById(codDest.trim())
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{codDest}")
    public ResponseEntity<?> actualizarDestino(@PathVariable String codDest, @Valid @RequestBody Destino destinoDetails) {
        return destinoRepository.findById(codDest.trim())
                .<ResponseEntity<?>>map(existingDestino -> {
                    existingDestino.setCiuDest(destinoDetails.getCiuDest());
                    existingDestino.setCostDest(destinoDetails.getCostDest());
                    return ResponseEntity.ok(destinoRepository.save(existingDestino));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{codDest}")
    public ResponseEntity<?> eliminarDestino(@PathVariable String codDest) {
        String trimmedCod = codDest.trim();
        return destinoRepository.findById(trimmedCod)
                .<ResponseEntity<?>>map(existingDestino -> {
                    if (reservaRepository.existsByDestinoCodDest(trimmedCod)) {
                        return ResponseEntity.badRequest().body("No se puede eliminar el destino porque tiene reservas asociadas");
                    }
                    destinoRepository.delete(existingDestino);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
