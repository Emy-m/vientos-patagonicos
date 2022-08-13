package ar.unrn.tp.modelo;

public class ProductoVenta {
    private int codigo;
    private double precio;

    public ProductoVenta(Producto producto) {
        this.codigo = producto.getCodigo();
        this.precio = producto.getPrecio();
    }

    public int getCodigo() {
        return codigo;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductoVenta productoVenta = (ProductoVenta) o;

        return getCodigo() == productoVenta.getCodigo();
    }

    @Override
    public int hashCode() {
        return getCodigo();
    }
}
