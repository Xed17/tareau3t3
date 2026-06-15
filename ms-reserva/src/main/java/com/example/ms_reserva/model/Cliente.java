package com.example.ms_reserva.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.JdbcTypeCode;

@Entity
@Table(name = "cliente", indexes = {
        @Index(name = "idx_nom_ape_cli", columnList = "nom_cli, ape_cli")
})
public class Cliente {

    @Id
    @Column(name = "cod_cli", length = 5)
    @JdbcTypeCode(java.sql.Types.CHAR)
    @NotBlank(message = "El código de cliente es obligatorio")
    @Size(max = 5, message = "El código de cliente no puede superar los 5 caracteres")
    private String codCli;

    @Column(name = "nom_cli", length = 20)
    @NotBlank(message = "El nombre de cliente es obligatorio")
    @Size(max = 20, message = "El nombre no puede superar los 20 caracteres")
    private String nomCli;

    @Column(name = "ape_cli", length = 30)
    @NotBlank(message = "El apellido de cliente es obligatorio")
    @Size(max = 30, message = "El apellido no puede superar los 30 caracteres")
    private String apeCli;

    @Column(name = "edad_cli", length = 2)
    @JdbcTypeCode(java.sql.Types.CHAR)
    @NotBlank(message = "La edad del cliente es obligatoria")
    @Size(max = 2, message = "La edad del cliente no puede superar los 2 caracteres")
    private String edadCli;

    @Column(name = "sexo_cli", length = 1)
    @JdbcTypeCode(java.sql.Types.CHAR)
    @NotBlank(message = "El sexo del cliente es obligatorio")
    @Size(max = 1, message = "El sexo debe ser de 1 caracter")
    private String sexoCli;

    public Cliente() {}

    public Cliente(String codCli, String nomCli, String apeCli, String edadCli, String sexoCli) {
        this.codCli = codCli;
        this.nomCli = nomCli;
        this.apeCli = apeCli;
        this.edadCli = edadCli;
        this.sexoCli = sexoCli;
    }

    public String getCodCli() {
        return codCli;
    }

    public void setCodCli(String codCli) {
        this.codCli = codCli;
    }

    public String getNomCli() {
        return nomCli;
    }

    public void setNomCli(String nomCli) {
        this.nomCli = nomCli;
    }

    public String getApeCli() {
        return apeCli;
    }

    public void setApeCli(String apeCli) {
        this.apeCli = apeCli;
    }

    public String getEdadCli() {
        return edadCli;
    }

    public void setEdadCli(String edadCli) {
        this.edadCli = edadCli;
    }

    public String getSexoCli() {
        return sexoCli;
    }

    public void setSexoCli(String sexoCli) {
        this.sexoCli = sexoCli;
    }
}
