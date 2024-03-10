package main.java.utilitats;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Logs per l'activitat de l'aplicació
 *
 * @author Miquel Fajardo <miquel.fajardo@protonmail.com>
 */
public class Log {
    private final static String DIRECTORI_LOG = "logs";
    private final static String ARXIU_LOG = "logs.log";

    /**
     * Escriu el log en l'arxiu passat per paràmetre
     */
    public static void escriure(String arxiu, String textLog) {
        Date ara = new Date();
        SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyy HH:mm");
        String dataActual = formatData.format(ara);
        arxiu = DIRECTORI_LOG + FileSystems.getDefault().getSeparator() + arxiu;

        try {
            if(!Arxiu.existeix(DIRECTORI_LOG)) Arxiu.crearDirectori(DIRECTORI_LOG);
            if(!Arxiu.existeix(arxiu)) Arxiu.crearArxiu(arxiu);
            FileWriter fileWriter = new FileWriter(arxiu, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(dataActual + " - " + textLog);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            escriure(ARXIU_LOG, "No s'ha pogut guardar el log en l'arxiu " + arxiu + "\n" + e);
        }
    }


}
