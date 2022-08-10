package ar.unrn.tp.modelo;

import java.util.Date;

public class TarjetaDeCredito {
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

    public boolean tieneSuficiente(double monto) {
        return this.getSaldo() >= monto;
    }

    public boolean estaActiva() {
        return this.fechaVencimiento.after(new Date());
    }

    public void debitar(double monto) {
        if (estaActiva() && tieneSuficiente(monto)) {
            this.saldo -= monto;
        } else {
            throw new RuntimeException("No tiene saldo suficiente o esta inactiva");
        }
    }

    public boolean mismoMetodo(String metodo) {
        return this.nombre.equals(metodo);
    }
}
