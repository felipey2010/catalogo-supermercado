package br.com.sistemadesupermercado.common.domain.model;

import br.com.sistemadesupermercado.application.util.Constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(schema = Constants.SCHEMA_COMMON, name = "pais")
public class Pais implements Serializable{

    @Column(length = 2)
    private String isoBinario;

    @Column(length = 3)
    private String isoTernario;

    @Id
    @NotNull(message = "Dado obrigatório")
    @Column(name = "codigo",nullable = false)
    private Integer codigoPais;

    @NotNull(message = "Dado obrigatório")
    @Column(name = "nome", nullable = false)
    private String nome;

    public Integer getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(Integer codigoPais) {
        this.codigoPais = codigoPais;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIsoBinario() {
        return isoBinario;
    }

    public void setIsoBinario(String isoBinario) {
        this.isoBinario = isoBinario;
    }

    public String getIsoTernario() {
        return isoTernario;
    }

    public void setIsoTernario(String isoTernario) {
        this.isoTernario = isoTernario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pais pais = (Pais) o;

        return pais.codigoPais.equals(codigoPais);
    }

    @Override
    public int hashCode() {
        return codigoPais.hashCode();
    }
}
