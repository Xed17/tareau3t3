package com.example.ms_reserva.controller;

import com.example.ms_reserva.model.Bus;
import com.example.ms_reserva.repository.BusRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buses")
public class BusController {

    private final BusRepository busRepository;

    public BusController(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @PostMapping
    public ResponseEntity<?> crearBus(@RequestBody Bus bus) {
        if (bus.getCodBus() == null || bus.getCodBus().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El código del bus es obligatorio");
        }
        bus.setCodBus(bus.getCodBus().trim());
        if (bus.getPlacaBus() != null) {
            bus.setPlacaBus(bus.getPlacaBus().trim());
        }
        if (busRepository.existsById(bus.getCodBus())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El bus con ese código ya existe");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(busRepository.save(bus));
    }

    @GetMapping
    public ResponseEntity<List<Bus>> listarBuses() {
        return ResponseEntity.ok(busRepository.findAll());
    }
}
