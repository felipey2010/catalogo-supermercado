package br.com.sistemadesupermercado.common.dao;

import br.com.sistemadesupermercado.common.domain.model.Perfil;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

public class PerfilDAO implements Serializable{

    private EntityManager em;

    @Inject
    public PerfilDAO(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public Perfil save(Perfil perfil) {
        if(perfil.getId() != null) {
            em.merge(perfil);
        } else {
            em.persist(perfil);
        }
        return perfil;
    }

    public List<Perfil> listarPerfisAtivos() {
        return em.createQuery(
                        "SELECT p from Perfil p WHERE p.dataRemocao IS NULL ORDER BY p.id",
                        Perfil.class)
                .getResultList();
    }

    public List<Perfil> listarTodosPerfis() {
        return em.createQuery(
                        "SELECT p from Perfil p ORDER BY p.id",
                        Perfil.class)
                .getResultList();
    }

    public Perfil load(Perfil perfil){
        try {
            return em.find(Perfil.class, perfil.getId());
        } catch (NoResultException e) {
            return null;
        }
    }

//    public List<Perfil> listarPerfisAtivosByTipoPerfil(TipoAutorizacao tipoPerfil) {
//        try {
//            return em.createQuery("SELECT p FROM Perfil p " +
//                    "WHERE p.dataRemocao IS NULL " +
//                    "AND p.tipoPerfil = :tipoPerfil " +
//                    "ORDER BY p.nome ", Perfil.class)
//                    .setParameter("tipoPerfil", tipoPerfil)
//                    .getResultList();
//        } catch (NoResultException e){
//            return null;
//        }
//    }

//    public List<Perfil> listarPerfisAtivosByTipoPerfil(List<TipoAutorizacao> tipoPerfis) {
//        try{
//            return em.createQuery("SELECT p FROM Perfil p " +
//                    "WHERE p.dataRemocao IS NULL " +
//                    "AND p.tipoPerfil IN (:tipoPerfis) " +
//                    "ORDER BY p.nome ", Perfil.class)
//                    .setParameter("tipoPerfis", tipoPerfis)
//                    .getResultList();
//        } catch (NoResultException e){
//            return null;
//        }
//    }
}