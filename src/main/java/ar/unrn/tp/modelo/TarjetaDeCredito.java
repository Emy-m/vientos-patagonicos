package ar.unrn.tp.modelo;

public class TarjetaDeCredito {
    private String nombre;
    private int codigo;
    private double monto;

    public TarjetaDeCredito(String nombre, int codigo, double monto) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.monto = monto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TarjetaDeCredito that = (TarjetaDeCredito) o;

        return getCodigo() == that.getCodigo();
    }

    @Override
    public int hashCode() {
        return getCodigo();
    }
}
