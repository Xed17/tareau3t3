package com.example.ms_reserva.service;

import com.example.ms_reserva.model.Reserva;
import java.util.List;

public interface ReservaService {
    Reserva crearReserva(Reserva reserva);
    List<Reserva> listarReservas();
    Reserva obtenerReserva(String nroReser);
}
