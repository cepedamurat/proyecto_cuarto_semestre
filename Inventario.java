package Proyecto;

public class Inventario {
    private int Id;
    private String nombre;
    private int cantidad;
    private String descripcion;
    private double precio;

    // Constructor
    public Inventario(int Id, String nombre, int cantidad, String descripcion, Double precio) {
        this.Id = Id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    // Getters y Setters
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
}
