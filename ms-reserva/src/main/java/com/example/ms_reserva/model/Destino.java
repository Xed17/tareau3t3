package com.example.ms_reserva.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.JdbcTypeCode;
import java.math.BigDecimal;

@Entity
@Table(name = "destino", indexes = {
        @Index(name = "idx_ciu_dest", columnList = "ciu_dest")
})
public class Destino {

    @Id
    @Column(name = "cod_dest", length = 4)
    @JdbcTypeCode(java.sql.Types.CHAR)
    @NotBlank(message = "El código de destino es obligatorio")
    @Size(max = 4, message = "El código de destino no puede superar los 4 caracteres")
    private String codDest;

    @Column(name = "ciu_dest", length = 25)
    @NotBlank(message = "La ciudad de destino es obligatoria")
    @Size(max = 25, message = "La ciudad de destino no puede superar los 25 caracteres")
    private String ciuDest;

    @Column(name = "cost_dest", precision = 10, scale = 2)
    @NotNull(message = "El costo de destino es obligatorio")
    @DecimalMin(value = "0.0", message = "El costo de destino debe ser mayor o igual a 0")
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
