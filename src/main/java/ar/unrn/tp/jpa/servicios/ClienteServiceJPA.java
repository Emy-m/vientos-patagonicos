package ar.unrn.tp.jpa.servicios;


import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.DateHelper;
import ar.unrn.tp.servicios.TarjetaDeCredito;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

public class ClienteServiceJPA implements ClienteService {

    @Override
    public void crearCliente(String nombre, String apellido, String dni, String email) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-objectdb");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            //hacer algo con em
            Cliente p = new Cliente(nombre, apellido, dni, email);
            em.persist(p);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Override
    public void modificarCliente(Long idCliente, String nombre, String apellido, String dni, String email) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-objectdb");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            //hacer algo con em
            Cliente cliente = em.getReference(Cliente.class, idCliente);
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setDNI(dni);
            cliente.setEmail(email);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Override
    public void agregarTarjeta(Long idCliente, String nro, String marca, float saldo, LocalDate fechaVencimiento) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-objectdb");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            //hacer algo con em
            Cliente cliente = em.getReference(Cliente.class, idCliente);
            TarjetaDeCredito tarjetaDeCredito = new TarjetaDeCredito(marca, nro, saldo, DateHelper.convertToDate(fechaVencimiento));
            cliente.agregarTarjeta(tarjetaDeCredito);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Override
    public List listarTarjetas(Long idCliente) {
        return null;
    }
}
