package com.example.ms_reserva.controller;

import com.example.ms_reserva.model.Reserva;
import com.example.ms_reserva.service.ReservaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<?> crearReserva(@RequestBody Reserva reserva) {
        try {
            Reserva nuevaReserva = reservaService.crearReserva(reserva);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReserva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear reserva: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Reserva>> listarReservas() {
        return ResponseEntity.ok(reservaService.listarReservas());
    }

    @GetMapping("/{nroReser}")
    public ResponseEntity<?> obtenerReserva(@PathVariable String nroReser) {
        try {
            Reserva reserva = reservaService.obtenerReserva(nroReser);
            return ResponseEntity.ok(reserva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener reserva: " + e.getMessage());
        }
    }
    @PutMapping("/{nroReser}")
    public ResponseEntity<?> actualizarReserva(@PathVariable String nroReser, @RequestBody Reserva reserva) {
        try {
            Reserva actualizada = reservaService.actualizarReserva(nroReser, reserva);
            return ResponseEntity.ok(actualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar reserva: " + e.getMessage());
        }
    }

    @DeleteMapping("/{nroReser}")
    public ResponseEntity<?> eliminarReserva(@PathVariable String nroReser) {
        try {
            reservaService.eliminarReserva(nroReser);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar reserva: " + e.getMessage());
        }
    }
}
