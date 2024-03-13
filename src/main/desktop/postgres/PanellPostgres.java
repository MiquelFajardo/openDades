package main.desktop.postgres;

import main.desktop.PanellInicial;
import main.java.baseDades.postgreSQL.PostgresSQL;
import main.java.utilitats.Log;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Miquel Fajardo <miquel.fajardo@protonmail.com>
 */
public class PanellPostgres {
    final static String ARXIU_LOG = "panell_postgres.log";

    private JPanel panellDashboardPostgres;
    private JPanel panellConnexio;
    private boolean connectat;
    private JTextField ipField;
    private JTextField bdField;
    private JTextField usuariField;
    private JPasswordField contrasenyaField;
    Connection connexio = null;

    public PanellPostgres() {
        panellDashboardPostgres = new JPanel(new BorderLayout());
        connectat = false;

        panellConnexio = new JPanel(new GridLayout(1,2));
        panellConnexio.setBorder(BorderFactory.createLineBorder(Color.black,1,true));

        // Icona postgreSQL
        Icon iconaPostgres = new ImageIcon("src/main/desktop/resources/icona/postgres_32.png");
        JLabel labelIcona = new JLabel(iconaPostgres);
        panellConnexio.add(labelIcona);

        // IP
        JLabel ipLabel = new JLabel("IP: ");
        ipLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        ipField = new JTextField(15);
        panellConnexio.add(ipLabel);
        panellConnexio.add(ipField);

        // Base de dades
        JLabel bdLabel = new JLabel("Base de dades: ");
        bdLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        bdField = new JTextField();
        panellConnexio.add(bdLabel);
        panellConnexio.add(bdField);

        // Usuari
        JLabel usuariLabel = new JLabel("Usuari: ");
        usuariLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        usuariField = new JTextField();
        panellConnexio.add(usuariLabel);
        panellConnexio.add(usuariField);

        // Contrasenya
        JLabel contrasenyaLabel = new JLabel("Contrasenya: ");
        contrasenyaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        contrasenyaField = new JPasswordField();
        panellConnexio.add(contrasenyaLabel);
        panellConnexio.add(contrasenyaField);

        // Botó connectar
        JButton botoConnectat = new JButton();
        botoConnectat.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        botoConnectat.setIcon(new ImageIcon("src/main/desktop/resources/icona/connectar.png"));
        botoConnectat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!connectat) {     // Intentem connectar
                    char[] charContrasenya = contrasenyaField.getPassword();
                    String contrasenya = new String(charContrasenya);
                    connectar(ipField.getText(), bdField.getText(), usuariField.getText(), contrasenya);
                    if(connexio != null) {
                        campsEditables(false);
                        connectat = !connectat;
                    } else {
                        JOptionPane.showMessageDialog(null, "Les dades introduïdes no són correctes", "Error al connectar amb postgreSQL",JOptionPane.ERROR_MESSAGE);
                    }
                } else if(volsDesconnectar() == JOptionPane.YES_OPTION) {   // desconnecto
                    // tancar connexio i panells de la base de dades
                    desconnectar();
                    connectat = !connectat;
                    campsEditables(true);
                }
                actualitzaBotoConnectat(botoConnectat);
            }
        });
        panellConnexio.add(botoConnectat);

        // Botó inici
        JButton botoInici = new JButton();
        botoInici.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        botoInici.setIcon(new ImageIcon("src/main/desktop/resources/icona/inici.png"));
        botoInici.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(connectat) {
                    if(volsDesconnectar() == JOptionPane.YES_OPTION) {
                        // tancar connexio
                        desconnectar();
                        tornarPanellInicial();;
                    }
                } else {
                    desconnectar();
                    tornarPanellInicial();;
                }
            }
        });
        panellConnexio.add(botoInici);

        // Afegir panell
        panellDashboardPostgres.add(panellConnexio);
    }

    /**
     * Connectar amb la base de dades
     */
    public void connectar(String ip, String baseDades, String usuari, String contrasenya) {
        boolean connectar = true;

        if(!ip.equals("localhost") && !IPValida(ip)) connectar = false;
        if(!baseDadesValida(baseDades)) connectar = false;


        if(connectar) {
            connexio = PostgresSQL.connectar(ip, baseDades, usuari, contrasenya);
        }
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

    public void desconnectar() {
        try {
            if(connexio!=null) connexio.close();
            connexio = null;
        } catch (SQLException e) {
            Log.escriure(ARXIU_LOG, "No s'ha pogut desconnectar de la base de dades\n" + e);
        }
    }



    /**
     * Actualitza botons de connexió
     */
    private void actualitzaBotoConnectat(JButton boto) {
        if (connectat) {        // connectar
            boto.setIcon(new ImageIcon("src/main/desktop/resources/icona/desconnectar.png"));
            boto.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        } else {        // desconnectar
            boto.setIcon(new ImageIcon("src/main/desktop/resources/icona/connectar.png"));
            boto.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        }
    }

    /**
     * Bloquejar o desbloquejar camps
     */
    private void campsEditables(boolean estat) {
            ipField.setEditable(estat);
            bdField.setEditable(estat);
            usuariField.setEditable(estat);
            contrasenyaField.setEditable(estat);
    }


    /**
     * Desconnectar de la base de dades
     */
    public int volsDesconnectar() {
        return JOptionPane.showOptionDialog(
                null,
                "Vols desconnectar de postgreSQL?",
                "Confirmació desconnexió",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Si", "no"},
                "Si"
        );
    }

    public void tornarPanellInicial() {
        panellDashboardPostgres.removeAll();
        panellDashboardPostgres.revalidate();;
        panellDashboardPostgres.repaint();

        PanellInicial panellInicial= new PanellInicial();
        panellDashboardPostgres.add(panellInicial.getPanellInicial());
        panellDashboardPostgres.revalidate();
        panellDashboardPostgres.repaint();

    }

    public JPanel getPanellDashboardPostgres() {
        return panellDashboardPostgres;
    }


}
