package br.com.sistemadesupermercado.application.core.cdi;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerProducer {

    @PersistenceContext
    @Produces
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }
}