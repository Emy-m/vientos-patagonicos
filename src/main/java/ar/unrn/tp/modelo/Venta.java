package ar.unrn.tp.modelo;

import java.util.Date;
import java.util.List;

public class Venta {
    private CarroDeCompras carro;
    private Date fechaVenta;
    private String metodoPago;

    public Venta(CarroDeCompras carro, Date fechaVenta, String metodoPago) {
        this.carro = carro;
        this.fechaVenta = fechaVenta;
        this.metodoPago = metodoPago;
    }

    public double montoTotal(CarroDeCompras carro) {
        double total = 0;

        for (Producto producto : carro.getProductos()) {
            total += producto.getPrecio();
        }

        return total;
    }

    public double montoTotal(CarroDeCompras carro, List<Promocion> promociones) {
        double total = montoTotal(carro);

        return total;
    }

    public List<Producto> getProductos() {
        return this.carro.getProductos();
    }

    public Date getFechaVenta() {
        return this.fechaVenta;
    }

    public String getMetodoPago() {
        return this.metodoPago;
    }
}
