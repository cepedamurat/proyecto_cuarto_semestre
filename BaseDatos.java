package Proyecto;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BaseDatos {
    // Atributos de la clase
    private String nombreBD = "consultorio";
    private String usuario = "docMuniz";
    private String contrasenaBD = "12345";
    private String urlBD = "jdbc:mysql://localhost:3306/" + nombreBD;
    private String driverBD = "com.mysql.cj.jdbc.Driver";
    Connection connection = null;

    // Constructor
    public BaseDatos() {
        // Conexión a la base de datos
        try {
            Class.forName(this.driverBD);
            connection = DriverManager.getConnection(this.urlBD, this.usuario, this.contrasenaBD);
        } catch (Exception e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    // Metodos para tabla usuario

    public int validaLogin(String usuario, String contrasena) {
        // Método para validar el login
        try {
            String query = "SELECT * FROM Usuario WHERE nombre = ? AND contrasena = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, usuario);
            statement.setString(2, contrasena);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("rol"); // Roles {1: Administrador, 2: Doctor, 3: Asistente}
            } else {
                return 0; // No es valido
            }
        } catch (SQLException e) {
            System.out.println("Error al validar el login: " + e.getMessage());
            return 0;
        }
    }

    public void agregaUsuario(String nombre, String contrasena, int rol) {
        // Método para agregar un nuevo usuario
        try {
            String query = "INSERT INTO Usuario (nombre, contrasena, rol) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nombre);
            statement.setString(2, contrasena);
            statement.setInt(3, rol);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al agregar el usuario: " + e.getMessage());
        }

    }

    // Metodos para tabla paciente


    public void agregarPaciente(Paciente paciente){

        try {
            String query = "INSERT INTO Paciente (nombre, telefono, correo, direccion, fechaNacimiento, genero) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, paciente.getNombre());
            statement.setString(2, paciente.getTelefono());
            statement.setString(3, paciente.getCorreo());
            statement.setString(4, paciente.getDireccion());
            statement.setDate(5, new java.sql.Date(paciente.getFechaNacimiento().getTime()));
            statement.setInt(6, paciente.getGenero());

            statement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Error al agregar el paciente: " + e.getMessage());
        }
    }

    public ArrayList<Paciente> buscaPacientePorNombre(String nombre){
        ArrayList<Paciente> pacientes = new ArrayList<>();
        try {
            String query = "SELECT * FROM Paciente WHERE nombre LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + nombre + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Paciente paciente;
                paciente = new Paciente(resultSet.getInt("id"),
                                        resultSet.getString("nombre"),
                                        resultSet.getString("telefono"),
                                        resultSet.getString("correo"),
                                        resultSet.getString("direccion"),
                                        resultSet.getDate("fechaNacimiento"),
                                        resultSet.getInt("genero"));
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el paciente");
        }
        return pacientes;
    }

    //Metodos para tabla cita

    public void agregarCita(Cita cita){
        try {
            String query = "INSERT INTO Cita (idPaciente, idUsuario, fecha, motivo, estado) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            System.out.println(statement.toString());
    
            statement.setInt(1, cita.getidPaciente());
            statement.setInt(2, cita.getidDoctor());
            statement.setTimestamp(3, cita.getFecha());
            statement.setString(4, cita.getMotivo());
            statement.setInt(5, cita.getEstado());


            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al agregar la cita: " + e.getMessage());
        }
    }

    public void setEstadoCita(int idCita, int estado){
        try {
            String query = "UPDATE Cita SET estado = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
    
            statement.setInt(2, idCita);
            statement.setInt(1, estado);

            System.out.println("Cambiando estado de la cita con ID: " + idCita + " a estado: " + estado);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al cambiar el estado de la cita: " + e.getMessage());
        }
    }

    public ArrayList<Cita> buscaCitaPorPaciente(int idPaciente){
        ArrayList<Cita> citas = new ArrayList<>();
        try {
            String query = "SELECT Cita.*, Paciente.nombre AS nombrePaciente FROM Cita JOIN Paciente ON Cita.idPaciente = Paciente.id WHERE Cita.idPaciente = ? ORDER BY Cita.fecha DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idPaciente);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Cita cita;

                System.out.println("Buscando citas para el paciente con ID: " + idPaciente);

                cita = new Cita(
                    resultSet.getInt("id"),
                    resultSet.getInt("idPaciente"),
                    resultSet.getInt("idUsuario"),
                    resultSet.getTimestamp("fecha"),
                    resultSet.getString("motivo"),
                    resultSet.getInt("estado"),
                    resultSet.getString("nombrePaciente"));

                citas.add(cita);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar la cita");
        }
        return citas;
    }

    public ArrayList<Cita> citasPorDia(Timestamp horaini, Timestamp horafin){
        ArrayList<Cita> citas = new ArrayList<>();
        try {
            String query = "SELECT Cita.*, Paciente.nombre FROM Cita " + //
                                "JOIN Paciente ON Cita.idPaciente = Paciente.id " + //
                                "WHERE Cita.fecha BETWEEN ? AND ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setTimestamp(1, horaini);
            statement.setTimestamp(2, horafin);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Cita cita;

                cita = new Cita(
                    resultSet.getInt("id"),
                    resultSet.getInt("idPaciente"),
                    resultSet.getInt("idUsuario"),
                    resultSet.getTimestamp("fecha"),
                    resultSet.getString("motivo"),
                    resultSet.getInt("estado"),
                    resultSet.getString("nombre")
                );

                citas.add(cita);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar la cita");
        }
        return citas;
    }


    // Metodo para tabla consulta

    public void agregarConsulta(Consulta consulta){
        try {
            String query = "INSERT INTO Consulta (idCita, Paciente, Doctor, Tratamiento, Diagnostico, examenmed) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
    

            statement.setInt(1, consulta.getIdCita());
            statement.setInt(2, consulta.getPaciente());
            statement.setInt(3, consulta.getDoctor());
            statement.setString(4, consulta.getTratamiento());
            statement.setString(5, consulta.getDiagnostico());
            statement.setString(6, consulta.getExamenmed());


            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al agregar la consulta: " + e.getMessage());
        }
    }



    // Metodo para tabla inventario

    public void agregarInventario(Inventario inventario){
        try {
            String query = "INSERT INTO Inventario (nombre, cantidad, descripcion, precio) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
    
            statement.setString(1, inventario.getNombre());
            statement.setInt(2, inventario.getCantidad());
            statement.setString(3, inventario.getDescripcion());
            statement.setDouble(4, inventario.getPrecio());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al agregar el inventario: " + e.getMessage());
        }
    }

    public ArrayList<Inventario> buscaInventarioPorNombre(String nombre){
        ArrayList<Inventario> inventarios = new ArrayList<>();
        try {
            String query = "SELECT * FROM Inventario WHERE nombre LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + nombre + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Inventario inventario;
                inventario = new Inventario(resultSet.getInt("id"),
                                            resultSet.getString("nombre"),
                                            resultSet.getInt("cantidad"),
                                            resultSet.getString("descripcion"),
                                            resultSet.getDouble("precio"));
                inventarios.add(inventario);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el inventario");
        }
        return inventarios;
    }

    public void borrarInventario(int id) {
        // Método para borrar un inventario por ID
        try {
            String query = "DELETE FROM Inventario WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al borrar el inventario: " + e.getMessage());
        }
    }   

    public void actualizarCantidadInventario(int id, int cantidad) {
        // Método para actualizar la cantidad de un inventario por ID
        try {
            String query = "UPDATE Inventario SET cantidad = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, cantidad);
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar la cantidad del inventario: " + e.getMessage());
        }
    }   

}
