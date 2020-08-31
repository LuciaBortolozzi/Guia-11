package view;

import controller.CtrlFrameEstadisticas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrameEstadisticas {
    private CtrlFrameEstadisticas ctrlFrameEstadisticas;

    private JFrame ventana = new JFrame("Estadisticas");
    private JPanel panelIzq = new JPanel();
    private JPanel panelDer = new JPanel();
    private ImageIcon icon = new ImageIcon("src/resources/blood-donation-1.png");
    private JPanel panelEstadistica0 = new JPanel(new FlowLayout());
    private JPanel panelEstadistica1 = new JPanel(new FlowLayout());
    private JPanel panelEstadistica2 = new JPanel(new FlowLayout());
    private JPanel panelEstadistica3 = new JPanel(new FlowLayout());
    private JPanel panelEstadistica4 = new JPanel(new FlowLayout());
    private JLabel labelProvincia = new JLabel("En CABA:");
    private String[] columnas = {"DNI", "Nombre", "Apellido", "Tipo Sangre"};
    private DefaultTableModel tableModel = new DefaultTableModel();
    private JTable tabla = new JTable(tableModel);
    private JScrollPane scrollPane = new JScrollPane(tabla);
    private String[] columnas2 = {"DNI", "Nombre", "Apellido", "Fecha Nacimiento"};
    private DefaultTableModel tableModel2 = new DefaultTableModel();
    private JTable tabla2 = new JTable(tableModel2);
    private JScrollPane scrollPane2 = new JScrollPane(tabla2);
    private JLabel labelCantidadTotExt = new JLabel("Cantidad total extraida (en ml)");
    private JTextField textCantidadTotExt = new JTextField(10);
    private JLabel labelPeso = new JLabel("Listado de personas que no poseen el siguiente peso (en kg): ");
    private JTextField textPeso = new JTextField(5);

    public FrameEstadisticas(CtrlFrameEstadisticas ctrlFrameEstadisticas) {
        this.ctrlFrameEstadisticas = ctrlFrameEstadisticas;
        ctrlFrameEstadisticas.setVista(this);

        ventana.setIconImage(icon.getImage());
        ventana.setSize(625, 1000);
        ventana.setLayout(new FlowLayout());
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
        ventana.setVisible(true);

        panelEstadistica0.add(labelProvincia);
        for (int i = 0; i < columnas.length; i++) {
            tableModel.addColumn(columnas[i]);
        }
        panelEstadistica1.add(scrollPane);

        panelEstadistica2.add(labelCantidadTotExt);
        panelEstadistica2.add(labelCantidadTotExt);
        panelEstadistica2.add(textCantidadTotExt);
        textCantidadTotExt.setEnabled(false);

        panelEstadistica3.add(labelPeso);
        panelEstadistica3.add(textPeso);
        textPeso.setEnabled(false);

        for (int i = 0; i < columnas2.length; i++) {
            tableModel2.addColumn(columnas2[i]);
        }
        panelEstadistica4.add(scrollPane2);

        panelIzq.setLayout(new BoxLayout(panelIzq, BoxLayout.Y_AXIS));
        panelIzq.add(panelEstadistica0);
        panelIzq.add(panelEstadistica1);

        panelDer.setLayout(new BoxLayout(panelDer, BoxLayout.Y_AXIS));
        panelDer.add(panelEstadistica3);
        panelDer.add(panelEstadistica4);
        panelDer.add(panelEstadistica2);
        ventana.add(panelIzq);
        ventana.add(panelDer);

        ctrlFrameEstadisticas.getEstadisticas();

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

    public JTextField getTextCantidadTotExt() {
        return textCantidadTotExt;
    }

    public void setTextCantidadTotExt(JTextField textCantidadTotExt) {
        this.textCantidadTotExt = textCantidadTotExt;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public DefaultTableModel getTableModel2() {
        return tableModel2;
    }

    public void setTableModel2(DefaultTableModel tableModel2) {
        this.tableModel2 = tableModel2;
    }

    public JTable getTabla2() {
        return tabla2;
    }

    public void setTabla2(JTable tabla2) {
        this.tabla2 = tabla2;
    }

    public JTextField getTextPeso() {
        return textPeso;
    }

    public void setTextPeso(JTextField textPeso) {
        this.textPeso = textPeso;
    }
}
