package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
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
            AbstractCobrable tarjeta = em.find(AbstractCobrable.class, idTarjeta);

            if (!cliente.tieneTarjeta(tarjeta)) {
                throw new RuntimeException("Servicio Venta - realizarVenta: La Tarjeta no es del cliente");
            }

            // Se guarda la venta con este listado de productos
            TypedQuery<Producto> productosReales = em.createQuery("select prod from Producto prod", Producto.class);
            List<Producto> prods = productosReales.getResultList();

            // El monto total se calcula ahora, puede diferir del listado anterior..
            float monto = calcularMonto(productos, idTarjeta);

            Venta venta = new Venta(DateHelper.nowWithTime(), cliente, tarjeta.getMetodo(), prods, monto);
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
            float monto = 0;
            float descuento = 0;

            AbstractCobrable tarjeta = em.find(AbstractCobrable.class, idTarjeta);
            if (productos == null || productos.size() == 0) {
                throw new RuntimeException("Servicio Venta - calcularMonto: No hay productos");
            }

            TypedQuery<Promocion> promos = em.createQuery("select promo from Promocion promo", Promocion.class);
            List<Promocion> promocionList = promos.getResultList();
            List<Producto> productoList = new ArrayList<>();

            Producto prod = null;
            for (Long idProducto : productos) {
                prod = em.find(Producto.class, idProducto);
                productoList.add(prod);
                monto += prod.getPrecio();
            }

            for (Promocion promo : promocionList) {
                descuento += promo.devolverMontoDescontado(productoList, tarjeta.getMetodo());
            }

            return monto - descuento;
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
