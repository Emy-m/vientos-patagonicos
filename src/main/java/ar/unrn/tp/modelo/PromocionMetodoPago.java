package ar.unrn.tp.modelo;

import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Entity
public class PromocionMetodoPago extends Promocion {
    private String nombreMetodo;

    protected PromocionMetodoPago() {
    }

    public PromocionMetodoPago(Date fechaInicio, Date fechaFin, double descuento, String nombreMetodo) {
        super(fechaInicio, fechaFin, descuento);
        this.nombreMetodo = nombreMetodo;
    }

    @Override
    public double devolverMontoDescontado(List<Producto> productos, String metodoPago) {
        double monto = 0;

        if (this.nombreMetodo.equals(metodoPago)) {
            if (fechaDentroDePromocion()) {
                for (Producto producto : productos) {
                    monto += producto.getPrecio();
                }

                monto *= this.descuento;
            }
        }

        return monto;
    }

    @Override
    public String toString() {
        return "PromocionMetodoPago{" +
                "nombreMetodo='" + nombreMetodo + '\'' +
                "} " + super.toString();
    }
}
