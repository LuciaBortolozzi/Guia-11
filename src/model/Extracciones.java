package model;

import java.util.Calendar;

public class Extracciones {
    private int nroExtraccion;
    private Calendar fechaDonacion;
    private double pesoDonador;
    private boolean pudoDonar;
    private String presion;
    private double recuentoGlobulosRojos;
    private double cantExtraida;

    public Extracciones() {
    }

    public Extracciones(int nroExtraccion, Calendar fechaDonacion, double pesoDonador, boolean pudoDonar, String presion, double recuentoGlobulosRojos, double cantExtraida) {
        this.nroExtraccion = nroExtraccion;
        this.fechaDonacion = fechaDonacion;
        this.pesoDonador = pesoDonador;
        this.pudoDonar = pudoDonar;
        this.presion = presion;
        this.recuentoGlobulosRojos = recuentoGlobulosRojos;
        this.cantExtraida = cantExtraida;
    }

    public int getNroExtraccion() {
        return nroExtraccion;
    }

    public void setNroExtraccion(int nroExtraccion) {
        this.nroExtraccion = nroExtraccion;
    }

    public Calendar getFechaDonacion() {
        return fechaDonacion;
    }

    public void setFechaDonacion(Calendar fechaDonacion) {
        this.fechaDonacion = fechaDonacion;
    }

    public double getPesoDonador() {
        return pesoDonador;
    }

    public void setPesoDonador(double pesoDonador) {
        this.pesoDonador = pesoDonador;
    }

    public boolean isPudoDonar() {
        return pudoDonar;
    }

    public void setPudoDonar(boolean pudoDonar) {
        this.pudoDonar = pudoDonar;
    }

    public String getPresion() {
        return presion;
    }

    public void setPresion(String presion) {
        this.presion = presion;
    }

    public double getRecuentoGlobulosRojos() {
        return recuentoGlobulosRojos;
    }

    public void setRecuentoGlobulosRojos(double recuentoGlobulosRojos) {
        this.recuentoGlobulosRojos = recuentoGlobulosRojos;
    }

    public double getCantExtraida() {
        return cantExtraida;
    }

    public void setCantExtraida(double cantExtraida) {
        this.cantExtraida = cantExtraida;
    }

}
