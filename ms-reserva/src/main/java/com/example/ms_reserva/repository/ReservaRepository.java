package com.example.ms_reserva.repository;

import com.example.ms_reserva.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, String> {

    @Query("SELECT MAX(r.nroReser) FROM Reserva r")
    String findMaxNroReser();

    boolean existsByClienteCodCli(String codCli);
    boolean existsByDestinoCodDest(String codDest);
    boolean existsByProgramacionIdProg(Integer idProg);
}
