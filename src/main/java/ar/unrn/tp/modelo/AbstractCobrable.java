package ar.unrn.tp.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public abstract class AbstractCobrable {
    @Id
    @GeneratedValue
    protected Long idCobrable;

    protected AbstractCobrable() {}

    public abstract boolean tieneSuficiente(double monto);

    public abstract boolean estaActiva();

    public abstract void debitar(double monto);

    public abstract boolean mismoMetodo(String metodo);
}
