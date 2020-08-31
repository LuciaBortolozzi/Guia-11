package model.DAO;

import model.Provincias;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ProvinciasTXT {

    //           private static final String directorio = "C:\\\\Users\\\\Flor\\\\IdeaProjects\\\\Guia-10\\\\src\\\\resources\\";
    private static final String directorio = "D:\\\\IdeaProjects\\\\Guia-10\\\\src\\\\resources\\\\";

    public static ArrayList<Provincias> bajarProvinciasTXT() {

        ArrayList<Provincias> provincias = new ArrayList<Provincias>();
        try {
            File archivo = new File(directorio + "Provincias.txt");
            if (archivo.exists()) {
                Scanner leerArchivoProvincias = new Scanner(archivo);
                ArrayList<String> provinciasST = new ArrayList<String>();

                //Guardar contenido en String
                while (leerArchivoProvincias.hasNext()) {
                    String lineaActual = leerArchivoProvincias.nextLine();
                    provinciasST.add(lineaActual);
                }

                // Guardar objetos
                for (String s : provinciasST) {

                    int codigoProvincia = Integer.parseInt(s.substring(0, 3).trim());
                    String nombreProvincia = s.substring(3, 23).trim();

                    provincias.add(new Provincias(nombreProvincia, codigoProvincia));
                }

                leerArchivoProvincias.close();
            }

        } catch (InputMismatchException | IOException e) {
            e.printStackTrace();
        }

        return provincias;
    }
}
