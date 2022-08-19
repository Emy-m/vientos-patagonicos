package ar.unrn.tp.servicios;

import ar.unrn.tp.modelo.DateHelper;
import ar.unrn.tp.modelo.AbstractCobrable;

import java.time.LocalDate;
import java.util.Date;

public class TarjetaDeCredito extends AbstractCobrable {
    private String metodo;
    private String codigo;
    private float saldo;
    private Date fechaVencimiento;

    public TarjetaDeCredito(String metodo, String codigo, float saldo, Date fechaVencimiento) {
        super();
        this.metodo = metodo;
        this.codigo = codigo;
        this.saldo = saldo;
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public LocalDate getFechaVencimiento() {
        return DateHelper.convertToLocalDate(fechaVencimiento);
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
        if (estaActiva() && tieneSuficiente(monto)) {
            this.saldo -= monto;
        } else {
            throw new RuntimeException("No tiene saldo suficiente o esta inactiva");
        }
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
