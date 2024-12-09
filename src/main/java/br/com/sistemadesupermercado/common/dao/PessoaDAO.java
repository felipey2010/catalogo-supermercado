package br.com.sistemadesupermercado.common.dao;

import br.com.sistemadesupermercado.common.domain.model.Pessoa;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.Serializable;

public class PessoaDAO implements Serializable {

    private EntityManager em;

    @Inject
    public PessoaDAO(EntityManager em) {
        this.em = em;
    }

    public Pessoa save(Pessoa pessoa) {
        if(pessoa.getId() != null) {
            em.merge(pessoa);
        } else {
            em.persist(pessoa);
        }
        return pessoa;
    }

    public boolean excluir(Pessoa pessoa) throws Exception {
        try {
            em.createQuery(
                    "DELETE FROM Pessoa p WHERE p.id = :id")
                    .setParameter("id", pessoa.getId())
                    .executeUpdate();
            return true;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public Pessoa findPessoaByCPF(String cpf) {
        try {
            return em.createQuery("SELECT p from Pessoa p WHERE p.codigo = :cpf ", Pessoa.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}