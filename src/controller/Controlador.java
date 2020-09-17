package controller;

import model.*;
import model.DAO.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class Controlador {

    static ArrayList<Provincias> provincias = ProvinciasDB.selectProvincias();
    static ArrayList<Localidades> localidades = LocalidadesDB.selectLocalidades(provincias);
    static ArrayList<TiposSangre> tiposSangres = TiposSangreDB.selectTiposSangre();
    static ArrayList<Medicamentos> medicamentos = MedicamentosDB.selectMedicamentos();

    static TreeSet<Personas> personasAux = PersonasDB.selectPersonas(provincias, localidades, tiposSangres);
    static TreeSet<Personas> personasConDonadores = PersonasDB.selectDonadores(personasAux);
    static TreeSet<Personas> personasConPacientes = PersonasDB.selectPacientes(personasConDonadores, medicamentos);

    public static Localidades buscarLocalidad(String localidadST) {
        Localidades localidad = null;

        for (Localidades loc : localidades) {
            if (loc.getNombreLoc().equals(localidadST)) {
                return loc;
            }
        }
        return localidad;
    }

    public static TiposSangre buscarTipoSangreST(String tipoSangreST) {
        TiposSangre tiposSangre = null;

        for (TiposSangre tipo : tiposSangres) {
            String aux = tipo.getGrupo() + "-" + tipo.getFactor();
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
            tiposSangresST.add(tipo.getGrupo() + "-" + tipo.getFactor());
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

    public static Provincias buscarProvincia(ArrayList<Provincias> provincias, int provincia) {
        for (Provincias prov : provincias) {
            if (prov.getIdProvincia() == provincia) {
                return prov;
            }
        }
        return null;
    }

    public static Localidades buscarLocalidad(ArrayList<Provincias> provincias, int provincia, ArrayList<Localidades> localidades, int localidad) {
        Provincias prov = buscarProvincia(provincias, provincia);
        if (prov != null) {
            for (Localidades loc : localidades) {
                if (loc.getIdLocalidad() == localidad && loc.getProvincia().getIdProvincia() == prov.getIdProvincia()) {
                    return loc;
                }
            }
        }
        return null;
    }

    public static TiposSangre buscarTipoSangre(ArrayList<TiposSangre> tiposSangres, int tipoSangre) {
        for (TiposSangre tipo : tiposSangres) {
            if (tipo.getId() == tipoSangre) {
                return tipo;
            }
        }
        return null;
    }
}
