package com.example.ms_reserva.controller;

import com.example.ms_reserva.model.Bus;
import com.example.ms_reserva.model.Programacion;
import com.example.ms_reserva.repository.BusRepository;
import com.example.ms_reserva.repository.ProgramacionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/programaciones")
public class ProgramacionController {

    private final ProgramacionRepository programacionRepository;
    private final BusRepository busRepository;

    public ProgramacionController(ProgramacionRepository programacionRepository, BusRepository busRepository) {
        this.programacionRepository = programacionRepository;
        this.busRepository = busRepository;
    }

    @PostMapping
    public ResponseEntity<?> crearProgramacion(@RequestBody Programacion programacion) {
        if (programacion.getBus() == null || programacion.getBus().getCodBus() == null) {
            return ResponseEntity.badRequest().body("El bus y su código son obligatorios");
        }
        Bus bus = busRepository.findById(programacion.getBus().getCodBus().trim())
                .orElseThrow(() -> new IllegalArgumentException("Bus no encontrado con código: " + programacion.getBus().getCodBus()));
        programacion.setBus(bus);
        return ResponseEntity.status(HttpStatus.CREATED).body(programacionRepository.save(programacion));
    }

    @GetMapping
    public ResponseEntity<List<Programacion>> listarProgramaciones() {
        return ResponseEntity.ok(programacionRepository.findAll());
    }
}
