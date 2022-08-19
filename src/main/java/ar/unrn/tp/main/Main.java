package ar.unrn.tp.main;

import ar.unrn.tp.jpa.servicios.ClienteServiceJPA;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.DateHelper;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ClienteServiceJPA clienteService = new ClienteServiceJPA();
        //clienteService.crearCliente("Emilio", "Martin", "12345678", "emilio@gmail.com");
        //clienteService.modificarCliente(new Long(3), "Emeleo", "Merten", "12345678", "emi@gmail.com");
        mostrar(new Long(3));
        clienteService.agregarTarjeta(new Long(3), "1", "MasterCard", 500, LocalDate.now().plusDays(3));
        clienteService.listarTarjetas(new Long(3));
    }

    public static void mostrar(Long idCliente) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-objectdb");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            //hacer algo con em
            TypedQuery<Cliente> q =
                    em.createQuery("select c from Cliente c", Cliente.class);
            List<Cliente> clientes = q.getResultList();
            for (Cliente cliente : clientes) {
                System.out.println(cliente.toString());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }
}
