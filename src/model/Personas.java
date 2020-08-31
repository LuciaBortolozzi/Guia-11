package model;

import java.util.Calendar;

public abstract class Personas implements Comparable<Personas>{
    protected String nombre;
    protected String apellido;
    protected int dni;
    protected Localidades localidad;
    protected Calendar fechaNac;
    protected char sexo;
    protected TiposSangre tipoSangre;

    public Personas() {
    }

    public Personas(String nombre, String apellido, int dni, Localidades localidad, Calendar fechaNac, char sexo, TiposSangre tipoSangre) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.localidad = localidad;
        this.fechaNac = fechaNac;
        this.sexo = sexo;
        this.tipoSangre = tipoSangre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public Localidades getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidades localidad) {
        this.localidad = localidad;
    }

    public Calendar getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Calendar fechaNac) {
        this.fechaNac = fechaNac;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public TiposSangre getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(TiposSangre tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    @Override
    public int compareTo(Personas a) {

        return this.dni - a.dni;
    }
}
