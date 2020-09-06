package controller;

import model.*;
import model.DAO.MedicamentosTXT;
import model.DAO.PersonasTXT;
import view.FrameIngreso;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import static controller.Controlador.*;
import static controller.PersonasControlador.*;

public class CtrlFrameIngreso implements ActionListener, ItemListener {

    private FrameIngreso vista;

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.getButtonAceptar()) {

            agregarPersona(vista);
            vista.limpiar(false);

        } else if (e.getSource() == vista.getButtonCopiar()) {
            vista.getMedsAux().removeAllElements();
            List<String> lst = vista.getListMedicamentos().getSelectedValuesList();
            for (int i = 0; i < lst.size(); i++) {
                vista.getMedsAux().add(i, lst.get(i));
            }

        } else if (e.getSource() == vista.getButtonEditar()) {
            vista.editable(true);
            vista.getButtonAceptar().setVisible(true);
            vista.getButtonCancelar().setVisible(true);
            vista.getButtonAceptar().setEnabled(true);
            vista.getButtonCancelar().setEnabled(true);
            vista.getButtonEditar().setVisible(false);
            vista.getTextDNI().setEnabled(false);
            vista.getRadioButtonDonador().setEnabled(false);
            vista.getRadioButtonPaciente().setEnabled(false);
            vista.getButtonAnular().setVisible(false);

        } else if (e.getSource() == vista.getButtonAnular()) {
            int dni = Integer.parseInt(vista.getTextDNI().getText().trim());

            int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar la persona?",
                    "Confirmación", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {

                eliminarPersona(dni);
                PersonasTXT.grabarSetPersonasTXT(personasConPacientes);
                MedicamentosTXT.grabarPacientesMedicamentosTXT(personasConPacientes);
                JOptionPane.showMessageDialog(null, "La persona se elimino correctamente");
            }
            vista.limpiar(false);
        }
    }

    public void setVista(FrameIngreso vista) {


        vista.getComboProvincias().addItem("Seleccione una Provincia");
        vista.getComboLocalidades().addItem("Seleccione una localidad");
        for (Provincias pro : provincias) {
            vista.getComboProvincias().addItem(pro.getNombreProv());

        }

        for (Medicamentos med : medicamentos) {
            vista.getMeds().addElement(med.getNombreMed());
        }

        this.vista = vista;

    }

    public void itemStateChanged(ItemEvent o) {
        String provinciaSeleccionada;
        List<String> STLocalidades;
        if (o.getSource() == vista.getComboProvincias()) {
            if (o.getStateChange() == ItemEvent.SELECTED) {
                if (vista.getComboProvincias().getSelectedIndex() > 0) {
                    provinciaSeleccionada = vista.getComboProvincias().getSelectedItem().toString();

                    vista.getComboLocalidades().removeAllItems();
                    vista.getComboLocalidades().addItem("Seleccione una localidad");

                    STLocalidades = getLocalidadesxProvincia(provinciaSeleccionada);
                    String[] locPorProvincia = new String[STLocalidades.size()];
                    locPorProvincia = STLocalidades.toArray(locPorProvincia);

                    for (int i = 0; i < locPorProvincia.length; i++) {
                        vista.getComboLocalidades().addItem(locPorProvincia[i]);

                    }
                    vista.getLabelLocalidad().setVisible(true);
                    vista.getComboLocalidades().setVisible(true);
                } else if (vista.getComboProvincias().getSelectedIndex() == 0) {

                    vista.getComboLocalidades().removeAllItems();
                    vista.getComboLocalidades().addItem("Seleccione una localidad");
                }
            }
        }

    }

}
