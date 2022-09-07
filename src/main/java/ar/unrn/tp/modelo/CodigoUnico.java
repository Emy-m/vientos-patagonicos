package ar.unrn.tp.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CodigoUnico {
    @Id
    @GeneratedValue
    private Long id;

    private int numero;
    private int anio;

    protected CodigoUnico() {
    }

    public CodigoUnico(int numero, int anio) {
        this.numero = numero;
        this.anio = anio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void aumentarCodigoUnico() {
        this.numero += 1;
    }

    public String devolverCodigoUnico() {
        return this.numero + "-" + this.anio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodigoUnico that = (CodigoUnico) o;

        if (getNumero() != that.getNumero()) return false;
        return getAnio() == that.getAnio();
    }

    @Override
    public int hashCode() {
        int result = getNumero();
        result = 31 * result + getAnio();
        return result;
    }
}
