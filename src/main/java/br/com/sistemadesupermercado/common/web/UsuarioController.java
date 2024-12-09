package br.com.sistemadesupermercado.common.web;

import br.com.sistemadesupermercado.application.util.Util;
import br.com.sistemadesupermercado.common.business.PaisBusiness;
import br.com.sistemadesupermercado.common.business.PerfilBusiness;
import br.com.sistemadesupermercado.common.business.UsuarioBusiness;
import br.com.sistemadesupermercado.common.domain.enums.Genero;
import br.com.sistemadesupermercado.common.domain.model.*;
import br.com.sistemadesupermercado.common.domain.security.UsuarioLogado;
import br.com.sistemadesupermercado.common.domain.security.UsuarioSistema;
import br.com.sistemadesupermercado.common.exceptions.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Classe para interação e manipulação de objetos de {@link Usuario} na view
 */
@ManagedBean
@ViewScoped
public class UsuarioController implements Serializable {

    @Inject
    private UsuarioBusiness usuarioBusiness;

    @Inject
    private PerfilBusiness perfilBusiness;

    @Inject
    private PaisBusiness paisBusiness;

    @Inject
    @UsuarioLogado
    private UsuarioSistema usuarioLogado;

    private boolean usrGerado;

    private Usuario usuario;

    private Usuario usuarioAuxiliar;

    private List<Pais> paisList;

    private boolean desabilitaEndereco;

    private Telefone telefone;

    private Pessoa pessoa;

    private List<Usuario> listaDeUsuarios;

    private List<Usuario> listaDeUsuariosFiltrado;

    private String cpfPesquisa;

    private List<Perfil> perfisDisponiveis;

    private boolean desabilitado;

    private boolean btnNovoDesabilitado;

    private boolean cpfDesabilitado;

    private boolean fieldCompleted;

    private boolean btnCredenciaisDesabilitado;

    private int pageState;

    private Integer codigoPais;

    @PostConstruct
    public void init() {
        inicializarCampos();
        carregarDadosIniciais();
    }

    private void carregarDadosIniciais() {
        try {
            this.paisList = paisBusiness.listarPaises();
            this.perfisDisponiveis = perfilBusiness.listarPerfisAtivos();
            carregarUsuarios();
        } catch (Exception e) {
            Messages.create("Error initializing data: " + e.getMessage()).warn().add();
        }
    }

    public void carregarUsuarios() {
        try {
            this.listaDeUsuarios = this.usuarioBusiness.listarTodosUsuarios(this.usuarioLogado.getUsuario());
        } catch (Exception e) {
            Messages.create(e.getMessage()).warn().add();
        }
    }

    private void inicializarCampos() {
        this.pageState = 0;
        this.desabilitado = false;
        this.usuario = null;
        this.usuarioAuxiliar = null;
        this.cpfPesquisa = "";
        this.btnNovoDesabilitado = true;
        this.usrGerado = false;
        this.cpfDesabilitado = true;
        this.pessoa = null;
        this.telefone = new Telefone();
        this.fieldCompleted = false;
        this.codigoPais = null;
        this.btnCredenciaisDesabilitado = true;
    }

    public void detalhar() {
        try {
            initializeUserDetails(true, true);
            this.btnCredenciaisDesabilitado = true;
        } catch (Exception e) {
            Messages.create(e.getMessage()).warn().add();
        }
    }

    public void editarUsuario() {
        try {
            initializeUserDetails(false, false);
            this.checkFieldsListener();
        } catch (Exception e) {
            Messages.create(e.getMessage()).warn().add();
        }
    }

    private void initializeUserDetails(boolean desabilitado, boolean cpfDesabilitado) {
        this.pessoa = this.usuario.getPessoa();
        this.codigoPais = pessoa.getNacionalidade().getCodigoPais();
        this.pageState = 1;
        this.desabilitado = desabilitado;
        this.cpfDesabilitado = cpfDesabilitado;
        this.usrGerado = true;
    }

    public void voltarTelaPrincipal() {
        this.init();
    }

    public void ativarPesquisaDeUsuarios() {
        this.pageState = 1;
        this.usuario = null;
    }

    public void criarNovoUsuario() {
        try {
            this.desabilitado = false;
            this.telefone = new Telefone();
            this.desabilitaEndereco = true;
            this.cpfDesabilitado = true;
            this.pessoa = new Pessoa();
            this.pessoa.setCodigo(Util.removeMaskCPF(this.cpfPesquisa));
        } catch (Exception e) {
            Messages.create(e.getMessage()).warn().add();
        }
    }

    public void buscarUsuario() {
        if (cpfPesquisa.isEmpty()) {
            Messages.create("Por favor, informe um CPF válido do servidor").error().add();
            return;
        }
        try {
            this.usuario = this.usuarioBusiness.buscarUsuarioPorCPF(cpfPesquisa);
            if (this.usuario == null) {
                Messages.create("Usuário não encontrado. Por favor, cadastre.").add();
                this.usuario = null;
                this.btnNovoDesabilitado = false;
            } else {
                Messages.create("Já existe um usuário cadastrado com o mesmo CPF").warn().add();
                this.cpfPesquisa = "";
            }
        } catch (Exception e) {
            Messages.create(e.getMessage()).warn().add();
        }
    }

    public void gerarUsuario() {
        try {
            this.usrGerado = true;
            this.usuario = new Usuario();
            this.usuario.setLogin(this.pessoa.getCodigo());
            this.usuario.setPessoa(this.pessoa);
            this.usuario = usuarioBusiness.preparaNovoUsuario(this.usuario);
            Messages.create("Credenciais geradas").add();
        } catch (BusinessException e) {
            this.usrGerado = false;
            Messages.create(e.getMessage()).error().add();
        }
    }

