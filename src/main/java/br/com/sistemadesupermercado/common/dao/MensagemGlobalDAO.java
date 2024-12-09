package br.com.sistemadesupermercado.common.dao;

import br.com.sistemadesupermercado.common.domain.model.MensagemGlobal;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.Serializable;
import java.util.List;


public class MensagemGlobalDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private EntityManager em;

    @Inject
    public MensagemGlobalDAO(EntityManager em) {
        this.em = em;
    }

    public MensagemGlobal save(MensagemGlobal mensagem) {
        if (mensagem.getId() != null) {
            em.merge(mensagem);
        } else {
            em.persist(mensagem);
        }
        return mensagem;
    }

    public MensagemGlobal loadMensagem(MensagemGlobal mensagem)
            throws NoResultException {
        return em.find(MensagemGlobal.class, mensagem.getId());
    }


    public List<MensagemGlobal> listarMensagens() {
        return em
                .createQuery("SELECT m FROM MensagemGlobal m " +
                        "JOIN FETCH m.usuario u " +
                        "JOIN FETCH u.pessoa p " +
                        "WHERE m.dataRemocao IS NULL " +
                        "ORDER BY m.dataInclusao DESC", MensagemGlobal.class)
                .getResultList();
    }

    public List<MensagemGlobal> listarMensagens(boolean global) {
        return em
                .createQuery("SELECT m FROM MensagemGlobal m " +
                        "JOIN FETCH m.usuario u " +
                        "JOIN FETCH u.pessoa p " +
                        "WHERE m.global = :global AND m.dataRemocao IS NULL " +
                        "ORDER BY m.dataInclusao DESC", MensagemGlobal.class)
                .setParameter("global", global)
                .setMaxResults(10)
                .getResultList();
    }
}
