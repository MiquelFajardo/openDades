package main.desktop;

import main.desktop.postgres.PanellPostgres;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Miquel Fajardo <miquel.fajardo@protonmail.com>
 */
public class PanellInicial {
    private JPanel panellInicial;

    private JButton jButtonPostgres;
    private JButton jButtonJson;

    public PanellInicial() {
        panellInicial = new JPanel();
        Dimension midaPantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int amplada = (int) (midaPantalla.width*0.8);
        int alcada = 90;

        // Panell botons
        JPanel jPanelBoto = new JPanel(new GridLayout(2,1, 0,10));
        jPanelBoto.setPreferredSize(new Dimension(amplada, alcada * 2));

            // Botó postgres
		jButtonPostgres = new JButton("PostgreSQL",new ImageIcon("src/main/desktop/resources/icona/postgres.png")) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int w = getWidth();
                int h = getHeight();
                Graphics2D g2d = (Graphics2D) g.create();

                // Obtenim les mides del text
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(getText());

                // Obtenim les mides de la imatge
                Icon icon = getIcon();
                int iconWidth = (icon != null) ? icon.getIconWidth() : 0;

                // Sumem les mides del text i de la imatge per obtenir el punt d'inici del degradat
                int startX = textWidth + iconWidth;
                int startY = 0;

                GradientPaint gradient = new GradientPaint(startX, startY, getBackground(), w, h, Color.BLUE);
                g2d.setPaint(gradient);
                g2d.fillRect(startX, startY, w - startX, h);
                g2d.dispose();
            }
        };

        jButtonPostgres.setAlignmentX(Component.LEFT_ALIGNMENT);
        jButtonPostgres.setPreferredSize(new Dimension(amplada, alcada));
        jButtonPostgres.setBackground(Color.LIGHT_GRAY);
        jButtonPostgres.setHorizontalAlignment(SwingConstants.LEFT);
        jButtonPostgres.setVerticalAlignment(SwingConstants.CENTER);
        jButtonPostgres.setFont(jButtonPostgres.getFont().deriveFont(46f));
        jButtonPostgres.setBorder(null);

        jButtonPostgres.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panellInicial.removeAll();
                panellInicial.revalidate();
                panellInicial.repaint();

                PanellPostgres panellPostgres = new PanellPostgres();
                JPanel panellDashboardPostgres = panellPostgres.getPanellDashboardPostgres();
                panellInicial.add(panellDashboardPostgres);
                panellInicial.revalidate();
                panellInicial.repaint();
            }
        });

        // Botó JSon
        jButtonJson = new JButton("JSON",new ImageIcon("src/main/desktop/resources/icona/json.png")) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int w = getWidth();
                int h = getHeight();
                Graphics2D g2d = (Graphics2D) g.create();

                // Obtenim les mides del text
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getHeight();

                // Obtenim les mides de la imatge
                Icon icon = getIcon();
                int iconWidth = (icon != null) ? icon.getIconWidth() : 0;

                // Sumem les mides del text i de la imatge per obtenir el punt d'inici del degradat
                int startX = textWidth + iconWidth;
                int startY = 0;

                GradientPaint gradient = new GradientPaint(startX, startY, getBackground(), w, h, Color.DARK_GRAY);
                g2d.setPaint(gradient);
                g2d.fillRect(startX, startY, w - startX, h);
                g2d.dispose();
            }
        };

        jButtonJson.setPreferredSize(new Dimension(amplada, alcada));
        jButtonJson.setBackground(Color.LIGHT_GRAY);
        jButtonJson.setHorizontalAlignment(SwingConstants.LEFT);
        jButtonJson.setVerticalAlignment(SwingConstants.CENTER);
        jButtonJson.setFont(jButtonJson.getFont().deriveFont(46f));
        jButtonJson.setBorder(null);

        jButtonJson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });


        // Afegir panell al Frame
        jPanelBoto.add(jButtonPostgres);
        jPanelBoto.add(jButtonJson);
        panellInicial.add(jPanelBoto);
    }

    public JPanel getPanellInicial() {
        return panellInicial;
    }
}
