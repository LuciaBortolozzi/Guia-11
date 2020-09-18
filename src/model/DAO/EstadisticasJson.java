package model.DAO;

import model.Personas;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import java.io.*;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.TreeSet;

public class EstadisticasJson {

//           private static final String directorio = "C:\\\\Users\\\\Flor\\\\IdeaProjects\\\\Guia-10\\\\src\\\\resources\\\\";
    private static final String directorio = "D:\\\\IdeaProjects\\\\Guia-10\\\\src\\\\resources\\\\";

    public static void grabarJsonStream(TreeSet<Personas> personasAux) {

        try {

            File aJson = new File(directorio + "EstadisticaPeso.json");
            FileOutputStream fsOutJson = new FileOutputStream(aJson);
            JsonGenerator genJson = Json.createGenerator(fsOutJson);

            Personas persona;
            Iterator<Personas> iteratorPersonas = personasAux.iterator();
            genJson.writeStartObject(); //objeto inicial
            genJson.writeStartArray("Donadores");

            while (iteratorPersonas.hasNext()) {
                persona = iteratorPersonas.next();
                genJson.writeStartObject(); //objeto inicial
                genJson.write("Nombre", persona.getNombre());
                genJson.write("Apellido", persona.getApellido());
                genJson.write("DNI", persona.getDni());
                genJson.write("Fecha Nacimiento", String.format("%02d", persona.getFechaNac().get(Calendar.DAY_OF_MONTH)) + "/" +
                        String.format("%02d", (persona.getFechaNac().get(Calendar.MONTH) + 1)) + "/" +
                        persona.getFechaNac().get(Calendar.YEAR));
                genJson.writeEnd();
            }
            genJson.writeEnd();
            genJson.writeEnd();
            genJson.close();

        } catch (InputMismatchException | IOException e) {
            e.printStackTrace();
        }
    }
}
