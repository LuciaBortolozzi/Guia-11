package model;

import java.util.ArrayList;
import java.util.Calendar;

public class Pacientes extends Personas {
    private String enfermedad;
    private ArrayList<Medicamentos> medicamentos;
    private Calendar inicioTratamiento;

    public Pacientes(){}

    public Pacientes(String nombre, String apellido, int dni, Localidades localidad, Calendar fechaNac, char sexo, TiposSangre tipoSangre) {
        super(nombre, apellido, dni, localidad, fechaNac, sexo, tipoSangre);
    }

    public Pacientes(String enfermedad, ArrayList<Medicamentos> medicamentos, Calendar inicioTratamiento) {
        this.enfermedad = enfermedad;
        this.medicamentos = medicamentos;
        this.inicioTratamiento = inicioTratamiento;
    }

    public Pacientes(String nombre, String apellido, int dni, Localidades localidad, Calendar fechaNac, char sexo, TiposSangre tipoSangre, String enfermedad, ArrayList<Medicamentos> medicamentos, Calendar inicioTratamiento) {
        super(nombre, apellido, dni, localidad, fechaNac, sexo, tipoSangre);
        this.enfermedad = enfermedad;
        this.medicamentos = medicamentos;
        this.inicioTratamiento = inicioTratamiento;
    }

    public Calendar getInicioTratamiento() {
        return inicioTratamiento;
    }

    public void setInicioTratamiento(Calendar inicioTratamiento) {
        this.inicioTratamiento = inicioTratamiento;
    }

    public ArrayList<Medicamentos> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(ArrayList<Medicamentos> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }
}
