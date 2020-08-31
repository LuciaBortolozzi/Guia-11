package model;

public class TiposSangre {
    private int id;
    private String grupo;
    private String factor;

    public TiposSangre() {
    }

    public TiposSangre(int id, String grupo, String factor) {
        this.id = id;
        this.grupo = grupo;
        this.factor = factor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }
}
