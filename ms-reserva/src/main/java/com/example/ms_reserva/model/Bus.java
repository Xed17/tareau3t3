package com.example.ms_reserva.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

@Entity
@Table(name = "bus", indexes = {
        @Index(name = "idx_placa_bus", columnList = "placa_bus")
})
public class Bus {

    @Id
    @Column(name = "cod_bus", length = 5)
    @JdbcTypeCode(java.sql.Types.CHAR)
    private String codBus;

    @Column(name = "mod_bus", length = 15)
    private String modBus;

    @Column(name = "placa_bus", length = 8)
    @JdbcTypeCode(java.sql.Types.CHAR)
    private String placaBus;

    @Column(name = "cap_bus")
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
