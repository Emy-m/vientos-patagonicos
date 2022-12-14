package ar.unrn.tp.servicios;

import ar.unrn.tp.modelo.AbstractCobrable;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class TarjetaDeCredito extends AbstractCobrable {
    protected TarjetaDeCredito() {}

    public TarjetaDeCredito(String metodo, String codigo, float saldo, Date fechaVencimiento) {
        super(metodo, codigo, saldo, fechaVencimiento);
    }

    @Override
    public boolean tieneSuficiente(double monto) {
        return this.getSaldo() >= monto;
    }

    @Override
    public boolean estaActiva() {
        return this.fechaVencimiento.after(new Date());
    }

    @Override
    public void debitar(double monto) {
        if (!estaActiva()) {
            throw new RuntimeException("La tarjeta esta inactiva");
        }
        if (!tieneSuficiente(monto)) {
            throw new RuntimeException("No tiene saldo suficiente");
        }

        this.saldo -= monto;
    }

    @Override
    public boolean mismoMetodo(String metodo) {
        return this.metodo.equals(metodo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TarjetaDeCredito that = (TarjetaDeCredito) o;

        return getCodigo().equals(that.getCodigo());
    }

    @Override
    public int hashCode() {
        return getCodigo().hashCode();
    }
}
