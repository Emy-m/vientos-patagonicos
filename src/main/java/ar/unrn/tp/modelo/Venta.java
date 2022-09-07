package ar.unrn.tp.modelo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Venta {
    @Id
    @GeneratedValue
    private Long idVenta;

    @Column(unique = true, nullable = false)
    private String codigoUnico;

    @ManyToOne(fetch = FetchType.EAGER)
    private Cliente cliente;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<ProductoVenta> detalleProductos;

    private Date fechaVenta;
    private String metodoDePago;
    private double montoTotal;

    protected Venta() {
    }

    public Venta(Date fechaVenta, Cliente cliente, String metodoDePago, List<Producto> productos,
                 double montoTotal) {
        this.fechaVenta = fechaVenta;
        this.cliente = cliente;
        this.metodoDePago = metodoDePago;
        this.detalleProductos = devolverResumen(productos);
        this.montoTotal = montoTotal;
    }

    private List<ProductoVenta> devolverResumen(List<Producto> productos) {
        ArrayList<ProductoVenta> detalleProds = new ArrayList<>();

        for (Producto producto : productos) {
            detalleProds.add(new ProductoVenta(producto.getCodigo(), producto.getPrecio()));
        }

        return detalleProds;
    }

    public Long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Long idVenta) {
        this.idVenta = idVenta;
    }

    public String getCodigoUnico() {
        return codigoUnico;
    }

    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getMetodoDePago() {
        return metodoDePago;
    }

    public void setMetodoDePago(String metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    public List<ProductoVenta> getDetalleProductos() {
        return detalleProductos;
    }

    public void setDetalleProductos(List<ProductoVenta> detalleProductos) {
        this.detalleProductos = detalleProductos;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "idVenta=" + idVenta +
                ", codigoUnico='" + codigoUnico + '\'' +
                ", cliente=" + cliente +
                ", detalleProductos=" + detalleProductos +
                ", fechaVenta=" + fechaVenta +
                ", metodoDePago='" + metodoDePago + '\'' +
                ", montoTotal=" + montoTotal +
                '}';
    }
}
