package test.utilitats;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import main.java.utilitats.Arxiu;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test de la classe d'utilitats amb arxius
 *
 * @author Miquel Fajardo <miquel.fajardo@protonmail.com>
 */


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ArxiuTest {
    private final static String ARXIU_PROVA = "prova.log";

    @Test
    @Order(1)
    public void testNoExisteixArxiu() {
        assertFalse(Arxiu.existeix(ARXIU_PROVA));
    }

    @Test
    @Order(2)
    public void testCrearArxiu() {
        Arxiu.crearArxiu(ARXIU_PROVA);
        assertTrue(Arxiu.existeix(ARXIU_PROVA));
    }

    @Test
    @Order(3)
    public void testExisteixArxiu() {
        assertTrue(Arxiu.existeix(ARXIU_PROVA));
    }

    @Test
    @Order(4)
    public void testEliminarArxiu() {
        Arxiu.eliminarArxiu(ARXIU_PROVA);
        assertFalse(Arxiu.existeix(ARXIU_PROVA));
    }
}