package com.example.ms_reserva.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.JdbcTypeCode;

@Entity
@Table(name = "bus", indexes = {
        @Index(name = "idx_placa_bus", columnList = "placa_bus")
})
public class Bus {

    @Id
    @Column(name = "cod_bus", length = 5)
    @JdbcTypeCode(java.sql.Types.CHAR)
    @NotBlank(message = "El código del bus es obligatorio")
    @Size(max = 5, message = "El código del bus no puede superar los 5 caracteres")
    private String codBus;

    @Column(name = "mod_bus", length = 15)
    @Size(max = 15, message = "El modelo no puede superar los 15 caracteres")
    private String modBus;

    @Column(name = "placa_bus", length = 8)
    @JdbcTypeCode(java.sql.Types.CHAR)
    @Size(max = 8, message = "La placa no puede superar los 8 caracteres")
    private String placaBus;

    @Column(name = "cap_bus")
    @NotNull(message = "La capacidad del bus es obligatoria")
    @Min(value = 1, message = "La capacidad del bus debe ser al menos 1")
    private Integer capBus;

    public Bus() {}

    public Bus(String codBus, String modBus, String placaBus, Integer capBus) {
        this.codBus = codBus;
        this.modBus = modBus;
        this.placaBus = placaBus;
        this.capBus = capBus;
    }

    public String getCodBus() {
        return codBus;
    }

    public void setCodBus(String codBus) {
        this.codBus = codBus;
    }

    public String getModBus() {
        return modBus;
    }

    public void setModBus(String modBus) {
        this.modBus = modBus;
    }

    public String getPlacaBus() {
        return placaBus;
    }

    public void setPlacaBus(String placaBus) {
        this.placaBus = placaBus;
    }

    public Integer getCapBus() {
        return capBus;
    }

    public void setCapBus(Integer capBus) {
        this.capBus = capBus;
    }
}
