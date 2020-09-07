package model.DAO;

import controller.Conexion;
import model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeSet;


public class PersonasDB {

    public static TreeSet<Personas> selectPersonas(ArrayList<Localidades> localidades, ArrayList<TiposSangre> tiposSangres) {
        TreeSet<Personas> personas = new TreeSet<Personas>();

        try {
            Connection conn = Conexion.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT dni, nombre, apellido, sexo, fechaNac, localidad, tipoSangre, tipoPersona FROM Personas");
            while (rs.next()) {
                Localidades localidad = buscarLocalidad(localidades, rs.getInt("localidad"));
                TiposSangre tipoSangre = buscarTipoSangre(tiposSangres, rs.getInt("tipoSangre"));
                Calendar fechaNac = Calendar.getInstance();
                fechaNac.setTime(rs.getDate("fechaNac"));

                if (rs.getInt("tipoPersona") == 0) {
                    personas.add(new Pacientes(rs.getString("nombre"), rs.getString("apellido"), rs.getInt("dni"),
                            localidad, fechaNac, rs.getString("sexo").charAt(0), tipoSangre));
                } else {
                    personas.add(new Donadores(rs.getString("nombre"), rs.getString("apellido"), rs.getInt("dni"),
                            localidad, fechaNac, rs.getString("sexo").charAt(0), tipoSangre));
                }

            }
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personas;
    }

    private static Localidades buscarLocalidad(ArrayList<Localidades> localidades, int localidad) {
        for (Localidades loc : localidades) {
            if (loc.getIdLocalidad() == localidad) {
                return loc;
            }
        }
        return null;
    }

    private static TiposSangre buscarTipoSangre(ArrayList<TiposSangre> tiposSangres, int tipoSangre) {
        for (TiposSangre tipo : tiposSangres) {
            if (tipo.getId() == tipoSangre) {
                return tipo;
            }
        }
        return null;
    }

/*    public static TreeSet<Personas> selectDonadores(TreeSet<Personas> personas) {

        try {
            Connection conn = Conexion.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT dni, donaSangre, donaPlaquetas, donaPlasma FROM Donadores");
            while (rs.next()) {

                int dni = rs.getInt("dni");
                ResultSet rsAux = stmt.executeQuery("SELECT dni, nombre, apellido, sexo, fechaNac, localidad, tipoSangre FROM Personas");

                int dniAux = rsAux.getInt("dni");

                if (dni == dniAux){
                    Donadores donador = new Donadores(rsAux.getString("nombre"), rsAux.getString("apellido"), dni, localidad, fechaNac, sexo, tipoSangre,
                            rs.getBoolean("donaSangre"), rs.getBoolean("donaPlaquetas"), rs.getBoolean("donaPlasma"));
                    personas.add(donador);
                }
            }
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personas;
    }*/

    public static TreeSet<Personas> selectDonadores(TreeSet<Personas> personas) {
        try {
            Connection conn = Conexion.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT dni, donaSangre, donaPlaquetas, donaPlasma FROM Donadores");
            while (rs.next()) {
                for (Personas per : personas
                ) {
                    if (per instanceof Donadores && rs.getInt("dni") == per.getDni()) {
                        ((Donadores) per).setDonaSangre(rs.getBoolean("donaSangre"));
                        ((Donadores) per).setDonaPlaquetas(rs.getBoolean("donaPlaquetas"));
                        ((Donadores) per).setDonaPlasma(rs.getBoolean("donaPlasma"));

                        selectDonadoresExtracciones(per);
                    }
                }
            }
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personas;
    }

    public static void selectDonadoresExtracciones(Personas persona) {
        try {
            Connection conn = Conexion.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT dniDonador, nroExtraccion, fechaDonacion, pesoDonador, pudoDonar, " +
                    "presion, recuentoGlobulosRojos, cantExtraida FROM Extracciones WHERE dniDonador=" + persona.getDni());
            while (rs.next()) {

                Calendar fechaDonacion = Calendar.getInstance();
                fechaDonacion.setTime(rs.getDate("fechaDonacion"));

                ((Donadores) persona).setExtracciones(rs.getInt("nroExtraccion"), fechaDonacion, rs.getDouble("pesoDonador"),
                        rs.getBoolean("pudoDonar"), rs.getString("presion"), rs.getDouble("recuentoGlobulosRojos"),
                        rs.getDouble("cantExtraida"));


            }
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static TreeSet<Personas> selectPacientes(TreeSet<Personas> personas, ArrayList<Medicamentos> medicamentos) {
        try {
            Connection conn = Conexion.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT dni, enfermedad, inicioTratamiento FROM Pacientes");
            while (rs.next()) {
                for (Personas per : personas
                ) {

                    Calendar inicioTratamiento = Calendar.getInstance();
                    inicioTratamiento.setTime(rs.getDate("inicioTratamiento"));

                    if (per instanceof Pacientes && rs.getInt("dni") == per.getDni()) {
                        ((Pacientes) per).setEnfermedad(rs.getString("enfermedad"));
                        ((Pacientes) per).setInicioTratamiento(inicioTratamiento);

                        ArrayList<Medicamentos> medsAux = new ArrayList<Medicamentos>();
                        medsAux = selectPacientesMedicamentos(per, medicamentos);
                        ((Pacientes) per).setMedicamentos(medsAux);

                    }
                }
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personas;
    }

    public static ArrayList<Medicamentos> selectPacientesMedicamentos(Personas persona, ArrayList<Medicamentos> medicamentos) {
        ArrayList<Medicamentos> medsAux = new ArrayList<Medicamentos>();

        try {
            Connection conn = Conexion.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT dni, idMed FROM PacientesMedicamentos WHERE dni=" + persona.getDni());
            while (rs.next()) {

                for (Medicamentos medicamento : medicamentos
                ) {
                    if (medicamento.getIdMed() == rs.getInt("idMed")) {
                        medsAux.add(medicamento);
                    }
                }
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medsAux;
    }

    public static void insertPersonas(Personas persona) {

        try {

            int tipoPersona = 0;
            if (persona instanceof Donadores) {

                tipoPersona = 1;
            }

            for(Medicamentos med : ((Pacientes) persona).getMedicamentos()){

                Connection conn = Conexion.getConnection();
                Statement stmt = conn.createStatement();
                stmt.executeQuery("INSERT INTO Personas VALUES (" + persona.getDni() + ",'" + persona.getNombre() + "','" + persona.getApellido()
                        + "','" + persona.getSexo() + "','" + persona.getFechaNac().get(Calendar.YEAR) + + persona.getFechaNac().get(Calendar.MONTH)
                        + persona.getFechaNac().get(Calendar.DAY_OF_MONTH) + "'," + persona.getLocalidad().getIdLocalidad() +
                        "," + persona.getTipoSangre().getId() + "," + tipoPersona + ")");
                conn.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
