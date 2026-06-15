package com.example.ms_reserva.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "programacion", indexes = {
        @Index(name = "idx_fecha_cod_bus", columnList = "fecha, cod_bus")
})
public class Programacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prog")
    private Integer idProg;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_bus", referencedColumnName = "cod_bus", nullable = false)
    @NotNull(message = "El bus es obligatorio")
    private Bus bus;

    @Column(name = "fecha", nullable = false)
    @NotNull(message = "La fecha de programación es obligatoria")
    private LocalDate fecha;

    @Column(name = "hora", nullable = false)
    @NotNull(message = "La hora de programación es obligatoria")
    private LocalTime hora;

    public Programacion() {}

    public Programacion(Bus bus, LocalDate fecha, LocalTime hora) {
        this.bus = bus;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Integer getIdProg() {
        return idProg;
    }

    public void setIdProg(Integer idProg) {
        this.idProg = idProg;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
}
