package br.com.sistemadesupermercado.common.web;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.omnifaces.util.Messages;

import br.com.sistemadesupermercado.common.business.MensagemGlobalBusiness;
import br.com.sistemadesupermercado.common.domain.model.MensagemGlobal;
import br.com.sistemadesupermercado.common.domain.security.UsuarioLogado;
import br.com.sistemadesupermercado.common.domain.security.UsuarioSistema;

@ManagedBean
@ViewScoped
public class MensagemGlobalController implements Serializable {

    @EJB
    private MensagemGlobalBusiness mensagemGlobalBusiness;

    @Inject
    @UsuarioLogado
    private UsuarioSistema usuarioSistema;

    private MensagemGlobal mensagem;

    private MensagemGlobal mensagemRemocao;

    private List<MensagemGlobal> mensagens;

    private List<MensagemGlobal> listaFiltrada;

    private boolean desabilitado;

    @PostConstruct
    public void init(){
        this.mensagens = this.mensagemGlobalBusiness.listarMensagens();
        this.mensagem = this.mensagemRemocao = null;
        this.desabilitado = false;
    }

    public void criarNovaMensagem(){
        this.desabilitado = false;
        this.mensagem = new MensagemGlobal(this.usuarioSistema.getUsuario());
        this.mensagem.setGlobal(true);
    }

//    public void gerarPDFAnexo() {
//        if (this.mensagem.getArquivo().length > 0) {
//            FacesUtil.setMB("pdf", this.mensagem.getArquivo());
//        }
//    }

    public void salvarMensagem(){
        try {
            this.mensagemGlobalBusiness.save(mensagem);
            this.mensagem = null;
            this.mensagens = this.mensagemGlobalBusiness.listarMensagens();
            Messages.create("Mensagem salva com sucesso.").add();
        } catch (Exception e) {
            Messages.create(e.getMessage()).warn().add();
        }
    }

    public void excluirMensagem() {
        try {
            this.mensagemGlobalBusiness.excluir(this.mensagemRemocao);
            this.mensagemRemocao = null;
            this.mensagens = this.mensagemGlobalBusiness.listarMensagens();
            Messages.create("Mensagem excluída com sucesso.").add();
        } catch (Exception e) {
            Messages.create(e.getMessage()).warn().add();
        }
    }

    public void detalhar(){
        this.desabilitado = true;
    }

    public void editar () {
        this.desabilitado = false;
    }

    public void limparTela() {
        this.mensagem = null;
        desabilitado = false;
    }

    public MensagemGlobal getMensagem() {
        return mensagem;
    }

    public void setMensagem(MensagemGlobal mensagem) {
        this.mensagem = mensagem;
    }

    public List<MensagemGlobal> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<MensagemGlobal> mensagens) {
        this.mensagens = mensagens;
    }

    public boolean isDesabilitado() {
        return desabilitado;
    }

    public void setDesabilitado(boolean desabilitado) {
        this.desabilitado = desabilitado;
    }

    public List<MensagemGlobal> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(List<MensagemGlobal> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    public MensagemGlobal getMensagemRemocao() {
        return mensagemRemocao;
    }

    public void setMensagemRemocao(MensagemGlobal mensagemRemocao) {
        this.mensagemRemocao = mensagemRemocao;
    }
}
