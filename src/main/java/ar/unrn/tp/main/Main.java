package ar.unrn.tp.main;

import ar.unrn.tp.jpa.servicios.*;
import ar.unrn.tp.web.WebAPI;

public class Main {
    public static void main(String[] args) {
        ClienteServiceJPA clienteService = new ClienteServiceJPA();
        ProductoServiceJPA productoServiceJPA = new ProductoServiceJPA();
        DescuentoServiceJPA descuentoServiceJPA = new DescuentoServiceJPA();
        VentaServiceJPA ventaServiceJPA = new VentaServiceJPA();

        WebAPI api = new WebAPI(clienteService, descuentoServiceJPA, productoServiceJPA, ventaServiceJPA, 7070);
        api.start();
    }
}
