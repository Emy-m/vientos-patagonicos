package ar.unrn.tp.web;

import ar.unrn.tp.api.*;
import ar.unrn.tp.modelo.*;
import com.google.gson.Gson;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WebAPI {
    private ClienteService clientes;
    private DescuentoService descuentos;
    private ProductoService productos;
    private VentaService ventas;
    private CategoriaService categorias;
    private int webPort;

    public WebAPI(ClienteService clientes, DescuentoService descuentos, ProductoService productos, VentaService ventas, CategoriaService categorias, int webPort) {
        this.clientes = clientes;
        this.descuentos = descuentos;
        this.productos = productos;
        this.ventas = ventas;
        this.webPort = webPort;
        this.categorias = categorias;
    }

    public void start() {
        Javalin app = Javalin.create(config -> {
            config.enableCorsForAllOrigins();
        }).start(this.webPort);
        app.post("/clientes", crearCliente());
        app.get("/clientes", obtenerClientes());
        app.put("/clientes/{id}", modificarCliente());
        app.post("/clientes/tarjetas/{id}", agregarTarjeta());
        app.get("/clientes/tarjetas/{id}", obtenerTarjetas());

        app.get("/productos", obtenerProductos());
        app.post("/productos", crearProducto());
        app.put("/productos/{id}", modificarProducto());

        app.get("/descuentos", obtenerDescuentos());

        app.post("/ventas", crearVenta());
        app.get("/ventas/ultimas/{id}", obtenerUltimasVentas());

        app.get("/ventas/precio", obtenerPrecio());

        app.get("/categorias", obtenerCategorias());

        app.exception(Exception.class, (e, ctx) -> {
            ctx.json(Map.of("result", "error", "message", "Ups... algo se rompió.: " + e.getMessage())).status(400);
            // log error in a stream...
        });
    }

    private Handler crearCliente() {
        return ctx -> {
            ClienteDTO dto = ctx.bodyAsClass(ClienteDTO.class);
            this.clientes.crearCliente(dto.getNombre(), dto.getApellido(), dto.getDni(), dto.getEmail());
            ctx.json(Map.of("result", "success"));
        };
    }

    private Handler obtenerClientes() {
        return ctx -> {
            var clientes = this.clientes.clientes();
            var list = new ArrayList<Map<String, Object>>();
            for (Cliente cliente : clientes) {
                list.add(cliente.toMap());
            }
            ctx.json(Map.of("result", "success", "clientes", list));
        };
    }

    private Handler modificarCliente() {
        return ctx -> {
            ClienteDTO dto = ctx.bodyAsClass(ClienteDTO.class);
            Long id = Long.valueOf(ctx.pathParam("id"));
            this.clientes.modificarCliente(id, dto.getNombre(), dto.getApellido(), dto.getDni(), dto.getEmail());
            ctx.json(Map.of("result", "success"));
        };
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

    private Handler obtenerProductos() {
        return ctx -> {
            var productos = this.productos.listarProductos();
            var list = new ArrayList<Map<String, Object>>();
            for (Producto producto : productos) {
                list.add(producto.toMap());
            }
            ctx.json(Map.of("result", "success", "productos", list));
        };
    }

    private Handler crearProducto() {
        return ctx -> {
            ProductoDTO dto = ctx.bodyAsClass(ProductoDTO.class);
            this.productos.crearProducto(dto.getCodigo(), dto.getDescripcion(), dto.getMarca(), Float.parseFloat(dto.getPrecio()), Long.parseLong(dto.getCategoria()));
            ctx.json(Map.of("result", "success"));
        };
    }

    private Handler modificarProducto() {
        return ctx -> {
            ProductoDTO dto = ctx.bodyAsClass(ProductoDTO.class);
            Long id = Long.valueOf(ctx.pathParam("id"));
            this.productos.modificarProducto(id, dto.getCodigo(), dto.getDescripcion(), dto.getMarca(), Float.parseFloat(dto.getPrecio()), Long.parseLong(dto.getCategoria()), Long.parseLong(dto.getVersion()));
            ctx.json(Map.of("result", "success", "message", "Producto modificado con exito"));
        };
    }

    private Handler obtenerDescuentos() {
        return ctx -> {
            var descuentos = this.descuentos.descuentosActivos();
            var list = new ArrayList<Map<String, Object>>();
            for (Promocion descuento : descuentos) {
                list.add(descuento.toMap());
            }
            ctx.json(Map.of("result", "success", "descuentos", list));
        };
    }

    private Handler crearVenta() {
        return ctx -> {
            Gson gson = new Gson();
            var idCliente = ctx.queryParam("cliente");
            var idTarjeta = ctx.queryParam("tarjeta");
            var productos = ctx.bodyAsClass(Long[].class);

            List<Long> productosList = Arrays.asList(productos);
            Long clienteId = gson.fromJson(idCliente, Long.class);
            Long tarjetaId = gson.fromJson(idTarjeta, Long.class);

            this.ventas.realizarVenta(clienteId, productosList, tarjetaId);

            ctx.json(Map.of("result", "success", "message", "Venta realizada con exito"));
        };
    }

    private Handler obtenerPrecio() {
        return ctx -> {
            Gson gson = new Gson();
            var idTarjeta = ctx.queryParam("tarjeta");
            var productos = ctx.queryParam("productos");

            Long[] prodsIds = gson.fromJson(productos, Long[].class);
            List<Long> productosList = Arrays.asList(prodsIds);

            Long tarjeta = gson.fromJson(idTarjeta, Long.class);

            var precio = this.ventas.calcularMonto(productosList, tarjeta);

            ctx.json(Map.of("result", "success", "precio", precio));
        };
    }

    private Handler obtenerCategorias() {
        return ctx -> {
            var categoriasList = this.categorias.categorias();
            var list = new ArrayList<Map<String, Object>>();
            for (Categoria categoria : categoriasList) {
                list.add(categoria.toMap());
            }
            ctx.json(Map.of("result", "success", "categorias", list));
        };
    }

    private Handler obtenerUltimasVentas() {
        return ctx -> {
            Long idCliente = Long.valueOf(ctx.pathParam("id"));
            var list = this.ventas.ultimas(idCliente);

            ctx.json(Map.of("result", "success", "ventas", list));
        };
    }
}
