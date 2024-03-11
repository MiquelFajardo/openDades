package main.java.baseDades.postgreSQL;

import main.java.utilitats.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilitats per connexions a postgresSQL
 *
 * @author Miquel Fajardo <miquel.fajardo@protonmail.com>
 */
public class PostgresSQL {
    private final static String ARXIU_LOG = "jdbc.log";

    /**
     * Crea una base de dades
     */
    public static void crearBaseDades(String ip, String usuari, String contrasenya, String baseDades) {
        String url = "jdbc:postgresql://" + ip + ":5432/postgres";
        try {
            if(!existeixBaseDades(ip, usuari, contrasenya,baseDades)) {
                Connection connexio = DriverManager.getConnection(url, usuari, contrasenya);
                String sentenciaSQL = "CREATE DATABASE " + baseDades;
                Statement statement = connexio.createStatement();
                statement.executeUpdate(sentenciaSQL);
            }
        } catch (SQLException e) {
            Log.escriure(ARXIU_LOG, "No s'ha pogut crear la base de dades\n" + e);
        }
    }

    public static boolean existeixBaseDades(String ip, String usuari, String contrasenya, String baseDades) {
        String url = "jdbc:postgresql://" + ip + ":5432/postgres";
        boolean existeix = false;
        try {
            Connection connexio = DriverManager.getConnection(url, usuari, contrasenya);
            String sentenciaSQL = "SELECT 1 FROM pg_database WHERE datname = ?";
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, baseDades);
            ResultSet resultSet = preparedStatement.executeQuery();
            existeix = resultSet.next();
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Log.escriure(ARXIU_LOG, "No s'ha pogut comprovar si existeix  la base de dades\n" + e);
        }
        return existeix;
    }

    public static void eliminarBaseDades(String ip, String usuari, String contrasenya, String baseDades) {
        String sentenciaSQL = "DROP DATABASE " + baseDades;
        String sql = "SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = '" + baseDades + "' AND pid <> pg_backend_pid()";
        String url = "jdbc:postgresql://" + ip + ":5432/postgres";

        try {
            Connection connexio = DriverManager.getConnection(url, usuari, contrasenya);
            Statement statement = connexio.createStatement();
            statement.execute(sql);
            connexio.close();
            Thread.sleep(1000);
            connexio = DriverManager.getConnection(url, usuari, contrasenya);
            statement = connexio.createStatement();
            statement.executeUpdate(sentenciaSQL);
            connexio.close();
        } catch (SQLException | InterruptedException e) {
            Log.escriure(ARXIU_LOG, "No s'ha pogut eliminar la base de dades\n" + e);
        }
    }

    /**
     * Connectar amb la base de dades
     * @return la connexió
     */
    public static Connection connectar(String ip, String baseDades, String usuari, String contrasenya) {
        Connection connexio = null;
        try {
            String url = "jdbc:postgresql://" + ip + ":5432/" + baseDades;
            connexio = DriverManager.getConnection(url, usuari, contrasenya);
        } catch (SQLException e) {
            Log.escriure(ARXIU_LOG, "No s'ha pogut realitzar la connexió amb la base de dades\n" + e);
        }
        return connexio;
    }


    /**
     * Crea una taula a la base de dades
     */
    public static void crearTaula(Connection connexio, String nom, String camps) {
        String sentenciaSQL = "CREATE TABLE IF NOT EXISTS " + nom  + " (" + camps + ")";
        try {
            Statement statement = connexio.createStatement();
            statement.executeUpdate(sentenciaSQL);
            statement.close();
        } catch (SQLException e) {
            Log.escriure(ARXIU_LOG, "No s'ha pogut crear la taula " + nom + "\n" + e);
        }
    }

    /**
     * Comprova si existeix una taula a la base de dades
     * @return si la taula existeix
     */
    public static boolean existeixTaula(Connection connexio, String nom) {
        try {
            DatabaseMetaData dbm = connexio.getMetaData();
            ResultSet taula = dbm.getTables(null, null, nom, null);
            return taula.next();
        } catch (SQLException e) {
            Log.escriure(ARXIU_LOG, "No s'ha pogut comprovar si existeix la taula " + nom + "\n" + e);
        }
        return false;
    }

    public static List<String> llistarTaules(Connection connexio) {
        List<String> taules = new ArrayList<>();
        String sentenciaSQL = "SELECT tablename FROM pg_catalog.pg_tables WHERE schemaname != 'pg_catalog' AND schemaname != 'information_schema';";
        try {
            Statement statement = connexio.createStatement();
            ResultSet resultSet = statement.executeQuery(sentenciaSQL);
            while(resultSet.next()) {
                taules.add(resultSet.getString("tablename"));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            Log.escriure(ARXIU_LOG, "No s'ha pogut obtenir el llistat de les taules\n" + e);
        }
        return taules;
    }

    public static List<ColumnaInfo> infoTaula(Connection connexio, String nomTaula) {
        List<ColumnaInfo> columnesInfo = new ArrayList<>();
        try {
            DatabaseMetaData databaseMetaData = connexio.getMetaData();
            ResultSet resultSet = databaseMetaData.getColumns(null, null, nomTaula, null);
            while(resultSet.next()) {

                String nomEsquema = resultSet.getString("TABLE_SCHEM");
                String nomColumna = resultSet.getString("COLUMN_NAME");;
                String tipusDades = resultSet.getString("DATA_TYPE");
                int midaColumna = resultSet.getInt("COLUMN_SIZE");
                int digitDecimals = resultSet.getInt("DECIMAL_DIGITS");
                int permetValorNull = resultSet.getInt("NULLABLE");
                String valorPerDefecte = resultSet.getString("COLUMN_DEF");
                ColumnaInfo columnaInfo = new ColumnaInfo(nomEsquema, nomTaula, nomColumna, tipusDades, midaColumna, digitDecimals, permetValorNull, valorPerDefecte);
                columnesInfo.add(columnaInfo);
            }
            resultSet.close();
        } catch (SQLException e) {
            Log.escriure(ARXIU_LOG, "No s'ha pogut obtenir informació de la taula\n" + e);
        }
        return columnesInfo;
    }

    /**
     * Elimina una taula passada per paràmetre
     */
    public static void eliminarTaula(Connection connexio, String nom) {
        String sentenciaSQL = "DROP TABLE IF EXISTS " + nom;
        Statement statement;
        try {
            statement = connexio.createStatement();
            statement.executeUpdate(sentenciaSQL);
            statement.close();
        } catch (SQLException e) {
            Log.escriure(ARXIU_LOG, "No s'ha pogut eliminar la taula " + nom + "\n" + e);
        }
    }
}