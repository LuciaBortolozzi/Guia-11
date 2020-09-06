package model;

public class Localidades implements Comparable<Localidades>{
    private int idLocalidad;
    private String nombreLoc;
    private String codigoPostal;
    private Provincias provincia;

    public Localidades() {
    }

    public Localidades(int idLocalidad, String nombreLoc, String codigoPostal, Provincias provincia) {
        this.idLocalidad = idLocalidad;
        this.nombreLoc = nombreLoc;
        this.codigoPostal = codigoPostal;
        this.provincia = provincia;
    }

    public int getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad(int idLocalidad) {
        this.idLocalidad = idLocalidad;
    }

    public String getNombreLoc() {
        return nombreLoc;
    }

    public void setNombreLoc(String nombreLoc) {
        this.nombreLoc = nombreLoc;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Provincias getProvincia() { return provincia; }

    public void setProvincia(Provincias provincia) { this.provincia = provincia; }

    @Override
    public int compareTo(Localidades o) {
        return nombreLoc.compareTo(o.nombreLoc);
    }
}
