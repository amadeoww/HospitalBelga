import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.event.ActionListener;

public class PacientesTableComponent extends JPanel {
    private JTable pacientesTable;
    private DefaultTableModel tableModel;
    private JTextField hcField;
    private JTextField nombreField;
    private TableActionListener actionListener;

    // Interface para comunicación con la clase principal
    public interface TableActionListener {
        void onVerHistoriaClicked(String hc, String nombrePaciente);
        void onPaginaChanged(int pagina);
        void onBusquedaRealizada(String hc, String nombre);
    }

    public PacientesTableComponent() {
        initializeComponent();
        setupLayout();
        populateTable();
    }

    private void initializeComponent() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Campos de búsqueda
        hcField = new JTextField(15);
        nombreField = new JTextField(15);

        // Configurar tabla
        String[] columnNames = {"HC", "FECHA ▲", "NOMBRE", "CI", "HISTORIA"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer toda la tabla no editable
            }
        };

        pacientesTable = new JTable(tableModel);
        configureTable();
    }

    private void setupLayout() {
        // Panel de búsqueda
        JPanel searchPanel = createSearchPanel();
        add(searchPanel, BorderLayout.NORTH);

        // Tabla con scroll
        JScrollPane scrollPane = new JScrollPane(pacientesTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de paginación
        JPanel paginationPanel = createPaginationPanel();
        add(paginationPanel, BorderLayout.SOUTH);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.setBackground(Color.WHITE);

        JLabel searchTitle = new JLabel("INGRESE PARA BUSCAR");
        searchTitle.setFont(new Font("Arial", Font.BOLD, 16));
        searchTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Panel de campos
        JPanel fieldsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fieldsPanel.setBackground(Color.WHITE);

        JLabel hcLabel = new JLabel("HC:");
        JLabel nombreLabel = new JLabel("Nombre:");

        // Estilizar campos de texto
        styleTextField(hcField);
        styleTextField(nombreField);

        fieldsPanel.add(hcLabel);
        fieldsPanel.add(hcField);
        fieldsPanel.add(Box.createHorizontalStrut(20));
        fieldsPanel.add(nombreLabel);
        fieldsPanel.add(nombreField);

        // Botón de búsqueda
        JButton buscarButton = createBuscarButton();
        fieldsPanel.add(Box.createHorizontalStrut(20));
        fieldsPanel.add(buscarButton);

        // Agregar funcionalidad de búsqueda
        addSearchFunctionality();

        searchPanel.add(searchTitle);
        searchPanel.add(Box.createVerticalStrut(10));
        searchPanel.add(fieldsPanel);

        return searchPanel;
    }

    private void styleTextField(JTextField textField) {
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        textField.setFont(new Font("Arial", Font.PLAIN, 12));
    }

    private JButton createBuscarButton() {
        JButton buscarButton = new JButton("Buscar");
        buscarButton.setFocusPainted(false);
        buscarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buscarButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        buscarButton.addActionListener(e -> realizarBusqueda());

        return buscarButton;
    }

    private void addSearchFunctionality() {
        // Búsqueda al presionar Enter
        ActionListener enterAction = e -> realizarBusqueda();
        hcField.addActionListener(enterAction);
        nombreField.addActionListener(enterAction);
    }

    private void realizarBusqueda() {
        String hcText = hcField.getText().trim();
        String nombreText = nombreField.getText().trim();

        if (actionListener != null) {
            actionListener.onBusquedaRealizada(hcText, nombreText);
        } else {
            // Lógica de búsqueda local
            filtrarTabla(hcText, nombreText);
        }
    }

    private void filtrarTabla(String hc, String nombre) {
        // Implementación básica de filtrado
        System.out.println("Filtrando por HC: '" + hc + "', Nombre: '" + nombre + "'");

        // Aquí puedes implementar el filtrado real de la tabla
        // Por ahora solo mostramos en consola
    }

    private JPanel createPaginationPanel() {
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paginationPanel.setBackground(Color.WHITE);
        paginationPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        String[] pages = {"1", "2", "3", "4", "5", "6", "..."};
        for (int i = 0; i < pages.length; i++) {
            JButton pageButton = createPageButton(pages[i], i == 1, i + 1); // Página 2 activa
            paginationPanel.add(pageButton);
        }

        return paginationPanel;
    }

    private JButton createPageButton(String text, boolean isActive, int pageNumber) {
        JButton pageButton = new JButton(text);
        pageButton.setPreferredSize(new Dimension(35, 35));
        pageButton.setFocusPainted(false);
        pageButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (isActive) {
            pageButton.setBackground(new Color(70, 130, 200));
            pageButton.setForeground(Color.WHITE);
            pageButton.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 200)));
        } else {
            pageButton.setBackground(Color.LIGHT_GRAY);
            pageButton.setForeground(Color.BLACK);
            pageButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }

        // Efecto hover para botones inactivos
        if (!isActive && !text.equals("...")) {
            pageButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    pageButton.setBackground(new Color(220, 220, 220));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    pageButton.setBackground(Color.LIGHT_GRAY);
                }
            });
        }

        // Funcionalidad de clic
        if (!text.equals("...")) {
            pageButton.addActionListener(e -> {
                if (actionListener != null) {
                    actionListener.onPaginaChanged(pageNumber);
                } else {
                    System.out.println("Página " + text + " seleccionada");
                }
            });
        }

        return pageButton;
    }

    private void configureTable() {
        // Configurar columnas
        TableColumnModel columnModel = pacientesTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(80);  // HC
        columnModel.getColumn(1).setPreferredWidth(100); // FECHA
        columnModel.getColumn(2).setPreferredWidth(300); // NOMBRE
        columnModel.getColumn(3).setPreferredWidth(100); // CI
        columnModel.getColumn(4).setPreferredWidth(120); // HISTORIA

        // Configurar apariencia
        pacientesTable.setRowHeight(35);
        pacientesTable.getTableHeader().setBackground(new Color(240, 240, 240));
        pacientesTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        pacientesTable.setGridColor(new Color(230, 230, 230));
        pacientesTable.setSelectionBackground(new Color(220, 235, 255));
        pacientesTable.setFont(new Font("Arial", Font.PLAIN, 12));

        // Renderer personalizado para los enlaces
        pacientesTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (column == 4 && value != null && value.toString().equals("VER HISTORIA")) {
                    setForeground(new Color(70, 130, 200));
                    setFont(getFont().deriveFont(Font.BOLD));
                    setText("🔍 " + value.toString());
                } else {
                    setForeground(isSelected ? Color.BLACK : Color.DARK_GRAY);
                    setFont(getFont().deriveFont(Font.PLAIN));
                }

                // Centrar el contenido de ciertas columnas
                if (column == 0 || column == 3 || column == 4) {
                    setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    setHorizontalAlignment(SwingConstants.LEFT);
                }

                return this;
            }
        });

        // Agregar funcionalidad de clic en "VER HISTORIA"
        pacientesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = pacientesTable.rowAtPoint(e.getPoint());
                int col = pacientesTable.columnAtPoint(e.getPoint());

                if (row >= 0 && col == 4) {
                    String hc = pacientesTable.getValueAt(row, 0).toString();
                    String nombre = pacientesTable.getValueAt(row, 2).toString();

                    if (actionListener != null) {
                        actionListener.onVerHistoriaClicked(hc, nombre);
                    } else {
                        System.out.println("Ver historia de: " + nombre + " (HC: " + hc + ")");
                    }
                }
            }
        });
    }

    private void populateTable() {
        Object[][] data = {
                {"569506", "13/09/2025", "Antonio Martin Rodriguez Verduguez", "18121313", "VER HISTORIA"},
                {"642322", "10/09/2025", "Maria Antonieta Cardenas Soliz", "1212121", "VER HISTORIA"},
                {"232323", "8/08/2025", "Marco Jose Betancourt Rivera", "121212", "VER HISTORIA"},
                {"454545", "7/08/2025", "Marcela Valeria Soto Mendez", "424242", "VER HISTORIA"},
                {"5845845", "5/08/2025", "Mary Rivera Soria", "232323", "VER HISTORIA"},
                {"5575757", "6/5/2025", "Jaime Zeballos Ovando", "53535353", "VER HISTORIA"}
        };

        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }

    // Métodos públicos para la API del componente
    public void setActionListener(TableActionListener listener) {
        this.actionListener = listener;
    }

    public void addPaciente(Object[] pacienteData) {
        tableModel.addRow(pacienteData);
    }

    public void clearTable() {
        tableModel.setRowCount(0);
    }

    public String getSelectedPatientHC() {
        int selectedRow = pacientesTable.getSelectedRow();
        if (selectedRow >= 0) {
            return pacientesTable.getValueAt(selectedRow, 0).toString();
        }
        return null;
    }

    public void updateTableData(Object[][] newData) {
        tableModel.setRowCount(0);
        for (Object[] row : newData) {
            tableModel.addRow(row);
        }
    }

    public void clearSearchFields() {
        hcField.setText("");
        nombreField.setText("");
    }
}