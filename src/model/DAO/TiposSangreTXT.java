package model.DAO;

import model.TiposSangre;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TiposSangreTXT {

    //        private static final String directorio = "C:\\\\Users\\\\Flor\\\\IdeaProjects\\\\Guia-10\\\\src\\\\resources\\\\";
    private static final String directorio = "D:\\\\IdeaProjects\\\\Guia-10\\\\src\\\\resources\\\\";

    public static ArrayList<TiposSangre> bajarTiposSangreTXT() {

        ArrayList<TiposSangre> tiposSangre = new ArrayList<TiposSangre>();
        try {
            File archivo = new File(directorio + "TiposSangre.txt");
            if (archivo.exists()) {
                Scanner leerArchivoTiposSangre = new Scanner(archivo);
                ArrayList<String> TiposSangreST = new ArrayList<String>();

                //Guardar contenido en String
                while (leerArchivoTiposSangre.hasNext()) {
                    String lineaActual = leerArchivoTiposSangre.nextLine();
                    TiposSangreST.add(lineaActual);
                }

                // Guardar objetos
                for (String s : TiposSangreST) {

                    int id = Integer.parseInt(s.substring(0, 2).trim());
                    String grupo = s.substring(2, 4).trim();
                    String factor = s.substring(4, 12).trim();

                    tiposSangre.add(new TiposSangre(id, grupo, factor));
                }

                leerArchivoTiposSangre.close();
            }

        } catch (InputMismatchException | IOException e) {
            e.printStackTrace();
        }

        return tiposSangre;
    }
}
