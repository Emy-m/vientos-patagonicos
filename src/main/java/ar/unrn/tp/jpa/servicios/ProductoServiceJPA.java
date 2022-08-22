package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Producto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProductoServiceJPA implements ProductoService {

    @Override
    public void crearProducto(String codigo, String descripcion, String marca, float precio, Long IdCategoría) {
        EntityManagerFactory emf = JPAHelper.getJPAFactory();
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
    public void modificarProducto(Long idProducto, String descripcion, String marca, float precio, Long IdCategoría) {
        EntityManagerFactory emf = JPAHelper.getJPAFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            //hacer algo con em
            Categoria categoria = em.find(Categoria.class, IdCategoría);
            Producto producto = em.getReference(Producto.class, idProducto);
            producto.setCategoria(categoria);
            producto.setDescripcion(descripcion);
            producto.setMarca(marca);
            producto.setPrecio(precio);

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
    public List listarProductos() {
        EntityManagerFactory emf = JPAHelper.getJPAFactory();
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
