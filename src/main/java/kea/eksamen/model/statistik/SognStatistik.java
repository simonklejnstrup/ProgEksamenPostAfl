package kea.eksamen.model.statistik;

import java.time.LocalDate;

public class SognStatistik {

    private int sogneKode;
    private String navn;
    private int incidens;
    private LocalDate nedlukning;
    private String kommune;
    private boolean erNedlukket;

    public SognStatistik(int sogneKode, String navn, int incidens, LocalDate nedlukning,  String kommune) {
        this.sogneKode = sogneKode;
        this.navn = navn;
        this.incidens = incidens;
        this.nedlukning = nedlukning;
        this.kommune = kommune;

        if(nedlukning != null) {
            if (LocalDate.now().isAfter(nedlukning) || LocalDate.now().isEqual(nedlukning)) {
                erNedlukket = true;
            } else {
                erNedlukket = false;
            }
        }


    }

    public boolean isErNedlukket() {
        return erNedlukket;
    }

    public void setErNedlukket(boolean erNedlukket) {
        this.erNedlukket = erNedlukket;
    }

    public int getSogneKode() {
        return sogneKode;
    }

    public void setSogneKode(int sogneKode) {
        this.sogneKode = sogneKode;
    }

    public String getKommune() {
        return kommune;
    }

    public void setKommune(String kommune) {
        this.kommune = kommune;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public int getIncidens() {
        return incidens;
    }

    public void setIncidens(int incidens) {
        this.incidens = incidens;
    }

    public LocalDate getNedlukning() {
        return nedlukning;
    }

    public void setNedlukning(LocalDate nedlukning) {
        this.nedlukning = nedlukning;
    }
}
