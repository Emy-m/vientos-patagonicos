package ar.unrn.tp.main;

import ar.unrn.tp.jpa.servicios.ClienteServiceJPA;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        ClienteServiceJPA clienteService = new ClienteServiceJPA();
        clienteService.crearCliente("Emilio", "Martin", "12345678", "emilio@gmail.com");
        clienteService.modificarCliente(new Long(17), "Emeleo", "Merten", "12345678", "emi@gmail.com");
        clienteService.agregarTarjeta(new Long(17), "14", "UALA", 500, LocalDate.now().plusDays(3));
        System.out.println(clienteService.listarTarjetas(new Long(17)));
    }
}
