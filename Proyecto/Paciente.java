package Proyecto;

import java.sql.Date;

public class Paciente {
    
    private int id; // Asumiendo que el ID es un campo autogenerado en la base de datos
    private String nombre;
    private String telefono; 
    private String correo;
    private String direccion;
    private Date fechaNacimiento;
    private int genero;

    // Constructor
    public Paciente(int Id, String nombre, String telefono, String correo, String direccion, Date fechaNacimiento, int genero){
        this.id = Id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getTelefono(){
        return telefono;
    }
    public void setTelefono(String telefono){
        this.telefono = telefono;
    }

    public String getCorreo(){
        return correo;
    }
    public void setCorreo(String correo){
        this.correo = correo;
    }

    public String getDireccion(){
        return direccion;
    }
    public void setDireccion(String direccion){
        this.direccion = direccion;
    }

    public Date getFechaNacimiento(){
        return fechaNacimiento;
    }
    public void setFechaNacimiento(Date fechaNacimiento){
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getGenero(){
        return genero;
    }
    public void setGenero(int genero){
        this.genero = genero;
    }

}
