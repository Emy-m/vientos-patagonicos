package ar.unrn.tp.servicios;

import ar.unrn.tp.modelo.DateHelper;
import ar.unrn.tp.modelo.ICobrable;

import java.time.LocalDate;
import java.util.Date;

public class TarjetaDeCredito implements ICobrable {
    private String nombre;
    private int codigo;
    private double saldo;
    private Date fechaVencimiento;

    public TarjetaDeCredito(String nombre, int codigo, double saldo, Date fechaVencimiento) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.saldo = saldo;
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public LocalDate getFechaVencimiento() {
        return DateHelper.convertToLocalDate(fechaVencimiento);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TarjetaDeCredito that = (TarjetaDeCredito) o;

        return getCodigo() == that.getCodigo();
    }

    @Override
    public int hashCode() {
        return getCodigo();
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
        return this.nombre.equals(metodo);
    }
}
