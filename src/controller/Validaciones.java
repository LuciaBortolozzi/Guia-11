package controller;

import java.util.Calendar;

public class Validaciones {

    public static Calendar convertirAFechaCalendar(String f) {
        Calendar fecha = Calendar.getInstance();

        if (f.matches("\\d{4}-\\d{2}-\\d{2}")) {
            //  aaaa-mm-dd
            String[] aux = f.split("-");
            int year = Integer.parseInt(aux[0]);
            int month = Integer.parseInt(aux[1]);
            int day = Integer.parseInt(aux[2]);

            if (day >= 1 && day <= 31
                    && month >= 1 && month <= 12
                    && year >= 1900) {

                fecha.set(Calendar.YEAR, year);
                fecha.set(Calendar.MONTH, (month - 1));
                fecha.set(Calendar.DAY_OF_MONTH, day);
            }

        }
        return fecha;
    }

}
