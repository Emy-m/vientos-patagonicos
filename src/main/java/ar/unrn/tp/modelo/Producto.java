package ar.unrn.tp.modelo;

public class Producto {
    private int codigo;
    private String descripcion;
    private String marca;
    private String categoria;
    private double precio;

    public Producto(int codigo, String descripcion, String marca, String categoria, double precio) {
        if (marca.isEmpty() || marca == null) {
            throw new RuntimeException("Se intento crear un producto sin marca");
        }
        if (descripcion.isEmpty() || descripcion == null) {
            throw new RuntimeException("Se intento crear un producto sin descripcion");
        }
        if (precio <= 0) {
            throw new RuntimeException("Se intento crear un producto con precio invalido");
        }

        this.codigo = codigo;
        this.descripcion = descripcion;
        this.marca = marca;
        this.categoria = categoria;
        this.precio = precio;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getMarca() {
        return marca;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Producto producto = (Producto) o;

        return getCodigo() == producto.getCodigo();
    }

    @Override
    public int hashCode() {
        return getCodigo();
    }
}
