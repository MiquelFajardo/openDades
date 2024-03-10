package test.baseDades.postgresSQL;

import main.java.baseDades.postgreSQL.PostgresSQL;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test postgresSQL
 * S'ha de tenir un usuari creat a postgres
 *
 * @author Miquel Fajardo <miquel.fajardo@protonmail.com>
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostgresSQLTest {
    private final static String USUARI = "openUser";
    private final static String CONTRASENYA = "openPass";
    private final static String BASE_DADES = "base_proves_open_dades";
    private final static String NOM_TAULA = "taula_prova";

    static Connection connexio = null;

    @Test
    @Order(1)
    public void testCrearBaseDades() {
        PostgresSQL.crearBaseDades(USUARI, CONTRASENYA, BASE_DADES);
        assertTrue(PostgresSQL.existeixBaseDades(USUARI, CONTRASENYA, BASE_DADES));
        assertFalse(PostgresSQL.existeixBaseDades(USUARI, CONTRASENYA, BASE_DADES + "error"));
    }

    @Test
    @Order(2)
    public void testConnectar() {
        connexio = PostgresSQL.connectar(BASE_DADES, USUARI, CONTRASENYA);
        assertNotNull(connexio);
    }

    @Test
    @Order(3)
    public void testCrearTaula() throws SQLException {
        String camps = "id int, nom varchar(50)";
        PostgresSQL.crearTaula(connexio, NOM_TAULA, camps);
        String sentenciaSQL = "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = ?)";
        PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
        preparedStatement.setString(1, NOM_TAULA);
        ResultSet resultSet = preparedStatement.executeQuery();
        assertTrue(resultSet.next());
    }

    @Test
    @Order(4)
    public void testExisteixTaula() {
        assertTrue(PostgresSQL.existeixTaula(connexio, NOM_TAULA));
        assertFalse(PostgresSQL.existeixTaula(connexio, NOM_TAULA + "error"));
    }

    @Test
    @Order(5)
    public void testEliminarTaula() {
        PostgresSQL.eliminarTaula(connexio, NOM_TAULA);
        assertFalse(PostgresSQL.existeixTaula(connexio, NOM_TAULA));
    }

    @Test
    @Order(6)
    public void testEliminarBaseDades() {
        PostgresSQL.eliminarBaseDades(USUARI, CONTRASENYA, BASE_DADES);
        assertFalse(PostgresSQL.existeixBaseDades(USUARI, CONTRASENYA, BASE_DADES));
    }


}
