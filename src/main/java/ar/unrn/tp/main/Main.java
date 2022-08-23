package ar.unrn.tp.main;

import ar.unrn.tp.jpa.servicios.*;
import ar.unrn.tp.modelo.*;
import ar.unrn.tp.web.WebAPI;
import io.javalin.Javalin;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ClienteServiceJPA clienteService = new ClienteServiceJPA();
        /*clienteService.crearCliente("Emilio", "Martin", "12345678", "emilio@gmail.com");
        clienteService.modificarCliente(new Long(17), "Emeleo", "Merten", "12345678", "emi@gmail.com");
        clienteService.agregarTarjeta(new Long(17), "14", "UALA", 500, LocalDate.now().plusDays(3));
        System.out.println(clienteService.listarTarjetas(new Long(17)));*/

        ProductoServiceJPA productoServiceJPA = new ProductoServiceJPA();
        //productoServiceJPA.crearProducto("ASD123", "Picada para 8", "Paladini", 2000, new Long(19));
        //productoServiceJPA.modificarProducto(new Long(21), "Picada para 4", "Paladini", 1000, new Long(19));
        //System.out.println(productoServiceJPA.listarProductos());

        DescuentoServiceJPA descuentoServiceJPA = new DescuentoServiceJPA();
        //descuentoServiceJPA.crearDescuento("Paladini", LocalDate.now().plusDays(-1), LocalDate.now().plusDays(3), (float) 0.05);
        //descuentoServiceJPA.crearDescuentoSobreTotal("UALA", LocalDate.now(), LocalDate.now().plusDays(3), (float) 0.08);

        VentaServiceJPA ventaServiceJPA = new VentaServiceJPA();
        /*List<Long> prods = new ArrayList<>();
        prods.add(new Long(20));
        prods.add(new Long(21));
        ventaServiceJPA.realizarVenta(new Long(17), prods, new Long(18));
        System.out.println(ventaServiceJPA.ventas());*/

        WebAPI api = new WebAPI(clienteService, descuentoServiceJPA, productoServiceJPA, ventaServiceJPA, 7070);
        api.start();
    }
}