    public void salvarNovoUsuario() {
        this.desabilitado = true;
        try {
            if (this.usrGerado) {
                this.pessoa.setNacionalidade(this.paisBusiness.findById(this.codigoPais));
                this.usuario.setPessoa(this.pessoa);
            }
            this.usuarioBusiness.save(this.usuario);
            Messages.create("Usuário salvo").add();
        } catch (BusinessException e) {
            Messages.create(e.getMessage()).error().add();
        }

        this.init();
    }

    public void atualizarUsuario() {
        this.desabilitado = true;
        try {
            this.usuario.setPessoa(this.pessoa);
            this.usuarioBusiness.save(this.usuario);
            this.checkUser();
            Messages.create("Usuário atualizado com êxito").add();
            this.init();
        } catch (BusinessException | IOException e) {
            this.desabilitado = false;
            Messages.create(e.getMessage()).error().add();
        }
    }

    private void checkUser() throws IOException {
        if (usuarioLogado.getUsuario().equals(this.usuario)) {
            Messages.create("Seu acesso foi alterado. Por favor, faça login de novo").warn().add();
            String contextPath = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
            Faces.redirect(contextPath + "/logout.xhtml");
        }
    }

    public void addTelefone() {
        if (!this.telefone.getNumero().isEmpty()) {
            Telefone telefoneAdd = new Telefone();
            telefoneAdd.setNumero(this.telefone.getNumero());
            this.pessoa.addTelefone(telefoneAdd);
            this.telefone = new Telefone();
        }
    }

    public void deleteTelefone() {
        this.pessoa.deleteTelefone(telefone);
        this.telefone = new Telefone();
    }

    public void mudarEstadoUsuario() {
        try {
            this.usuarioBusiness.mudarEstadoUsuario(this.usuarioAuxiliar);
            Messages.create("Procedimento realizado com sucesso.").add();
            this.carregarUsuarios();
        } catch (Exception e) {
            Messages.create(e.getMessage()).warn().add();
        }
    }

    public void redefinirSenha() {
        try {
            this.usuarioBusiness.redefinirSenha(this.usuarioAuxiliar);
            this.init();
            Messages.create("Senha do usuário redefinida com sucesso.").add();
        } catch (Exception e) {
            Messages.create(e.getMessage()).error().add();
        }
    }

    public void checkFieldsListener() {
        this.fieldCompleted = false;
        if (this.pessoa != null) {
            try {
                this.fieldCompleted = StringUtils.isNotEmpty(this.pessoa.getCodigo()) && StringUtils.isNotEmpty(this.pessoa.getEmail());
            } catch (Exception e) {
                this.fieldCompleted = false;
            }
        }
    }

    public void countryListener() {
        this.pessoa.setNacionalidade(this.paisBusiness.findById(this.codigoPais));
    }

    public List<Genero> getGeneros() {
        return Arrays.asList(Genero.values());
    }

    public List<Perfil> getPerfisDisponiveis() {
        return perfisDisponiveis;
    }

    public void setPerfisDisponiveis(List<Perfil> perfisDisponiveis) {
        this.perfisDisponiveis = perfisDisponiveis;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCpfPesquisa() {
        return cpfPesquisa;
    }

    public void setCpfPesquisa(String cpfPesquisa) {
        this.cpfPesquisa = cpfPesquisa.trim();
    }

    public boolean isUsrGerado() {
        return usrGerado;
    }

    public void setUsrGerado(boolean usrGerado) {
        this.usrGerado = usrGerado;
    }

    public List<Usuario> getListaDeUsuarios() {
        return listaDeUsuarios;
    }

    public void setListaDeUsuarios(List<Usuario> listaDeUsuarios) {
        this.listaDeUsuarios = listaDeUsuarios;
    }

    public List<Usuario> getListaDeUsuariosFiltrado() {
        return listaDeUsuariosFiltrado;
    }

    public void setListaDeUsuariosFiltrado(List<Usuario> listaDeUsuariosFiltrado) {
        this.listaDeUsuariosFiltrado = listaDeUsuariosFiltrado;
    }

    public boolean isDesabilitado() {
        return desabilitado;
    }

    public void setDesabilitado(boolean desabilitado) {
        this.desabilitado = desabilitado;
    }

    public Usuario getUsuarioAuxiliar() {
        return usuarioAuxiliar;
    }

    public void setUsuarioAuxiliar(Usuario usuarioAuxiliar) {
        this.usuarioAuxiliar = usuarioAuxiliar;
    }

    public int getPageState() {
        return pageState;
    }

    public void setPageState(int pageState) {
        this.pageState = pageState;
    }

    public boolean isBtnNovoDesabilitado() {
        return btnNovoDesabilitado;
    }

    public void setBtnNovoDesabilitado(boolean btnNovoDesabilitado) {
        this.btnNovoDesabilitado = btnNovoDesabilitado;
    }

    public List<Pais> getPaisList() {
        return paisList;
    }

    public void setPaisList(List<Pais> paisList) {
        this.paisList = paisList;
    }

    public boolean isDesabilitaEndereco() {
        return desabilitaEndereco;
    }

    public void setDesabilitaEndereco(boolean desabilitaEndereco) {
        this.desabilitaEndereco = desabilitaEndereco;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public boolean isCpfDesabilitado() {
        return cpfDesabilitado;
    }

    public void setCpfDesabilitado(boolean cpfDesabilitado) {
        this.cpfDesabilitado = cpfDesabilitado;
    }

    public boolean isFieldCompleted() {
        return fieldCompleted;
    }

    public Integer getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(Integer codigoPais) {
        this.codigoPais = codigoPais;
    }

    public boolean isBtnCredenciaisDesabilitado() {
        return btnCredenciaisDesabilitado;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}