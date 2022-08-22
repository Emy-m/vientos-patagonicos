package ar.unrn.tp.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
public abstract class Promocion {
    @Id
    @GeneratedValue
    protected Long idPromocion;

    protected Date fechaInicio;
    protected Date fechaFin;
    protected double descuento;

    protected Promocion() {
    }

    protected Promocion(Date fechaInicio, Date fechaFin, double descuento) {
        if (fechaInicio.equals(fechaFin) || fechaInicio.after(fechaFin)) {
            throw new RuntimeException("Se intento crear una promocion con fechas invalidas");
        }
        if (descuento <= 0 || descuento > 1) {
            throw new RuntimeException("Se intento crear una promocion con un descuento invalido");
        }

        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descuento = descuento;
    }

    public abstract double devolverMontoDescontado(List<Producto> productos, String metodoPago);

    protected boolean fechaDentroDePromocion() {
        return DateHelper.isDateBetween(fechaInicio, fechaFin, LocalDate.now());
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    @Override
    public String toString() {
        return "Promocion{" +
                "idPromocion=" + idPromocion +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", descuento=" + descuento +
                '}';
    }
}
