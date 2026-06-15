package com.example.ms_reserva.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "destino", indexes = {
        @Index(name = "idx_ciu_dest", columnList = "ciu_dest")
})
public class Destino {

    @Id
    @Column(name = "cod_dest", columnDefinition = "CHAR(4)")
    private String codDest;

    @Column(name = "ciu_dest", length = 25)
    private String ciuDest;

    @Column(name = "cost_dest", precision = 10, scale = 2)
    private BigDecimal costDest;

    public Destino() {}

    public Destino(String codDest, String ciuDest, BigDecimal costDest) {
        this.codDest = codDest;
        this.ciuDest = ciuDest;
        this.costDest = costDest;
    }

    public String getCodDest() {
        return codDest;
    }

    public void setCodDest(String codDest) {
        this.codDest = codDest;
    }

    public String getCiuDest() {
        return ciuDest;
    }

    public void setCiuDest(String ciuDest) {
        this.ciuDest = ciuDest;
    }

    public BigDecimal getCostDest() {
        return costDest;
    }

    public void setCostDest(BigDecimal costDest) {
        this.costDest = costDest;
    }
}
