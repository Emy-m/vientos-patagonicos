package ar.unrn.tp.modelo;

import javax.persistence.*;
import java.util.Map;

@Entity
public class Producto {
    @Id
    @GeneratedValue
    private Long idProducto;

    @Version
    private Long version;

    @Column(unique = true)
    private String codigo;

    private String descripcion;
    private String marca;

    @ManyToOne(fetch = FetchType.EAGER)
    private Categoria categoria;

    private double precio;

    protected Producto() {}

    public Producto(String codigo, String descripcion, String marca, Categoria categoria, double precio) {
        if (codigo.isEmpty()) {
            throw new RuntimeException("Se intento crear un producto sin codigo");
        }
        if (marca.isEmpty()) {
            throw new RuntimeException("Se intento crear un producto sin marca");
        }
        if (descripcion.isEmpty()) {
            throw new RuntimeException("Se intento crear un producto sin descripcion");
        }
        if (categoria == null) {
            throw new RuntimeException("Se intento crear un producto sin categoria");
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

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
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

        return getCodigo().equals(producto.getCodigo());
    }

    @Override
    public int hashCode() {
        return getCodigo().hashCode();
    }

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", marca='" + marca + '\'' +
                ", categoria=" + categoria +
                ", precio=" + precio +
                '}';
    }

    public Map<String, Object> toMap() {
        return Map.of("id", idProducto, "descripcion", descripcion, "codigo", codigo, "marca", marca,
                "categoria", categoria, "precio", precio, "version", version);
    }
}
