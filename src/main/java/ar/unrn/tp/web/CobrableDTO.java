package ar.unrn.tp.web;

import java.time.LocalDate;

public class CobrableDTO {
    private String codigo;
    private String marca;
    private float saldo;
    private LocalDate vencimiento;

    public CobrableDTO(String codigo, String marca, float saldo, LocalDate vencimiento) {
        this.codigo = codigo;
        this.marca = marca;
        this.saldo = saldo;
        this.vencimiento = vencimiento;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public LocalDate getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(LocalDate vencimiento) {
        this.vencimiento = vencimiento;
    }
}
