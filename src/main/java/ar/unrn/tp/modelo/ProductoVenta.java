package ar.unrn.tp.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProductoVenta {
    @Id
    @GeneratedValue
    private Long idProductoVenta;

    private String codigo;
    private double precio;

    protected ProductoVenta() {
    }

    public ProductoVenta(String codigo, double precio) {
        this.codigo = codigo;
        this.precio = precio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "ProductoVenta{" +
                "idProductoVenta=" + idProductoVenta +
                ", codigo='" + codigo + '\'' +
                ", precio=" + precio +
                '}';
    }
}
