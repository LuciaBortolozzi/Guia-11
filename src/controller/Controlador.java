package controller;

import model.*;
import model.DAO.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class Controlador {

    static ArrayList<Provincias> provincias = ProvinciasTXT.bajarProvinciasTXT();
    static ArrayList<Localidades> localidades = LocalidadesTXT.bajarLocalidadesTXT(provincias);
    static ArrayList<TiposSangre> tiposSangres = TiposSangreTXT.bajarTiposSangreTXT();
    static ArrayList<Medicamentos> medicamentos = MedicamentosTXT.bajarMedicamentosTXT();

    static TreeSet<Personas> personasAux = PersonasTXT.bajarPersonasTXT(localidades, tiposSangres);

    static TreeSet<Personas> personasExt = PersonasTXT.bajarDonadoresExtraccionesTXT(personasAux);

    static TreeSet<Personas> personas = MedicamentosTXT.bajarPacientesMedicamentosTXT(personasExt, medicamentos);

    public static Localidades buscarLocalidad(String localidadST) {
        Localidades localidad = null;

        for (Localidades loc : localidades) {
            if (loc.getNombreLoc().equals(localidadST)) {
                return loc;
            }
        }
        return localidad;
    }

    public static TiposSangre buscarTipoSangre(String tipoSangreST) {
        TiposSangre tiposSangre = null;

        for (TiposSangre tipo : tiposSangres) {
            String aux = tipo.getGrupo() + "-RH" + tipo.getFactor();
            if (aux.equals(tipoSangreST)) {
                return tipo;
            }
        }
        return tiposSangre;
    }

    public static ArrayList<Medicamentos> buscarMedicamentos(List<String> medicamentosST) {
        ArrayList<Medicamentos> meds = new ArrayList<Medicamentos>();

        for (Medicamentos med : medicamentos) {
            for (String medAux : medicamentosST) {
                if (med.getNombreMed().equals(medAux)) {
                    meds.add(med);
                }
            }

        }
        return meds;
    }

    public static List<String> getLocalidadesxProvincia(String provSeleccionada) {

        List<String> STLocalidades = new ArrayList<String>();

        for (Localidades loc : localidades) {
            if (loc != null) {
                if (loc.getProvincia().getNombreProv().equals(provSeleccionada)) {

                    STLocalidades.add(loc.getNombreLoc());

                }
            }
        }
        return STLocalidades;
    }

    public static ArrayList<String> stringifyTiposSangres() {

        ArrayList<String> tiposSangresST = new ArrayList<String>();
        tiposSangresST.add("Seleccione tipo de sangre");
        for (TiposSangre tipo : tiposSangres) {
            tiposSangresST.add(tipo.getGrupo() + "-RH" + tipo.getFactor());
        }
        return tiposSangresST;
    }

    public static Medicamentos agregarMedicamentos(ArrayList<Medicamentos> medicamentos, int idMed) {

        Medicamentos medicamento = null;

        Iterator<Medicamentos> hosp = medicamentos.iterator();
        while (hosp.hasNext()) {
            medicamento = hosp.next();

            if (medicamento.getIdMed() == idMed) {
                break;
            }
        }
        return medicamento;
    }
}
