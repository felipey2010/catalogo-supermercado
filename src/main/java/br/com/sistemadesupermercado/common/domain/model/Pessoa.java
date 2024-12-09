package br.com.sistemadesupermercado.common.domain.model;

import br.com.sistemadesupermercado.application.util.Constants;
import br.com.sistemadesupermercado.application.util.Util;
import br.com.sistemadesupermercado.common.domain.enums.Genero;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe para representar a pessoa
 */
@Entity
@Table(schema = Constants.SCHEMA_COMMON, name = "pessoa")
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Dado obrigatório")
    @Size(max = 120, message = "O tamanho máximo para o nome é de 120 caracteres")
    @Column(nullable = false)
    private String nome;

    private String codigo; // CODIGO = CPF

    @Email(message = "Email inválido")
    private String email;

    @NotNull(message = "Dado obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(length = 12, name = "genero")
    private Genero genero;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Dado obrigatório")
    @JoinColumn(name = "nacionalidade_id", foreignKey = @ForeignKey(name = "fk_nacionalidade_id"), nullable = false)
    private Pais nacionalidade;

    @Size(max = 30, message = "A naturalidade deve ter no máximo vinte caracteres")
    @Column(length = 30, name = "naturalidade")
    private String naturalidade;

    @ElementCollection
    @CollectionTable(schema = Constants.SCHEMA_COMMON, name = "telefone",
            joinColumns = @JoinColumn(name = "pessoa_id", foreignKey = @ForeignKey(name = "fk_pessoa_id")))
    private List<Telefone> telefones = new ArrayList<>();

    @NotNull(message = "Dado obrigatório")
    @Column(name = "dt_inclusao")
    private Date dataInclusao;

    @Column(name = "dt_remocao")
    private Date dataRemocao;

    public Pessoa() {
        this.dataInclusao = new Date();
        this.nacionalidade = new Pais();
    }

    public String getCodigoMask() {
        return Util.addMascara(this.codigo, "999.999.999-99");
    }

    @PrePersist
    private void setDataInclusao() {
        this.dataInclusao = new Date();
    }

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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
        if (!codigo.isEmpty())
            this.codigo = Util.removeMaskCPF(codigo);
    }

    public void addTelefone(Telefone telefone) {
        if (!telefones.contains(telefone)) {
            telefones.add(telefone);
        }
    }

    public void deleteTelefone(Telefone telefone) {
        telefones.remove(telefone);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Pais getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(Pais nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
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
}
