package main.desktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Miquel Fajardo <miquel.fajardo@protonmail.com>
 */
public class pantallaInici extends JFrame {
    private JPanel jPanelPrincipal;
    private JPanel jPanelSuperior;
    private JPanel jPanelInferior;
    private JLabel titol;
    private JButton buttonPostgres;
    private JButton buttonJSON;

    PantallaPostgres pantallaPostgres;

    public pantallaInici() {
        opcioPostgres();

        setTitle("openDades");
        setPreferredSize(new Dimension(650,600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        add(jPanelPrincipal);
    }

    public void opcioPostgres() {
        buttonPostgres.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                pantallaPostgres = new PantallaPostgres();
                pantallaPostgres.pantallaPostgres();
                dispose();
            }
        });
    }
}
