package model.DAO;

import controller.Conexion;
import controller.Controlador;
import model.*;

import java.sql.*;
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

            String fechaNac = persona.getFechaNac().get(Calendar.YEAR) + "-" + (persona.getFechaNac().get(Calendar.MONTH) + 1) + "-"
                    + persona.getFechaNac().get(Calendar.DAY_OF_MONTH);
            Connection conn = Conexion.getConnection();

            CallableStatement stmt = conn.prepareCall("{call insertPersonas(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            stmt.setInt(1, persona.getDni());
            stmt.setString(2, persona.getNombre());
            stmt.setString(3, persona.getApellido());
            stmt.setString(4, String.valueOf(persona.getSexo()));
            stmt.setDate(5, Date.valueOf(fechaNac));
            stmt.setInt(6, persona.getLocalidad().getProvincia().getIdProvincia());
            stmt.setInt(7, persona.getLocalidad().getIdLocalidad());
            stmt.setInt(8, persona.getTipoSangre().getId());
            stmt.setInt(9, tipoPersona);
            stmt.execute();
            conn.close();


            if (persona instanceof Donadores) {

                insertDonadores(persona);

            } else {

                insertPacientes(persona);
                insertPacientesMedicamentos(persona);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertDonadores(Personas persona) {

        int donaSangre = 0;
        int donaPlaquetas = 0;
        int donaPlasma = 0;
        if (((Donadores) persona).isDonaSangre()) {
            donaSangre = 1;
        }
        if (((Donadores) persona).isDonaPlaquetas()) {
            donaPlaquetas = 1;
        }
        if (((Donadores) persona).isDonaPlasma()) {
            donaPlasma = 1;
        }

        try {
            Connection conn = Conexion.getConnection();
            CallableStatement stmt = conn.prepareCall("{call insertDonadores(?, ?, ?, ?)}");
            stmt.setInt(1, persona.getDni());
            stmt.setInt(2, donaSangre);
            stmt.setInt(3, donaPlaquetas);
            stmt.setInt(4, donaPlasma);
            stmt.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertPacientes(Personas persona) {

        String inicioTratamiento = ((Pacientes) persona).getInicioTratamiento().get(Calendar.YEAR) + "-" + (((Pacientes) persona).getInicioTratamiento().get(Calendar.MONTH) + 1) + "-"
                + ((Pacientes) persona).getInicioTratamiento().get(Calendar.DAY_OF_MONTH);
        try {
            Connection conn = Conexion.getConnection();
            CallableStatement stmt = conn.prepareCall("{call insertPacientes(?, ?, ?)}");
            stmt.setInt(1, persona.getDni());
            stmt.setString(2, ((Pacientes) persona).getEnfermedad());
            stmt.setDate(3, Date.valueOf(inicioTratamiento));
            stmt.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertPacientesMedicamentos(Personas persona) {

        try {

            Connection conn = Conexion.getConnection();
            CallableStatement stmt;

            if (persona instanceof Pacientes) {

                for (Medicamentos med : ((Pacientes) persona).getMedicamentos()) {
                    stmt = conn.prepareCall("{call insertPacientesMedicamentos(?, ?)}");
                    stmt.setInt(1, persona.getDni());
                    stmt.setInt(2, med.getIdMed());
                    stmt.execute();
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
            stmt.executeUpdate("UPDATE Personas SET nombre='" + persona.getNombre() + "', apellido='" + persona.getApellido()
                    + "', sexo='" + persona.getSexo() + "', fechaNac='" + persona.getFechaNac().get(Calendar.YEAR) + "-" + (persona.getFechaNac().get(Calendar.MONTH) + 1) + "-"
                    + persona.getFechaNac().get(Calendar.DAY_OF_MONTH) + "', provincia=" + persona.getLocalidad().getProvincia().getIdProvincia() + ", localidad=" + persona.getLocalidad().getIdLocalidad() +
                    ", tipoSangre=" + persona.getTipoSangre().getId() + " WHERE dni=" + persona.getDni() + "");

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
        stmt.executeUpdate("UPDATE Donadores SET donaSangre=" + donaSangre + ", donaPlasma=" + donaPlasma
                + ", donaPlaquetas=" + donaPlaquetas + " WHERE  dni=" + persona.getDni() + "");
        conn.close();
    }

    public static void updatePacientes(Personas persona) throws SQLException {

        Connection conn = Conexion.getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE Pacientes SET enfermedad='" + ((Pacientes) persona).getEnfermedad() + "', inicioTratamiento='"
                + ((Pacientes) persona).getInicioTratamiento().get(Calendar.YEAR) + "-" + (((Pacientes) persona).getInicioTratamiento().get(Calendar.MONTH) + 1) + "-"
                + ((Pacientes) persona).getInicioTratamiento().get(Calendar.DAY_OF_MONTH) + "' WHERE  dni=" + persona.getDni() + "");
        conn.close();
    }

    public static void updateMedicamentosPacientes(Personas persona) {

        try {
            Connection conn = Conexion.getConnection();
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("DELETE FROM PacientesMedicamentos WHERE dni=" + persona.getDni() + "");
            insertPacientesMedicamentos(persona);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletePersona(Personas persona) {

        try {
            Connection conn = Conexion.getConnection();
            CallableStatement stmt;

            if (persona instanceof Pacientes) {

                if (!((Pacientes) persona).getMedicamentos().isEmpty()) {
                    stmt = conn.prepareCall("{call deletePacientesMedicamentos(?)}");
                    stmt.setInt(1, persona.getDni());
                    stmt.execute();
                }
                stmt = conn.prepareCall("{call deletePacientes(?)}");
                stmt.setInt(1, persona.getDni());
                stmt.execute();

            } else {

                if (!((Donadores) persona).getExtracciones().isEmpty()) {
                    stmt = conn.prepareCall("{call deleteExtracciones(?)}");
                    stmt.setInt(1, persona.getDni());
                    stmt.execute();
                }
                stmt = conn.prepareCall("{call deleteDonadores(?)}");
                stmt.setInt(1, persona.getDni());
                stmt.execute();

            }

            stmt = conn.prepareCall("{call deletePersonas(?)}");
            stmt.setInt(1, persona.getDni());
            stmt.execute();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static double calcularMililitros() {

        double cantidad = 0;

        try {
            Connection conn = Conexion.getConnection();
            CallableStatement instruction = conn.prepareCall("{call calcularMililitros (?)}");
            instruction.registerOutParameter(1, java.sql.Types.DOUBLE);
            instruction.execute();

            cantidad = instruction.getDouble(1);
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cantidad;
    }

    public static void updateTablaPersonas(Personas persona) {

        try {
            Connection conn = Conexion.getConnection();

            PreparedStatement pStat = conn.prepareStatement("UPDATE Personas SET nombre = ?, apellido = ?, sexo = ?, localidad = ? WHERE dni = ?");

            pStat.setString(1, persona.getNombre());
            pStat.setString(2, persona.getApellido());
            pStat.setString(3, String.valueOf(persona.getSexo()));
            pStat.setInt(4, persona.getLocalidad().getIdLocalidad());
            pStat.setInt(5, persona.getDni());
            pStat.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DEPRECATED - consumir tabla de memoria en vez de db
    /*public static DefaultTableModel selectConsultaMasiva(String provinciaST, String tipoDeSangreST) {
        DefaultTableModel dtm = new DefaultTableModel();

        try {
            Connection conn = Conexion.getConnection();
            ResultSet Idprov = null;
            ResultSet IdSan = null;
            ResultSet rs;

            String provincia = "";
            if (provinciaST.length() != 0) {
                provincia = provinciaST.toUpperCase().trim();
                PreparedStatement pStatProv = conn.prepareStatement("SELECT idProvincia FROM Provincias WHERE nombreProv = ?");
                pStatProv.setString(1, provincia);
                Idprov = pStatProv.executeQuery();
                Idprov.next();
            }

            String tipoDeSangre = "";
            if (tipoDeSangreST.length() != 0) {
                tipoDeSangre = tipoDeSangreST.toUpperCase().trim();
                PreparedStatement pStatSang = conn.prepareStatement("SELECT id FROM TiposSangre WHERE CONCAT(grupo,factor)= ?");
                pStatSang.setString(1, tipoDeSangre);
                IdSan = pStatSang.executeQuery();
                IdSan.next();
            }

            if (Idprov != null && IdSan != null) {
                PreparedStatement pStat = conn.prepareStatement("SELECT dni, nombre, apellido, sexo, fechaNac, localidad FROM Personas WHERE tipoSangre = ? AND provincia = ?");
                pStat.setInt(2, IdSan.getInt("id"));
                pStat.setInt(1, Idprov.getInt("idProvincia"));
                rs = pStat.executeQuery();
            } else if (Idprov != null) {
                PreparedStatement pStat = conn.prepareStatement("SELECT dni, nombre, apellido, sexo, fechaNac, localidad FROM Personas WHERE provincia = ?");
                pStat.setInt(1, Idprov.getInt("idProvincia"));
                rs = pStat.executeQuery();
            } else if (IdSan != null) {
                PreparedStatement pStat = conn.prepareStatement("SELECT dni, nombre, apellido, sexo, fechaNac, localidad FROM Personas WHERE tipoSangre = ?");
                pStat.setInt(1, IdSan.getInt("id"));
                rs = pStat.executeQuery();
            } else {
                PreparedStatement pStat = conn.prepareStatement("SELECT dni, nombre, apellido, sexo, fechaNac, localidad FROM Personas ");
                rs = pStat.executeQuery();
            }


            ResultSetMetaData rsmd = rs.getMetaData();      //info de las columnas del rs
            int numCols = rsmd.getColumnCount();
            Object[] datosDin = new Object[numCols];
            String colDin[] = new String[numCols];

            for (int i = 0; i < numCols; i++) {
                colDin[i] = rsmd.getColumnName(i + 1);
            }        //nombre de columnas
            dtm.setColumnIdentifiers(colDin);
            while (rs.next()) {
                for (int i = 0; i < numCols; i++) {
                    datosDin[i] = rs.getObject(i + 1);
                }
                dtm.addRow(datosDin);
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dtm;
    }*/
}
