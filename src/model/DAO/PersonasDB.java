package model.DAO;

import controller.Conexion;
import controller.Controlador;
import model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeSet;


public class PersonasDB {

    public static TreeSet<Personas> selectPersonas(ArrayList<Provincias> provincias, ArrayList<Localidades> localidades, ArrayList<TiposSangre> tiposSangres) {
        TreeSet<Personas> personas = new TreeSet<Personas>();

        try {
            Connection conn = Conexion.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT dni, nombre, apellido, sexo, fechaNac, provincia, localidad, tipoSangre, tipoPersona FROM Personas");
            while (rs.next()) {
                Localidades localidad = Controlador.buscarLocalidad(provincias, rs.getInt("provincia"), localidades, rs.getInt("localidad"));
                TiposSangre tipoSangre = Controlador.buscarTipoSangre(tiposSangres, rs.getInt("tipoSangre"));
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

            int tipoPersona;

            if (persona instanceof Donadores) {
                tipoPersona = 1;
            } else {
                tipoPersona = 0;
            }

            Connection conn = Conexion.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeQuery("INSERT INTO Personas VALUES (" + persona.getDni() + ",'" + persona.getNombre() + "','" + persona.getApellido()
                    + "','" + persona.getSexo() + "','" + persona.getFechaNac().get(Calendar.YEAR) + (persona.getFechaNac().get(Calendar.MONTH) + 1)
                    + persona.getFechaNac().get(Calendar.DAY_OF_MONTH) + "'," + persona.getLocalidad().getProvincia() + "," + persona.getLocalidad().getIdLocalidad() +
                    "," + persona.getTipoSangre().getId() + "," + tipoPersona + ")");
            conn.close();


            if (persona instanceof Donadores) {

                insertDonadores(persona);

            } else {

                insertPacientes(persona);
                insertMedicamentosPacientes(persona);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertDonadores(Personas persona) throws SQLException {

        int donaSangre = 0;
        int donaPlasma = 0;
        int donaPlaquetas = 0;
        if (((Donadores) persona).isDonaSangre()) {
            donaSangre = 1;
        }
        if (((Donadores) persona).isDonaPlasma()) {
            donaPlasma = 1;
        }
        if (((Donadores) persona).isDonaPlaquetas()) {
            donaPlaquetas = 1;
        }

        Connection conn = Conexion.getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeQuery("INSERT INTO Donadores VALUES (" + persona.getDni() + "," + donaSangre + "," + donaPlasma
                + "," + donaPlaquetas + ")");
        conn.close();

    }

    public static void insertPacientes(Personas persona) throws SQLException {

        Connection conn = Conexion.getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeQuery("INSERT INTO Pacientes VALUES (" + persona.getDni() + ",'" + ((Pacientes) persona).getEnfermedad() + "','"
                + ((Pacientes) persona).getInicioTratamiento().get(Calendar.YEAR) + (((Pacientes) persona).getInicioTratamiento().get(Calendar.MONTH) + 1)
                + ((Pacientes) persona).getInicioTratamiento().get(Calendar.DAY_OF_MONTH) + "')");
        conn.close();
    }

    public static void insertMedicamentosPacientes(Personas persona) {

        try {
            Connection conn = Conexion.getConnection();
            Statement stmt = conn.createStatement();

            if (persona instanceof Pacientes) {

                for (Medicamentos med : ((Pacientes) persona).getMedicamentos()) {

                    stmt.executeQuery("INSERT INTO PacientesMedicamentos VALUES(" + persona.getDni() + "," + med.getIdMed() + ")");

                }
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePersonas(Personas persona) {

        try {

            int tipoPersona;

            if (persona instanceof Donadores) {
                tipoPersona = 1;
            } else {
                tipoPersona = 0;
            }

            Connection conn = Conexion.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeQuery("UPDATE Personas SET nombre='" + persona.getNombre() + "', apellido='" + persona.getApellido()
                    + "', sexo='" + persona.getSexo() + "', fechaNac='" + persona.getFechaNac().get(Calendar.YEAR) + (persona.getFechaNac().get(Calendar.MONTH) + 1)
                    + persona.getFechaNac().get(Calendar.DAY_OF_MONTH) + "', provincia=" + persona.getLocalidad().getProvincia() + ", localidad=" + persona.getLocalidad().getIdLocalidad() +
                    ", tipoSangre=" + persona.getTipoSangre().getId() + " WHERE dni=" + persona.getDni());
            conn.close();

            if (persona instanceof Donadores) {

                updateDonadores(persona);

            } else {

                updatePacientes(persona);
                updateMedicamentosPacientes(persona);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateDonadores(Personas persona) throws SQLException {

        int donaSangre = 0;
        int donaPlasma = 0;
        int donaPlaquetas = 0;
        if (((Donadores) persona).isDonaSangre()) {
            donaSangre = 1;
        }
        if (((Donadores) persona).isDonaPlasma()) {
            donaPlasma = 1;
        }
        if (((Donadores) persona).isDonaPlaquetas()) {
            donaPlaquetas = 1;
        }

        Connection conn = Conexion.getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeQuery("UPDATE Donadores SET donaSangre=" + donaSangre + ", donaPlasma=" + donaPlasma
                + ", donaPlaquetas=" + donaPlaquetas + " WHERE  dni="+ persona.getDni());
        conn.close();
    }

    public static void updatePacientes(Personas persona) throws SQLException {

        Connection conn = Conexion.getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeQuery("UPDATE Pacientes SET enfermedad='" + ((Pacientes) persona).getEnfermedad() + "', inicioTratamiento='"
                + ((Pacientes) persona).getInicioTratamiento().get(Calendar.YEAR) + (((Pacientes) persona).getInicioTratamiento().get(Calendar.MONTH) + 1)
                + ((Pacientes) persona).getInicioTratamiento().get(Calendar.DAY_OF_MONTH) + "' WHERE  dni="+ persona.getDni());
        conn.close();
    }

    public static void updateMedicamentosPacientes(Personas persona) {

        try {
            Connection conn = Conexion.getConnection();
            Statement stmt = conn.createStatement();

            stmt.executeQuery("DELETE FROM PacientesMedicamentos WHERE dni=" + persona.getDni());
            insertMedicamentosPacientes(persona);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletePersona(Personas persona) {

        try {
            Connection conn = Conexion.getConnection();
            Statement stmt = conn.createStatement();

            if (persona instanceof Pacientes) {

                stmt.executeQuery("DELETE FROM PacientesMedicamentos WHERE dni=" + persona.getDni());
                stmt.executeQuery("DELETE FROM Pacientes WHERE dni=" + persona.getDni());

            } else {

                stmt.executeQuery("DELETE FROM Extracciones WHERE dniDonador=" + persona.getDni());
                stmt.executeQuery("DELETE FROM Donadores WHERE dni=" + persona.getDni());

            }

            stmt.executeQuery("DELETE FROM Personas WHERE dni=" + persona.getDni());
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
