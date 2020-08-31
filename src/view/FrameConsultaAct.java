package view;

import controller.CtrlFrameConsultaAct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FrameConsultaAct {

    private CtrlFrameConsultaAct ctrlFrameConsultaAct;

    private JFrame ventana = new JFrame("Consulta personas");
    private ImageIcon icon = new ImageIcon("src/resources/blood-donation-1.png");
    private JPanel panelConsulta = new JPanel();

    private JTextArea textArea = new JTextArea("- Ingrese el documento de la persona del cual desea realizar la consulta -");
    private JLabel labelDNI = new JLabel("Documento");
    private JTextField textDNI = new JTextField(10);
    private JButton buttonBuscar = new JButton("Buscar");
    private JButton buttonCancelar = new JButton("Cancelar");

    public FrameConsultaAct(CtrlFrameConsultaAct ctrlFrameConsultaAct) {

        ventana.setIconImage(icon.getImage());
        ventana.setSize(450, 100);
        this.ctrlFrameConsultaAct = ctrlFrameConsultaAct;
        ctrlFrameConsultaAct.setVista(this);

        buttonCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiar(true);
            }
        });

        buttonBuscar.addActionListener(ctrlFrameConsultaAct);

        //ARREGLAR ESTO PARA QUE QUEDE MAS LINDO
        panelConsulta.setLayout(new FlowLayout());
        textArea.setEditable(false);
        panelConsulta.add(textArea);
        panelConsulta.add(labelDNI);
        panelConsulta.add(textDNI);
        panelConsulta.add(buttonBuscar);
        panelConsulta.add(buttonCancelar);

        ventana.add(panelConsulta);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

    }

    public void limpiar(boolean conf) {
        int rta = !(conf) ? 0 : (JOptionPane.showConfirmDialog(null, "Los datos no fueron guardados. Confirma?", "Confirmacion", JOptionPane.YES_NO_OPTION));
        if (rta == JOptionPane.YES_OPTION) {

            textDNI.setText("");
        }
    }

    public JFrame getVentana() {
        return ventana;
    }

    public void setVentana(JFrame ventana) {
        this.ventana = ventana;
    }

    public JPanel getPanelCentral() {
        return panelConsulta;
    }

    public void setPanelCentral(JPanel panelConsulta) {
        this.panelConsulta = panelConsulta;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public JLabel getLabelDNI() {
        return labelDNI;
    }

    public void setLabelDNI(JLabel labelDNI) {
        this.labelDNI = labelDNI;
    }

    public JTextField getTextDNI() {
        return textDNI;
    }

    public void setTextDNI(JTextField textDNI) {
        this.textDNI = textDNI;
    }

    public JButton getButtonBuscar() {
        return buttonBuscar;
    }

    public void setButtonBuscar(JButton buttonBuscar) {
        this.buttonBuscar = buttonBuscar;
    }

    public JButton getButtonCancelar() {
        return buttonCancelar;
    }

    public void setButtonCancelar(JButton buttonCancelar) {
        this.buttonCancelar = buttonCancelar;
    }

}
