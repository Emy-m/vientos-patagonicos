package ar.unrn.tp.main;

import ar.unrn.tp.api.*;
import ar.unrn.tp.cache.CacheServiceRedis;
import ar.unrn.tp.jpa.servicios.*;
import ar.unrn.tp.web.WebAPI;

public class Main {
    private static final String PERSISTENCE_UNIT = "jpa-mysql";
    private static final String CACHE_SERVICE_URL = "redis://default:redispw@localhost:49153";
    public static void main(String[] args) {
        ClienteService clienteService = new ClienteServiceJPA(PERSISTENCE_UNIT);
        ProductoService productoServiceJPA = new ProductoServiceJPA(PERSISTENCE_UNIT);
        DescuentoService descuentoServiceJPA = new DescuentoServiceJPA(PERSISTENCE_UNIT);
        VentaService ventaServiceJPA = new VentaServiceJPA(PERSISTENCE_UNIT, new CacheServiceRedis(CACHE_SERVICE_URL));
        CategoriaService categoriaServiceJPA = new CategoriaServiceJPA(PERSISTENCE_UNIT);

        WebAPI api = new WebAPI(clienteService, descuentoServiceJPA, productoServiceJPA, ventaServiceJPA, categoriaServiceJPA, 7070);
        api.start();
    }
}
