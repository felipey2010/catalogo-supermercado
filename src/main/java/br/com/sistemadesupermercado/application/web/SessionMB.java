package br.com.sistemadesupermercado.application.web;

import br.com.sistemadesupermercado.application.exceptions.ApplicationInicializationException;
import br.com.sistemadesupermercado.common.domain.security.UsuarioLogado;
import br.com.sistemadesupermercado.common.domain.security.UsuarioSistema;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * MANAGED BEAN DE SESSAO DO USUARIO. ESSE MENAGEDBEAN PERMITE
 * COMPARTILHAR O USUARIO DURANTE O FLUXO DE NAVEGACAO UMA ACAO
 * SEGUNDARIA DESSE MANAGEDBEAN AO FAZER LOG DE ACESSO, TODA VEZ QUE O
 * USUÁRIO REALIZAR LOGIN
 */
@ManagedBean
@SessionScoped
public class SessionMB implements Serializable {
    @Inject
    @UsuarioLogado
    private UsuarioSistema usuario;

    @PostConstruct
    public void init() throws ApplicationInicializationException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        if (this.usuario != null) {
            String login = usuario.getUsuario().getLogin();
            HttpSession session = (HttpSession) context.getSession(true);
            session.setAttribute("usuario", login);
        } else {
            throw new ApplicationInicializationException("Não foi possível carregar o usuário logado");
        }
    }

    public UsuarioSistema getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioSistema usuario) {
        this.usuario = usuario;
    }

    public String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null) ip = request.getHeader("X_FORWARDED_FOR");
        if (ip == null) ip = request.getRemoteAddr();
        return ip;
    }

}
