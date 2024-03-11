package main.desktop.postgres;

import main.desktop.MenuPrincipal;

import javax.swing.*;
import java.awt.*;

/**
 * @author Miquel Fajardo <miquel.fajardo@protonmail.com>
 */
public class DashboardPostgres {
    private JFrame jFramePostgres;
    private JPanel jPanelPostgres;
    private MenuPrincipal menuPrincipal;

    public DashboardPostgres() {
        jFramePostgres = new JFrame("openDades - Postgres");
        // Menú principal
        menuPrincipal = new MenuPrincipal();
        jPanelPostgres = new JPanel(new BorderLayout());
        jPanelPostgres.add(menuPrincipal.getPanelPrincipal(), BorderLayout.NORTH);
    }

    public void mostrar() {
        jFramePostgres.add(jPanelPostgres);
        jFramePostgres.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jFramePostgres.setMinimumSize(new Dimension(600, 400));
        jFramePostgres.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFramePostgres.setVisible(true);
    }
}
