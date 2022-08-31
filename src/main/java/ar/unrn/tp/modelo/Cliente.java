package ar.unrn.tp.modelo;

import org.apache.commons.validator.routines.EmailValidator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
public class Cliente {
    @Id
    @GeneratedValue
    private Long idCliente;
    private String nombre;
    private String apellido;

    @Column(unique = true)
    private String DNI;

    private String email;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<AbstractCobrable> tarjetas = new ArrayList<>();

    protected Cliente() {
    }

    public Cliente(String nombre, String apellido, String DNI, String email, List<AbstractCobrable> tarjetas) {
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

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
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

    public List<AbstractCobrable> getTarjetas() {
        return tarjetas;
    }

    public AbstractCobrable getTarjeta(String unaTarjeta) {
        AbstractCobrable tarjeta = null;

        for (AbstractCobrable otraTarjeta : this.getTarjetas()) {
            if (otraTarjeta.mismoMetodo(unaTarjeta)) {
                tarjeta = otraTarjeta;
            }
        }

        return tarjeta;
    }

    public AbstractCobrable getTarjeta(Long idTarjeta) {
        for (AbstractCobrable tarjetaCliente : tarjetas) {
            if (tarjetaCliente.getIdCobrable().equals(idTarjeta)) {
                return tarjetaCliente;
            }
        }

        throw new RuntimeException("El cliente no posee la tarjeta");
    }

    public void setTarjetas(List<AbstractCobrable> tarjetas) {
        this.tarjetas = tarjetas;
    }

    public void agregarTarjeta(AbstractCobrable tarjeta) {
        if (this.tarjetas == null) {
            ArrayList<AbstractCobrable> tarjetas = new ArrayList<>();
            tarjetas.add(tarjeta);
            this.tarjetas = tarjetas;
        }
        else {
            this.tarjetas.add(tarjeta);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cliente cliente = (Cliente) o;

        return getDNI() != null ? getDNI().equals(cliente.getDNI()) : cliente.getDNI() == null;
    }

    public Map<String, Object> toMap() {
        List<Map<String, Object>> tarjetasMapeadas = new ArrayList<>();
        if (tarjetas != null) {
            for (AbstractCobrable tarjeta : tarjetas) {
                tarjetasMapeadas.add(tarjeta.toMap());
            }
        }
        return Map.of("id", idCliente, "nombre", nombre, "apellido", apellido, "dni", DNI, "email", email,
                "tarjetas", tarjetasMapeadas);
    }

    @Override
    public int hashCode() {
        return getDNI() != null ? getDNI().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", DNI='" + DNI + '\'' +
                ", email='" + email + '\'' +
                ", tarjetas=" + tarjetas +
                '}';
    }
}
