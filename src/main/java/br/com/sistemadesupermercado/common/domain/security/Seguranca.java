package br.com.sistemadesupermercado.common.domain.security;

import br.com.sistemadesupermercado.application.core.cdi.Loggado;
import br.com.sistemadesupermercado.common.domain.model.Pessoa;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;

/**
 * This section manages the access of users.
 * On changing any of the accesses, make sure to update the SecurityConfig at common/domain/security
 */
@ManagedBean
@SessionScoped
public class Seguranca implements Serializable {

    private ExternalContext externalContext = usuarioSessao();

    @Loggado
    @Produces
    public Pessoa getPessoa() {
        UsuarioSistema usuario = getUsuarioLogado();
        return usuario != null ? usuario.getUsuario().getPessoa() : null;
    }

    @Produces
    @UsuarioLogado
    public UsuarioSistema getUsuarioLogado() {
        UsuarioSistema usuario = null;

        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext
                .getCurrentInstance().getExternalContext().getUserPrincipal();

        if (auth != null && auth.getPrincipal() != null) {
            usuario = (UsuarioSistema) auth.getPrincipal();
        }

        return usuario;
    }

    public ExternalContext usuarioSessao() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getExternalContext();
    }

    /**
     * Roles
     */
    public boolean isRoleMensagemGlobal() {
        return externalContext.isUserInRole("MENSAGEM_GLOBAL");
    }

    public boolean isRolePerfisAcesso() {
        return externalContext.isUserInRole("PERFIS_DE_ACESSO");
    }

    public boolean isRoleUsuario() {
        return externalContext.isUserInRole("USUARIO");
    }
}