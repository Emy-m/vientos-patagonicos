package ar.unrn.tp.modelo;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class CarroDeCompras {
    private List<Producto> productos;
    private Cliente cliente;
    private Date fechaCreacion;
    private List<Promocion> promociones;
    private String metodoPago;

    public CarroDeCompras(List<Producto> productos, Cliente cliente, Date fechaCreacion, List<Promocion> promos) {
        this.productos = productos;
        this.cliente = cliente;
        this.fechaCreacion = fechaCreacion;
        this.promociones = promos;
    }

    public CarroDeCompras(List<Producto> productos, Cliente cliente, Date fechaCreacion, List<Promocion> promos, String metodoPago) {
        this(productos, cliente, fechaCreacion, promos);
        this.metodoPago = metodoPago;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDateTime getFechaCreacion() {
        return DateHelper.convertToLocalDateTime(fechaCreacion);
    }

    public List<Promocion> getPromociones() {
        return promociones;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public double calcularDescuento() {
        double descuento = 0;

        for (Promocion promo : this.getPromociones()) {
            descuento += promo.devolverMontoDescontado(productos, metodoPago);
        }

        return descuento;
    }

    public double montoTotal() {
        double total = 0;

        for (Producto producto : this.getProductos()) {
            total += producto.getPrecio();
        }

        return total - this.calcularDescuento();
    }

    public Venta pagarCarrito(String metodoPago) {
        try {
            setMetodoPago(metodoPago);
            AbstractCobrable tarjeta = getCliente().getTarjeta(metodoPago);
            tarjeta.debitar(montoTotal());
            Venta venta = new Venta(DateHelper.nowWithTime(), cliente, metodoPago, productos,
                    montoTotal());
            return venta;
        } catch (RuntimeException e) {
            throw e;
        }
    }
}
