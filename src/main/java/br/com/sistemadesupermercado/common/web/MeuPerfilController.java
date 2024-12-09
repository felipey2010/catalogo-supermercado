package br.com.sistemadesupermercado.common.web;

import br.com.sistemadesupermercado.common.business.PaisBusiness;
import br.com.sistemadesupermercado.common.business.PessoaBusiness;
import br.com.sistemadesupermercado.common.business.UsuarioBusiness;
import br.com.sistemadesupermercado.common.domain.enums.Genero;
import br.com.sistemadesupermercado.common.domain.model.Pais;
import br.com.sistemadesupermercado.common.domain.model.Pessoa;
import br.com.sistemadesupermercado.common.domain.model.Telefone;
import br.com.sistemadesupermercado.common.domain.security.UsuarioLogado;
import br.com.sistemadesupermercado.common.domain.security.UsuarioSistema;
import br.com.sistemadesupermercado.common.exceptions.BusinessException;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

@ManagedBean
@ViewScoped
public class MeuPerfilController implements Serializable {

    @Inject
    @UsuarioLogado
    private UsuarioSistema usuarioLogado;

    @Inject
    private PaisBusiness paisBusiness;

    @Inject
    private UsuarioBusiness usuarioBusiness;

    @Inject
    private PessoaBusiness pessoaBusiness;

    private Pessoa pessoa;

    private List<Pais> paisList;

    private boolean desabilitado;

    private boolean desabilitaEndereco;

    private Telefone telefone;

    private String senhaAtual;

    private String senhaNova;

    private String senhaConfirmar;

    private boolean btnRedefinirSenhaDesabilitado;

    private int buttonSelected;

    @PostConstruct
    public void init() {
        this.telefone = new Telefone();
        this.desabilitarCampos();
        this.buttonSelected = 1;
        this.inicializarCamposDeSenha();
        this.pessoa = this.pessoaBusiness.findPessoaPorCPF(
                this.usuarioLogado.getUsuario().getPessoa().getCodigo()
        );
        this.paisList = this.paisBusiness.listarPaises();
    }

    public void inicializarCamposDeSenha() {
        this.senhaAtual="";
        this.senhaNova = "";
        this.senhaConfirmar = "";
        this.btnRedefinirSenhaDesabilitado = true;
    }

    public void updatePage(int number) {
        try {
            this.buttonSelected = number;
        } catch (Exception e) {
            Messages.create(e.getMessage()).error().add();
        }
    }

    public void salvarPessoa() {
        try {
            this.pessoa.setNacionalidade(this.paisBusiness.findById(pessoa.getNacionalidade().getCodigoPais()));
            this.pessoaBusiness.save(this.pessoa);
            Messages.create("Funcionário salvo com sucesso.").add();
            this.init();
        } catch (BusinessException e) {
            Messages.create(e.getMessage()).error().add();
        }
    }

    public void editar() {
        this.desabilitado = !desabilitado;
    }

    public void limparTela() {
        init();
    }

    public void desabilitarCampos() {
        this.desabilitado = true;
        this.desabilitaEndereco = true;
    }

    public void redefinirSenha() {
        try {
            this.usuarioBusiness.alterarSenha(this.pessoa, this.senhaAtual, this.senhaNova, this.senhaConfirmar);
            Messages.create("Senha alterada com sucesso.").add();
            String contextPath = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
            Faces.redirect(contextPath + "/logout.xhtml");
        } catch (NoSuchAlgorithmException e) {
            Messages.create("Não foi possível alterar a senha.").error().add();
        }
            catch (BusinessException | IOException e) {
            Messages.create(e.getMessage()).warn().add();
        }
    }

    public void excluirConta() {
        try {
            boolean result = this.pessoaBusiness.excluirPessoa(this.pessoa);

            if(result) {
                String contextPath = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
                Faces.redirect(contextPath + "/logout.xhtml");
            }
            else {
                Messages.create("Não foi possível excluir sua conta. Entre em contato com a SEED").error().add();
            }
        } catch (Exception e) {
            Messages.create(e.getMessage()).error().add();
        }
    }

    public void deleteTelefone() {
        this.pessoa.deleteTelefone(telefone);
        this.telefone = new Telefone();
    }

    public void checkCamposDeSenhaListener() {
        this.btnRedefinirSenhaDesabilitado = this.senhaAtual.isEmpty() || this.senhaNova.isEmpty() || this.senhaConfirmar.isEmpty() ||
                !this.senhaNova.equals(this.senhaConfirmar);
    }

    public List<Genero> getGeneros() {
        return Arrays.asList(Genero.values());
    }

    public List<Pais> getPaisList() {
        return paisList;
    }

    public void setPaisList(List<Pais> paisList) {
        this.paisList = paisList;
    }

    public boolean isDesabilitado() {
        return desabilitado;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public boolean isDesabilitaEndereco() {
        return desabilitaEndereco;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getSenhaNova() {
        return senhaNova;
    }

    public void setSenhaNova(String senhaNova) {
        this.senhaNova = senhaNova;
    }

    public String getSenhaConfirmar() {
        return senhaConfirmar;
    }

    public void setSenhaConfirmar(String senhaConfirmar) {
        this.senhaConfirmar = senhaConfirmar;
    }

    public boolean isBtnRedefinirSenhaDesabilitado() {
        return btnRedefinirSenhaDesabilitado;
    }

    public int getButtonSelected() {
        return buttonSelected;
    }

    public void setButtonSelected(int buttonSelected) {
        this.buttonSelected = buttonSelected;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
