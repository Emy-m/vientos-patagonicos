package ar.unrn.tp.modelo;

import java.util.Date;

public class PromocionMarca extends Promocion {
    private String marcaEnDescuento;

    public PromocionMarca(Date fechaInicio, Date fechaFin, double descuento, String marcaEnDescuento) {
        super(fechaInicio, fechaFin, descuento);
        this.marcaEnDescuento = marcaEnDescuento;
    }

    @Override
    double devolverMontoDescontado(CarroDeCompras carro) {
        double monto = 0;

        if (this.fechaInicio.before(carro.getFechaCreacion()) && this.fechaFin.after(carro.getFechaCreacion())) {
            for (Producto producto : carro.getProductos()) {
                if (producto.getMarca().equals(this.marcaEnDescuento)) {
                    monto += producto.getPrecio() * this.descuento;
                }
            }
        }

        return monto;
    }
}
