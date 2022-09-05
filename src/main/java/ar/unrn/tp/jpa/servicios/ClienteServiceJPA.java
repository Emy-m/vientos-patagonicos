package ar.unrn.tp.jpa.servicios;


import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.modelo.AbstractCobrable;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.DateHelper;
import ar.unrn.tp.servicios.TarjetaDeCredito;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteServiceJPA implements ClienteService {
    private String persistenceUnit;

    public ClienteServiceJPA(String persistenceUnit) {
        this.persistenceUnit = persistenceUnit;
    }

    @Override
    public void crearCliente(String nombre, String apellido, String dni, String email) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            //hacer algo con em
            Cliente p = new Cliente(nombre, apellido, dni, email, null);
            em.persist(p);

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
    public List<Cliente> clientes() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Cliente> clientes = em.createQuery("select cliente from Cliente cliente", Cliente.class);
            return clientes.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Override
    public void modificarCliente(Long idCliente, String nombre, String apellido, String dni, String email) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
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
    public void agregarTarjeta(Long idCliente, String nro, String marca, float saldo, LocalDate fechaVencimiento) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            //hacer algo con em
            Cliente cliente = em.getReference(Cliente.class, idCliente);

            if (cliente != null) {
                TarjetaDeCredito tarjetaDeCredito = new TarjetaDeCredito(marca, nro, saldo, DateHelper.convertToDate(fechaVencimiento));
                cliente.agregarTarjeta(tarjetaDeCredito);
            }

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
    public List listarTarjetas(Long idCliente) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        EntityManager em = emf.createEntityManager();
        try {
            //hacer algo con em
            Cliente cliente = em.find(Cliente.class, idCliente);
            /*//Esta es la mejor opcion sin romper el encapsulamiento, devolviendo la lista con FetchType.EAGER
            // en las tarjetas del cliente
            return cliente.getTarjetas();*/

            // romper el encapsulamiento crendo y devolviendo una nueva lista partiendo de un Proxy
            List list = new ArrayList<>();
            for (AbstractCobrable abstractCobrable : cliente.getTarjetas()) {
                list.add(abstractCobrable);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }
}
