package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.CategoriaService;
import ar.unrn.tp.modelo.Categoria;

import javax.persistence.*;
import java.util.List;

public class CategoriaServiceJPA implements CategoriaService {
    private String persistenceUnit;

    public CategoriaServiceJPA(String persistenceUnit) {
        this.persistenceUnit = persistenceUnit;
    }

    @Override
    public void crearCategoria(String nombre) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            //hacer algo con em
            Categoria c = new Categoria(nombre);
            em.persist(c);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Override
    public List<Categoria> categorias() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        EntityManager em = emf.createEntityManager();
        try {
            //hacer algo con em
            TypedQuery<Categoria> categoriaQuery = em.createQuery("select cats from Categoria cats", Categoria.class);
            return categoriaQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Override
    public void modificarCategoria(Long idCategoria, String nombre) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            //hacer algo con em
            Categoria c = em.getReference(Categoria.class, idCategoria);
            c.setNombre(nombre);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }
}
