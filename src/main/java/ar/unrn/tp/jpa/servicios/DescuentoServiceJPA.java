package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.modelo.DateHelper;
import ar.unrn.tp.modelo.PromocionMarca;
import ar.unrn.tp.modelo.PromocionMetodoPago;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDate;

public class DescuentoServiceJPA implements DescuentoService {
    @Override
    public void crearDescuentoSobreTotal(String marcaTarjeta, LocalDate fechaDesde, LocalDate fechaHasta, float porcentaje) {
        EntityManagerFactory emf = JPAHelper.getJPAFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            //hacer algo con em
            PromocionMetodoPago descSobreTotal = new PromocionMetodoPago(DateHelper.convertToDate(fechaDesde),
                    DateHelper.convertToDate(fechaHasta), porcentaje, marcaTarjeta);
            em.persist(descSobreTotal);

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
    public void crearDescuento(String marcaProducto, LocalDate fechaDesde, LocalDate fechaHasta, float porcentaje) {
        EntityManagerFactory emf = JPAHelper.getJPAFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            //hacer algo con em
            PromocionMarca descMarca = new PromocionMarca(DateHelper.convertToDate(fechaDesde),
                    DateHelper.convertToDate(fechaHasta), porcentaje, marcaProducto);
            em.persist(descMarca);

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
