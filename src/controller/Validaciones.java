package controller;

import java.util.Calendar;

public class Validaciones {

    public static Calendar convertirAFechaCalendar(String f) {
        Calendar fecha = Calendar.getInstance();

        //  dd/mm/aaaa
        String[] aux = f.split("/");
        int day = Integer.parseInt(aux[0]);
        int month = Integer.parseInt(aux[1]);
        int year = Integer.parseInt(aux[2]);

        fecha.set(Calendar.DAY_OF_MONTH, day);
        fecha.set(Calendar.MONTH, (month - 1));
        fecha.set(Calendar.YEAR, year);

        return fecha;
    }

    public static Calendar seisMesesAntes(Calendar fechaActual) {
        fechaActual.add(Calendar.MONTH, -6);
        return fechaActual;
    }
}
