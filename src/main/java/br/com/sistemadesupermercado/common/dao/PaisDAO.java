package br.com.sistemadesupermercado.common.dao;

import br.com.sistemadesupermercado.common.domain.model.Pais;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.Serializable;
import java.util.List;

public class PaisDAO implements Serializable{

    private EntityManager em;

    @Inject
    public PaisDAO(EntityManager em) {
        this.em = em;
    }


    public List<Pais> listarPaises() {
        return em
                .createQuery(
                        "SELECT pais from Pais pais " +
                                "LEFT JOIN PessoaFisica pf ON pf.nacionalidade = pais " +
                                "GROUP BY pais " +
                                "ORDER BY COUNT(pf) DESC, pais.nome ",
                        Pais.class)
                .getResultList();
    }
    
    public Pais findById(Integer codPais) {
    	return em.find(Pais.class, codPais);
    }

    public Pais carregarPais(Pais pais){
        try {
            return em.find(Pais.class,pais.getCodigoPais());
        } catch (NoResultException e) {
            return null;
        }
    }
    

}
