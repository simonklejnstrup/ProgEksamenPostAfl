package kea.eksamen.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "kommuner")
public class Kommune {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String navn;


    @JsonIgnore
    @OneToMany (mappedBy = "kommune")
    private List<Sogn> sogne = new ArrayList<>();

    // Contructors

    public Kommune() {
    }

    public Kommune(String navn){
        this.navn = navn;

    }




    //Getters

    public Long getId() {
        return id;
    }

    public String getNavn() {
        return navn;
    }



    public List<Sogn> getSogne() {
        return sogne;
    }

    //Setters


    public void setId(Long id) {
        this.id = id;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }



    public void addSogn(Sogn sogn) {
        sogne.add(sogn);
    }

    // deleters

    public void deleteSogn(Sogn sogn){
        for(Sogn s: sogne){
            if (s.getSogneKode() == sogn.getSogneKode()){
                sogne.remove(s);
                return;
            }
        }
    }

    @Override
    public String toString() {
        return navn;
    }
}
