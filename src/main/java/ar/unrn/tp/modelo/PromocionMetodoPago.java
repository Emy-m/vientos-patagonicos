package ar.unrn.tp.modelo;

import java.util.Date;

public class PromocionMetodoPago extends Promocion {
    private String nombreMetodo;

    public PromocionMetodoPago(Date fechaInicio, Date fechaFin, double descuento, String nombreMetodo) {
        super(fechaInicio, fechaFin, descuento);
        this.nombreMetodo = nombreMetodo;
    }

    @Override
    public double devolverMontoDescontado(CarroDeCompras carro) {
        double monto = 0;

        if (this.nombreMetodo.equals(carro.getMetodoPago())) {
            if (this.fechaInicio.before(carro.getFechaCreacion()) && this.fechaFin.after(carro.getFechaCreacion())) {
                for (Producto producto : carro.getProductos()) {
                    monto += producto.getPrecio();
                }

                monto *= this.descuento;
            }
        }

        return monto;
    }
}
