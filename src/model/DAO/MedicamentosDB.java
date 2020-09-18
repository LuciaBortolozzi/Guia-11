package model.DAO;

import controller.Conexion;
import model.Medicamentos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MedicamentosDB {

    public static ArrayList<Medicamentos> selectMedicamentos() {
        ArrayList<Medicamentos> medicamentos = new ArrayList<>();

        try {
            Connection conn = Conexion.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT idMed, nombreMed, nombreLab FROM Medicamentos");
            while (rs.next()) {
                medicamentos.add(new Medicamentos(rs.getInt("idMed"), rs.getString("nombreMed"), rs.getString("nombreLab")));
            }
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicamentos;
    }
}
