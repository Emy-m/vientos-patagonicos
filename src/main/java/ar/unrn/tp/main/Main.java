package ar.unrn.tp.main;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.jpa.servicios.ClienteServiceJPA;
import ar.unrn.tp.jpa.servicios.DescuentoServiceJPA;
import ar.unrn.tp.jpa.servicios.ProductoServiceJPA;
import ar.unrn.tp.jpa.servicios.VentaServiceJPA;
import ar.unrn.tp.web.WebAPI;

public class Main {
    private static final String PERSISTENCE_UNIT = "jpa-mysql";
    public static void main(String[] args) {
        ClienteService clienteService = new ClienteServiceJPA(PERSISTENCE_UNIT);
        ProductoService productoServiceJPA = new ProductoServiceJPA(PERSISTENCE_UNIT);
        DescuentoService descuentoServiceJPA = new DescuentoServiceJPA(PERSISTENCE_UNIT);
        VentaService ventaServiceJPA = new VentaServiceJPA(PERSISTENCE_UNIT);

        WebAPI api = new WebAPI(clienteService, descuentoServiceJPA, productoServiceJPA, ventaServiceJPA, 7071);
        api.start();
    }
}
