package main.desktop;

import main.desktop.postgres.DashboardPostgres;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Miquel Fajardo <miquel.fajardo@protonmail.com>
 */
public class PantallaInici  {
    private JFrame jFrameInici;
    private JPanel jPanelInici;
    private JButton jButtonPostgres;
    private MenuPrincipal menuPrincipal;

    public  PantallaInici() {
        jFrameInici = new JFrame("openDades");
        // Menú principal
        menuPrincipal = new MenuPrincipal();
        jPanelInici = new JPanel(new BorderLayout());
        jPanelInici.add(menuPrincipal.getPanelPrincipal(), BorderLayout.NORTH);

        // Panell botons
        JPanel jPanelBoto = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jPanelBoto.setPreferredSize(new Dimension(700, 50)); // Fixem la grandària del JPanel


        // Botó postgres
		jButtonPostgres = new JButton(new ImageIcon("src/main/desktop/resources/icona/postgres.png"));
        jButtonPostgres.setPreferredSize(new Dimension(144,144));
        jButtonPostgres.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(jPanelInici);
                DashboardPostgres dashboardPostgres = new DashboardPostgres();
                dashboardPostgres.mostrar();
                frame.dispose();
            }
        });

		jPanelBoto.add(jButtonPostgres);
        jPanelInici.add(jPanelBoto, BorderLayout.WEST);



        jFrameInici.add(jPanelInici);
		jFrameInici.setExtendedState(JFrame.MAXIMIZED_BOTH);
		jFrameInici.setMinimumSize(new Dimension(600, 400));
		jFrameInici.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrameInici.setVisible(true);
	 }

}
