package br.com.sistemadesupermercado.common.domain.model;

import br.com.sistemadesupermercado.application.util.Constants;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(schema = Constants.SCHEMA_COMMON, name = "mensagem_global")
public class MensagemGlobal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Dado obrigatório")
    @Size(max = 120, message = "O tamanho máximo para o título é de 120 caracteres")
    @Column(name = "titulo",nullable = false)
    private String titulo;

    @NotBlank(message = "É necessário informar o conteúdo da mensagem")
    @Column(columnDefinition = "text", nullable = false, updatable = false)
    private String mensagem;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInclusao;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRemocao;

    private boolean global;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "fk_usuario_id"))
    private Usuario usuario;

    public MensagemGlobal() {
        this.dataInclusao = new Date();
    }

    public MensagemGlobal(Usuario usuario) {
        this.usuario = usuario;
        this.dataInclusao = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Date getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(Date dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataRemocao() {
        return dataRemocao;
    }

    public void setDataRemocao(Date dataRemocao) {
        this.dataRemocao = dataRemocao;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MensagemGlobal mensagem = (MensagemGlobal) o;
        return Objects.equals(id, mensagem.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
