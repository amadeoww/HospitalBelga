import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NavbarComponent extends JPanel {
    private JLabel timeLabel;
    private Timer timer;

    public NavbarComponent() {
        initializeComponent();
        setupLayout();
        startClock();
    }

    private void initializeComponent() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setLayout(new BorderLayout());

        timeLabel = new JLabel();
        timeLabel.setForeground(Color.BLACK);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 12));
    }

    private void setupLayout() {
        // Panel izquierdo - Logo y título
        JPanel logoPanel = createLogoPanel();

        // Panel derecho - Opciones de usuario
        JPanel userPanel = createUserPanel();

        add(logoPanel, BorderLayout.WEST);
        add(userPanel, BorderLayout.EAST);
    }

    private JPanel createLogoPanel() {
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(Color.WHITE);

        JLabel logoLabel = new JLabel("⚕");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        logoLabel.setForeground(new Color(70, 130, 200));

        JLabel titleLabel = new JLabel("Hospital Belga");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(70, 130, 200));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        logoPanel.add(logoLabel);
        logoPanel.add(Box.createHorizontalStrut(10));
        logoPanel.add(titleLabel);

        return logoPanel;
    }

    private JPanel createUserPanel() {
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setBackground(Color.WHITE);

        JLabel inicioLabel = createClickableLabel("Inicio");
        JLabel cerrarLabel = createClickableLabel("Cerrar sesión");
        JLabel usuarioLabel = new JLabel("👤 USUARIO");

        userPanel.add(inicioLabel);
        userPanel.add(Box.createHorizontalStrut(20));
        userPanel.add(cerrarLabel);
        userPanel.add(Box.createHorizontalStrut(20));
        userPanel.add(usuarioLabel);
        userPanel.add(Box.createHorizontalStrut(10));
        userPanel.add(timeLabel);

        return userPanel;
    }

    private JLabel createClickableLabel(String text) {
        JLabel label = new JLabel(text);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.setForeground(Color.DARK_GRAY);

        // Agregar funcionalidad
        label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                handleNavbarClick(text);
            }
        });

        return label;
    }

    private void handleNavbarClick(String option) {
        switch (option) {
            case "Inicio":
                System.out.println("Navegando a Inicio...");
                break;
            case "Cerrar sesión":
                int result = JOptionPane.showConfirmDialog(
                        this,
                        "¿Está seguro que desea cerrar sesión?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION
                );
                if (result == JOptionPane.YES_OPTION) {
                    System.out.println("Cerrando sesión...");
                    // Aquí puedes agregar la lógica para cerrar sesión
                }
                break;
        }
    }

    private void startClock() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat sdf = new SimpleDateFormat("h:mm a\ndd/MM/yyyy");
                String timeText = sdf.format(new Date());
                timeLabel.setText("<html>" + timeText.replace("\n", "<br>") + "</html>");
            }
        });
        timer.start();
    }

    public void stopClock() {
        if (timer != null) {
            timer.stop();
        }
    }
}