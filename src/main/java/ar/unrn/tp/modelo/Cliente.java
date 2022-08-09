package ar.unrn.tp.modelo;

import java.util.List;

public class Cliente {
    private String nombre;
    private String apellido;
    private String DNI;
    private String email;
    private List<TarjetaDeCredito> tarjetas;

    public Cliente(String nombre, String apellido, String DNI, String email, List<TarjetaDeCredito> tarjetas) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.DNI = DNI;
        this.email = email;
        this.tarjetas = tarjetas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<TarjetaDeCredito> getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(List<TarjetaDeCredito> tarjetas) {
        this.tarjetas = tarjetas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cliente cliente = (Cliente) o;

        return getDNI() != null ? getDNI().equals(cliente.getDNI()) : cliente.getDNI() == null;
    }

    @Override
    public int hashCode() {
        return getDNI() != null ? getDNI().hashCode() : 0;
    }
}
