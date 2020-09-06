package model.DAO;

import model.Localidades;
import model.Provincias;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class LocalidadesTXT {

    //    private static final String directorio = "C:\\\\Users\\\\Flor\\\\IdeaProjects\\\\Guia-10\\\\src\\\\resources\\";
    private static final String directorio = "D:\\\\IdeaProjects\\\\Guia-10\\\\src\\\\resources\\\\";

/*    public static ArrayList<Localidades> bajarLocalidadesTXT(ArrayList<Provincias> provincias) {

        ArrayList<Localidades> localidades = new ArrayList<Localidades>();
        try {
            File archivo = new File(directorio + "Localidades.txt");
            if (archivo.exists()) {
                Scanner leerArchivoLocalidades = new Scanner(archivo);
                ArrayList<String> localidadesST = new ArrayList<String>();

                //Guardar contenido en String
                while (leerArchivoLocalidades.hasNext()) {
                    String lineaActual = leerArchivoLocalidades.nextLine();
                    localidadesST.add(lineaActual);
                }

                // Guardar objetos
                for (String s : localidadesST) {

                    String letraProvincia = s.substring(0, 2).trim();
                    int codigoProvincia = Integer.parseInt(s.substring(2, 6).trim());
                    String numeroPostal = s.substring(6, 14).trim();
                    String nombreLocalidad = s.substring(14, 34).trim();
                    String codigoPostal = letraProvincia + numeroPostal;

                    Provincias provinciaAux = agregarProvincia(provincias, codigoProvincia);

                    localidades.add(new Localidades(nombreLocalidad, codigoPostal, provinciaAux));
                }

                leerArchivoLocalidades.close();
            }

        } catch (InputMismatchException | IOException e) {
            e.printStackTrace();
        }

        return localidades;
    }*/

    public static Provincias agregarProvincia(ArrayList<Provincias> provincias, int codigoProvincia) {

        Provincias provincia;
        Provincias provinciaAux = null;
        Iterator<Provincias> prov = provincias.iterator();
        while (prov.hasNext()) {
            provincia = prov.next();

            if (provincia.getIdProvincia() == codigoProvincia) {
                provinciaAux = provincia;
                break;
            }
        }
        return provinciaAux;
    }
}
