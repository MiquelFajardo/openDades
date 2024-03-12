package main.desktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Miquel Fajardo <miquel.fajardo@protonmail.com>
 */
public class FinestraPrincipal extends JFrame {
    private JPanel panellPrincipal;
    private JPanel panellMenu;
    private JPanel panellCentral;
    private JMenuBar menuPrincipal;

    public FinestraPrincipal() {
        menuPrincipal = new JMenuBar();

        // Finestra principal
        Dimension midaPantalla = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle("openDades");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(midaPantalla.width, midaPantalla.height);
        setVisible(true);

        // Panells
        panellPrincipal = new JPanel(new BorderLayout());
        panellMenu = new JPanel();
        panellCentral = new JPanel();

        // Menú principal
        // Arxiu
        JMenu menuArxiu = new JMenu("Arxiu");
        JMenuItem submenuPreferencies = new JMenuItem("Preferències");
        menuArxiu.add(submenuPreferencies);

        JMenuItem submenuSortir = new JMenuItem("Sortir");
        submenuSortir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int option = JOptionPane.showOptionDialog(
                        null,
                        "Vols sortir de openDades?",
                        "Confirmació de sortida",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Si", "no"},
                        "Si"
                );
                if(option == JOptionPane.YES_NO_OPTION) {
                    System.exit(0);
                }
            }
        });
        menuArxiu.add(submenuSortir);

        // Ajuda
        JMenu menuAjuda = new JMenu("Ajuda");
        JMenuItem submenuSobre = new JMenuItem("Sobre openDades");
        menuAjuda.add(submenuSobre);

        // Afegir menus
        menuPrincipal.add(menuArxiu);
        menuPrincipal.add(menuAjuda);

        // Afegir panells i menú
        panellPrincipal.add(panellMenu, BorderLayout.NORTH);
        panellPrincipal.add(panellCentral, BorderLayout.CENTER);

        // Afegir panell inicial
        PanellInicial panellInicial = new PanellInicial();
        panellCentral.add(panellInicial.getPanellInicial(), BorderLayout.CENTER);

        setJMenuBar(menuPrincipal);
        add(panellPrincipal);
        setVisible(true);
    }
}
