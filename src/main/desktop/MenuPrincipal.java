package main.desktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Miquel Fajardo <miquel.fajardo@protonmail.com>
 */
public class MenuPrincipal {
    private JPanel jPanelPrincipal;
    private JMenuBar jMenuBar;


    public MenuPrincipal() {
        jPanelPrincipal = new JPanel(new BorderLayout());
        jMenuBar = new JMenuBar();

        // Menú arxiu
        JMenu menuArxiu = new JMenu("Arxiu");
        // Botó preferències
        JMenuItem menuPreferencies = new JMenuItem("Preferències");
        menuArxiu.add(menuPreferencies);

        // Botó Sortir
        JMenuItem menuSortir = new JMenuItem("Sortir");
        menuSortir.addActionListener(new ActionListener() {
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
        menuArxiu.add(menuSortir);

        // Menú ajuda
        JMenu menuAjuda = new JMenu("Ajuda");
        // Boto Sobre openDades
        JMenuItem menuSobre = new JMenuItem("Sobre openDades");
        menuAjuda.add(menuSobre);


        // Afegir menus
        jMenuBar.add(menuArxiu);
        jMenuBar.add(menuAjuda);


        jPanelPrincipal.add(jMenuBar, BorderLayout.NORTH);

    }
    public JPanel getPanelPrincipal() {
        return jPanelPrincipal;
    }

}
