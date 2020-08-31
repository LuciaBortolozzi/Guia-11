package controller;

import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CtrlFrameMenu implements ActionListener {

    private FrameMenu frameMenu;
    private double totalPeso;

    public CtrlFrameMenu(double totalPeso) {

        this.totalPeso = totalPeso;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frameMenu.getSubopcion1()) {

            new FrameIngreso(new CtrlFrameIngreso(), true);

        } else if (e.getSource() == frameMenu.getSubopcion2()) {

            new FrameConsultaAct(new CtrlFrameConsultaAct());

        } else if (e.getSource() == frameMenu.getSubopcion3()) {

            new FrameConsultaMas(new CtrlFrameConsultaMas());

        } else if (e.getSource() == frameMenu.getOpcion2()) {

            new FrameEstadisticas(new CtrlFrameEstadisticas(totalPeso));

        }
    }

    public FrameMenu getFrameMenu() {
        return frameMenu;
    }

    public void setFrameMenu(FrameMenu frameMenu) {
        this.frameMenu = frameMenu;
    }
}
