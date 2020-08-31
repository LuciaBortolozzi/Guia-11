package controller;

import model.Personas;
import view.FrameConsultaAct;
import view.FrameIngreso;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;

import static controller.Controlador.personas;
import static controller.PersonasControlador.mostrarPersona;

public class CtrlFrameConsultaAct implements ActionListener, ItemListener {

    private FrameConsultaAct vista;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getButtonBuscar()) {

            Personas persona = buscarPersona(vista);

            if (persona != null) {

                CtrlFrameIngreso ctrlFrameIngreso = new CtrlFrameIngreso();
                FrameIngreso consulta = new FrameIngreso(ctrlFrameIngreso, false);
                mostrarPersona(persona, consulta);
                consulta.editable(false);

            } else {
                JOptionPane.showMessageDialog(null, "No existe esa persona");
            }
        }
    }

    private Personas buscarPersona(FrameConsultaAct vista) {
        Personas persona;
        try {
            int dni = Integer.parseInt(vista.getTextDNI().getText());

            Iterator<Personas> iteratorPersonas = personas.iterator();
            while (iteratorPersonas.hasNext()) {
                persona = iteratorPersonas.next();
                if (persona.getDni() == dni) {
                    return persona;
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un dni ");
        }
        return null;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    public void setVista(FrameConsultaAct vista) {
        this.vista = vista;
    }
}
