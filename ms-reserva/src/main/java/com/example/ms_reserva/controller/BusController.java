package com.example.ms_reserva.controller;

import com.example.ms_reserva.model.Bus;
import com.example.ms_reserva.repository.BusRepository;
import com.example.ms_reserva.repository.ProgramacionRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buses")
public class BusController {

    private final BusRepository busRepository;
    private final ProgramacionRepository programacionRepository;

    public BusController(BusRepository busRepository, ProgramacionRepository programacionRepository) {
        this.busRepository = busRepository;
        this.programacionRepository = programacionRepository;
    }

    @PostMapping
    public ResponseEntity<?> crearBus(@Valid @RequestBody Bus bus) {
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
    @GetMapping("/{codBus}")
    public ResponseEntity<?> obtenerBus(@PathVariable String codBus) {
        return busRepository.findById(codBus.trim())
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{codBus}")
    public ResponseEntity<?> actualizarBus(@PathVariable String codBus, @Valid @RequestBody Bus busDetails) {
        return busRepository.findById(codBus.trim())
                .<ResponseEntity<?>>map(existingBus -> {
                    existingBus.setModBus(busDetails.getModBus());
                    if (busDetails.getPlacaBus() != null) {
                        existingBus.setPlacaBus(busDetails.getPlacaBus().trim());
                    }
                    existingBus.setCapBus(busDetails.getCapBus());
                    return ResponseEntity.ok(busRepository.save(existingBus));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{codBus}")
    public ResponseEntity<?> eliminarBus(@PathVariable String codBus) {
        String trimmedCod = codBus.trim();
        return busRepository.findById(trimmedCod)
                .<ResponseEntity<?>>map(existingBus -> {
                    if (programacionRepository.existsByBusCodBus(trimmedCod)) {
                        return ResponseEntity.badRequest().body("No se puede eliminar el bus porque tiene programaciones asociadas");
                    }
                    busRepository.delete(existingBus);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
