import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.Box;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SidebarComponent extends JPanel {
    // Atributos
    private HospitalBelgaInterface ventanaPrincipal;
    private JButton botonVolver;

    // Constructor - recibe la ventana principal para comunicarse
    public SidebarComponent(HospitalBelgaInterface ventana) {
        this.ventanaPrincipal = ventana;
        configurarPanel();
        crearElementos();
    }

    // Configurar las propiedades básicas del panel
    private void configurarPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(200, 0)); // Ancho fijo de 200 pixeles
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    // Crear todos los elementos del sidebar
    private void crearElementos() {
        // Crear botón VOLVER en la parte superior
        botonVolver = crearBotonVolver();

        // Crear panel de navegación
        JPanel panelNavegacion = crearPanelNavegacion();

        // Agregar elementos al sidebar
        add(botonVolver, BorderLayout.NORTH);

        // Container para centrar el panel de navegación
        JPanel contenedorCentral = new JPanel(new BorderLayout());
        contenedorCentral.setBackground(Color.WHITE);
        contenedorCentral.add(panelNavegacion, BorderLayout.NORTH);
        contenedorCentral.add(Box.createVerticalGlue(), BorderLayout.CENTER);

        add(contenedorCentral, BorderLayout.CENTER);
    }

    // Botón VOLVER
    private JButton crearBotonVolver() {
        JButton boton = new JButton("VOLVER");
        boton.setBackground(Color.LIGHT_GRAY);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.setFocusPainted(false); // Quitar el borde cuando está seleccionado
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Agregar efecto hover (cambio de color al pasar el mouse)
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(Color.GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(Color.LIGHT_GRAY);
            }
        });

        // Agregar acción al hacer click
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaPrincipal.volverAlMenuPrincipal();
            }
        });

        return boton;
    }

    // Crear el panel de navegación con los enlaces
    private JPanel crearPanelNavegacion() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Elementos en columna
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0)); // Espacio arriba

        // Crear enlaces de navegación
        JLabel linkPacientes = crearLinkNavegacion("Pacientes", true); // true = activo
        JLabel linkDoctores = crearLinkNavegacion("Doctores", false);
        JLabel linkCitas = crearLinkNavegacion("Citas", false);
        JLabel linkHistoria = crearLinkNavegacion("Historia Clínica", false);

        // Agregar enlaces al panel con espaciado
        panel.add(linkPacientes);
        panel.add(Box.createVerticalStrut(15)); // Espacio vertical
        panel.add(linkDoctores);
        panel.add(Box.createVerticalStrut(15));
        panel.add(linkCitas);
        panel.add(Box.createVerticalStrut(15));
        panel.add(linkHistoria);

        return panel;
    }

    //Enlace de navegación
    private JLabel crearLinkNavegacion(String texto, boolean estaActivo) {
        JLabel enlace = new JLabel(texto);
        enlace.setCursor(new Cursor(Cursor.HAND_CURSOR));
        enlace.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Configurar estilo según si está activo o no
        if (estaActivo) {
            enlace.setForeground(new Color(70, 130, 200)); // Azul
            enlace.setFont(new Font("Arial", Font.BOLD, 14));
        } else {
            enlace.setForeground(Color.DARK_GRAY);
            enlace.setFont(new Font("Arial", Font.PLAIN, 14));
        }

        //efectos hover
        enlace.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!estaActivo) {
                    enlace.setForeground(new Color(70, 130, 200));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!estaActivo) {
                    enlace.setForeground(Color.DARK_GRAY);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                ventanaPrincipal.navegarASeccion(texto);
            }
        });

        return enlace;
    }

}