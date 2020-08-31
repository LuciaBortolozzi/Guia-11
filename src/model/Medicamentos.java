package model;

public class Medicamentos {
    private int idMed;
    private String nombreMed;
    private String nombreLab;

    public Medicamentos() {
    }

    public Medicamentos(int idMed, String nombreMed, String nombreLab) {
        this.idMed = idMed;
        this.nombreMed = nombreMed;
        this.nombreLab = nombreLab;
    }

    public int getIdMed() {
        return idMed;
    }

    public void setIdMed(int idMed) {
        this.idMed = idMed;
    }

    public String getNombreMed() {
        return nombreMed;
    }

    public void setNombreMed(String nombreMed) {
        this.nombreMed = nombreMed;
    }

    public String getNombreLab() {
        return nombreLab;
    }

    public void setNombreLab(String nombreLab) {
        this.nombreLab = nombreLab;
    }
}
