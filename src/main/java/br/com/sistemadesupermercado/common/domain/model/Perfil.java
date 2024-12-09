package br.com.sistemadesupermercado.common.domain.model;

import br.com.sistemadesupermercado.application.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(schema = Constants.SCHEMA_COMMON, name = "perfil")
public class Perfil implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Dado obrigatório")
    private String nome;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "perfil_acesso",
            joinColumns = { @JoinColumn(name = "perfil_id", foreignKey = @ForeignKey(name = "perfil_id")) },
            inverseJoinColumns = { @JoinColumn(name = "acesso_id", foreignKey = @ForeignKey(name = "acesso_id")) }
    )
    private List<Acesso> acessos = new ArrayList<>();

    @NotNull(message = "Dado obrigatório")
    @Column(name = "dt_inclusao")
    private Date dataInclusao;

    @Column(name = "dt_remocao")
    private Date dataRemocao;

    @NotNull(message = "Dado obrigatório")
    @Column(name= "iseditable")
    private boolean isEditable;

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
        this.nome = nome;
    }

    public List<Acesso> getAcessos() {
        return acessos;
    }

    public void setAcessos(List<Acesso> acessos) {
        this.acessos = acessos;
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

    public String getDescricaoAcessos(){
        return StringUtils.join(this.acessos.stream().map(Acesso::getDescricao).collect(Collectors.toList()), ";\n");
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Perfil perfil = (Perfil) o;
        return Objects.equals(id, perfil.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
