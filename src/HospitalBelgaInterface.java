import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Dimension;


public class HospitalBelgaInterface extends JFrame {
    // Atributos - Los componentes principales de la ventana
    private NavbarComponent navbar;
    private SidebarComponent sidebar;
    private PacientesTableComponent pacientesTable;

    // Constructor - Se ejecuta cuando creamos la ventana
    public HospitalBelgaInterface() {
        configurarVentana();
        crearComponentes();
        organizarComponentes();
    }

    // Método para configurar las propiedades básicas de la ventana
    private void configurarVentana() {
        setTitle("Hospital Belga - Sistema de Pacientes");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana
        setMinimumSize(new Dimension(1000, 600));
    }

    // Método para crear todos los componentes
    private void crearComponentes() {
        navbar = new NavbarComponent(this); // Le pasamos 'this' para que pueda comunicarse con la ventana principal
        sidebar = new SidebarComponent(this);
        pacientesTable = new PacientesTableComponent(this);
    }

    // Método para organizar los componentes en la ventana
    private void organizarComponentes() {
        setLayout(new BorderLayout());

        // Agregar la barra superior
        add(navbar, BorderLayout.NORTH);

        // Panel principal con fondo azul
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(70, 130, 200));

        // Agregar sidebar a la izquierda
        panelPrincipal.add(sidebar, BorderLayout.WEST);

        // Crear y agregar el panel central
        JPanel panelCentral = crearPanelCentral();
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        add(panelPrincipal, BorderLayout.CENTER);
    }

    // Método para crear el panel central con título y tabla
    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(70, 130, 200));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título PACIENTES
        JLabel titulo = new JLabel("PACIENTES", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(pacientesTable, BorderLayout.CENTER);

        return panel;
    }

    // Métodos públicos que los otros componentes pueden llamar
    public void volverAlMenuPrincipal() {
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea volver al menú principal?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            System.out.println("Volviendo al menú principal...");
            // Aquí implementarías la lógica para volver al menú
        }
    }

    public void navegarASeccion(String seccion) {
        if (seccion.equals("Pacientes")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ya se encuentra en la sección de Pacientes",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Navegando a la sección: " + seccion + "\n(En desarrollo)",
                    "Navegación",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    public void mostrarHistoriaClinica(String hc, String nombrePaciente) {
        VentanaHistoriaClinica ventana = new VentanaHistoriaClinica(this, hc, nombrePaciente);
        ventana.setVisible(true);
    }

    public void cambiarPagina(int numeroPagina) {
        System.out.println("Cambiando a página: " + numeroPagina);
        JOptionPane.showMessageDialog(
                this,
                "Cargando página " + numeroPagina + "...",
                "Paginación",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void buscarPaciente(String hc, String nombre) {
        System.out.println("Buscando - HC: " + hc + ", Nombre: " + nombre);

        String mensaje = "Buscando paciente";
        if (!hc.isEmpty() && !nombre.isEmpty()) {
            mensaje += " con HC: " + hc + " y Nombre: " + nombre;
        } else if (!hc.isEmpty()) {
            mensaje += " con HC: " + hc;
        } else if (!nombre.isEmpty()) {
            mensaje += " con Nombre: " + nombre;
        } else {
            mensaje = "Mostrando todos los pacientes";
        }

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Búsqueda",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea cerrar sesión?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            System.out.println("Cerrando sesión...");
            // Aquí implementarías la lógica para cerrar sesión
        }
    }

    // Método que se ejecuta al cerrar la ventana
    @Override
    public void dispose() {
        if (navbar != null) {
            navbar.detenerReloj();
        }
        super.dispose();
    }
}