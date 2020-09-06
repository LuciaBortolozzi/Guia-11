package defaultPackage;

import controller.CtrlFrameMenu;
import model.*;
import model.DAO.*;
import view.FrameMenu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeSet;

import static controller.Conexion.getConnection;

public class Main {

    public static void main(String[] args) {

        double totalPeso = Double.parseDouble(args[0]);

        try {
            getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ArrayList<Provincias> provincias = ProvinciasDB.selectProvincias();
        ArrayList<Localidades> localidades = LocalidadesDB.selectLocalidades(provincias);
        ArrayList<TiposSangre> tiposSangres = TiposSangreDB.selectTiposSangre();
        ArrayList<Medicamentos> medicamentos = MedicamentosDB.selectMedicamentos();
        TreeSet<Personas> personasAux = PersonasDB.selectPersonas(localidades, tiposSangres);
        TreeSet<Personas> personasConDonadores = PersonasDB.selectDonadores(personasAux);
        TreeSet<Personas> personasConPacientes = PersonasDB.selectPacientes(personasConDonadores, medicamentos);


        CtrlFrameMenu ctrlFrameMenu = new CtrlFrameMenu(totalPeso);
        new FrameMenu(ctrlFrameMenu);
    }
}
