package controller;

import model.DAO.PersonasDB;
import model.Localidades;
import model.Personas;
import view.FrameConsultaMas;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.TreeSet;

import static controller.Controlador.personasConPacientes;
import static controller.Validaciones.convertirAFechaCalendar;

public class CtrlFrameConsultaMas implements ActionListener, TableModelListener {

    private FrameConsultaMas vista;

    public void setVista(FrameConsultaMas vista) {
        this.vista = vista;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getButtonBuscar()) {
            String provinciaST = vista.getTextProvincia().getText();
            String tipoDeSangreST = vista.getTextTipoSangre().getText();

            vista.getTableModel().setRowCount(0);

            TreeSet<Personas> personasAux = consultaPersonas(provinciaST, tipoDeSangreST);
            for (Personas pers : personasAux) {
                Object[] row = {pers.getDni(), pers.getNombre(),
                        pers.getApellido(), pers.getLocalidad().getNombreLoc(),
                        String.format("%02d", pers.getFechaNac().get(Calendar.DAY_OF_MONTH)) + "/" +
                                String.format("%02d", (pers.getFechaNac().get(Calendar.MONTH) + 1)) + "/" +
                                pers.getFechaNac().get(Calendar.YEAR), pers.getSexo()};
                vista.getTableModel().addRow(row);
            }

            vista.getTextResultados().setText(String.valueOf(personasAux.size()));
            vista.getTextTotales().setText(String.valueOf(personasConPacientes.size()));

        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {

        if (e.getType() == TableModelEvent.UPDATE) {

            String registro = ((String) vista.getTabla().getValueAt(e.getFirstRow(), e.getColumn())).toUpperCase().trim();
            int columna = e.getColumn();
            int dni = (Integer) (vista.getTabla().getValueAt(vista.getTabla().getSelectedRow(), 0));

            Personas persona = PersonasControlador.buscarPersona(dni);

            if (persona != null) {
                switch (columna) {
                    case 1:
                        persona.setNombre(registro);
                        PersonasDB.updateTablaPersonas(persona);
                        break;

                    case 2:
                        persona.setApellido(registro);
                        PersonasDB.updateTablaPersonas(persona);
                        break;

                    case 5:
                        char sexo = registro.charAt(0);
                        persona.setSexo(sexo);
                        PersonasDB.updateTablaPersonas(persona);
                        break;
                }
            }

        }

    }

    public TreeSet<Personas> consultaPersonas(String provinciaST, String tipoDeSangreST) {

        TreeSet<Personas> personasAux = new TreeSet<Personas>();

        String provincia = "";
        if (provinciaST.length() != 0) {
            provincia = provinciaST.toUpperCase().trim();
        }

        String tipoDeSangre = "";
        if (tipoDeSangreST.length() != 0) {
            tipoDeSangre = tipoDeSangreST.toUpperCase().trim();
        }

        if (provincia.equals("") && tipoDeSangre.equals("")) {
            return personasConPacientes;
        }

        for (Personas p : personasConPacientes) {

            String tipo = p.getTipoSangre().getGrupo() + p.getTipoSangre().getFactor();
            String prov = p.getLocalidad().getProvincia().getNombreProv();

            if (!provincia.equals("") && !tipoDeSangre.equals("")) {
                if (prov.equals(provincia) && tipo.equals(tipoDeSangre)) {
                    personasAux.add(p);
                }
            } else if (!provincia.equals("")) {
                if (prov.equals(provincia)) {
                    personasAux.add(p);
                }
            } else {
                if (tipo.equals(tipoDeSangre)) {
                    personasAux.add(p);
                }
            }
        }

        return personasAux;
    }
}
