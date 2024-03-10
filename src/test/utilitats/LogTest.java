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
 * Test log
 *
 * @author Miquel Fajardo <miquel.fajardo@protonmail.com>
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LogTest {
    private final static String DIRECTORI_LOG = "logs";
    private final static String ARXIU_PROVA =  "provalog.log";

    @Test
    @Order(1)
    public void testCrearLog() {
        Log.escriure(ARXIU_PROVA, "prova log");
        assertTrue(Arxiu.existeix(DIRECTORI_LOG + FileSystems.getDefault().getSeparator() + ARXIU_PROVA));
        Arxiu.eliminarArxiu(DIRECTORI_LOG + FileSystems.getDefault().getSeparator()  + ARXIU_PROVA);
        assertFalse(Arxiu.existeix(DIRECTORI_LOG + FileSystems.getDefault().getSeparator() + ARXIU_PROVA));
    }
}
