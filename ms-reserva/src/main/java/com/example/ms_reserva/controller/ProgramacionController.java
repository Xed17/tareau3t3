package com.example.ms_reserva.controller;

import com.example.ms_reserva.model.Bus;
import com.example.ms_reserva.model.Programacion;
import com.example.ms_reserva.repository.BusRepository;
import com.example.ms_reserva.repository.ProgramacionRepository;
import com.example.ms_reserva.repository.ReservaRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/programaciones")
public class ProgramacionController {

    private final ProgramacionRepository programacionRepository;
    private final BusRepository busRepository;
    private final ReservaRepository reservaRepository;

    public ProgramacionController(ProgramacionRepository programacionRepository, BusRepository busRepository, ReservaRepository reservaRepository) {
        this.programacionRepository = programacionRepository;
        this.busRepository = busRepository;
        this.reservaRepository = reservaRepository;
    }

    @PostMapping
    public ResponseEntity<?> crearProgramacion(@Valid @RequestBody Programacion programacion) {
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

    @GetMapping("/{idProg}")
    public ResponseEntity<?> obtenerProgramacion(@PathVariable Integer idProg) {
        return programacionRepository.findById(idProg)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{idProg}")
    public ResponseEntity<?> actualizarProgramacion(@PathVariable Integer idProg, @Valid @RequestBody Programacion programacionDetails) {
        return programacionRepository.findById(idProg)
                .<ResponseEntity<?>>map(existingProg -> {
                    if (programacionDetails.getBus() != null && programacionDetails.getBus().getCodBus() != null) {
                        Bus bus = busRepository.findById(programacionDetails.getBus().getCodBus().trim())
                                .orElse(null);
                        if (bus == null) {
                            return ResponseEntity.badRequest().body("Bus no encontrado con código: " + programacionDetails.getBus().getCodBus());
                        }
                        existingProg.setBus(bus);
                    }
                    if (programacionDetails.getFecha() != null) {
                        existingProg.setFecha(programacionDetails.getFecha());
                    }
                    if (programacionDetails.getHora() != null) {
                        existingProg.setHora(programacionDetails.getHora());
                    }
                    return ResponseEntity.ok(programacionRepository.save(existingProg));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idProg}")
    public ResponseEntity<?> eliminarProgramacion(@PathVariable Integer idProg) {
        return programacionRepository.findById(idProg)
                .<ResponseEntity<?>>map(existingProg -> {
                    if (reservaRepository.existsByProgramacionIdProg(idProg)) {
                        return ResponseEntity.badRequest().body("No se puede eliminar la programación porque tiene reservas asociadas");
                    }
                    programacionRepository.delete(existingProg);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
