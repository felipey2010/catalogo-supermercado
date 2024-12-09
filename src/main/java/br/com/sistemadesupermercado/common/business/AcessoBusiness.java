package br.com.sistemadesupermercado.common.business;

import br.com.sistemadesupermercado.common.dao.AcessoDAO;
import br.com.sistemadesupermercado.common.domain.model.Acesso;
import br.com.sistemadesupermercado.common.domain.model.Usuario;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class AcessoBusiness {

    @Inject
    private AcessoDAO acessoDAO;

    public Acesso save(Acesso acesso) {
        return acessoDAO.save(acesso);
    }

    public List<Acesso> listarAcessos() {
        return acessoDAO.listarAcessos();
    }

    public Acesso load(Acesso acesso) {
        return acessoDAO.load(acesso);
    }

    public List<Acesso> listarPorUsuario(Usuario usuario) {
        return acessoDAO.listarPorUsuario(usuario);
    }
}
