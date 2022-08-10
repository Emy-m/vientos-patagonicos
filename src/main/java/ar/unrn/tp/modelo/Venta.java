package ar.unrn.tp.modelo;

public class Venta {
    private CarroDeCompras carro;
    private String metodoDePago;

    public Venta(CarroDeCompras carro, String metodoDePago) {
        this.carro = carro;
        this.metodoDePago = metodoDePago;
    }

    public double montoTotal() {
        return this.carro.montoTotal();
    }
}
