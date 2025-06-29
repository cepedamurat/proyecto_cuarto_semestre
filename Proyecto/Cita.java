package Proyecto;

import java.sql.Timestamp;

public class Cita {
    private int id;
    private int idPaciente;
    private int idDoctor;
    private Timestamp fecha;
    private String motivo;
    private int estado;
    private String nombrePaciente;

    // Constructor
    public Cita(int id, int idPaciente, int idDoctor, Timestamp fecha, String motivo, int estado, String nombrePaciente) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idDoctor = idDoctor;

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTimeInMillis(fecha.getTime());
        int minute = cal.get(java.util.Calendar.MINUTE);
        if (minute < 15) {
            cal.set(java.util.Calendar.MINUTE, 0);
        } else if (minute < 45) {
            cal.set(java.util.Calendar.MINUTE, 30);
        } else {
            cal.set(java.util.Calendar.MINUTE, 0);
            cal.add(java.util.Calendar.HOUR_OF_DAY, 1);
        }
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        fecha.setTime(cal.getTimeInMillis());

        this.fecha = fecha;
        this.motivo = motivo;
        this.estado = estado;
        this.nombrePaciente = nombrePaciente;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getidPaciente() {
        return idPaciente;
    }

    public void idPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getidDoctor() {
        return idDoctor;
    }

    public void setidDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }
    
}
