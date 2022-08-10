package ar.unrn.tp.modelo;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

        Date inicio = Date.from(LocalDate.of(2022, 5, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fin = Date.from(LocalDate.of(2022, 5, 29).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Promocion promoMarcaNoSe = new PromocionMarca(inicio, fin, 0.05, "nose");
        List<Promocion> promos = new ArrayList<>();
        promos.add(promoMarcaNoSe);

        TarjetaDeCredito tarjeta = new TarjetaDeCredito("Naranja", 1, 500, fin);
        List<TarjetaDeCredito> tarjetas = new ArrayList<>();
        tarjetas.add(tarjeta);

        Cliente cliente = new Cliente("Emilio", "Martin", "39648740", "eamartin@gmail.com", tarjetas);

        CarroDeCompras carro = new CarroDeCompras(productos, cliente, new Date(), promos);

        assertEquals(500, carro.montoTotal());
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

        Date inicio = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fin = Date.from(LocalDate.now().plusDays(3).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Promocion promoMarcaACME = new PromocionMarca(inicio, fin, 0.05, "acme");
        List<Promocion> promos = new ArrayList<>();
        promos.add(promoMarcaACME);

        TarjetaDeCredito tarjeta = new TarjetaDeCredito("Naranja", 1, 500, fin);
        List<TarjetaDeCredito> tarjetas = new ArrayList<>();
        tarjetas.add(tarjeta);

        Cliente cliente = new Cliente("Emilio", "Martin", "39648740", "eamartin@gmail.com", tarjetas);

        CarroDeCompras carro = new CarroDeCompras(productos, cliente, new Date(), promos);

        assertEquals(485, carro.montoTotal());
    }

    @Test
    public void calcularConPromoPago() {
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

        Date inicio = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fin = Date.from(LocalDate.now().plusDays(3).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Promocion promoMetodoPago = new PromocionMetodoPago(inicio, fin, 0.08, "Naranja");
        List<Promocion> promos = new ArrayList<>();
        promos.add(promoMetodoPago);

        TarjetaDeCredito tarjeta = new TarjetaDeCredito("Naranja", 1, 500, fin);
        List<TarjetaDeCredito> tarjetas = new ArrayList<>();
        tarjetas.add(tarjeta);

        Cliente cliente = new Cliente("Emilio", "Martin", "39648740", "eamartin@gmail.com", tarjetas);

        CarroDeCompras carro = new CarroDeCompras(productos, cliente, new Date(), promos, "Naranja");

        assertEquals(460, carro.montoTotal());
    }

    @Test
    public void calcularConDosPromos() {
        Producto prod1 = new Producto(1, "termo", "luminagro", "bazar", 100);
        Producto prod2 = new Producto(2, "zapatillas", "nike", "ropa", 100);
        Producto prod3 = new Producto(3, "mochila", "Comarca", "alguna", 100);
        Producto prod4 = new Producto(4, "campera", "Comarca", "ropa", 100);
        Producto prod5 = new Producto(5, "mate", "Comarca", "artesanal", 100);

        List<Producto> productos = new ArrayList<>();
        productos.add(prod1);
        productos.add(prod2);
        productos.add(prod3);
        productos.add(prod4);
        productos.add(prod5);

        Date inicio = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fin = Date.from(LocalDate.now().plusDays(3).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Promocion promoMetodoPago = new PromocionMetodoPago(inicio, fin, 0.08, "MemeCard");
        Promocion promoMarcaComarca = new PromocionMarca(inicio, fin, 0.05, "Comarca");
        List<Promocion> promos = new ArrayList<>();
        promos.add(promoMetodoPago);
        promos.add(promoMarcaComarca);

        TarjetaDeCredito tarjeta = new TarjetaDeCredito("MemeCard", 1, 500, fin);
        List<TarjetaDeCredito> tarjetas = new ArrayList<>();
        tarjetas.add(tarjeta);

        Cliente cliente = new Cliente("Emilio", "Martin", "39648740", "eamartin@gmail.com", tarjetas);

        CarroDeCompras carro = new CarroDeCompras(productos, cliente, new Date(), promos, "MemeCard");

        /*
         * * Total 500
         * * La promo del metodo de pago sobre el total: 500 * 0.08 = 40
         * * La promo sobre el precio del producto, son 3 productos de 100 c/u: 300 * 0.05 = 15
         * * Descuento total 55. Monto total: 500 - 55 = 445
         * */

        assertEquals(445, carro.montoTotal());
    }

    @Test
    public void pagarCarro() {
        Producto prod1 = new Producto(1, "termo", "luminagro", "bazar", 100);
        Producto prod2 = new Producto(2, "zapatillas", "nike", "ropa", 100);
        Producto prod3 = new Producto(3, "mochila", "Comarca", "alguna", 100);
        Producto prod4 = new Producto(4, "campera", "Comarca", "ropa", 100);
        Producto prod5 = new Producto(5, "mate", "Comarca", "artesanal", 100);

        List<Producto> productos = new ArrayList<>();
        productos.add(prod1);
        productos.add(prod2);
        productos.add(prod3);
        productos.add(prod4);
        productos.add(prod5);


        Date fin = Date.from(LocalDate.now().plusDays(3).atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<Promocion> promos = new ArrayList<>();

        TarjetaDeCredito tarjeta = new TarjetaDeCredito("MemeCard", 1, 500, fin);
        List<TarjetaDeCredito> tarjetas = new ArrayList<>();
        tarjetas.add(tarjeta);

        Cliente cliente = new Cliente("Emilio", "Martin", "39648740", "eamartin@gmail.com", tarjetas);

        CarroDeCompras carro = new CarroDeCompras(productos, cliente, new Date(), promos, "MemeCard");

        /*
         * * Total 500
         * * La tarjeta "MemeCard" del cliente esta activa y tiene 500 exactos
         * */

        assertEquals(500, carro.pagarCarrito("MemeCard").montoTotal());
    }

    /*@Test
    public void crearProductoConCategoria() {
        Producto prod1 = new Producto(1, "termo", "luminagro", "bazar", 100);

        assertEquals(500, carro.pagarCarrito("MemeCard").montoTotal());
    }*/
}