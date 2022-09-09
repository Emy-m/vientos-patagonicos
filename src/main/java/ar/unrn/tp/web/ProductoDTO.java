package ar.unrn.tp.web;

public class ProductoDTO {
    private String codigo;
    private String descripcion;
    private String precio;
    private String marca;
    private String categoria;
    private String version;

    public ProductoDTO() {

    }

    public ProductoDTO(String codigo, String descripcion, String precio, String marca, String categoria, String version) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.marca = marca;
        this.precio = precio;
        this.categoria = categoria;
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

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
