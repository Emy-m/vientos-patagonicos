package ar.unrn.tp.modelo;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Venta {
    private Date fechaVenta;
    private Cliente cliente;
    private String metodoDePago;
    private List<ProductoVenta> detalleProductos;
    private double descuento;

    public Venta(Date fechaVenta, Cliente cliente, String metodoDePago, List<ProductoVenta> detalleProductos,
                 double descuento) {
        this.fechaVenta = fechaVenta;
        this.cliente = cliente;
        this.metodoDePago = metodoDePago;
        this.detalleProductos = detalleProductos;
        this.descuento = descuento;
    }

    public double montoTotal() {
        double total = 0;

        for (ProductoVenta producto : detalleProductos) {
            total += producto.getPrecio();
        }

        return total - descuento;
    }

    public String getMetodoDePago() {
        return metodoDePago;
    }

    public LocalDateTime getFechaVenta() {
        return DateHelper.convertToLocalDateTime(fechaVenta);
    }

    public Cliente getCliente() {
        return cliente;
    }
}
