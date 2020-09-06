package model.DAO;

import controller.Conexion;
import model.Localidades;
import model.Provincias;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LocalidadesDB {

    public static ArrayList<Localidades> selectLocalidades(ArrayList<Provincias> provincias) {
        ArrayList<Localidades> localidades = new ArrayList<Localidades>();

        try {
            Connection conn = Conexion.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT idProvincia, idLocalidad, nombreLoc, codigoPostal FROM Localidades");
            while (rs.next()) {
                Provincias provincia = new Provincias();
                int idProvincia = (rs.getInt("idProvincia"));

                for (Provincias prov : provincias) {
                    if (prov.getIdProvincia() == idProvincia) {
                        provincia = prov;
                    }
                }
                localidades.add(new Localidades(rs.getInt("idLocalidad"), rs.getString("nombreLoc"), rs.getString("codigoPostal"), provincia));
            }
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return localidades;
    }

}
