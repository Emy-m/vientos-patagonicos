package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.modelo.DateHelper;
import ar.unrn.tp.modelo.Promocion;
import ar.unrn.tp.modelo.PromocionMarca;
import ar.unrn.tp.modelo.PromocionMetodoPago;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

public class DescuentoServiceJPA implements DescuentoService {
    private String persistenceUnit;

    public DescuentoServiceJPA(String persistenceUnit) {
        this.persistenceUnit = persistenceUnit;
    }

    @Override
    public void crearDescuentoSobreTotal(String marcaTarjeta, LocalDate fechaDesde, LocalDate fechaHasta, float porcentaje) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            //hacer algo con em
            PromocionMetodoPago descSobreTotal = new PromocionMetodoPago(DateHelper.convertToDate(fechaDesde), DateHelper.convertToDate(fechaHasta), porcentaje, marcaTarjeta);
            em.persist(descSobreTotal);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    @Override
    public void crearDescuento(String marcaProducto, LocalDate fechaDesde, LocalDate fechaHasta, float porcentaje) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            //hacer algo con em
            PromocionMarca descMarca = new PromocionMarca(DateHelper.convertToDate(fechaDesde), DateHelper.convertToDate(fechaHasta), porcentaje, marcaProducto);
            em.persist(descMarca);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    @Override
    public List<Promocion> descuentosActivos() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        EntityManager em = emf.createEntityManager();
        try {
            //hacer algo con em
            TypedQuery<Promocion> descuentos = em.createQuery("select promo from Promocion promo where " +
                    ":now between promo.fechaInicio and promo.fechaFin", Promocion.class);
            descuentos.setParameter("now", DateHelper.nowWithTime());

            return descuentos.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }
}
