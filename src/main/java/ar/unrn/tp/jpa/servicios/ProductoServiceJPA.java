package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Producto;

import javax.persistence.*;
import java.util.List;

public class ProductoServiceJPA implements ProductoService {
    private String persistenceUnit;

    public ProductoServiceJPA(String persistenceUnit) {
        this.persistenceUnit = persistenceUnit;
    }

    @Override
    public void crearProducto(String codigo, String descripcion, String marca, float precio, Long IdCategoría) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            //hacer algo con em
            Categoria categoria = em.find(Categoria.class, IdCategoría);
            Producto producto = new Producto(codigo, descripcion, marca, categoria, precio);
            em.persist(producto);

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
    public void modificarProducto(Long idProducto, String codigo, String descripcion, String marca, float precio, Long IdCategoría, Long version) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            //hacer algo con em
            Categoria categoria = em.find(Categoria.class, IdCategoría);
            Producto producto = new Producto(codigo, descripcion, marca, categoria, precio);
            producto.setIdProducto(idProducto);
            producto.setVersion(version);
            em.merge(producto);

            tx.commit();
        } catch (OptimisticLockException e) {
            throw new RuntimeException("La version del producto que quiere editar ya no es la ultima", e);
        }
        catch (Exception e) {
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
    public List<Producto> listarProductos() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        EntityManager em = emf.createEntityManager();
        try {
            //hacer algo con em
            TypedQuery<Producto> prods = em.createQuery("select prods from Producto prods", Producto.class);
            return prods.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }
}
