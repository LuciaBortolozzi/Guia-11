package controller;

import javax.swing.*;

public class PersonaExistente extends Exception {

    private JOptionPane msg = new JOptionPane();

    public PersonaExistente(int dni) {

        msg.showMessageDialog(null, dni, "Ya existe una persona con el DNI: ", JOptionPane.WARNING_MESSAGE);

    }

}
