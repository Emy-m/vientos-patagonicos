package ar.unrn.tp.modelo;

public class Categoria {
    private String nombre;

    public Categoria(String nombre) {
        if (nombre.isEmpty()) {
            throw new RuntimeException("Se intento crear una categoria sin nombre");
        }

        this.nombre = nombre;
    }
}
