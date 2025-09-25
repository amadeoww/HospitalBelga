package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaHistoriaClinica extends JDialog {
    // Atributos
    private String numeroHC;
    private String nombrePaciente;

    // Constructor
    public VentanaHistoriaClinica(JFrame ventanaPadre, String hc, String nombre) {
        super(ventanaPadre, "Historia Clínica", true); // true = modal (bloquea la ventana padre)
        this.numeroHC = hc;
        this.nombrePaciente = nombre;

        configurarVentana();
        crearElementos();
    }

    // Configurar las propiedades de la ventana
    private void configurarVentana() {
        setSize(600, 400);
        setLocationRelativeTo(getParent()); // Centrar respecto a la ventana padre
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    // Crear todos los elementos de la ventana
    private void crearElementos() {
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titulo = crearTitulo();

        // Panel de información del paciente
        JPanel panelInfo = crearPanelInformacion();

        // Área de texto para el historial
        JScrollPane areaHistorial = crearAreaHistorial();

        // Panel de botones
        JPanel panelBotones = crearPanelBotones();

        // Organizar elementos
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        JPanel panelContenido = new JPanel(new BorderLayout());
        panelContenido.add(panelInfo, BorderLayout.NORTH);
        panelContenido.add(areaHistorial, BorderLayout.CENTER);
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    // Crear el título de la ventana
    private JLabel crearTitulo() {
        JLabel titulo = new JLabel("Historia Clínica - " + nombrePaciente);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        return titulo;
    }

    // Crear el panel con información básica del paciente
    private JPanel crearPanelInformacion() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10)); // 4 filas, 2 columnas
        panel.setBorder(BorderFactory.createTitledBorder("Información del Paciente"));

        // Agregar información (etiqueta - valor)
        panel.add(new JLabel("HC:"));
        panel.add(new JLabel(numeroHC));

        panel.add(new JLabel("Nombre:"));
        panel.add(new JLabel(nombrePaciente));

        panel.add(new JLabel("Estado:"));
        panel.add(new JLabel("Activo"));

        panel.add(new JLabel("Última consulta:"));
        panel.add(new JLabel("15/09/2025"));

        return panel;
    }

    // Crear el área de texto con el historial médico
    private JScrollPane crearAreaHistorial() {
        JTextArea areaTexto = new JTextArea();
        areaTexto.setText(obtenerHistorialMedico());
        areaTexto.setEditable(false); // Solo lectura
        areaTexto.setBackground(new Color(245, 245, 245));
        areaTexto.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setBorder(BorderFactory.createTitledBorder("Historial Médico"));

        return scroll;
    }

    // Obtener el texto del historial médico (simulado)
    private String obtenerHistorialMedico() {
        return "Historial médico del paciente:\n\n" +
                "• Consulta 13/09/2025: Revisión general\n" +
                "  - Presión arterial: 120/80\n" +
                "  - Peso: 70 kg\n" +
                "  - Observaciones: Paciente en buen estado\n\n" +
                "• Consulta 01/08/2025: Seguimiento tratamiento\n" +
                "  - Continuar con medicación actual\n" +
                "  - Mejoría notable en síntomas\n\n" +
                "• Consulta 15/07/2025: Consulta inicial\n" +
                "  - Primera visita\n" +
                "  - Diagnóstico: Hipertensión leve\n\n" +
                "Observaciones generales:\n" +
                "Paciente colaborativo y puntual en sus citas.\n" +
                "Responde bien al tratamiento prescrito.";
    }

    // Crear el panel de botones
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout());

        JButton botonImprimir = new JButton("Imprimir");
        JButton botonCerrar = new JButton("Cerrar");


        botonCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cerrar la ventana
            }
        });

        panel.add(botonImprimir);
        panel.add(botonCerrar);

        return panel;
    }

}