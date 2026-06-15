package com.example.ms_reserva.service;

import com.example.ms_reserva.model.Cliente;
import com.example.ms_reserva.model.Destino;
import com.example.ms_reserva.model.Programacion;
import com.example.ms_reserva.model.Reserva;
import com.example.ms_reserva.repository.ClienteRepository;
import com.example.ms_reserva.repository.DestinoRepository;
import com.example.ms_reserva.repository.ProgramacionRepository;
import com.example.ms_reserva.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final ProgramacionRepository programacionRepository;
    private final DestinoRepository destinoRepository;

    public ReservaServiceImpl(ReservaRepository reservaRepository,
                              ClienteRepository clienteRepository,
                              ProgramacionRepository programacionRepository,
                              DestinoRepository destinoRepository) {
        this.reservaRepository = reservaRepository;
        this.clienteRepository = clienteRepository;
        this.programacionRepository = programacionRepository;
        this.destinoRepository = destinoRepository;
    }

    @Override
    @Transactional
    public Reserva crearReserva(Reserva reserva) {
        if (reserva.getCliente() == null || reserva.getCliente().getCodCli() == null) {
            throw new IllegalArgumentException("El cliente y su código son obligatorios");
        }
        if (reserva.getProgramacion() == null || reserva.getProgramacion().getIdProg() == null) {
            throw new IllegalArgumentException("La programación y su ID son obligatorios");
        }
        if (reserva.getDestino() == null || reserva.getDestino().getCodDest() == null) {
            throw new IllegalArgumentException("El destino y su código son obligatorios");
        }

        // Validate Cliente
        Cliente cliente = clienteRepository.findById(reserva.getCliente().getCodCli().trim())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con código: " + reserva.getCliente().getCodCli()));
        reserva.setCliente(cliente);

        // Validate Programacion
        Programacion prog = programacionRepository.findById(reserva.getProgramacion().getIdProg())
                .orElseThrow(() -> new IllegalArgumentException("Programación no encontrada con ID: " + reserva.getProgramacion().getIdProg()));
        reserva.setProgramacion(prog);

        // Validate Destino
        Destino dest = destinoRepository.findById(reserva.getDestino().getCodDest().trim())
                .orElseThrow(() -> new IllegalArgumentException("Destino no encontrado con código: " + reserva.getDestino().getCodDest()));
        reserva.setDestino(dest);

        // Generate nro_reser if not provided
        if (reserva.getNroReser() == null || reserva.getNroReser().trim().isEmpty()) {
            reserva.setNroReser(generarSiguienteNroReserva());
        } else {
            reserva.setNroReser(reserva.getNroReser().trim());
        }

        return reservaRepository.save(reserva);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Reserva obtenerReserva(String nroReser) {
        return reservaRepository.findById(nroReser.trim())
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada: " + nroReser));
    }

    private synchronized String generarSiguienteNroReserva() {
        String maxNro = reservaRepository.findMaxNroReser();
        if (maxNro == null || maxNro.trim().isEmpty()) {
            return "R0000001";
        }
        
        try {
            String maxNroTrimmed = maxNro.trim();
            String numericPartStr = maxNroTrimmed.substring(1);
            int numericPart = Integer.parseInt(numericPartStr);
            int nextPart = numericPart + 1;
            return String.format("R%07d", nextPart);
        } catch (Exception e) {
            long count = reservaRepository.count();
            return String.format("R%07d", count + 1);
        }
    }
    @Override
    @Transactional
    public Reserva actualizarReserva(String nroReser, Reserva reserva) {
        Reserva existente = obtenerReserva(nroReser);

        if (reserva.getCliente() == null || reserva.getCliente().getCodCli() == null) {
            throw new IllegalArgumentException("El cliente y su código son obligatorios");
        }
        if (reserva.getProgramacion() == null || reserva.getProgramacion().getIdProg() == null) {
            throw new IllegalArgumentException("La programación y su ID son obligatorios");
        }
        if (reserva.getDestino() == null || reserva.getDestino().getCodDest() == null) {
            throw new IllegalArgumentException("El destino y su código son obligatorios");
        }

        // Validate Cliente
        Cliente cliente = clienteRepository.findById(reserva.getCliente().getCodCli().trim())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con código: " + reserva.getCliente().getCodCli()));
        existente.setCliente(cliente);

        // Validate Programacion
        Programacion prog = programacionRepository.findById(reserva.getProgramacion().getIdProg())
                .orElseThrow(() -> new IllegalArgumentException("Programación no encontrada con ID: " + reserva.getProgramacion().getIdProg()));
        existente.setProgramacion(prog);

        // Validate Destino
        Destino dest = destinoRepository.findById(reserva.getDestino().getCodDest().trim())
                .orElseThrow(() -> new IllegalArgumentException("Destino no encontrado con código: " + reserva.getDestino().getCodDest()));
        existente.setDestino(dest);

        existente.setFechaReser(reserva.getFechaReser());
        existente.setHoraReser(reserva.getHoraReser());

        return reservaRepository.save(existente);
    }

    @Override
    @Transactional
    public void eliminarReserva(String nroReser) {
        Reserva existente = obtenerReserva(nroReser);
        reservaRepository.delete(existente);
    }
}
