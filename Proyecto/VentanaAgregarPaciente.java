/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Proyecto;

import java.security.Timestamp;
import java.sql.Date;
import java.text.ParseException;

/**
 *
 * @author cesar
 */
public class VentanaAgregarPaciente extends javax.swing.JFrame {

    private BaseDatos bd;

    public VentanaAgregarPaciente() {
        initComponents();
        bd = new BaseDatos();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setTitle("Agregar Paciente");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();

        javax.swing.JLabel lblNombre = new javax.swing.JLabel("Nombre:");
        javax.swing.JTextField txtNombre = new javax.swing.JTextField(20);

        javax.swing.JLabel lblFecha = new javax.swing.JLabel("Fecha de nacimiento:");
        javax.swing.JTextField txtFecha = new javax.swing.JTextField(10);

        javax.swing.JLabel lblTelefono = new javax.swing.JLabel("Teléfono:");
        javax.swing.JTextField txtTelefono = new javax.swing.JTextField(15);

        javax.swing.JLabel lblCorreo = new javax.swing.JLabel("Correo:");
        javax.swing.JTextField txtCorreo = new javax.swing.JTextField(20);

        javax.swing.JLabel lblDireccion = new javax.swing.JLabel("Dirección:");
        javax.swing.JTextArea txtDireccion = new javax.swing.JTextArea(3, 20);
        javax.swing.JScrollPane scrollDireccion = new javax.swing.JScrollPane(txtDireccion);

        javax.swing.JLabel lblGenero = new javax.swing.JLabel("Género:");
        javax.swing.JComboBox<String> cmbGenero = new javax.swing.JComboBox<>(new String[]{"Femenino", "Masculino", "Otro"});

        javax.swing.JButton btnAgregar = new javax.swing.JButton("Agregar");
        javax.swing.JButton btnCancelar = new javax.swing.JButton("Cancelar");

        // Labels - columna 0
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = java.awt.GridBagConstraints.LINE_END;
        gbc.insets = new java.awt.Insets(5, 10, 5, 5);
        gbc.weightx = 0.2;
        add(lblNombre, gbc);

        gbc.gridy++;
        add(lblFecha, gbc);

        gbc.gridy++;
        add(lblTelefono, gbc);

        gbc.gridy++;
        add(lblCorreo, gbc);

        gbc.gridy++;
        add(lblDireccion, gbc);

        gbc.gridy++;
        add(lblGenero, gbc);

        // Inputs - columna 1
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = java.awt.GridBagConstraints.LINE_START;
        gbc.insets = new java.awt.Insets(5, 5, 5, 10);
        gbc.weightx = 0.8;
        add(txtNombre, gbc);

        gbc.gridy++;
        add(txtFecha, gbc);

        gbc.gridy++;
        add(txtTelefono, gbc);

        gbc.gridy++;
        add(txtCorreo, gbc);

        gbc.gridy++;
        add(scrollDireccion, gbc);

        gbc.gridy++;
        add(cmbGenero, gbc);

        // Botones
        javax.swing.JPanel panelBotones = new javax.swing.JPanel();
        panelBotones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = java.awt.GridBagConstraints.LINE_END;
        gbc.insets = new java.awt.Insets(15, 10, 10, 10);
        add(panelBotones, gbc);

        // Acción botón Cancelar
        btnCancelar.addActionListener(e -> dispose());

        // Acción botón Agregar
        btnAgregar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String fecha = txtFecha.getText().trim();
            Date fechaNacimiento = null;
            try {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
                sdf.setLenient(false);
                java.util.Date utilDate = sdf.parse(fecha);
                fechaNacimiento = new Date(utilDate.getTime());

            } catch (IllegalArgumentException ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "Fecha inválida. Formato esperado: DD-MM-YYYY", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            } catch (ParseException e1) {
                javax.swing.JOptionPane.showMessageDialog(this, "Fecha inválida. Formato esperado: DD-MM-YYYY", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
            String telefono = txtTelefono.getText().trim();
            String correo = txtCorreo.getText().trim();
            String direccion = txtDireccion.getText().trim();
            int genero = cmbGenero.getSelectedIndex();


            Paciente paciente = new Paciente(0, nombre, telefono, correo, direccion, fechaNacimiento, genero);
            bd.agregarPaciente(paciente);
            dispose();
        });

        pack();
        setLocationRelativeTo(null);
    }                       

}
