package model.DAO;

import controller.Conexion;
import model.Provincias;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProvinciasDB {

    public static ArrayList<Provincias> selectProvincias() {
        ArrayList<Provincias> provincias = new ArrayList<Provincias>();

        try {
            Connection conn = Conexion.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT idProvincia, nombreProv FROM Provincias");
            while (rs.next()) {
                provincias.add(new Provincias(rs.getString("nombreProv"), rs.getInt("idProvincia")));
            }
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return provincias;
    }
}
