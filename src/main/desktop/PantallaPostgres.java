package main.desktop;

import main.java.baseDades.postgreSQL.PostgresSQL;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Miquel Fajardo <miquel.fajardo@protonmail.com>
 */
public class PantallaPostgres extends JFrame {
    private JPanel jPanelPostgres;
    private JPanel jPanelConnexio;
    private JLabel labelIP;
    private JTextField textFieldIP;
    private JLabel labelBD;
    private JTextField textFieldBD;
    private JLabel labelUsuari;
    private JTextField textFieldUsuari;
    private JLabel labelContrasenya;
    private JPasswordField passwordFieldContrasenya;
    private JButton buttonConnectar;
    private JButton buttonInici;

    private boolean connectat;
    private Connection connexio = null;

    public void pantallaPostgres() {
        connectat = false;
        carregarFuncions();

        Dimension midaPantalla = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle("openDades - postgreSQL");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(midaPantalla);
        pack();
        setVisible(true);
        add(jPanelPostgres);
    }

    public void carregarFuncions() {
        pulsarBotoConnectar();
        pulsarBotoInici();
    }

    public void pulsarBotoConnectar() {
        buttonConnectar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(connectat) {
                    if(confirmarDesconnexio() == JOptionPane.YES_OPTION) {
                        connectat = false;
                        campsEditable(true);
                        connexio = null;
                    }
                } else {
                    connectar();
                    if(connexio != null) {
                        connectat = true;
                        campsEditable(false);
                    } else {
                        errorConnexio();
                    }
                }
                actualitzaBotoConnectat();
            }
        });

    }

    public void connectar() {
        if(dadesValides()) {
            String contrasenya = new String(passwordFieldContrasenya.getPassword());
            connexio = PostgresSQL.connectar(textFieldIP.getText(), textFieldBD.getText(), textFieldUsuari.getText(), contrasenya);
        }
    }

    public boolean dadesValides() {
        return (textFieldIP.getText().equals("localhost") || IPValida(textFieldIP.getText()) && (baseDadesValida(textFieldBD.getText())));
    }
    public boolean IPValida(String ip) {
        String patroIP =
                "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        Pattern patro = Pattern.compile(patroIP);
        Matcher matcher = patro.matcher(ip);
        return matcher.matches();
    }

    public boolean baseDadesValida(String baseDades) {
        String patro = "^[a-zA-Z0-9 _-]+$";
        Pattern pattern = Pattern.compile(patro);
        Matcher matcher = pattern.matcher(baseDades);
        return matcher.matches();
    }

    /**
     * Actualitza botons de connexió
     */
    private void actualitzaBotoConnectat() {
        if (connectat) {        // connectar
            buttonConnectar.setIcon(new ImageIcon("src/main/desktop/resources/icona/desconnectar.png"));
            buttonConnectar.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        } else {        // desconnectar
            buttonConnectar.setIcon(new ImageIcon("src/main/desktop/resources/icona/connectar.png"));
            buttonConnectar.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        }
    }


    public void pulsarBotoInici() {
        buttonInici.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(connectat) {
                    if(confirmarDesconnexio() == JOptionPane.YES_OPTION) tornarPantallaInicial();
                } else {
                    tornarPantallaInicial();
                }
            }
        });
    }

    public int confirmarDesconnexio() {
        return JOptionPane.showConfirmDialog(null, "Voleu desconnectar de la base de dades?", "Desconnectar postgreSQL", JOptionPane.YES_NO_OPTION);
    }

    public void errorConnexio() {
        JOptionPane.showMessageDialog(null,"Dades de connexió incorrectes", "Error de connexió", JOptionPane.ERROR_MESSAGE);
    }

    public void campsEditable(boolean estat) {
        textFieldIP.setEditable(estat);
        textFieldBD.setEditable(estat);
        textFieldUsuari.setEditable(estat);
        passwordFieldContrasenya.setEditable(estat);
    }

    public void tornarPantallaInicial() {
        new pantallaInici();
        dispose();
    }
}
