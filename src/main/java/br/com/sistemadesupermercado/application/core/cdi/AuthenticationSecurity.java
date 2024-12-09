package br.com.sistemadesupermercado.application.core.cdi;

import br.com.sistemadesupermercado.common.dao.UsuarioDAO;
import br.com.sistemadesupermercado.common.domain.model.Usuario;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;

@Stateless
public class AuthenticationSecurity implements Serializable {

    @Inject
    private UsuarioDAO dao;

    public Usuario searchByLogin(String login) {
        return dao.searchByLogin(login);
    }
}