package ar.unrn.tp.modelo;

import javax.jdo.annotations.Unique;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Map;

@Entity
public class Categoria {
    @Id
    @GeneratedValue
    private Long idCategoria;

    @Unique
    private String nombre;

    protected Categoria() {}

    public Categoria(String nombre) {
        if (nombre.isEmpty()) {
            throw new RuntimeException("Se intento crear una categoria sin nombre");
        }

        this.nombre = nombre;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Categoria categoria = (Categoria) o;

        return getNombre().equals(categoria.getNombre());
    }

    @Override
    public int hashCode() {
        return getNombre().hashCode();
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "idCategoria=" + idCategoria +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    public Map<String, Object> toMap() {
        return Map.of("id", idCategoria, "nombre", nombre);
    }
}
