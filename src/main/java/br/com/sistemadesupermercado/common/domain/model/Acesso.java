package br.com.sistemadesupermercado.common.domain.model;

import br.com.sistemadesupermercado.application.util.Constants;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * Classe que define grupos de acessos que estarão relacioandos
 * a permissoes especificas usadas por um usuário
 */

@Entity
@Table(schema = Constants.SCHEMA_COMMON, name = "acesso")
public class Acesso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //Nome - ex: Administrador
    @NotNull(message = "Dado obrigaório")
    @Column(length = 40)
    private String nome;

    //Descrição - ex: ROLE_ADMIN
    @NotEmpty(message = "Dado obrigatório")
    @Column(length = 40)
    private String descricao;

    @ManyToMany(mappedBy = "acessos")
    @Column(name = "perfil")
    private Set<Perfil> perfis;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_inclusao", nullable = false)
    private Date dataInclusao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_remocao")
    private Date dataRemocao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome.toUpperCase();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(Date dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public Date getDataRemocao() {
        return dataRemocao;
    }

    public void setDataRemocao(Date dataRemocao) {
        this.dataRemocao = dataRemocao;
    }

    public Set<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(Set<Perfil> perfis) {
        this.perfis = perfis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Acesso acesso = (Acesso) o;
        return Objects.equals(id, acesso.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
