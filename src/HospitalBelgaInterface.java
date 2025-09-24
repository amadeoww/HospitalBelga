import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;

public class HospitalBelgaInterface extends JFrame implements
        SidebarComponent.NavigationListener,
        PacientesTableComponent.TableActionListener { //No está claro el motivo de éstas implementaciones, no se aplican los fundamentos de O.O.P.

    private NavbarComponent navbar;
    private SidebarComponent sidebar;
    private PacientesTableComponent pacientesTable;

    public HospitalBelgaInterface() {
        initializeComponents();
        setupLayout();
        connectComponents();
    }

    private void initializeComponents() {
        setTitle("Hospital Belga - Sistema de Pacientes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 600));

        // Inicializar componentes
        navbar = new NavbarComponent();
        sidebar = new SidebarComponent();
        pacientesTable = new PacientesTableComponent();
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Agregar navbar
        add(navbar, BorderLayout.NORTH);

        // Panel principal con sidebar y contenido
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(70, 130, 200));

        // Agregar sidebar
        mainPanel.add(sidebar, BorderLayout.WEST);

        // Panel central con título y tabla
        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void connectComponents() {
        // Conectar los listeners para la comunicación entre componentes
        sidebar.setNavigationListener(this);
        pacientesTable.setActionListener(this);
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(70, 130, 200));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título PACIENTES
        JLabel titleLabel = new JLabel("PACIENTES", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        centerPanel.add(titleLabel, BorderLayout.NORTH);
        centerPanel.add(pacientesTable, BorderLayout.CENTER);

        return centerPanel;
    }

    // La implementación de estos médotos sugieren que debieran ser componentens independientes y tener su implementación separada de este contexto.
    // Implementación de SidebarComponent.NavigationListener
    @Override
    public void onVolverClicked() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea volver al menú principal?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            System.out.println("Volviendo al menú principal...");
            // Aquí puedes implementar la navegación al menú principal
        }
    } 

    @Override
    public void onPacientesClicked() {
        System.out.println("Ya estás en la sección de Pacientes");
        // Podrías actualizar la vista o mostrar un mensaje
        JOptionPane.showMessageDialog(
                this,
                "Ya se encuentra en la sección de Pacientes",
                "Información",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void onNavigationRequested(String destination) {
        switch (destination) {
            case "Doctores":
                navigateToSection("Doctores");
                break;
            case "Citas":
                navigateToSection("Citas");
                break;
            case "Historia Clínica":
                navigateToSection("Historia Clínica");
                break;
            default:
                System.out.println("Navegación no implementada para: " + destination);
                break;
        }
    }

    private void navigateToSection(String section) {
        // Mostrar mensaje temporal - aquí implementarías la navegación real
        JOptionPane.showMessageDialog(
                this,
                "Navegando a la sección: " + section + "\n(Funcionalidad en desarrollo)",
                "Navegación",
                JOptionPane.INFORMATION_MESSAGE
        );
        System.out.println("Navegando a: " + section);
    }

    // Implementación de PacientesTableComponent.TableActionListener
    @Override
    public void onVerHistoriaClicked(String hc, String nombrePaciente) {
        // Crear ventana modal para mostrar la historia clínica
        showHistoriaClinica(hc, nombrePaciente);
    }

    @Override
    public void onPaginaChanged(int pagina) {
        System.out.println("Cambiando a página: " + pagina);

        // Aquí implementarías la lógica para cargar datos de la página específica
        // Por ahora solo mostramos un mensaje
        JOptionPane.showMessageDialog(
                this,
                "Cargando página " + pagina + "...\n(Funcionalidad en desarrollo)",
                "Paginación",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void onBusquedaRealizada(String hc, String nombre) {
        System.out.println("Búsqueda realizada - HC: '" + hc + "', Nombre: '" + nombre + "'");

        // Simulación de búsqueda
        if (!hc.isEmpty() || !nombre.isEmpty()) {
            String mensaje = "Buscando";
            if (!hc.isEmpty()) mensaje += " HC: " + hc;
            if (!nombre.isEmpty()) {
                if (!hc.isEmpty()) mensaje += " y";
                mensaje += " Nombre: " + nombre;
            }

            // Mostrar resultado de búsqueda
            JOptionPane.showMessageDialog(
                    this,
                    mensaje + "\n\n(En una implementación real aquí se filtrarían los resultados)",
                    "Resultado de Búsqueda",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            // Si ambos campos están vacíos, mostrar todos los pacientes
            JOptionPane.showMessageDialog(
                    this,
                    "Mostrando todos los pacientes",
                    "Búsqueda",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    /**
    * Lo ideal sería que venga el objeto para que los componentes puedan cargar la vista con su respectiva información, por ejemplo un modelo PACIENTE que tenga los atributos correctamente configurados,
    así también podamos cumplir con el criterios de CALIDAD DE SOFTWARE*/
    private void showHistoriaClinica(String hc, String nombrePaciente) { 
        // Crear ventana modal para la historia clínica
        JDialog historiaDialog = new JDialog(this, "Historia Clínica", true);
        historiaDialog.setSize(600, 400);
        historiaDialog.setLocationRelativeTo(this);

        // Panel principal del diálogo
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titleLabel = new JLabel("Historia Clínica - " + nombrePaciente);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Información del paciente
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Información del Paciente"));

        infoPanel.add(new JLabel("HC:"));
        infoPanel.add(new JLabel(hc));
        infoPanel.add(new JLabel("Nombre:"));
        infoPanel.add(new JLabel(nombrePaciente));
        infoPanel.add(new JLabel("Estado:"));
        infoPanel.add(new JLabel("Activo"));
        infoPanel.add(new JLabel("Última consulta:"));
        infoPanel.add(new JLabel("15/09/2025"));

        // Área de texto para historial médico
        JTextArea historiaArea = new JTextArea();
        historiaArea.setText("Historial médico del paciente:\n\n" +
                "• Consulta 13/09/2025: Revisión general\n" +
                "• Consulta 01/08/2025: Seguimiento tratamiento\n" +
                "• Consulta 15/07/2025: Consulta inicial\n\n" +
                "Observaciones:\n" +
                "Paciente en buen estado general. Continuar con tratamiento actual.");
        historiaArea.setEditable(false);
        historiaArea.setBackground(new Color(245, 245, 245));
        historiaArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(historiaArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Historial Médico"));

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton cerrarButton = new JButton("Cerrar");
        JButton imprimirButton = new JButton("Imprimir");

        cerrarButton.addActionListener(e -> historiaDialog.dispose());
        imprimirButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(historiaDialog,
                    "Función de impresión en desarrollo", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        buttonPanel.add(imprimirButton);
        buttonPanel.add(cerrarButton);

        // Ensamblar el diálogo
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(infoPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        historiaDialog.add(mainPanel);
        historiaDialog.setVisible(true);
    }

    // Métodos públicos para control externo
    public void refreshPacientesTable() {
        // Método para actualizar la tabla desde fuera
        System.out.println("Actualizando tabla de pacientes...");
        // Aquí implementarías la lógica para recargar datos
    }

    public void navigateToPaciente(String hc) {
        // Método para navegar directamente a un paciente específico
        System.out.println("Navegando al paciente con HC: " + hc);
        // Aquí implementarías la lógica para buscar y seleccionar el paciente
    }

    @Override
    public void dispose() {
        // Limpiar recursos antes de cerrar
        if (navbar != null) {
            navbar.stopClock();
        }
        super.dispose();
    }

}
