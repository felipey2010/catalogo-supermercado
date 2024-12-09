package br.com.sistemadesupermercado.common.dao;

import br.com.sistemadesupermercado.common.domain.model.Usuario;
import br.com.sistemadesupermercado.common.domain.model.Acesso;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.Serializable;
import java.util.List;

public class AcessoDAO implements Serializable{

    private EntityManager em;

    @Inject
    public AcessoDAO(EntityManager em) {
        this.em = em;
    }

    public Acesso save(Acesso acesso) {
        if(acesso.getId() != null) {
            em.merge(acesso);
        } else {
            em.persist(acesso);
        }
        return acesso;
    }

    public List<Acesso> listarAcessos() {
        return em
                .createQuery(
                        "SELECT a from Acesso a WHERE a.dataRemocao IS NULL ORDER BY a.nome",
                        Acesso.class)
                .getResultList();
    }

    public Acesso load(Acesso acesso){
        try {
            return em.find(Acesso.class, acesso.getId());
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Acesso> listarPorUsuario(Usuario usuario) {
        try {
            return em.createNativeQuery("select distinct a.* from acesso a " +
                    "join perfil_acesso pa on a.id = pa.acesso_id " +
                    "join perfil p on pa.perfil_id = p.id " +
                    "join usuario_perfil up on p.id = up.perfil_id " +
                    "join usuario u on up.usuario_id = u.login " +
                    "WHERE u.login = :login " +
                    "order by a.id", Acesso.class)
                    .setParameter("login", usuario.getLogin()).getResultList();
        } catch (NoResultException e){
            return null;
        }
    }
}
