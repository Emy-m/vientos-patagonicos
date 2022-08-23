package ar.unrn.tp.web;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.AbstractCobrable;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.ArrayList;
import java.util.Map;

public class WebAPI {
    private ClienteService clientes;
    private DescuentoService descuentos;
    private ProductoService productos;
    private VentaService ventas;
    private int webPort;

    public WebAPI(ClienteService clientes, DescuentoService descuentos, ProductoService productos, VentaService ventas, int webPort) {
        this.clientes = clientes;
        this.descuentos = descuentos;
        this.productos = productos;
        this.ventas = ventas;
        this.webPort = webPort;
    }

    public void start() {
        Javalin app = Javalin.create(config -> {
            config.enableCorsForAllOrigins();
        }).start(this.webPort);
        /*app.post("/clientes", crearCliente());
        app.put("/clientes", modificarCliente());*/
        app.post("/clientes/tarjetas/{id}", agregarTarjeta());
        app.get("/clientes/tarjetas/{id}", obtenerTarjetas());

        app.exception(RuntimeException.class, (e, ctx) -> {
            ctx.json(Map.of("result", "error", "message", e.getMessage()));
            // log error in a stream...
        });

        app.exception(Exception.class, (e, ctx) -> {
            ctx.json(Map.of("result", "error", "message", "Ups... algo se rompió.: " + e.getMessage()));
            // log error in a stream...
        });
    }

    private Handler crearCliente() {
        return null;
    }

    private Handler modificarCliente() {
        return null;
    }

    private Handler agregarTarjeta() {
        return ctx -> {
            CobrableDTO dto = ctx.bodyAsClass(CobrableDTO.class);
            Long id = Long.valueOf(ctx.pathParam("id"));
            this.clientes.agregarTarjeta(id, dto.getCodigo(), dto.getMarca(),
                    dto.getSaldo(), dto.getVencimiento());
            ctx.json(Map.of("result", "success"));
        };
    }

    private Handler obtenerTarjetas() {
         return ctx -> {
             var id = Long.valueOf(ctx.pathParam("id"));
             var tarjetas = this.clientes.listarTarjetas(id);
             var list = new ArrayList<Map<String, Object>>();
             for (AbstractCobrable tarjeta : tarjetas) {
                 list.add(tarjeta.toMap());
             }
             ctx.json(Map.of("result", "success", "tarjetas", list));
         };
    }
}
