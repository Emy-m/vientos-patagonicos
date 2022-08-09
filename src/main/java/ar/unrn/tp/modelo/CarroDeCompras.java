package ar.unrn.tp.modelo;

import java.util.Date;
import java.util.List;

public class CarroDeCompras {
    private List<Producto> productos;
    private Cliente cliente;
    private Date fechaCreacion;

    public CarroDeCompras(List<Producto> productos, Cliente cliente, Date fechaCreacion) {
        this.productos = productos;
        this.cliente = cliente;
        this.fechaCreacion = fechaCreacion;
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
}
