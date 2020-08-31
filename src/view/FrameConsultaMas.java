package view;

import controller.CtrlFrameConsultaMas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrameConsultaMas {

    private CtrlFrameConsultaMas ctrlFrameConsultaMas;

    private JFrame ventana = new JFrame("Consulta masiva");
    private ImageIcon icon = new ImageIcon("src/resources/blood-donation-1.png");
    private JPanel panelConsulta = new JPanel(new FlowLayout());
    private JPanel panelTotales = new JPanel(new FlowLayout());
    private JPanel panelCentral = new JPanel();

    private JLabel labelProvincia = new JLabel("Provincia");
    private JTextField textProvincia = new JTextField(20);
    private JLabel labelTipoSangre = new JLabel("Tipo de Sangre");
    private JTextField textTipoSangre = new JTextField(20);
    private JButton buttonBuscar = new JButton("Buscar");
    private String[] columnas = {"DNI", "Nombre", "Apellido", "Localidad", "Fecha de Nacimiento", "Sexo"};
    private DefaultTableModel tableModel = new DefaultTableModel();
    private JTable tabla = new JTable(tableModel);
    private JScrollPane scrollPane = new JScrollPane(tabla);
    private JLabel labelResultado = new JLabel("coincidencias en");
    private JTextField textResultados = new JTextField(5);
    private JLabel labelTotales = new JLabel("personas");
    private JTextField textTotales = new JTextField(5);

    public FrameConsultaMas(CtrlFrameConsultaMas ctrlFrameConsultaMas) {
        this.ctrlFrameConsultaMas = ctrlFrameConsultaMas;
        ctrlFrameConsultaMas.setVista(this);

        ventana.setIconImage(icon.getImage());
        ventana.setSize(650, 600);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

        buttonBuscar.addActionListener(ctrlFrameConsultaMas);

        textTipoSangre.setToolTipText("Grupo + RH + Factor. Ej: ABRHPOSITIVO");

        panelConsulta.add(labelProvincia);
        panelConsulta.add(textProvincia);
        panelConsulta.add(labelTipoSangre);
        panelConsulta.add(textTipoSangre);

        panelTotales.add(textResultados);
        panelTotales.add(labelResultado);
        panelTotales.add(textTotales);
        panelTotales.add(labelTotales);
        textResultados.setEditable(false);
        textTotales.setEditable(false);

        for (int i = 0; i < columnas.length; i++) {
            tableModel.addColumn(columnas[i]);
        }

        panelCentral.add(scrollPane, BorderLayout.CENTER);
        panelCentral.add(panelTotales, BorderLayout.SOUTH);

        ventana.add(panelConsulta, BorderLayout.NORTH);
        ventana.add(buttonBuscar, BorderLayout.SOUTH);
        ventana.add(panelCentral, BorderLayout.CENTER);

    }

    public JTextField getTextProvincia() {
        return textProvincia;
    }

    public void setTextProvincia(JTextField textProvincia) {
        this.textProvincia = textProvincia;
    }

    public JTextField getTextTipoSangre() {
        return textTipoSangre;
    }

    public void setTextTipoSangre(JTextField textTipoSangre) {
        this.textTipoSangre = textTipoSangre;
    }

    public JButton getButtonBuscar() {
        return buttonBuscar;
    }

    public void setButtonBuscar(JButton buttonBuscar) {
        this.buttonBuscar = buttonBuscar;
    }

    public String[] getColumnas() {
        return columnas;
    }

    public void setColumnas(String[] columnas) {
        this.columnas = columnas;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public JTable getTabla() {
        return tabla;
    }

    public void setTabla(JTable tabla) {
        this.tabla = tabla;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public JTextField getTextResultados() {
        return textResultados;
    }

    public void setTextResultados(JTextField textResultados) {
        this.textResultados = textResultados;
    }

    public JTextField getTextTotales() {
        return textTotales;
    }

    public void setTextTotales(JTextField textTotales) {
        this.textTotales = textTotales;
    }
}
