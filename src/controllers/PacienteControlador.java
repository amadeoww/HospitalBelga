package controllers;

import models.Paciente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PacienteControlador {

    private final List<Paciente> pacientes;

    public PacienteControlador() {
        pacientes = new ArrayList<>();

        loadDefault();
    }

    public void agregarPaciente(Paciente paciente) {
        pacientes.add(paciente);
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public Paciente getPaciente(int hc) {
        Paciente encontrado = null;
        for (Paciente actual : pacientes) {
            if (actual.getHc() == hc) {
                encontrado = actual;
            }
        }

        return encontrado;
    }

    private void loadDefault() {
        // pacientes = DBPaciente.pacientes();
        pacientes.add(new Paciente(332323, 7854123, "Fernando Rodriguez", LocalDate.now()));
        pacientes.add(new Paciente(332324, 7854124, "Alexander Vasquez", LocalDate.now()));
        pacientes.add(new Paciente(332325, 7854125, "Valeria Villarroel", LocalDate.now()));
        pacientes.add(new Paciente(332326, 7854126, "Juan Perez", LocalDate.now()));
        pacientes.add(new Paciente(332326, 7854126, "Daniel Zeballos", LocalDate.now()));
        pacientes.add(new Paciente(3323267, 78541269, "Bernardo Garcia", LocalDate.now()));
        pacientes.add(new Paciente(3323265, 78541261, "Luciana Fernandez", LocalDate.now()));
    }
}