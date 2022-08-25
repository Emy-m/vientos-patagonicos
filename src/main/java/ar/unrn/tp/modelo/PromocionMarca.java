package ar.unrn.tp.modelo;

import javax.persistence.Entity;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
public class PromocionMarca extends Promocion {
    private String marcaEnDescuento;

    protected PromocionMarca() {
    }

    public PromocionMarca(Date fechaInicio, Date fechaFin, double descuento, String marcaEnDescuento) {
        super(fechaInicio, fechaFin, descuento);
        this.marcaEnDescuento = marcaEnDescuento;
    }

    @Override
    public double devolverMontoDescontado(List<Producto> productos, String metodoPago) {
        double monto = 0;

        if (fechaDentroDePromocion()) {
            for (Producto producto : productos) {
                if (producto.getMarca().equals(this.marcaEnDescuento)) {
                    monto += producto.getPrecio() * this.descuento;
                }
            }
        }

        return monto;
    }

    @Override
    public String toString() {
        return "PromocionMarca{" +
                "marcaEnDescuento='" + marcaEnDescuento + '\'' +
                "} " + super.toString();
    }

    @Override
    public Map<String, Object> toMap() {
        return Map.of("id", idPromocion, "fechaInicio", fechaInicio, "fechaFin", fechaFin, "descuento", descuento, "marcaEnDescuento", marcaEnDescuento);
    }
}
