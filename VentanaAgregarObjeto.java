/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Proyecto;

/**
 *
 * @author cesar
 */
public class VentanaAgregarObjeto extends javax.swing.JFrame {

    private BaseDatos bd;

    /**
     * Creates new form ObjetoInventario
     */
    public VentanaAgregarObjeto() {
        bd = new BaseDatos();
        initComponents();
    }

    private void initComponents() {
        setTitle("Agregar Objeto al Inventario");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);

        java.awt.Container contentPane = getContentPane();
        contentPane.setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;

        // Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        contentPane.add(new javax.swing.JLabel("Nombre:"), gbc);
        javax.swing.JTextField txtNombre = new javax.swing.JTextField(20);
        gbc.gridx = 1; gbc.gridy = 0;
        contentPane.add(txtNombre, gbc);

        // Descripción
        gbc.gridx = 0; gbc.gridy = 1;
        contentPane.add(new javax.swing.JLabel("Descripción:"), gbc);
        javax.swing.JTextArea txtDescripcion = new javax.swing.JTextArea(4, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        javax.swing.JScrollPane scrollDesc = new javax.swing.JScrollPane(txtDescripcion);
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.fill = java.awt.GridBagConstraints.BOTH;
        contentPane.add(scrollDesc, gbc);
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;

        // Precio
        gbc.gridx = 0; gbc.gridy = 2;
        contentPane.add(new javax.swing.JLabel("Precio:"), gbc);
        javax.swing.JTextField txtPrecio = new javax.swing.JTextField(10);
        gbc.gridx = 1; gbc.gridy = 2;
        contentPane.add(txtPrecio, gbc);

        // Cantidad
        gbc.gridx = 0; gbc.gridy = 3;
        contentPane.add(new javax.swing.JLabel("Cantidad:"), gbc);
        javax.swing.JTextField txtCantidad = new javax.swing.JTextField(10);
        gbc.gridx = 1; gbc.gridy = 3;
        contentPane.add(txtCantidad, gbc);

        // Botones
        javax.swing.JButton btnAgregar = new javax.swing.JButton("Agregar");
        javax.swing.JButton btnCancelar = new javax.swing.JButton("Cancelar");
        java.awt.Panel panelBotones = new java.awt.Panel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnCancelar);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = java.awt.GridBagConstraints.CENTER;
        contentPane.add(panelBotones, gbc);

        // Acción Cancelar
        btnCancelar.addActionListener(e -> dispose());

        // Acción Agregar
        btnAgregar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            String precioStr = txtPrecio.getText().trim();
            String cantidadStr = txtCantidad.getText().trim();

            if (nombre.isEmpty() || descripcion.isEmpty() || precioStr.isEmpty() || cantidadStr.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }

            double precio;
            int cantidad;
            try {
                precio = Double.parseDouble(precioStr);
            } catch (NumberFormatException ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "El precio debe ser un número válido.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                cantidad = Integer.parseInt(cantidadStr);
                if (cantidad < 0) {
                    javax.swing.JOptionPane.showMessageDialog(this, "La cantidad no puede ser negativa.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }

            Inventario inventario = new Inventario(0, nombre, cantidad, descripcion, precio);
            bd.agregarInventario(inventario);

            javax.swing.JOptionPane.showMessageDialog(this, "Objeto agregado correctamente.", "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });
    }

}
