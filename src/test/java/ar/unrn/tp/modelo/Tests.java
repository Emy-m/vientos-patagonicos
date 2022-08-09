package ar.unrn.tp.modelo;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {
    @Test
    public void calculoConPromoCaducada() {
        Producto prod1 = new Producto(1, "termo", "luminagro", "bazar", 100);
        Producto prod2 = new Producto(2, "zapatillas", "nike", "ropa", 100);
        Producto prod3 = new Producto(3, "mochila", "nose", "alguna", 100);
        Producto prod4 = new Producto(4, "campera", "nose", "ropa", 100);
        Producto prod5 = new Producto(5, "mate", "nose", "artesanal", 100);

        List<Producto> productos = new ArrayList<>();
        productos.add(prod1);
        productos.add(prod2);
        productos.add(prod3);
        productos.add(prod4);
        productos.add(prod5);

        TarjetaDeCredito tarjeta = new TarjetaDeCredito("Naranja", 1, 500);
        List<TarjetaDeCredito> tarjetas = new ArrayList<>();
        tarjetas.add(tarjeta);

        Cliente cliente = new Cliente("Emilio", "Martin", "39648740", "eamartin@gmail.com", tarjetas);

        CarroDeCompras carro = new CarroDeCompras(productos, cliente, new Date());

        Venta venta = new Venta(carro, new Date(), "Naranja");

        Date inicio = Date.from(LocalDate.of(2022, 5, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fin = Date.from(LocalDate.of(2022, 5, 29).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Promocion promoMarcaNoSe = new PromocionMarca(inicio, fin, 0.05, "nose");

        double descuento = promoMarcaNoSe.devolverDescuento(venta);
        assertEquals(0, descuento);
    }

    @Test
    public void calcularConPromoAcme() {
        Producto prod1 = new Producto(1, "termo", "luminagro", "bazar", 100);
        Producto prod2 = new Producto(2, "zapatillas", "nike", "ropa", 100);
        Producto prod3 = new Producto(3, "mochila", "acme", "alguna", 100);
        Producto prod4 = new Producto(4, "campera", "acme", "ropa", 100);
        Producto prod5 = new Producto(5, "mate", "acme", "artesanal", 100);

        List<Producto> productos = new ArrayList<>();
        productos.add(prod1);
        productos.add(prod2);
        productos.add(prod3);
        productos.add(prod4);
        productos.add(prod5);

        TarjetaDeCredito tarjeta = new TarjetaDeCredito("Naranja", 1, 500);
        List<TarjetaDeCredito> tarjetas = new ArrayList<>();
        tarjetas.add(tarjeta);

        Cliente cliente = new Cliente("Emilio", "Martin", "39648740", "eamartin@gmail.com", tarjetas);

        CarroDeCompras carro = new CarroDeCompras(productos, cliente, new Date());

        Venta venta = new Venta(carro, new Date(), "Naranja");

        Date inicio = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fin = Date.from(LocalDate.now().plusDays(3).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Promocion promoMarcaACME = new PromocionMarca(inicio, fin, 0.05, "acme");

        double descuento = promoMarcaACME.devolverDescuento(venta);
        assertEquals(15, descuento);
    }
}