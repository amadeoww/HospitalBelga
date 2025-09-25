import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.Box;
import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;



public class NavbarComponent extends JPanel {
    // Atributos
    private JLabel etiquetaHora;
    private Timer temporizador;
    private HospitalBelgaInterface ventanaPrincipal; // Referencia a la ventana principal

    // Constructor - recibe la ventana principal para poder comunicarse
    public NavbarComponent(HospitalBelgaInterface ventana) {
        this.ventanaPrincipal = ventana;
        configurarPanel();
        crearElementos();
        iniciarReloj();
    }

    // Configurar las propiedades básicas del panel
    private void configurarPanel() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setLayout(new BorderLayout());
    }

    // Crear todos los elementos del navbar
    private void crearElementos() {
        // Panel izquierdo - Logo y título
        JPanel panelIzquierdo = crearPanelLogo();

        // Panel derecho - Opciones y reloj
        JPanel panelDerecho = crearPanelUsuario();

        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.EAST);
    }

    // Crear el panel del logo y título
    private JPanel crearPanelLogo() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);

        // Icono médico
        JLabel iconoMedico = new JLabel("⚕");
        iconoMedico.setFont(new Font("Arial", Font.BOLD, 24));
        iconoMedico.setForeground(new Color(70, 130, 200));

        // Título del hospital
        JLabel titulo = new JLabel("Hospital Belga");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        titulo.setOpaque(true);
        titulo.setBackground(new Color(70, 130, 200));
        titulo.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        panel.add(iconoMedico);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(titulo);

        return panel;
    }

    // Crear el panel de usuario y opciones
    private JPanel crearPanelUsuario() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(Color.WHITE);

        // Crear etiquetas clicleables
        JLabel linkInicio = crearLinkCliceable("Inicio");
        JLabel linkCerrarSesion = crearLinkCliceable("Cerrar sesión");
        JLabel etiquetaUsuario = new JLabel("👤 USUARIO");

        // Crear etiqueta de hora
        etiquetaHora = new JLabel();
        etiquetaHora.setForeground(Color.BLACK);
        etiquetaHora.setFont(new Font("Arial", Font.BOLD, 12));

        // Agregar elementos al panel
        panel.add(linkInicio);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(linkCerrarSesion);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(etiquetaUsuario);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(etiquetaHora);

        return panel;
    }

    // Crear una etiqueta que se puede clickear
    private JLabel crearLinkCliceable(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        etiqueta.setForeground(Color.DARK_GRAY);

        // Agregar evento de click
        etiqueta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                manejarClickNavbar(texto);
            }
        });

        return etiqueta;
    }

    // Manejar los clicks en las opciones del navbar
    private void manejarClickNavbar(String opcion) {
        if (opcion.equals("Inicio")) {
            System.out.println("Navegando a Inicio...");
        } else if (opcion.equals("Cerrar sesión")) {
            ventanaPrincipal.cerrarSesion();
        }
    }

    // Iniciar el reloj que muestra la hora
    private void iniciarReloj() {
        temporizador = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarHora();
            }
        });
        temporizador.start();
        actualizarHora(); // Mostrar la hora inmediatamente
    }

    // Actualizar la hora mostrada
    private void actualizarHora() {
        SimpleDateFormat formato = new SimpleDateFormat("h:mm a\ndd/MM/yyyy");
        String textoHora = formato.format(new Date());
        etiquetaHora.setText("<html>" + textoHora.replace("\n", "<br>") + "</html>");
    }

    // Detener el reloj (importante para liberar recursos)
    public void detenerReloj() {
        if (temporizador != null) {
            temporizador.stop();
        }
    }
}