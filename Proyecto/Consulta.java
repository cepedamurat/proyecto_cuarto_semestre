package Proyecto;

public class Consulta {

    private int idCita;
    private int Paciente;
    private int Doctor;
    private String tratamiento;
    private String diagnostico;
    private String examenmed;

    // Constructor
    public Consulta(int idCita, int Paciente, int Doctor, String tratamiento, String diagnostico, String examenmed) {
        this.idCita = idCita;
        this.Paciente = Paciente;
        this.Doctor = Doctor;
        this.tratamiento = tratamiento;
        this.diagnostico = diagnostico;
        this.examenmed = examenmed;
    }

    // Getters y Setters

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public int getPaciente() {
        return Paciente;
    }

    public void setPaciente(int Paciente) {
        this.Paciente = Paciente;
    }

    public int getDoctor() {
        return Doctor;
    }

    public void setDoctor(int Doctor) {
        this.Doctor = Doctor;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getExamenmed() {
        return examenmed;
    }

    public void setExamenmed(String examenmed) {
        this.examenmed = examenmed;
    }
    
}
