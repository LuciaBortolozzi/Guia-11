package defaultPackage;

import controller.CtrlFrameMenu;
import view.FrameMenu;

import java.sql.SQLException;

import static controller.Conexion.getConnection;

public class Main {

    public static void main(String[] args) {

        double totalPeso = Double.parseDouble(args[0]);

        try {
            getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        CtrlFrameMenu ctrlFrameMenu = new CtrlFrameMenu(totalPeso);
        new FrameMenu(ctrlFrameMenu);
    }
}
