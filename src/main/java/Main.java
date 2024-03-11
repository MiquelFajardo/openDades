package main.java;

import main.desktop.PantallaInici;

import javax.swing.*;

/**
 * Aplicació per treballar amb dades
 *
 * @author Miquel Fajardo <miquel.fajardo@protonmail.com>
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PantallaInici::new);
    }
}
