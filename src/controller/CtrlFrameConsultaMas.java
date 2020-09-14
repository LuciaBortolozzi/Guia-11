package controller;

import model.DAO.PersonasDB;
import model.Localidades;
import model.Personas;
import view.FrameConsultaMas;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;


import static controller.Controlador.personasConPacientes;

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

            DefaultTableModel dtm = PersonasDB.selectConsultaMasiva(provinciaST, tipoDeSangreST);

            vista.getTabla().setModel(dtm);
            vista.getTextResultados().setText(String.valueOf(dtm.getRowCount()));
            vista.getTextTotales().setText(String.valueOf(personasConPacientes.size()));

        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {

        Calendar fecha = Calendar.getInstance();

        if (e.getType() == TableModelEvent.UPDATE && e.getColumn() > 0) {

            String registro = ((String) vista.getTabla().getValueAt(e.getFirstRow(), e.getColumn())).toUpperCase();
            int columna = e.getColumn();
            int dni= Integer.parseInt( (String) vista.getTabla().getValueAt(vista.getTabla().getSelectedRow(),0));
            
            Personas persona = PersonasControlador.buscarPersona(dni);

            switch (columna) {
                case 1:
                    persona.setNombre(registro);
                    PersonasDB.updateTablaPersonas(persona);
                    break;

                case 2:
                    persona.setApellido(registro);
                    PersonasDB.updateTablaPersonas(persona);
                    break;

                case 3:
                    persona.setSexo(registro.charAt(0));
                    PersonasDB.updateTablaPersonas(persona);
                    break;

                case 4:
                   // persona.setFechaNac(registro);  ----> PASAR REG A FECHA
                    PersonasDB.updateTablaPersonas(persona);
                    break;

                case 5:
                    int localidad = Integer.parseInt(registro);
                    if(localidad >= 1 && localidad<=24){

                        Localidades loc = Controlador.buscarLocalidad(localidad);
                        persona.setLocalidad(loc);
                        PersonasDB.updateTablaPersonas(persona);
                    }
                    break;

            }
        }

    }

    /*public TreeSet<Personas> consultaPersonas(String provinciaST, String tipoDeSangreST) {

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

            String tipo = p.getTipoSangre().getGrupo() + "RH" + p.getTipoSangre().getFactor();
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
    }*/
}
