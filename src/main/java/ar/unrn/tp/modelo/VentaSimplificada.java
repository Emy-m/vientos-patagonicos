package ar.unrn.tp.modelo;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

public class VentaSimplificada implements Comparable<VentaSimplificada> {
    private Long id;
    private String codigo;
    private List<ProductoVenta> detalleProds;
    private Date fecha;
    private String metodoPago;
    private double monto;

    public VentaSimplificada(Long id, String codigo, List<ProductoVenta> detalleProds, Date fecha, String metodoPago, double monto) {
        this.id = id;
        this.codigo = codigo;
        this.detalleProds = detalleProds;
        this.fecha = fecha;
        this.metodoPago = metodoPago;
        this.monto = monto;
    }

    public VentaSimplificada(Venta venta) {
        this(venta.getIdVenta(), venta.getCodigoUnico(), venta.getDetalleProductos(), venta.getFechaVenta(),
                venta.getMetodoDePago(), venta.getMontoTotal());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<ProductoVenta> getDetalleProds() {
        return detalleProds;
    }

    public void setDetalleProds(List<ProductoVenta> detalleProds) {
        this.detalleProds = detalleProds;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    @Override
    public String toString() {
        return "VentaSimplificada{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", detalleProds=" + detalleProds +
                ", fecha=" + fecha +
                ", metodoPago='" + metodoPago + '\'' +
                ", monto=" + monto +
                '}';
    }

    @Override
    public int compareTo(@NotNull VentaSimplificada o) {
        return this.fecha.compareTo(o.getFecha());
    }
}
