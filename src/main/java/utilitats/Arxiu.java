package main.java.utilitats;

import java.io.File;
import java.io.IOException;

/**
 * Treballs amb arxius (crear, eliminar, si existeix...)
 *
 * @author Miquel Fajardo <miquel.fajardo@protonmail.com>
 */
public class Arxiu {
    private final static String ARXIU_LOG = "arxiu.log";

    /**
     * Comprova si existeix un arxiu o directori
     *
     * @return si existeix l'arxiu o directori
     */
    public static boolean existeix(String arxiu) {
        File file = new File(arxiu);
        return file.exists();
    }

    /**
     * Crea un arxiu passat per paràmetre
     */
    public static void crearArxiu(String arxiu) {
        File file = new File(arxiu);
        try {
            if(!file.createNewFile())
                Log.escriure(ARXIU_LOG, "No s'ha pogut crear l'arxiu " + arxiu);
        } catch (IOException e) {
            Log.escriure(ARXIU_LOG, "No s'ha pogut crear l'arxiu " + arxiu + "\n" + e);
        }
    }

    /**
     * Crea un directori passat per paràmetre
     */
    public static void crearDirectori(String directori) {
        File file = new File(directori);
        if(!file.mkdir())  Log.escriure(ARXIU_LOG, "No s'ha pogut crear el directori " + directori);
    }

    /**
     * Elimina un arxiu passat per paràmetre
     */
    public static void eliminarArxiu(String arxiu) {
        File file = new File(arxiu);
        if(!file.delete())  Log.escriure(ARXIU_LOG, "No s'ha pogut eliminar l'arxiu " + arxiu);
    }
}
