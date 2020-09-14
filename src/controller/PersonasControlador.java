package controller;

import model.*;
import model.DAO.PersonasDB;
import view.FrameIngreso;

import javax.swing.*;
import java.util.*;

import static controller.Controlador.*;

public class PersonasControlador {

    public static Personas buscarPersona(int dniPersona) {

        Personas persona;
        Iterator<Personas> iteratorPersonas = personasConPacientes.iterator();
        while (iteratorPersonas.hasNext()) {
            persona = iteratorPersonas.next();

            if (persona.getDni() == dniPersona) {
                return persona;
            }
        }
        return null;
    }

    public static void agregarPersona(FrameIngreso vista) {

        String error = "";
        int dni;
        int d, m, y;
        String nombreST;
        String apellidoST;
        char sexo;
        String fechaNacST;
        Calendar fechaNac = Calendar.getInstance();

        boolean donaSangre;
        boolean donaPlaquetas;
        boolean donaPlasma;

        String enfermedadST;
        String inicioTratamientoST;
        Calendar inicioTratamiento = Calendar.getInstance();

        try {

            nombreST = vista.getTextNombre().getText().toUpperCase().trim();
            apellidoST = vista.getTextApellido().getText().toUpperCase().trim();

            dni = Integer.parseInt(vista.getTextDNI().getText().trim());

            if (dni > 5000000) {

                // Validar con excepcion propia si ya existe la persona
                if (vista.esIngreso()) {

                    Personas persona = buscarPersona(dni);

                    if (persona != null) {
                        throw new PersonaExistente(dni);
                    }
                }

            } else {
                error = "Error al ingresar DNI\n";
                throw new Exception();
            }

            fechaNacST = vista.getTextFechaNac().getText().trim();

            if (fechaNacST.matches("\\d{8}")) {

                d = Integer.parseInt(fechaNacST.substring(0, 2));
                m = Integer.parseInt(fechaNacST.substring(2, 4));
                y = Integer.parseInt(fechaNacST.substring(4, 8));

                if (d >= 1 && d <= 31
                        && m >= 1 && m <= 12
                        && y >= 1900) {

                    m = m - 1;
                    fechaNac.set(y, m, d);
                }

            } else {
                error = "Error al ingresar fecha de nacimiento (el formato debe ser DDMMYYYY) \n";
                throw new Exception();
            }

            if (vista.getRadioButtonFem().isSelected()) {

                sexo = 'F';

            } else if (vista.getRadioButtonMasc().isSelected()) {

                sexo = 'M';

            } else {
                error = "No selecciono sexo \n";
                throw new Exception();
            }

            String localidadST = vista.getComboLocalidades().getSelectedItem().toString();
            Localidades localidad = Controlador.buscarLocalidad(localidadST);

            String tipoSangreST = vista.getComboTiposSangre().getSelectedItem().toString();
            TiposSangre tipoSangre = Controlador.buscarTipoSangreST(tipoSangreST);


            if (vista.getRadioButtonDonador().isSelected()) {

                donaPlaquetas = vista.getBoxPlaquetas().isSelected();

                donaPlasma = vista.getBoxPlasma().isSelected();

                donaSangre = vista.getBoxSangre().isSelected();

                Personas persona = new Donadores(nombreST, apellidoST, dni, localidad, fechaNac, sexo, tipoSangre, donaSangre, donaPlaquetas, donaPlasma);

                if (vista.esIngreso()) {

                    personasConPacientes.add(persona);
                    JOptionPane.showMessageDialog(null, "La persona se ingreso correctamente");
                    PersonasDB.insertPersonas(persona);

                } else {

                    PersonasControlador.modificarPersona(persona);
                    JOptionPane.showMessageDialog(null, "La persona se modifico correctamente");
                    PersonasDB.updatePersonas(persona);

                }

            } else if (vista.getRadioButtonPaciente().isSelected()) {

                enfermedadST = vista.getTextEnfermedad().getText().toUpperCase().trim();

                inicioTratamientoST = vista.getTextInicioTratamiento().getText().trim();

                if (inicioTratamientoST.matches("\\d{8}")) {

                    d = Integer.parseInt(inicioTratamientoST.substring(0, 2));
                    m = Integer.parseInt(inicioTratamientoST.substring(2, 4));
                    y = Integer.parseInt(inicioTratamientoST.substring(4, 8));

                    if (d >= 1 && d <= 31
                            && m >= 1 && m <= 12
                            && y >= 1900) {

                        m = m - 1;
                        inicioTratamiento.set(y, m, d);
                    }
                } else {
                    error = "Error al ingresar inicio del tratamiento (el formato debe ser DDMMYYYY) \n";
                    throw new Exception();
                }

                List<String> medicamentosST = new ArrayList<String>();
                ArrayList<Medicamentos> meds = new ArrayList<Medicamentos>();

                for (int i = 0; i < vista.getMedsAux().size(); i++) {
                    medicamentosST.add(vista.getMedsAux().get(i));
                    meds = Controlador.buscarMedicamentos(medicamentosST);
                }

                Personas persona = new Pacientes(nombreST, apellidoST, dni, localidad, fechaNac, sexo, tipoSangre, enfermedadST, meds, inicioTratamiento);

                if (vista.esIngreso()) {

                    personasConPacientes.add(persona);
                    JOptionPane.showMessageDialog(null, "La persona se ingreso correctamente");
                    PersonasDB.insertPersonas(persona);

                } else {

                    PersonasControlador.modificarPersona(persona);
                    JOptionPane.showMessageDialog(null, "La persona se modifico correctamente");
                    PersonasDB.updatePersonas(persona);

                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en los datos ingresados\n" + error);
        }
    }

    public static void modificarPersona(Personas persona) {

        boolean encontrada = false;

        Personas personaAux;
        Iterator<Personas> per = personasConPacientes.iterator();
        while (per.hasNext()) {
            personaAux = per.next();

            if (personaAux.getDni() == persona.getDni()) {

                encontrada = true;
                personaAux.setNombre(persona.getNombre());
                personaAux.setApellido(persona.getApellido());
                personaAux.setTipoSangre(persona.getTipoSangre());
                personaAux.setLocalidad(persona.getLocalidad());
                personaAux.setSexo(persona.getSexo());

                if (personaAux instanceof Donadores) {

                    ((Donadores) personaAux).setDonaPlaquetas(((Donadores) persona).isDonaPlaquetas());
                    ((Donadores) personaAux).setDonaPlasma(((Donadores) persona).isDonaPlasma());
                    ((Donadores) personaAux).setDonaSangre(((Donadores) persona).isDonaSangre());

                } else if (personaAux instanceof Pacientes) {

                    ((Pacientes) personaAux).setInicioTratamiento(((Pacientes) persona).getInicioTratamiento());
                    ((Pacientes) personaAux).setEnfermedad(((Pacientes) persona).getEnfermedad());
                    ((Pacientes) personaAux).setMedicamentos(((Pacientes) persona).getMedicamentos());

                }
                break;
            }
        }

        if (!encontrada) {
            JOptionPane.showMessageDialog(null, "No se puede modificar la persona, la misma no se encuentra ingresada");
        }
    }

    public static void eliminarPersona(int dni) {

        boolean encontrada = false;

        Personas personaAux;
        Iterator<Personas> per = personasConPacientes.iterator();
        while (per.hasNext()) {
            personaAux = per.next();

            if (personaAux.getDni() == dni) {
                encontrada = true;
                personasConPacientes.remove(personaAux);
                break;
            }
        }

        if (!encontrada) {
            JOptionPane.showMessageDialog(null, "No se puede eliminar la persona, la misma no se encuentra ingresada");
        }
    }

    public static void mostrarPersona(Personas persona, FrameIngreso vista) {

        vista.getTextDNI().setText(String.valueOf(persona.getDni()));
        vista.getTextApellido().setText(persona.getApellido());
        vista.getTextNombre().setText(persona.getNombre());
        String fechaNac = String.format("%02d", persona.getFechaNac().get(Calendar.DAY_OF_MONTH))
                + String.format("%02d", (persona.getFechaNac().get(Calendar.MONTH) + 1))
                + String.format("%04d", (persona.getFechaNac().get(Calendar.YEAR)));
        vista.getTextFechaNac().setText(fechaNac);
        vista.getComboTiposSangre().setSelectedItem(persona.getTipoSangre().getGrupo() + "-RH" + persona.getTipoSangre().getFactor());
        vista.getComboProvincias().setSelectedItem(persona.getLocalidad().getProvincia().getNombreProv());
        vista.getComboLocalidades().setSelectedItem(persona.getLocalidad().getNombreLoc());

        vista.getRadioButtonFem().setSelected(persona.getSexo() == 'F' ? true : false);
        vista.getRadioButtonMasc().setSelected(persona.getSexo() == 'M' ? true : false);

        if (persona instanceof Pacientes) {
            vista.getRadioButtonPaciente().setSelected(true);
            vista.getRadioButtonDonador().setSelected(false);

            vista.getTextEnfermedad().setText(((Pacientes) persona).getEnfermedad());
            String fechaInicioTratamiento = String.format("%02d", ((Pacientes) persona).getInicioTratamiento().get(Calendar.DAY_OF_MONTH))
                    + String.format("%02d", (((Pacientes) persona).getInicioTratamiento().get(Calendar.MONTH) + 1))
                    + String.format("%04d", ((Pacientes) persona).getInicioTratamiento().get(Calendar.YEAR));
            vista.getTextInicioTratamiento().setText(fechaInicioTratamiento);

            for (Medicamentos meds : medicamentos) {
                vista.getMeds().addElement(meds.getNombreMed());
            }

            for (Medicamentos meds : ((Pacientes) persona).getMedicamentos()) {
                vista.getMedsAux().addElement(meds.getNombreMed());
            }

        } else {
            vista.getRadioButtonPaciente().setSelected(false);
            vista.getRadioButtonDonador().setSelected(true);

            boolean plaquetas = ((Donadores) persona).isDonaPlaquetas();
            boolean plasma = ((Donadores) persona).isDonaPlasma();
            boolean sangre = ((Donadores) persona).isDonaSangre();

            vista.getBoxPlaquetas().setSelected(plaquetas);
            vista.getBoxPlasma().setSelected(plasma);
            vista.getBoxSangre().setSelected(sangre);

        }

    }
}
