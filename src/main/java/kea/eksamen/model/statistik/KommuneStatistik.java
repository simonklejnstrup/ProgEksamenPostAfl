package kea.eksamen.model.statistik;

public class KommuneStatistik {

    private String navn;
    private int samletIncidens;
    private double gennemsnitligIncidens;

    public KommuneStatistik(String navn) {
        this.navn = navn;
    }

    public void calculateGennemsnitligIncidens(int totalSogne) {
        if (totalSogne > 0) {
            gennemsnitligIncidens = samletIncidens / totalSogne;
        } else {
            gennemsnitligIncidens = 0;
        }
    }

    public void addToSamletIncidens(int incidens) {
        samletIncidens += incidens;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public int getSamletIncidens() {
        return samletIncidens;
    }

    public void setSamletIncidens(int samletIncidens) {
        this.samletIncidens = samletIncidens;
    }

    public double getGennemsnitligIncidens() {
        return gennemsnitligIncidens;
    }

    public void setGennemsnitligIncidens(double gennemsnitligIncidens) {
        this.gennemsnitligIncidens = gennemsnitligIncidens;
    }
}
