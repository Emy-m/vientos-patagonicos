package ar.unrn.tp.modelo;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.List;

public class Cliente {
    private String nombre;
    private String apellido;
    private String DNI;
    private String email;
    private List<ICobrable> tarjetas;

    public Cliente(String nombre, String apellido, String DNI, String email, List<ICobrable> tarjetas) {
        if (nombre.isEmpty()) {
            throw new RuntimeException("Se intento crear un cliente sin nombre");
        }
        if (apellido.isEmpty()) {
            throw new RuntimeException("Se intento crear un cliente sin apellido");
        }
        if (DNI.isEmpty()) {
            throw new RuntimeException("Se intento crear un cliente sin DNI");
        }
        if (!validateEmail(email)) {
            throw new RuntimeException("Se intento crear un cliente con un email invalido");
        }

        this.nombre = nombre;
        this.apellido = apellido;
        this.DNI = DNI;
        this.email = email;
        this.tarjetas = tarjetas;
    }

    /* * https://commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/routines/package-summary.html#other.email */
    private boolean validateEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
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

    public List<ICobrable> getTarjetas() {
        return tarjetas;
    }

    public ICobrable getTarjeta(String unaTarjeta) {
        ICobrable tarjeta = null;

        for (ICobrable otraTarjeta : this.getTarjetas()) {
            if (otraTarjeta.mismoMetodo(unaTarjeta)) {
                tarjeta = otraTarjeta;
            }
        }

        return tarjeta;
    }

    public void setTarjetas(List<ICobrable> tarjetas) {
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
