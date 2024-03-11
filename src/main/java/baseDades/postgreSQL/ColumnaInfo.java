package main.java.baseDades.postgreSQL;

/**
 * Classe per la informació de les columnes d'una taula
 *
 * @author Miquel Fajardo <miquel.fajardo@protonmail.com>
 */
public class ColumnaInfo {
    private String nomEsquema;
    private String nomTaula;
    private String nomColumna;
    private String tipusDades;
    private int midaColumna;
    private int digitDecimals;
    private int permetValorNull;
    private String valorPerDefecte;

    // Constructor
    public ColumnaInfo() { }

    public ColumnaInfo(String nomEsquema, String nomTaula, String nomColumna, String tipusDades, int midaColumna, int digitDecimals, int permetValorNull,
                       String valorPerDefecte) {
        this.nomEsquema = nomEsquema;
        this.nomTaula = nomTaula;
        this.nomColumna = nomColumna;
        this.tipusDades = tipusDades;
        this.midaColumna = midaColumna;
        this.digitDecimals = digitDecimals;
        this.permetValorNull = permetValorNull;
        this.valorPerDefecte = valorPerDefecte;
    }

    // Mètodes accessors
    public String getNomEsquema() { return nomEsquema; }
    public void setNomEsquema(String nomEsquema) { this.nomEsquema = nomEsquema; }

    public String getNomTaula() { return nomTaula; }
    public void setNomTaula(String nomTaula) { this.nomTaula = nomTaula; }

    public String getNomColumna() { return nomColumna; }
    public void setNomColumna(String nomColumna) { this.nomColumna = nomColumna; }

    public String getTipusDades() { return tipusDades; }
    public void setTipusDades(String tipusDades) { this.tipusDades = tipusDades; }

    public int getMidaColumna() { return midaColumna; }
    public void setMidaColumna(int midaColumna) { this.midaColumna = midaColumna; }

    public int getDigitDecimals() { return digitDecimals; }
    public void setDigitDecimals(int digitDecimals) { this.digitDecimals = digitDecimals; }

    public int getPermetValorNull() { return permetValorNull; }
    public void setPermetValorNull(int permetValorNull) { this.permetValorNull = permetValorNull; }

    public String getValorPerDefecte() { return valorPerDefecte; }
    public void setValorPerDefecte(String valorPerDefecte) { this.valorPerDefecte = valorPerDefecte; }
}
