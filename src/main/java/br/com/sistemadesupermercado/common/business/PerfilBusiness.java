package br.com.sistemadesupermercado.common.business;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.sistemadesupermercado.common.dao.PerfilDAO;
import br.com.sistemadesupermercado.common.domain.model.Perfil;

/**
 * Classe para gerenciamento de regras de negócio para {@link Perfil}
 */
@Stateless
public class PerfilBusiness {

    /**
     * Instancia de comunicação com o banco de dados para {@link Perfil}
     */
    @Inject
    private PerfilDAO perfilDAO;

    /**
     * Persiste o {@link Perfil} no banco de dados. Se {@link Perfil#getDataInclusao} retornar nulo
     * é settado new {@link Date()}
     *
     * @param perfil Perfil que será persistido no banco
     * @return perfil previamente persistido
     */
    public Perfil save(Perfil perfil) {
        if(null == perfil.getDataInclusao()) {
            perfil.setDataInclusao(new Date());
        }

        return perfilDAO.save(perfil);
    }

    /**
     * Retorna todos os perfis ativos existentes no banco
     *
     * @return {@link List}<{@link Perfil}> de perfis ativos
     */
    public List<Perfil> listarPerfisAtivos() {
        return perfilDAO.listarPerfisAtivos();
    }

//    public List<Perfil> listarPerfisAtivosByTipoAutorizacao(TipoAutorizacao tipoPerfil){
//        return listarPerfisAtivos().stream().filter(p -> {
//            if(p.getTipoPerfil()!= null)
//                return p.getTipoPerfil().ordinal() >= tipoPerfil.ordinal();
//            else
//                return false;
//        }).collect(Collectors.toList());
//    }

    private List<Perfil> listarTodosPerfis() {
        return perfilDAO.listarTodosPerfis();
    }

    public Perfil load(Perfil perfil) {
        return perfilDAO.load(perfil);
    }

    public Perfil togglePerfilAtivo(Perfil perfil) {
        if(perfil.getDataRemocao() == null) { perfil.setDataRemocao(new Date()); }
        else { perfil.setDataRemocao(null); }
        return this.save(perfil);
    }
}