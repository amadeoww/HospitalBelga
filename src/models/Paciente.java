package models;

import java.time.LocalDate;

public class Paciente {

    private final int hc;
    private final int ci;
    private LocalDate fechaNacimiento;
    private String nombreCompleto;

    public Paciente(int hc, int ci, String nombreCompleto, LocalDate fechaNacimiento) {
        this.hc = hc;
        this.ci = ci;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Paciente(int hc, int ci) {
        this(hc, ci, "Sin Nombre", LocalDate.now());
    }

    public int getHc() {
        return hc;
    }

    public int getCi() {
        return ci;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
}