package kea.eksamen.model.database;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "sogne")
public class Sogn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sognekode")
    private int sogneKode;

    private String navn;

    @ManyToOne
    @JoinColumn(name="kommune_id")
    private Kommune kommune;

    private LocalDate nedlukning;

    @Column(name = "islukketned")
    private boolean isLukketNed;
    private int incidens;

    //Constructors

    public Sogn() {
    }

    public Sogn(int sogneKode, String navn, Kommune kommune, LocalDate nedlukning, int incidens) {
        this.sogneKode = sogneKode;
        this.navn = navn;
        this.kommune = kommune;
        this.nedlukning = nedlukning;
        this.incidens = incidens;
        isLukketNed = false;
    }

    public Sogn(int sogneKode, String navn, Kommune kommune, int incidens) {
        this.sogneKode = sogneKode;
        this.navn = navn;
        this.kommune = kommune;
        this.incidens = incidens;
        isLukketNed = false;

    }

    public Sogn(int sogneKode, String navn, Kommune kommune) {
        this.sogneKode = sogneKode;
        this.navn = navn;
        this.kommune = kommune;
        isLukketNed = false;

    }

    public Sogn(int sogneKode, String navn) {
        this.sogneKode = sogneKode;
        this.navn = navn;
        isLukketNed = false;

    }


    //Getters

    public Long getId() {
        return id;
    }

    public int getSogneKode() {
        return sogneKode;
    }

    public String getNavn() {
        return navn;
    }

    public Kommune getKommune() {
        return kommune;
    }

    public LocalDate getNedlukning() {
        return nedlukning;
    }

    public int getIncidens() {
        return incidens;
    }

    public boolean isLukketNed() {
        return isLukketNed;
    }

    //Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setSogneKode(int sogneKode) {
        this.sogneKode = sogneKode;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public void setKommune(Kommune kommune) {
        this.kommune = kommune;
    }

    public void setNedlukning(LocalDate nedlukning) {
        this.nedlukning = nedlukning;
    }

    public void setIncidens(int incidens) {
        this.incidens = incidens;
    }

    public void setLukketNed(boolean lukketNed) {
        isLukketNed = lukketNed;
    }

    @Override
    public String toString() {
        return navn;
    }
}
