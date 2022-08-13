package ar.unrn.tp.modelo;

import java.time.LocalDateTime;
import java.util.Date;

public abstract class Promocion {
    protected Date fechaInicio;
    protected Date fechaFin;
    protected double descuento;

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

    abstract double devolverMontoDescontado(CarroDeCompras carro);

    protected boolean fechaDentroDePromocion(LocalDateTime when) {
        return DateHelper.isDateBetween(fechaInicio, fechaFin, when);
    }
}
