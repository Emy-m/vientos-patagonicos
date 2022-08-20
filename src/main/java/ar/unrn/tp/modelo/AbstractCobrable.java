package ar.unrn.tp.modelo;

import javax.jdo.annotations.Unique;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
public abstract class AbstractCobrable {
    @Id
    @GeneratedValue
    protected Long idCobrable;

    protected String metodo;

    @Unique
    protected String codigo;
    protected float saldo;
    protected Date fechaVencimiento;

    protected AbstractCobrable() {}

    protected AbstractCobrable(String metodo, String codigo, float saldo, Date fechaVencimiento) {
        this.metodo = metodo;
        this.codigo = codigo;
        this.saldo = saldo;
        this.fechaVencimiento = fechaVencimiento;
    }

    public Long getIdCobrable() {
        return idCobrable;
    }

    public void setIdCobrable(Long idCobrable) {
        this.idCobrable = idCobrable;
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

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public abstract boolean tieneSuficiente(double monto);

    public abstract boolean estaActiva();

    public abstract void debitar(double monto);

    public abstract boolean mismoMetodo(String metodo);

    @Override
    public String toString() {
        return "AbstractCobrable{" +
                "idCobrable=" + idCobrable +
                ", metodo='" + metodo + '\'' +
                ", codigo='" + codigo + '\'' +
                ", saldo=" + saldo +
                ", fechaVencimiento=" + fechaVencimiento +
                '}';
    }
}