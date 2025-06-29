package Proyecto;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;    
import java.sql.Timestamp;

public class consultorio extends JFrame{

    private CardLayout cardLayout;
    private JPanel contenedorTarjetas;
    private JMenuBar barraMenu;
    private BaseDatos bd;
    private int rolUsuario;
    private final JPanel panelCitas = new JPanel(new GridLayout(0, 8));
    private int mesActual = 0;
    private int anioActual = 0;
    private int diaActual = 0;



    public consultorio() {
        setTitle("Sistema de administración de consultorios");
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screen.width, screen.height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crea la conexión a la base de datos
        bd = new BaseDatos();

        // Crea el menu principal de la aplicacion
        creaBarraDeMenu();

        // Contenedor de tarjetas con CardLayout
        cardLayout = new CardLayout();
        contenedorTarjetas = new JPanel(cardLayout);

        // Cada ventana es una tarjeta distinta
        contenedorTarjetas.add(creaVentanaLogin(), "Login");
        contenedorTarjetas.add(creaVentanaCalendario(), "Calendario");
        //contenedorTarjetas.add(creaVentanaInventario(), "Inventario");

        add(contenedorTarjetas);

        // Mostrar pantalla de login al inicio
        cardLayout.show(contenedorTarjetas, "login");
    }

    private void creaBarraDeMenu() {
        barraMenu = new JMenuBar();
        JMenu menuHerramientas = new JMenu("Herramientas");
        JMenu menuAyuda = new JMenu("Ayuda");

        JMenuItem menuCalendario = new JMenuItem("Calendario");
        JMenuItem menuInventario = new JMenuItem("Inventario"); 
        JMenuItem menuHistoriales = new JMenuItem("Historiales médicos");

        JMenuItem menuAcercaDe = new JMenuItem("Acerca de...");
        JMenuItem menuSalir = new JMenuItem("Salir");

        menuHerramientas.add(menuCalendario);
        menuHerramientas.add(menuInventario);
        menuHerramientas.add(menuHistoriales);
        menuAyuda.add(menuAcercaDe);
        menuAyuda.add(menuSalir);

        menuCalendario.addActionListener((ActionEvent e) -> {
            cardLayout.show(contenedorTarjetas, "Calendario");
        });
        menuInventario.addActionListener((ActionEvent e) -> {
            cardLayout.show(contenedorTarjetas, "Inventario");
        });
        menuHistoriales.addActionListener((ActionEvent e) -> {
            cardLayout.show(contenedorTarjetas, "Historiales");
        });

        menuAcercaDe.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this, "Sistema de administración de consultorios\nVersión 1.0\nDesarrollado por ....");
        });
        menuSalir.addActionListener((ActionEvent e) -> {
            int respuesta = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea salir?", "Confirmar salida", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        barraMenu.add(menuHerramientas);
        barraMenu.add(menuAyuda);

        barraMenu.setVisible(false);

        setJMenuBar(barraMenu);
    }

    private void aplicaRol(int rol) {
        if (rol == 3) { // Rol 1: Administrador, Rol 2: Doctor, Rol 3: Asistente
            // Haz invisible la opcion de historiales medicos para los asistentes
            JMenuItem menuHistoriales = barraMenu.getMenu(0).getItem(2);
            if (menuHistoriales != null) {
                menuHistoriales.setVisible(false);
            }
        }
    }

    private Component creaVentanaLogin() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        int bordeVertical = Math.max((getHeight() - 120) / 2, 20);
        int bordeHorizontal = Math.max((getWidth() - 200) / 2, 20);
        panel.setBorder(BorderFactory.createEmptyBorder(bordeVertical, bordeHorizontal, bordeVertical, bordeHorizontal));

        JLabel labelUsuario = new JLabel("Usuario:");
        JTextField textoUsuario = new JTextField();

        JLabel labelClave = new JLabel("Contraseña:");
        JPasswordField textoClave = new JPasswordField();

        JButton btnLogin = new JButton("Ingresar");

        // Acción del botón
        btnLogin.addActionListener((ActionEvent e) -> {
            String usuario = textoUsuario.getText();
            String contraseña = new String(textoClave.getPassword());

            this.rolUsuario = bd.validaLogin(usuario, contraseña);
            if (this.rolUsuario != 0) {
                JOptionPane.showMessageDialog(this, "¡Bienvenido " + usuario + "!");
                // Si es un login valido, muestra la ventana correspondiente al rol
                aplicaRol(this.rolUsuario);
                barraMenu.setVisible(true);
                cardLayout.show(contenedorTarjetas, "Calendario");
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(labelUsuario);
        panel.add(textoUsuario);
        panel.add(labelClave);
        panel.add(textoClave);
        panel.add(new JLabel("")); // Espacio vacío para alinear el botón
        panel.add(btnLogin);

        return panel;
    }

    private Component creaVentanaCalendario() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        // Panel izquierdo con calendario mensual, boton para agregar cita y busqueda de pacientes
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelIzquierdo.setPreferredSize(new Dimension(300, 0)); // Ancho fijo para el panel izquierdo
        panelIzquierdo.setMinimumSize(new Dimension(300, 0)); // Ancho mínimo para el panel izquierdo

        // Componente de calendario mensual
        LocalDate fechaActual = LocalDate.now(); // Obten la fecha de hoy y el mes y el año respectivo
        final int[] mesActual = {fechaActual.getMonthValue()};
        final int[] anioActual = {fechaActual.getYear()};

        JPanel panelCalendario = new JPanel(new BorderLayout());
        JLabel labelMes = new JLabel("Mes Actual", SwingConstants.CENTER);
        JButton btnMesAnterior = new JButton("<");
        JButton btnMesSiguiente = new JButton(">");

        JPanel panelNavegacion = new JPanel(new BorderLayout());
        panelNavegacion.add(btnMesAnterior, BorderLayout.WEST);
        panelNavegacion.add(labelMes, BorderLayout.CENTER);
        panelNavegacion.add(btnMesSiguiente, BorderLayout.EAST);

        JPanel panelDias = new JPanel(new GridLayout(0, 7)); // 7 columnas para los días de la semana

        labelMes.setText(fechaActual.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + anioActual[0]);

        actualizarCalendario(panelDias, labelMes, mesActual[0], anioActual[0]);

        btnMesAnterior.addActionListener(e -> {
            if (mesActual[0] == 1) {
                mesActual[0] = 12;
                anioActual[0]--;
            } 
            else {
                mesActual[0]--;
            }
            LocalDate nuevaFecha = LocalDate.of(anioActual[0], mesActual[0], 1);
            labelMes.setText(nuevaFecha.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + anioActual[0]);
            actualizarCalendario(panelDias, labelMes, mesActual[0], anioActual[0]);
        });

        btnMesSiguiente.addActionListener(e -> {
            if (mesActual[0] == 12) {
                mesActual[0] = 1;
                anioActual[0]++;
            } 
            else {
                mesActual[0]++;
            }
            LocalDate nuevaFecha = LocalDate.of(anioActual[0], mesActual[0], 1);
            labelMes.setText(nuevaFecha.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + anioActual[0]);
            actualizarCalendario(panelDias, labelMes, mesActual[0], anioActual[0]);
        });

        panelCalendario.add(panelNavegacion, BorderLayout.NORTH);
        panelCalendario.add(panelDias, BorderLayout.CENTER);

        panelIzquierdo.add(panelCalendario, BorderLayout.NORTH);

        // Botones
        JPanel panelBotones = new JPanel(new BorderLayout());
        JPanel panelBotonesSuperior = new JPanel();

        panelBotonesSuperior.setPreferredSize(new Dimension(0, 50)); // Fija la altura pequeña de la primera fila

        JButton btnAgregarCita = new JButton("Agregar cita");
        panelBotonesSuperior.add(btnAgregarCita, BorderLayout.NORTH);
        btnAgregarCita.addActionListener(e -> {
            VentanaAgregarCita ventanaAgregar = new VentanaAgregarCita();
            ventanaAgregar.setLocationRelativeTo(this);
            ventanaAgregar.setVisible(true);

        });

        JPanel panelBusqueda = new JPanel(new BorderLayout());
        JPanel panelBusquedaSuperior = new JPanel(new BorderLayout());
        JTextField textoPaciente = new JTextField();
        JButton btnBuscarPaciente = new JButton("Buscar citas");
        panelBusquedaSuperior.add(textoPaciente, BorderLayout.CENTER);
        panelBusquedaSuperior.add(btnBuscarPaciente, BorderLayout.EAST);

        JPanel panelListadoCitas = new JPanel();
        panelListadoCitas.setLayout(new BoxLayout(panelListadoCitas, BoxLayout.Y_AXIS));
        JScrollPane scrollListadoCitas = new JScrollPane(panelListadoCitas);
        panelBusqueda.add(panelBusquedaSuperior, BorderLayout.NORTH);
        panelBusqueda.add(scrollListadoCitas, BorderLayout.CENTER);

        panelBotones.add(panelBotonesSuperior, BorderLayout.NORTH);
        panelBotones.add(panelBusqueda, BorderLayout.CENTER);

        panelIzquierdo.add(panelBotones);

        btnBuscarPaciente.addActionListener(e -> {
            String nombrePaciente = textoPaciente.getText().trim();
            panelListadoCitas.removeAll();
            if (!nombrePaciente.isEmpty()) {
                ArrayList<Paciente> pacientes = bd.buscaPacientePorNombre(nombrePaciente);
                if (pacientes.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "No se encontraron pacientes con ese nombre.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                ArrayList<Cita> citasPaciente = bd.buscaCitaPorPaciente(pacientes.get(0).getId());
                for (Cita cita : citasPaciente) {
                    JPanel tarjetaCita = new JPanel(new BorderLayout());
                    tarjetaCita.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    if (cita.getEstado() == 0) {
                        tarjetaCita.setBackground(Color.CYAN);
                    } else if (cita.getEstado() == 1) {
                        tarjetaCita.setBackground(Color.GREEN);
                    } else if (cita.getEstado() == 2) {
                        tarjetaCita.setBackground(Color.RED);
                    }
                    JLabel labelInfo = new JLabel("<html><b>" + 
                                            cita.getFecha().toLocalDateTime().toLocalDate() + "<br>" +
                                            cita.getFecha().toLocalDateTime().toLocalTime() + "</b><br><br>" + 
                                            cita.getMotivo() + "</html>");
                    tarjetaCita.add(labelInfo, BorderLayout.CENTER);
                    tarjetaCita.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    tarjetaCita.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            Object[] opciones = {"Cerrar", "Confirmar", "Cancelar"};
                            int seleccion = JOptionPane.showOptionDialog(
                                null,
                                "Detalle de la cita:\nPaciente: " + cita.getNombrePaciente() +
                                "\nFecha: " + cita.getFecha() +
                                "\nMotivo: " + cita.getMotivo(),
                                "Detalle de la cita",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                opciones,
                                opciones[0]
                            );

                            if (seleccion == 1) { // Confirmar
                                
                                bd.setEstadoCita(cita.getId(), 1); // 1 = Confirmada
                                JOptionPane.showMessageDialog(null, "La cita ha sido confirmada.");
                            } else if (seleccion == 2) { // Cancelar
                                bd.setEstadoCita(cita.getId(), 2); // 2 = Cancelada
                                JOptionPane.showMessageDialog(null, "La cita ha sido cancelada.");
                            }
                            JOptionPane.showMessageDialog(null, "Información de la cita:\nPaciente: " + cita.getNombrePaciente() +
                                "\nFecha: " + cita.getFecha() +
                                "\nMotivo: " + cita.getMotivo());
                        }
                    });

                    panelListadoCitas.add(tarjetaCita);
                }
            }
            panelListadoCitas.revalidate();
            panelListadoCitas.repaint();
        });


        // Agregar paneles al contenedor principal
        panel.add(panelIzquierdo, BorderLayout.WEST);
        panel.add(this.panelCitas, BorderLayout.CENTER);

        return panel;
    }

    private void agregarListenerDias(JPanel panelDias) {
        for (Component componente : panelDias.getComponents()) {
            if (componente instanceof JLabel) {
                JLabel labelDia = (JLabel) componente;
                if (!labelDia.getText().isEmpty()) { // Solo agregar listener a los días con números
                    labelDia.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    labelDia.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            for (Component componente : panelDias.getComponents()) {
                                if (componente instanceof JLabel) {
                                    JLabel labelDia = (JLabel) componente;
                                    labelDia.setOpaque(false);
                                    labelDia.setBackground(null);
                                }
                            }
                            labelDia.setOpaque(true);
                            labelDia.setBackground(Color.CYAN);

                            actualizarCitas(panelCitas, Integer.parseInt(labelDia.getText()), mesActual, anioActual);

                        }
                    });
                }
            }
        }
    }

    private void actualizarCalendario(JPanel panelDias, JLabel labelMes, int mes, int anio) {
        this.mesActual = mes;
        this.anioActual = anio;
        panelDias.removeAll();
        String[] diasSemana = {"Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb"};
        for (String dia : diasSemana) {
            panelDias.add(new JLabel(dia, SwingConstants.CENTER));
        }
        LocalDate primerDiaDelMes = LocalDate.of(anio, mes, 1);
        int diaDeLaSemana = primerDiaDelMes.getDayOfWeek().getValue() % 7; // Ajustar para que Domingo sea 0
        int diasEnMes = primerDiaDelMes.lengthOfMonth();

        // Agregar celdas vacías antes del primer día del mes
        for (int i = 0; i < diaDeLaSemana; i++) {
            panelDias.add(new JLabel(""));
        }

        // Agregar los días del mes
        for (int dia = 1; dia <= diasEnMes; dia++) {
            JLabel labelDia = new JLabel(String.valueOf(dia), SwingConstants.CENTER);
            labelDia.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            panelDias.add(labelDia);
        }

        // Agregar celdas vacías después del último día del mes
        int celdasRestantes = 42 - (diaDeLaSemana + diasEnMes);
        for (int i = 0; i < celdasRestantes; i++) {
            panelDias.add(new JLabel(""));
        }

        agregarListenerDias(panelDias);
        panelDias.revalidate();
        panelDias.repaint();
    }

    private String reduceTexto(String texto) {
        System.out.println("Reduciendo texto: " + texto);
        if (texto.length() > 15) {
            return texto.substring(0, 15) + "...";
        }
        return texto;
    }    

    private void actualizarCitas(JPanel panelCitas, int dia, int mes, int anio) {
        System.out.println("Dia: " + dia + ", Mes: " + mes + ", Año: " + anio);

        panelCitas.removeAll();
        
        String[] horas = {"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00  "};
        String[] diasSemana = {"Hora", "Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
        for (String nombreDia : diasSemana) {
            panelCitas.add(new JLabel(nombreDia, SwingConstants.CENTER));
        }

        LocalDate diaDelMes = LocalDate.of(anio, mes, dia);
        int diaDeLaSemana = diaDelMes.getDayOfWeek().getValue() % 7;

        LocalDateTime fechaInicio = LocalDate.of(anio, mes, dia - diaDeLaSemana).atStartOfDay();
        LocalDateTime fechaFin = LocalDate.of(anio, mes, dia + (7 - diaDeLaSemana)).atStartOfDay();
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Timestamp dia1 = Timestamp.valueOf(fechaInicio.format(dtf));
        Timestamp dia2 = Timestamp.valueOf(fechaFin.format(dtf));

        ArrayList<Cita> citas = this.bd.citasPorDia(dia1, dia2);
        System.out.println("Dia1: " + dia1);
        System.out.println("Dia2: " + dia2);
        System.out.println("Citas encontradas: " + citas.size());

        // Crear un array bidimensional para almacenar las citas
        String[][] matrizCitas = new String[horas.length][diasSemana.length - 1];
        int[][] matrizEstado = new int[horas.length][diasSemana.length - 1];
        int[][] matrizIdCita = new int[horas.length][diasSemana.length - 1];

        for (int i = 0; i < horas.length; i++) {
            for (int j = 0; j < diasSemana.length - 1; j++) {
                matrizCitas[i][j] = null;
                matrizEstado[i][j] = -1;
                matrizIdCita[i][j] = 0;
            }
        }

        // Llenar la matriz con las citas correspondientes
        for (Cita cita : citas) {
            int diaSemanaCita = cita.getFecha().toLocalDateTime().getDayOfWeek().getValue() % 7;
            int horaCita = cita.getFecha().toLocalDateTime().getHour();

            for (int i = 0; i < horas.length; i++) {
                if (horas[i].contains(String.valueOf(horaCita))) {
                    matrizCitas[i][diaSemanaCita] = "<html><div style='text-align: left;'>" + 
                                                    cita.getFecha().toLocalDateTime().toLocalTime().withSecond(0).toString() + 
                                                    ", <i>" + cita.getNombrePaciente() + "</i><br>" + 
                                                    reduceTexto(cita.getMotivo()) + 
                                                    "</div></html>";
                    matrizEstado[i][diaSemanaCita] = cita.getEstado();
                    matrizIdCita[i][diaSemanaCita] = cita.getidPaciente();
                    break;
                }
                else{
                    matrizIdCita[i][diaSemanaCita] = 0;
                }
            }
        }

        // Imprimir las citas en el panel
        for (int i = 0; i < horas.length; i++) {
            JLabel labelHora = new JLabel(horas[i], SwingConstants.CENTER);
            labelHora.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            panelCitas.add(labelHora);

            for (int j = 0; j < diasSemana.length - 1; j++) {
                JPanel celda = new JPanel();
                celda.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                if(matrizEstado[i][j] != -1){
                    if (matrizEstado[i][j] == 0) {
                        celda.setBackground(Color.CYAN);
                    } else if (matrizEstado[i][j] == 1) {
                        celda.setBackground(Color.GREEN);
                    } else if (matrizEstado[i][j] == 2) {
                        celda.setBackground(Color.RED);
                    }

                }

                if (matrizCitas[i][j] != null) {
                    JLabel labelCita = new JLabel(matrizCitas[i][j], SwingConstants.CENTER);
                    celda.add(labelCita);
                }

                panelCitas.add(celda);
            }
        }

        panelCitas.revalidate();
        panelCitas.repaint();

        anioActual = anio;
        mesActual = mes;
        diaActual = dia;
    }

    public static void main(String[] ags){
        consultorio ventana;
        ventana = new consultorio();
        ventana.setVisible(true);
    }

    
    
}
