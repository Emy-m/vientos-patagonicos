package ar.unrn.tp.modelo;

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

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<Promocion> getPromociones() {
        return promociones;
    }

    public void setPromociones(List<Promocion> promociones) {
        this.promociones = promociones;
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
            descuento += promo.devolverMontoDescontado(this);
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
            TarjetaDeCredito tarjeta = this.getCliente().getTarjeta(metodoPago);
            tarjeta.debitar(this.montoTotal());
            return new Venta(this, metodoPago);
        } catch (RuntimeException e) {
            throw e;
        }
    }
}
