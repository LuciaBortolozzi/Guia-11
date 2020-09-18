package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String server;
    private static String user;
    private static String password;

    static {
        try {
            Class.forName(driver);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {

        boolean lu = true;

        if (lu) {
            server = "jdbc:sqlserver://DESKTOP-LU;databaseName=Programacion_Avanzada";
            user = "sa";
            password = "prog.av";
        } else {
            server = "jdbc:sqlserver://DESKTOP-FLOR\\SQLEXPRESS:1433;databaseName=Programacion_Avanzada";
            user = "user";
            password = "1234";

        }

        return DriverManager.getConnection(server, user, password);
    }
}
