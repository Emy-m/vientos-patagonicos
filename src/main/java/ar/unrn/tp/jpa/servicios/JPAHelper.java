package ar.unrn.tp.jpa.servicios;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAHelper {
    private static final EntityManagerFactory emf = buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory() {
        try {
            return Persistence.createEntityManagerFactory("jpa-objectdb");
        } catch (Throwable ex) {
            throw new RuntimeException("Error al crear la factoria de JPA");
        }
    }

    public static EntityManagerFactory getJPAFactory() {
        return emf;
    }
}
