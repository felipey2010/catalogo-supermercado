package br.com.sistemadesupermercado.common.dao;


import br.com.sistemadesupermercado.common.domain.model.Usuario;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.Serializable;
import java.util.List;


public class UsuarioDAO implements Serializable {

    private EntityManager em;

    @Inject
    public UsuarioDAO(EntityManager em) {
        this.em = em;
    }

    public Usuario save(Usuario usuario) {
        if (usuario.getId() != null) {
            return em.merge(usuario);
        } else {
            em.persist(usuario);
        }
        return usuario;
    }

    public Usuario update(Usuario usuario) {
        return em.merge(usuario);
    }

    public Usuario searchByLogin(String login) {
        try {
            return em.createQuery(
                            "SELECT u FROM Usuario u WHERE u.login = :login" +
                                    " AND u.dataRemocao IS NULL", Usuario.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Usuario findUsuarioPorCPF(String cpf) {
        String paramentro = cpf.replaceAll("\\D", "");
        try {
            return em.createQuery(
                            "SELECT u FROM Usuario u " +
                                    "JOIN FETCH u.pessoa f " +
                                    "WHERE f.codigo = :cpf AND u.dataRemocao IS NULL",
                            Usuario.class)
                    .setParameter("cpf", paramentro)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Usuario> findTodosUsuariosAtivos() {
        try {
            return em.createQuery(
                            "SELECT u FROM Usuario u " +
                                    "JOIN FETCH u.pessoa p " +
                                    "WHERE u.dataRemocao IS NULL AND p.dataRemocao IS NULL " +
                                    "ORDER BY p.nome",
                            Usuario.class)
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public Usuario searchByLoginAtivo(String login) {
        try {
            return em.createQuery(
                            "SELECT u FROM Usuario u " +
                                    "JOIN FETCH u.pessoa p " +
                                    "WHERE u.login = :login AND u.dataRemocao IS NULL" +
                                    " AND u.ativo IS TRUE AND p.dataRemocao IS NULL", Usuario.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}