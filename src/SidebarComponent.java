import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SidebarComponent extends JPanel {
    private NavigationListener navigationListener;

    // Interface para comunicación con la clase principal
    public interface NavigationListener {
        void onVolverClicked();
        void onPacientesClicked();
        void onNavigationRequested(String destination);
    }

    public SidebarComponent() {
        initializeComponent();
        setupLayout();
    }

    private void initializeComponent() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(200, 0));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void setupLayout() {
        // Botón VOLVER
        JButton volverButton = createVolverButton();

        // Panel de navegación
        JPanel navigationPanel = createNavigationPanel();

        add(volverButton, BorderLayout.NORTH);

        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.setBackground(Color.WHITE);
        centerContainer.add(navigationPanel, BorderLayout.NORTH);
        centerContainer.add(Box.createVerticalGlue(), BorderLayout.CENTER);

        add(centerContainer, BorderLayout.CENTER);
    }

    private JButton createVolverButton() {
        JButton volverButton = new JButton("VOLVER");
        volverButton.setBackground(Color.LIGHT_GRAY);
        volverButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        volverButton.setFocusPainted(false);
        volverButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Agregar efecto hover
        volverButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                volverButton.setBackground(Color.GRAY);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                volverButton.setBackground(Color.LIGHT_GRAY);
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (navigationListener != null) {
                    navigationListener.onVolverClicked();
                } else {
                    System.out.println("Botón VOLVER presionado");
                }
            }
        });

        return volverButton;
    }

    private JPanel createNavigationPanel() {
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.Y_AXIS));
        navigationPanel.setBackground(Color.WHITE);
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        // Crear enlaces de navegación
        JLabel pacientesLink = createNavigationLink("Pacientes", true);
        JLabel doctoresLink = createNavigationLink("Doctores", false);
        JLabel citasLink = createNavigationLink("Citas", false);
        JLabel historiaLink = createNavigationLink("Historia Clínica", false);

        // Agregar al panel
        navigationPanel.add(pacientesLink);
        navigationPanel.add(Box.createVerticalStrut(15));
        navigationPanel.add(doctoresLink);
        navigationPanel.add(Box.createVerticalStrut(15));
        navigationPanel.add(citasLink);
        navigationPanel.add(Box.createVerticalStrut(15));
        navigationPanel.add(historiaLink);

        return navigationPanel;
    }

    private JLabel createNavigationLink(String text, boolean isActive) {
        JLabel link = new JLabel(text);
        link.setFont(new Font("Arial", Font.PLAIN, 14));
        link.setCursor(new Cursor(Cursor.HAND_CURSOR));
        link.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (isActive) {
            link.setForeground(new Color(70, 130, 200));
            link.setFont(new Font("Arial", Font.BOLD, 14));
        } else {
            link.setForeground(Color.DARK_GRAY);
        }

        // Agregar efectos hover
        link.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!isActive) {
                    link.setForeground(new Color(70, 130, 200));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!isActive) {
                    link.setForeground(Color.DARK_GRAY);
                }
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                handleNavigationClick(text);
            }
        });

        return link;
    }

    private void handleNavigationClick(String destination) {
        if (navigationListener != null) {
            switch (destination) {
                case "Pacientes":
                    navigationListener.onPacientesClicked();
                    break;
                default:
                    navigationListener.onNavigationRequested(destination);
                    break;
            }
        } else {
            System.out.println("Navegando a: " + destination);
        }
    }

    // Método para establecer el listener de navegación
    public void setNavigationListener(NavigationListener listener) {
        this.navigationListener = listener;
    }

    // Método para actualizar el estado activo de los enlaces
    public void setActiveNavigation(String activeItem) {
        // Aquí puedes implementar la lógica para cambiar el estado activo
        System.out.println("Activando navegación: " + activeItem);
    }
}