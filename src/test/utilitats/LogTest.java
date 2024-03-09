package test.utilitats;

import main.java.utilitats.Arxiu;
import main.java.utilitats.Log;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.nio.file.FileSystems;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test de la classe utilitats log
 *
 * @author Miquel Fajardo <miquel.fajardo@protonmail.com>
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LogTest {
    private final static String DIRECTORI_LOG = "logs";
    private final static String ARXIU_PROVA =  DIRECTORI_LOG + FileSystems.getDefault().getSeparator() +"provalog.log";

    @Test
    @Order(1)
    public void testCrearLog() {
        Log.escriure(ARXIU_PROVA, "prova log");
        assertTrue(Arxiu.existeix(ARXIU_PROVA));
        Arxiu.eliminarArxiu(ARXIU_PROVA);
        assertFalse(Arxiu.existeix(ARXIU_PROVA));
    }
}
