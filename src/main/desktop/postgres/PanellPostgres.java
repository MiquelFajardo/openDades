package main.desktop.postgres;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Miquel Fajardo <miquel.fajardo@protonmail.com>
 */
public class PanellPostgres {
    private JPanel panellDashboardPostgres;
    private JPanel panellConnexio;
    private boolean connectat;

    public PanellPostgres() {
        panellDashboardPostgres = new JPanel(new BorderLayout());
        connectat = false;

        panellConnexio = new JPanel(new GridLayout(1,2));
        panellConnexio.setBorder(BorderFactory.createLineBorder(Color.black,1,true));
        // IP
        JLabel ipLabel = new JLabel("IP: ");
        JTextField ipField = new JTextField();
        panellConnexio.add(ipLabel);
        panellConnexio.add(ipField);

        // Base de dades
        JLabel bdLabel = new JLabel("Base de dades: ");
        JTextField bdField = new JTextField();
        panellConnexio.add(bdLabel);
        panellConnexio.add(bdField);

        // Usuari
        JLabel usuariLabel = new JLabel("Usuari: ");
        JTextField usuariField = new JTextField();
        panellConnexio.add(usuariLabel);
        panellConnexio.add(usuariField);

        // Contrasenya
        JLabel contrasenyaLabel = new JLabel("Contrasenya: ");
        JPasswordField contrasenyaField = new JPasswordField();
        panellConnexio.add(contrasenyaLabel);
        panellConnexio.add(contrasenyaField);

        // Botó connectar
        JButton botoConnectat = new JButton();
        botoConnectat.setBorder(null);
        botoConnectat.setIcon(new ImageIcon("src/main/desktop/resources/icona/connectar.png"));
        botoConnectat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                connectat = !connectat;
                actualitzaBotoConnectat(botoConnectat);
            }
        });

        panellConnexio.add(botoConnectat);

        // Icona postgreSQL
        Icon iconaPostgres = new ImageIcon("src/main/desktop/resources/icona/postgres_32.png");
        JLabel labelIcona = new JLabel(iconaPostgres);
        panellConnexio.add(labelIcona);

        panellDashboardPostgres.add(panellConnexio);

    }
    private void actualitzaBotoConnectat(JButton boto) {
        if (connectat) {        // connectar
            boto.setIcon(new ImageIcon("src/main/desktop/resources/icona/desconnectar.png"));

        } else {        // desconnectar
            int option = JOptionPane.showOptionDialog(
                    null,
                    "Vols desconnectar de postgreSQL?",
                    "Confirmació desconnexió",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[]{"Si", "no"},
                    "Si"
            );

            if(option == JOptionPane.YES_NO_OPTION) {   // desconnecto
                boto.setIcon(new ImageIcon("src/main/desktop/resources/icona/connectar.png"));
                // tancar connexio i panells de la base de dades
            } else {        // no desconnecto
                connectat = !connectat;

            }

        }
    }


    public JPanel getPanellDashboardPostgres() {
        return panellDashboardPostgres;
    }
}
