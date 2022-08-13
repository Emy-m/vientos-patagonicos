package ar.unrn.tp.modelo;

public interface ICobrable {
    boolean tieneSuficiente(double monto);

    boolean estaActiva();

    void debitar(double monto);

    boolean mismoMetodo(String metodo);
}
