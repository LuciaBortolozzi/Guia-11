package model;

import java.util.ArrayList;
import java.util.Calendar;

public class Donadores extends Personas {
    private boolean donaSangre;
    private boolean donaPlaquetas;
    private boolean donaPlasma;
    private ArrayList<Extracciones> extracciones = new ArrayList<Extracciones>();

    public Donadores(){}

    public Donadores(boolean donaSangre, boolean donaPlaquetas, boolean donaPlasma) {
        this.donaSangre = donaSangre;
        this.donaPlaquetas = donaPlaquetas;
        this.donaPlasma = donaPlasma;
    }

    public Donadores(String nombre, String apellido, int dni, Localidades localidad, Calendar fechaNac, char sexo, TiposSangre tipoSangre, boolean donaSangre, boolean donaPlaquetas, boolean donaPlasma) {
        super(nombre, apellido, dni, localidad, fechaNac, sexo, tipoSangre);
        this.donaSangre = donaSangre;
        this.donaPlaquetas = donaPlaquetas;
        this.donaPlasma = donaPlasma;
    }

    public boolean isDonaSangre() {
        return donaSangre;
    }

    public void setDonaSangre(boolean donaSangre) {
        this.donaSangre = donaSangre;
    }

    public boolean isDonaPlaquetas() {
        return donaPlaquetas;
    }

    public void setDonaPlaquetas(boolean donaPlaquetas) {
        this.donaPlaquetas = donaPlaquetas;
    }

    public boolean isDonaPlasma() {
        return donaPlasma;
    }

    public void setDonaPlasma(boolean donaPlasma) {
        this.donaPlasma = donaPlasma;
    }

    public ArrayList<Extracciones> getExtracciones() {
        return extracciones;
    }

    public void setExtracciones(int nroExtraccion, Calendar fechaDonacion, double pesoDonador, boolean pudoDonar, String presion, double recuentoGlobulosRojos, double cantExtraida) {
        extracciones.add(new Extracciones(nroExtraccion, fechaDonacion, pesoDonador, pudoDonar, presion, recuentoGlobulosRojos, cantExtraida));
    }

    public void setExtracciones(int indice, int nroExtraccion, Calendar fechaDonacion, double pesoDonador, boolean pudoDonar, String presion, double recuentoGlobulosRojos, double cantExtraida) {
        extracciones.get(indice).setNroExtraccion(nroExtraccion);
        extracciones.get(indice).setFechaDonacion(fechaDonacion);
        extracciones.get(indice).setPesoDonador(pesoDonador);
        extracciones.get(indice).setPudoDonar(pudoDonar);
        extracciones.get(indice).setPresion(presion);
        extracciones.get(indice).setRecuentoGlobulosRojos(recuentoGlobulosRojos);
        extracciones.get(indice).setCantExtraida(cantExtraida);
    }
}
