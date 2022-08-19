package ar.unrn.tp.modelo;

public class ProductoVenta {
    private String codigo;
    private double precio;

    public ProductoVenta(Producto producto) {
        this.codigo = producto.getCodigo();
        this.precio = producto.getPrecio();
    }

    public String getCodigo() {
        return codigo;
    }

    public double getPrecio() {
        return precio;
    }
}
