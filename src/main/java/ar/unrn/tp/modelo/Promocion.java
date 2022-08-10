package ar.unrn.tp.modelo;

import java.util.Date;

public abstract class Promocion {
    protected Date fechaInicio;
    protected Date fechaFin;
    protected double descuento;

    protected Promocion(Date fechaInicio, Date fechaFin, double descuento) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descuento = descuento;
    }

    abstract double devolverMontoDescontado(CarroDeCompras carro);
}
