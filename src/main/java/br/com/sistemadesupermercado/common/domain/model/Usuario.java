package br.com.sistemadesupermercado.common.domain.model;

import br.com.sistemadesupermercado.application.util.Constants;
import br.com.sistemadesupermercado.application.util.Util;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = Constants.SCHEMA_COMMON, name = "usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Dado obrigatório")
    @Size(max = 20, message = "O tamano máximo para o login é de 20 caracteres")
    @Column(length = 20, unique = true, name = "login")
    private String login;

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Todo usuário precisa estar relacionado a uma pessoa")
    @JoinColumn(nullable = false, name = "pessoa_id", foreignKey = @ForeignKey(name = "fk_pessoa_id"))
    private Pessoa pessoa;

    @OneToMany
    @JoinTable(name = "usuario_perfil",
            joinColumns = {@JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "fk_usuario_id"))},
            inverseJoinColumns = {@JoinColumn(name = "perfil_id", foreignKey = @ForeignKey(name = "fk_perfil_id"))}
    )
    private Set<Perfil> perfis;

    @NotEmpty(message = "Todo usuário necessita de uma senha")
    @Column(name = "senha")
    private String senha;

    @Column(name = "ativo")
    private boolean ativo = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_inclusao", nullable = false)
    private Date dataInclusao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_remocao")
    private Date dataRemocao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_ultimo_login")
    private Date dataUltimoLogin;

    @Transient
    private String senhaTemporaria;

    public Usuario() {
        this.dataInclusao = new Date();
        this.perfis = new HashSet<>();
    }

    public Usuario(String login, String senha) {
        this.dataInclusao = new Date();
        this.login = login;
        this.senha = senha;
        this.perfis = new HashSet<>();
    }

    public Usuario(Pessoa pessoa) {
        this.dataInclusao = new Date();
        this.pessoa = pessoa;
        this.login = Util.removeMaskCNPJ(pessoa.getCodigo());
        this.perfis = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(Set<Perfil> perfis) {
        this.perfis = perfis;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Date getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(Date dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public Date getDataUltimoLogin() {
        return dataUltimoLogin;
    }

    public String getDateTimeUltimoLogin() {
        SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyyy 'as' HH:mm:ss");
        return formatData.format(dataUltimoLogin);
    }

    public void setDataUltimoLogin(Date dataUltimoLogin) {
        this.dataUltimoLogin = dataUltimoLogin;
    }

    public Date getDataRemocao() {
        return dataRemocao;
    }

    public void setSenhaTemporaria(String senhaTemporaria) {
        this.senhaTemporaria = senhaTemporaria;
    }

    public String getSenhaTemporaria() {
        return senhaTemporaria;
    }

    public void setDataRemocao(Date dataRemocao) {
        this.dataRemocao = dataRemocao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        return login != null ? login.equals(usuario.login) : usuario.login == null;
    }

    @Override
    public int hashCode() {
        return login != null ? login.hashCode() : 0;
    }
}
