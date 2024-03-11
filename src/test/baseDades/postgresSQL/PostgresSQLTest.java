package test.baseDades.postgresSQL;

import main.java.baseDades.postgreSQL.ColumnaInfo;
import main.java.baseDades.postgreSQL.PostgresSQL;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test postgresSQL
 * S'ha de tenir un usuari creat a postgres
 *
 * @author Miquel Fajardo <miquel.fajardo@protonmail.com>
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostgresSQLTest {
    private final static String IP = "localhost";
    private final static String USUARI = "openUser";
    private final static String CONTRASENYA = "openPass";
    private final static String BASE_DADES = "base_proves_open_dades";
    private final static int NUMERO_TAULES = 2;                         // Hi ha una que s'elimina en el test
    private final static String NOM_TAULA_1 = "taula_prova_1";
    private final static String NOM_TAULA_2 = "taula_prova_2";
    private final static String NOM_TAULA_3 = "taula_prova_3";
    private final static String CAMPS_TAULA = "id SERIAL PRIMARY KEY, nom VARCHAR(50), cognom VARCHAR(50)";

    static Connection connexio = null;

    @Test
    @Order(1)
    public void testCrearBaseDades() {
        PostgresSQL.crearBaseDades(IP, USUARI, CONTRASENYA, BASE_DADES);
        assertTrue(PostgresSQL.existeixBaseDades(IP, USUARI, CONTRASENYA, BASE_DADES));
        assertFalse(PostgresSQL.existeixBaseDades(IP, USUARI, CONTRASENYA, BASE_DADES + "error"));
    }

    @Test
    @Order(2)
    public void testConnectar() {
        connexio = PostgresSQL.connectar(IP, BASE_DADES, USUARI, CONTRASENYA);
        assertNotNull(connexio);
    }

    @Test
    @Order(3)
    public void testCrearTaula() throws SQLException {
        PostgresSQL.crearTaula(connexio, NOM_TAULA_1, CAMPS_TAULA);
        PostgresSQL.crearTaula(connexio, NOM_TAULA_2, CAMPS_TAULA);
        PostgresSQL.crearTaula(connexio, NOM_TAULA_3, CAMPS_TAULA);
        String sentenciaSQL = "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = ?)";
        PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
        preparedStatement.setString(1, NOM_TAULA_1);
        ResultSet resultSet = preparedStatement.executeQuery();
        assertTrue(resultSet.next());
    }

    @Test
    @Order(4)
    public void testExisteixTaula() {
        assertTrue(PostgresSQL.existeixTaula(connexio, NOM_TAULA_1));
        assertFalse(PostgresSQL.existeixTaula(connexio, NOM_TAULA_1 + "error"));
    }

    @Test
    @Order(5)
    public void testEliminarTaula() {
        PostgresSQL.eliminarTaula(connexio, NOM_TAULA_1);
        assertFalse(PostgresSQL.existeixTaula(connexio, NOM_TAULA_1));
    }

    @Test
    @Order(6)
    public void testObtenirLlistatTaules() {
        List<String> taules = PostgresSQL.llistarTaules(connexio);
        assertEquals(taules.size(), NUMERO_TAULES);
    }

    @Test
    @Order(7)
    public void testObtenirInfoTaula() {
        List<ColumnaInfo> columnesInfo2 = PostgresSQL.infoTaula(connexio, NOM_TAULA_2);
        List<ColumnaInfo> columnesInfo3 = PostgresSQL.infoTaula(connexio, NOM_TAULA_3);
        assertEquals(columnesInfo2.get(0).getNomTaula(),NOM_TAULA_2) ;
        assertEquals(columnesInfo3.get(0).getNomTaula(),NOM_TAULA_3) ;
   }


    @Test
    @Order(100)
    public void testEliminarBaseDades() {
        PostgresSQL.eliminarBaseDades(IP, USUARI, CONTRASENYA, BASE_DADES);
        assertFalse(PostgresSQL.existeixBaseDades(IP, USUARI, CONTRASENYA, BASE_DADES));
    }
}
