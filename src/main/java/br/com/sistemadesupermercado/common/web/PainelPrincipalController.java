package br.com.sistemadesupermercado.common.web;

import br.com.sistemadesupermercado.common.business.MensagemGlobalBusiness;
import br.com.sistemadesupermercado.common.domain.model.MensagemGlobal;
import br.com.sistemadesupermercado.common.domain.security.UsuarioLogado;
import br.com.sistemadesupermercado.common.domain.security.UsuarioSistema;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean
@ViewScoped
public class PainelPrincipalController implements Serializable {

    @EJB
    private MensagemGlobalBusiness mensagemGlobalBusiness;

    @Inject
    @UsuarioLogado
    private UsuarioSistema usuarioSistema;

    private MensagemGlobal mensagem;

    private List<MensagemGlobal> mensagensGlobais;

    private MensagemGlobal mensagemGlobalSelecionada;

    private int DISPLAYED_MESSAGES_LIMIT = 10;

    @PostConstruct
    public void init(){
        try {
            this.mensagemGlobalSelecionada = null;
            this.mensagensGlobais = this.mensagemGlobalBusiness.listarMensagens(true).stream().limit(DISPLAYED_MESSAGES_LIMIT).collect(Collectors.toList());
        } catch (Exception e) {
            Messages.create(e.getMessage()).warn().add();
        }
    }

    public String checkMessageLength(String word, int length) {
        int min = Math.min(word.length(), length);
        String shortWord = word.trim().substring(0, min);

        return min == length ? shortWord + "..." : shortWord;
    }

    public Date getCurrentDate() {
        return new Date();
    }

    public MensagemGlobal getMensagem() {
        return mensagem;
    }

    public void setMensagem(MensagemGlobal mensagem) {
        this.mensagem = mensagem;
    }

    public List<MensagemGlobal> getMensagensGlobais() {
        return mensagensGlobais;
    }

    public void setMensagensGlobais(List<MensagemGlobal> mensagensGlobais) {
        this.mensagensGlobais = mensagensGlobais;
    }

    public MensagemGlobal getMensagemGlobalSelecionada() {
        return mensagemGlobalSelecionada;
    }

    public void setMensagemGlobalSelecionada(MensagemGlobal mensagemGlobalSelecionada) {
        this.mensagemGlobalSelecionada = mensagemGlobalSelecionada;
    }
}
