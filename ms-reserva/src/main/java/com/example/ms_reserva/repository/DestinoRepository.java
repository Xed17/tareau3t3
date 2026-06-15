package com.example.ms_reserva.repository;

import com.example.ms_reserva.model.Destino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinoRepository extends JpaRepository<Destino, String> {
}
