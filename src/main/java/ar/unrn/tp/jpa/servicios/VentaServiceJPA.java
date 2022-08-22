package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class VentaServiceJPA implements VentaService {
    @Override
    public void realizarVenta(Long idCliente, List<Long> productos, Long idTarjeta) {
        EntityManagerFactory emf = JPAHelper.getJPAFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            //hacer algo con em
            Cliente cliente = em.find(Cliente.class, idCliente);
            AbstractCobrable tarjeta = cliente.getTarjeta(idTarjeta);

            // Se guarda la venta con este listado de productos
            TypedQuery<Producto> productosReales = em.createQuery("select prod from Producto prod where prod.idProducto in :idProdsList", Producto.class);
            productosReales.setParameter("idProdsList", productos);
            List<Producto> prods = productosReales.getResultList();

            TypedQuery<Promocion> promos = em.createQuery("select promo from Promocion promo where " +
                    ":now between promo.fechaInicio and promo.fechaFin", Promocion.class);
            promos.setParameter("now", DateHelper.nowWithTime());
            List<Promocion> promocionList = promos.getResultList();

            CarroDeCompras carro = new CarroDeCompras(prods, cliente, DateHelper.nowWithTime(), promocionList,
                    tarjeta.getMetodo());
            Venta venta = carro.pagarCarrito();
            em.persist(venta);

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
    public float calcularMonto(List<Long> productos, Long idTarjeta) {
        EntityManagerFactory emf = JPAHelper.getJPAFactory();
        EntityManager em = emf.createEntityManager();
        try {
            //hacer algo con em
            AbstractCobrable tarjeta = em.find(AbstractCobrable.class, idTarjeta);
            if (productos == null || productos.size() == 0) {
                throw new RuntimeException("Servicio Venta - calcularMonto: No hay productos");
            }

            TypedQuery<Promocion> promos = em.createQuery("select promo from Promocion promo where " +
                    ":now between promo.fechaInicio and promo.fechaFin", Promocion.class);
            promos.setParameter("now", DateHelper.nowWithTime());
            List<Promocion> promocionList = promos.getResultList();

            TypedQuery<Producto> prods = em.createQuery("select prod from Producto prod where prod.idProducto in :idProdsList", Producto.class);
            prods.setParameter("idProdsList", prods);
            List<Producto> productoList = prods.getResultList();

            CarroDeCompras carro = new CarroDeCompras(productoList, promocionList, tarjeta.getMetodo(), DateHelper.nowWithTime());
            return (float) carro.montoTotal();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Override
    public List ventas() {
        EntityManagerFactory emf = JPAHelper.getJPAFactory();
        EntityManager em = emf.createEntityManager();
        try {
            //hacer algo con em
            TypedQuery<Venta> ventas = em.createQuery("select venta from Venta venta", Venta.class);
            return ventas.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }
}
