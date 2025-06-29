/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Proyecto;

import java.util.ArrayList;

/**
 *
 * @author cesar
 */
public class VentanaAgregarCita extends javax.swing.JFrame {

    private BaseDatos bd;

    public VentanaAgregarCita() {
        // Crea la conexión a la base de datos
        bd = new BaseDatos();
        
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Configurar el cierre de la ventana
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc;

        // Fila 1 - Paciente
        javax.swing.JLabel lblPaciente = new javax.swing.JLabel("Paciente:");
        javax.swing.JTextField txtPaciente = new javax.swing.JTextField(15);
        javax.swing.JButton btnBuscarPaciente = new javax.swing.JButton("Buscar");
        javax.swing.JPanel pnlPaciente = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));
        pnlPaciente.add(txtPaciente);
        pnlPaciente.add(btnBuscarPaciente);
        gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = java.awt.GridBagConstraints.EAST;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblPaciente, gbc);
        gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(pnlPaciente, gbc);
        
        btnBuscarPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String nombrePaciente = txtPaciente.getText().trim();
                if (!nombrePaciente.isEmpty()) {
                    ArrayList<Paciente> pacientes = bd.buscaPacientePorNombre(nombrePaciente);
                    if (!pacientes.isEmpty()) {
                        javax.swing.JOptionPane.showMessageDialog(VentanaAgregarCita.this, 
                        "Paciente encontrado: " + pacientes.get(0).getNombre(), 
                        "Información", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(VentanaAgregarCita.this, "Paciente no encontrado", "Información", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    javax.swing.JOptionPane.showMessageDialog(VentanaAgregarCita.this, "Ingrese el nombre del paciente", "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Fila 2 - Doctor
        javax.swing.JButton btnAgregarPaciente = new javax.swing.JButton("Agregar nuevo paciente");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = java.awt.GridBagConstraints.CENTER;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnAgregarPaciente, gbc);

        // Fila 3 - Fecha
        javax.swing.JLabel lblFecha = new javax.swing.JLabel("Fecha:");
        javax.swing.JSpinner spinnerFecha = new javax.swing.JSpinner(new javax.swing.SpinnerDateModel());
        javax.swing.JSpinner.DateEditor dateEditor = new javax.swing.JSpinner.DateEditor(spinnerFecha, "yyyy-MM-dd HH:mm");
        spinnerFecha.setEditor(dateEditor);
        gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = java.awt.GridBagConstraints.EAST;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblFecha, gbc);
        gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(spinnerFecha, gbc);

        // Fila 4 - Motivo
        javax.swing.JLabel lblMotivo = new javax.swing.JLabel("Motivo:");
        javax.swing.JTextArea txtMotivo = new javax.swing.JTextArea(5, 15);
        txtMotivo.setLineWrap(true);
        txtMotivo.setWrapStyleWord(true);
        javax.swing.JScrollPane scrollMotivo = new javax.swing.JScrollPane(txtMotivo);
        gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblMotivo, gbc);
        gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = java.awt.GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(scrollMotivo, gbc);

        // Fila 5 - Botones Agregar y Cancelar
        javax.swing.JButton btnAgregar = new javax.swing.JButton("Agregar");
        javax.swing.JButton btnCancelar = new javax.swing.JButton("Cancelar");
        javax.swing.JPanel pnlButtons = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));
        pnlButtons.add(btnAgregar);
        pnlButtons.add(btnCancelar);
        gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = java.awt.GridBagConstraints.EAST;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(pnlButtons, gbc);

        pack();

        // Acción del botón Agregar
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {  
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String nombrePaciente = txtPaciente.getText().trim();
                String motivo = txtMotivo.getText().trim();
                java.util.Date fechaSeleccionada = (java.util.Date) spinnerFecha.getValue();

                if (nombrePaciente.isEmpty() || motivo.isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(VentanaAgregarCita.this, "Por favor, complete todos los campos", "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int idPaciente = 0;
                ArrayList<Paciente> pacientes = bd.buscaPacientePorNombre(nombrePaciente);
                if (!pacientes.isEmpty()) {
                    idPaciente = pacientes.get(0).getId(); // Asumiendo que el ID del paciente se obtiene de la búsqueda
                } else {
                    javax.swing.JOptionPane.showMessageDialog(VentanaAgregarCita.this, "Paciente no encontrado, por favor agregue un nuevo paciente", "Información", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                Cita nuevaCita = new Cita(0,
                    idPaciente, // ID del paciente, se debe obtener de la búsqueda o agregar paciente
                    1, // ID del doctor, se debe seleccionar o agregar
                    new java.sql.Timestamp(fechaSeleccionada.getTime()), 
                    motivo, 
                    0, // Estado de la cita (1 para activa)
                    nombrePaciente
                );
                bd.agregarCita(nuevaCita);

                javax.swing.JOptionPane.showMessageDialog(VentanaAgregarCita.this, "Cita agregada. Fecha: " + nuevaCita.getFecha().toString(), "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);

                dispose(); // Cierra la ventana después de agregar la cita
            }
        });

        // Acción del botón Cancelar
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispose(); // Cierra la ventana sin hacer nada
            }
        });

        // Acción del botón Agregar Paciente
        btnAgregarPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VentanaAgregarPaciente ventanaAgregarPaciente = new VentanaAgregarPaciente();
                ventanaAgregarPaciente.setVisible(true);
            }
        });
    }                        

}
