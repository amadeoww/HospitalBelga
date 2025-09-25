package views;

import controllers.PacienteControlador;
import models.Paciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PacientesTableComponent extends JPanel {
    // Atributos
    private HospitalBelgaInterface ventanaPrincipal;
    private JTable tablaPacientes;
    private DefaultTableModel modeloTabla;
    private JTextField campoHC;
    private JTextField campoNombre;

    // Constructor - recibe la ventana principal
    public PacientesTableComponent(HospitalBelgaInterface ventana) {
        this.ventanaPrincipal = ventana;
        configurarPanel();
        crearElementos();
        cargarDatosPacientes();
    }

    // Configurar las propiedades básicas del panel
    private void configurarPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    // Crear todos los elementos del componente
    private void crearElementos() {
        // Panel de búsqueda arriba
        JPanel panelBusqueda = crearPanelBusqueda();
        add(panelBusqueda, BorderLayout.NORTH);

        // Tabla en el centro
        JScrollPane scrollTabla = crearTabla();
        add(scrollTabla, BorderLayout.CENTER);

        // Panel de paginación abajo
        JPanel panelPaginacion = crearPanelPaginacion();
        add(panelPaginacion, BorderLayout.SOUTH);
    }

    // Crear el panel de búsqueda
    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        // Título
        JLabel titulo = new JLabel("INGRESE PARA BUSCAR");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Panel con los campos de búsqueda
        JPanel panelCampos = crearPanelCamposBusqueda();

        panel.add(titulo);
        panel.add(Box.createVerticalStrut(10)); // Espacio
        panel.add(panelCampos);

        return panel;
    }

    // Crear el panel con los campos de búsqueda
    private JPanel crearPanelCamposBusqueda() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);

        // Etiquetas y campos
        JLabel etiquetaHC = new JLabel("HC:");
        JLabel etiquetaNombre = new JLabel("Nombre:");

        campoHC = new JTextField(15);
        campoNombre = new JTextField(15);

        // Estilizar los campos de texto
        configurarCampoTexto(campoHC);
        configurarCampoTexto(campoNombre);

        // Botón de búsqueda
        JButton botonBuscar = crearBotonBuscar();

        // Agregar funcionalidad de Enter
        campoHC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarBusqueda();
            }
        });

        campoNombre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarBusqueda();
            }
        });

        // Agregar elementos al panel
        panel.add(etiquetaHC);
        panel.add(campoHC);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(etiquetaNombre);
        panel.add(campoNombre);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(botonBuscar);

        return panel;
    }

    // Configurar estilo de los campos de texto
    private void configurarCampoTexto(JTextField campo) {
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        campo.setFont(new Font("Arial", Font.PLAIN, 12));
    }

    // Crear botón de búsqueda
    private JButton crearBotonBuscar() {
        JButton boton = new JButton("Buscar");
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarBusqueda();
            }
        });

        return boton;
    }

    // Realizar la búsqueda
    private void realizarBusqueda() {
        String hc = campoHC.getText().trim();
        String nombre = campoNombre.getText().trim();

        // Llamar al método de la ventana principal
        ventanaPrincipal.buscarPaciente(hc, nombre);
    }

    // Crear la tabla de pacientes
    private JScrollPane crearTabla() {
        // Definir las columnas
        String[] nombreColumnas = {"HC", "FECHA ▲", "NOMBRE", "CI", "HISTORIA"};

        // Crear modelo de tabla (no editable)
        modeloTabla = new DefaultTableModel(nombreColumnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer toda la tabla de solo lectura
            }
        };

        // Crear tabla
        tablaPacientes = new JTable(modeloTabla);
        configurarTabla();

        // Crear panel con scroll
        JScrollPane scroll = new JScrollPane(tablaPacientes);
        scroll.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        scroll.getViewport().setBackground(Color.WHITE);

        return scroll;
    }

    // Configurar apariencia y comportamiento de la tabla
    private void configurarTabla() {
        // Configurar tamaños de columnas
        TableColumnModel columnas = tablaPacientes.getColumnModel();
        columnas.getColumn(0).setPreferredWidth(80);  // HC
        columnas.getColumn(1).setPreferredWidth(100); // FECHA
        columnas.getColumn(2).setPreferredWidth(300); // NOMBRE
        columnas.getColumn(3).setPreferredWidth(100); // CI
        columnas.getColumn(4).setPreferredWidth(120); // HISTORIA

        // Configurar apariencia general
        tablaPacientes.setRowHeight(35);
        tablaPacientes.getTableHeader().setBackground(new Color(240, 240, 240));
        tablaPacientes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaPacientes.setGridColor(new Color(230, 230, 230));
        tablaPacientes.setSelectionBackground(new Color(220, 235, 255));
        tablaPacientes.setFont(new Font("Arial", Font.PLAIN, 12));

        // Configurar renderer personalizado para dar formato a las celdas
        tablaPacientes.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Configurar estilo especial para la columna "HISTORIA"
                if (column == 4 && value != null && value.toString().equals("VER HISTORIA")) {
                    setForeground(new Color(70, 130, 200));
                    setFont(getFont().deriveFont(Font.BOLD));
                    setText("🔍 " + value.toString());
                } else {
                    setForeground(isSelected ? Color.BLACK : Color.DARK_GRAY);
                    setFont(getFont().deriveFont(Font.PLAIN));
                }

                // Centrar contenido en ciertas columnas
                if (column == 0 || column == 3 || column == 4) {
                    setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    setHorizontalAlignment(SwingConstants.LEFT);
                }

                return this;
            }
        });

        // Agregar evento de click para "VER HISTORIA"
        tablaPacientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaPacientes.rowAtPoint(e.getPoint());
                int columna = tablaPacientes.columnAtPoint(e.getPoint());

                // Si hizo click en la columna "HISTORIA"
                if (fila >= 0 && columna == 4) {
                    String hc = tablaPacientes.getValueAt(fila, 0).toString();
                    String nombre = tablaPacientes.getValueAt(fila, 2).toString();

                    // Llamar al método de la ventana principal
                    ventanaPrincipal.mostrarHistoriaClinica(hc, nombre);
                }
            }
        });
    }

    // Crear el panel de paginación
    private JPanel crearPanelPaginacion() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        // Crear botones de página
        String[] paginas = {"1", "2", "3", "4", "5", "6", "..."};
        for (int i = 0; i < paginas.length; i++) {
            boolean estaActivo = (i == 1); // Página 2 activa por defecto
            JButton botonPagina = crearBotonPagina(paginas[i], estaActivo, i + 1);
            panel.add(botonPagina);
        }

        return panel;
    }

    // Crear un botón de página
    private JButton crearBotonPagina(String texto, boolean estaActivo, int numeroPagina) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(35, 35));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Configurar estilo según si está activo
        if (estaActivo) {
            boton.setBackground(new Color(70, 130, 200));
            boton.setForeground(Color.WHITE);
            boton.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 200)));
        } else {
            boton.setBackground(Color.LIGHT_GRAY);
            boton.setForeground(Color.BLACK);
            boton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }

        // Agregar efecto hover para botones inactivos
        if (!estaActivo && !texto.equals("...")) {
            boton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    boton.setBackground(new Color(220, 220, 220));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    boton.setBackground(Color.LIGHT_GRAY);
                }
            });
        }

        // Agregar funcionalidad de click (excepto para "...")
        if (!texto.equals("...")) {
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ventanaPrincipal.cambiarPagina(numeroPagina);
                }
            });
        }

        return boton;
    }

    // Cargar datos de ejemplo en la tabla
    private void cargarDatosPacientes() {
        Object[][] datosPacientes = {
                {"569506", "13/09/2025", "Antonio Martin Rodriguez Verduguez", "18121313", "VER HISTORIA"},
                {"642322", "10/09/2025", "Maria Antonieta Cardenas Soliz", "1212121", "VER HISTORIA"},
                {"232323", "8/08/2025", "Marco Jose Betancourt Rivera", "121212", "VER HISTORIA"},
                {"454545", "7/08/2025", "Marcela Valeria Soto Mendez", "424242", "VER HISTORIA"},
                {"5845845", "5/08/2025", "Mary Rivera Soria", "232323", "VER HISTORIA"},
                {"5575757", "6/5/2025", "Jaime Zeballos Ovando", "53535353", "VER HISTORIA"}
        };

        for (Object[] fila : datosPacientes) {
            modeloTabla.addRow(fila);
        }
    }

    // Métodos públicos para interactuar con el componente desde fuera
    public void agregarPaciente(Object[] datosPaciente) {
        modeloTabla.addRow(datosPaciente);
    }

    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    public String obtenerHCPacienteSeleccionado() {
        int filaSeleccionada = tablaPacientes.getSelectedRow();
        if (filaSeleccionada >= 0) {
            return tablaPacientes.getValueAt(filaSeleccionada, 0).toString();
        }
        return null;
    }

    public void actualizarDatosTabla(Object[][] nuevosDatos) {
        modeloTabla.setRowCount(0);
        for (Object[] fila : nuevosDatos) {
            modeloTabla.addRow(fila);
        }
    }

    public void limpiarCamposBusqueda() {
        campoHC.setText("");
        campoNombre.setText("");
    }
}