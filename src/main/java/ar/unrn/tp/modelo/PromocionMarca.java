package ar.unrn.tp.modelo;

import java.util.Date;

public class PromocionMarca extends Promocion {
    private String marcaEnDescuento;

    public PromocionMarca(Date fechaInicio, Date fechaFin, double descuento, String marcaEnDescuento) {
        super(fechaInicio, fechaFin, descuento);
        this.marcaEnDescuento = marcaEnDescuento;
    }

    @Override
    double devolverDescuento(Venta venta) {
        double monto = 0;

        if (this.fechaInicio.before(venta.getFechaVenta()) && this.fechaFin.after(venta.getFechaVenta())) {
            for (Producto producto : venta.getProductos()) {
                if (producto.getMarca().equals(this.marcaEnDescuento)) {
                    monto += producto.getPrecio() * this.descuento;
                }
            }
        }

        return monto;
    }
}
