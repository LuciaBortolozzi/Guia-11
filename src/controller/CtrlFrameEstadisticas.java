package controller;

import model.DAO.EstadisticasJson;
import model.Donadores;
import model.Personas;
import view.FrameEstadisticas;

import java.util.Calendar;
import java.util.TreeSet;

import static controller.Controlador.personas;

public class CtrlFrameEstadisticas {

    private FrameEstadisticas vista;
    private double totalPeso;

    public FrameEstadisticas getVista() {
        return vista;
    }

    public CtrlFrameEstadisticas(double totalPeso) {

        this.totalPeso = totalPeso;
    }

    public void setVista(FrameEstadisticas vista) {

        this.vista = vista;
    }

    public void getEstadisticas() {

        vista.getTableModel().setRowCount(0);

        TreeSet<Personas> personasAux = consultaPersonas();

        for (Personas pers : personasAux) {
            Object[] row = {pers.getDni(), pers.getNombre(),
                    pers.getApellido(), pers.getTipoSangre().getGrupo() + pers.getTipoSangre().getFactor()};
            vista.getTableModel().addRow(row);
        }

        vista.getTextCantidadTotExt().setText(String.valueOf(calcularMililitros()));

        TreeSet<Personas> personasAux2 = consultaPorParametro();

        for (Personas pers : personasAux2) {
            Object[] row = {pers.getDni(), pers.getNombre(),
                    pers.getApellido(), String.format("%02d", pers.getFechaNac().get(Calendar.DAY_OF_MONTH)) + "/" +
                    String.format("%02d", (pers.getFechaNac().get(Calendar.MONTH) + 1))
                    + "/" + pers.getFechaNac().get(Calendar.YEAR)};
            vista.getTableModel2().addRow(row);
        }

        vista.getTextPeso().setText(String.valueOf(totalPeso));
    }

    public TreeSet<Personas> consultaPersonas() {

        TreeSet<Personas> personasAux = new TreeSet<Personas>();

        String provincia = "CABA";

        for (Personas p : personas) {

            if (p.getLocalidad().getProvincia().getNombreProv().equals(provincia)) {

                personasAux.add(p);
            }
        }

        return personasAux;
    }

    public double calcularMililitros() {

        double cantidad = 0;

        Calendar seisMesesAntes = Calendar.getInstance();
        Validaciones.seisMesesAntes(seisMesesAntes);

        for (Personas p : personas) {

            if (p instanceof Donadores) {
                if (((Donadores) p).isDonaPlaquetas() && ((Donadores) p).isDonaSangre() && ((Donadores) p).isDonaPlasma()) {

                    for (int i = 0; i < ((Donadores) p).getExtracciones().size(); i++) {

                        if (((Donadores) p).getExtracciones().get(i).getFechaDonacion().after(seisMesesAntes)) {

                            cantidad = cantidad + ((Donadores) p).getExtracciones().get(i).getCantExtraida();
                        }
                    }
                }
            }
        }
        return cantidad;
    }

    public TreeSet<Personas> consultaPorParametro() {

        TreeSet<Personas> personasAux = new TreeSet<Personas>();
        for (Personas p : personas) {

            if (p instanceof Donadores) {
                for (int i = 0; i < ((Donadores) p).getExtracciones().size(); i++) {
                    if (((Donadores) p).getExtracciones().get(i).getPesoDonador() != totalPeso) {

                        personasAux.add(p);
                    }
                }
            }
        }

        EstadisticasJson.grabarJsonStream(personasAux);
        return personasAux;
    }
}
