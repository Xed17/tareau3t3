package com.example.ms_reserva.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente", indexes = {
        @Index(name = "idx_nom_ape_cli", columnList = "nom_cli, ape_cli")
})
public class Cliente {

    @Id
    @Column(name = "cod_cli", columnDefinition = "CHAR(5)")
    private String codCli;

    @Column(name = "nom_cli", length = 20)
    private String nomCli;

    @Column(name = "ape_cli", length = 30)
    private String apeCli;

    @Column(name = "edad_cli", columnDefinition = "CHAR(2)")
    private String edadCli;

    @Column(name = "sexo_cli", columnDefinition = "CHAR(1)")
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
