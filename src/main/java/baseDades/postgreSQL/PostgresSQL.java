package main.java.baseDades.postgreSQL;

import main.java.utilitats.Log;

import java.sql.*;

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
    public static void crearBaseDades(String usuari, String contrasenya, String baseDades) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        try {
            if(!existeixBaseDades(usuari, contrasenya,baseDades)) {
                Connection connexio = DriverManager.getConnection(url, usuari, contrasenya);
                String sentenciaSQL = "CREATE DATABASE " + baseDades;
                Statement statement = connexio.createStatement();
                statement.executeUpdate(sentenciaSQL);
            }
        } catch (SQLException e) {
            Log.escriure(ARXIU_LOG, "No s'ha pogut crear la base de dades\n" + e);
        }
    }

    public static boolean existeixBaseDades(String usuari, String contrasenya, String baseDades) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        boolean existeix = false;
        try {
            Connection connexio = DriverManager.getConnection(url, usuari, contrasenya);
            String sentenciaSQL = "SELECT 1 FROM pg_database WHERE datname = ?";
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, baseDades);
            ResultSet resultSet = preparedStatement.executeQuery();
            existeix = resultSet.next();
            connexio.close();
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Log.escriure(ARXIU_LOG, "No s'ha pogut comprovar si existeix  la base de dades\n" + e);
        }
        return existeix;
    }

    public static void eliminarBaseDades(String usuari, String contrasenya, String baseDades) {
        String sentenciaSQL = "DROP DATABASE " + baseDades;
        String sql = "SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = '" + baseDades + "' AND pid <> pg_backend_pid()";
        String url = "jdbc:postgresql://localhost:5432/postgres";

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
    public static Connection connectar(String baseDades, String usuari, String contrasenya) {
        Connection connexio = null;
        try {
            String url = "jdbc:postgresql://localhost:5432/" + baseDades;
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