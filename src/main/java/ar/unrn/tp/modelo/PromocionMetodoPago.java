package ar.unrn.tp.modelo;

import java.util.Date;

public class PromocionMetodoPago extends Promocion {
    private String nombreMetodo;

    public PromocionMetodoPago(Date fechaInicio, Date fechaFin, double descuento, String nombreMetodo) {
        super(fechaInicio, fechaFin, descuento);
    }

    @Override
    public double devolverDescuento(Venta venta) {
        double monto = 0;

        if (this.nombreMetodo.equals(venta.getMetodoPago())) {
            if (this.fechaInicio.before(venta.getFechaVenta()) && this.fechaFin.after(venta.getFechaVenta())) {
                for (Producto producto : venta.getProductos()) {
                    monto += producto.getPrecio();
                }

                monto *= this.descuento;
            }
        }

        return monto;
    }
}
