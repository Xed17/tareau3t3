package com.example.ms_reserva.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.JdbcTypeCode;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reserva", indexes = {
        @Index(name = "idx_fecha_cod_cli", columnList = "fecha_reser, cod_cli"),
        @Index(name = "idx_id_prog_cod_dest", columnList = "id_prog, cod_dest")
})
public class Reserva {

    @Id
    @Column(name = "nro_reser", length = 8)
    @JdbcTypeCode(java.sql.Types.CHAR)
    @Size(max = 8, message = "El número de reserva no puede superar los 8 caracteres")
    private String nroReser;

    @Column(name = "fecha_reser", nullable = false)
    @NotNull(message = "La fecha de reserva es obligatoria")
    private LocalDate fechaReser;

    @Column(name = "hora_reser", nullable = false)
    @NotNull(message = "La hora de reserva es obligatoria")
    private LocalTime horaReser;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_cli", referencedColumnName = "cod_cli", nullable = false)
    @NotNull(message = "El cliente es obligatorio")
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_prog", referencedColumnName = "id_prog", nullable = false)
    @NotNull(message = "La programación es obligatoria")
    private Programacion programacion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_dest", referencedColumnName = "cod_dest", nullable = false)
    @NotNull(message = "El destino es obligatorio")
    private Destino destino;

    public Reserva() {}

    public Reserva(String nroReser, LocalDate fechaReser, LocalTime horaReser, Cliente cliente, Programacion programacion, Destino destino) {
        this.nroReser = nroReser;
        this.fechaReser = fechaReser;
        this.horaReser = horaReser;
        this.cliente = cliente;
        this.programacion = programacion;
        this.destino = destino;
    }

    public String getNroReser() {
        return nroReser;
    }

    public void setNroReser(String nroReser) {
        this.nroReser = nroReser;
    }

    public LocalDate getFechaReser() {
        return fechaReser;
    }

    public void setFechaReser(LocalDate fechaReser) {
        this.fechaReser = fechaReser;
    }

    public LocalTime getHoraReser() {
        return horaReser;
    }

    public void setHoraReser(LocalTime horaReser) {
        this.horaReser = horaReser;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Programacion getProgramacion() {
        return programacion;
    }

    public void setProgramacion(Programacion programacion) {
        this.programacion = programacion;
    }

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }
}
