package kea.eksamen.service;

import kea.eksamen.model.database.Kommune;
import kea.eksamen.model.database.Sogn;
import kea.eksamen.model.statistik.KommuneStatistik;
import kea.eksamen.model.statistik.SognStatistik;
import kea.eksamen.repository.KommuneRepository;
import kea.eksamen.repository.SognRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Dataservice {

    private KommuneRepository kommuneRepository;
    private SognRepository sognRepository;


    public Dataservice(KommuneRepository kommuneRepository, SognRepository sognRepository) {
        this.kommuneRepository = kommuneRepository;
        this.sognRepository = sognRepository;
    }

    public ArrayList<SognStatistik> getAllSogne() {
        ArrayList<SognStatistik> sogne = new ArrayList<>();
        List<Sogn> sognList = sognRepository.findAll();

        for (Sogn sogn : sognList) {
            sogne.add(new SognStatistik(
                    sogn.getSogneKode(),
                    sogn.getNavn(),
                    sogn.getIncidens(),
                    sogn.getNedlukning(),
                    sogn.getKommune().getNavn()
            ));
        }
        return sogne;
    }

    public ArrayList<KommuneStatistik> getAllKommuner() {
        ArrayList<KommuneStatistik> kommuner = new ArrayList<>();
        List<Kommune> kommuneList = kommuneRepository.findAll();

        for (Kommune kommune : kommuneList) {
            KommuneStatistik kr = new KommuneStatistik(kommune.getNavn());

            for (Sogn sogn: kommune.getSogne()) {
                kr.addToSamletIncidens(sogn.getIncidens());
            }

            kr.calculateGennemsnitligIncidens(kommune.getSogne().size());
            kommuner.add(kr);

        }

        return kommuner;
    }

    public SognStatistik createSogn(int sogneKode, String kommuneNavn, String sogneNavn, int incidens) {

        Optional<Sogn> sognOptional = sognRepository.findSognByNavn(sogneNavn);

        if (sognOptional.isEmpty()) {

            Kommune kommune = getKommune(kommuneRepository.findKommuneByNavn(kommuneNavn), kommuneNavn);
            Sogn sogn = new Sogn(sogneKode, sogneNavn, kommune,  incidens);

            kommune.addSogn(sogn);

            kommuneRepository.save(kommune);
            sognRepository.save(sogn);

            return new SognStatistik(sogn.getSogneKode(), sogn.getNavn(), sogn.getIncidens(), sogn.getNedlukning(), kommune.getNavn());
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Can not create sogn that already exists");
        }
    }

    public SognStatistik updateSogn(int sogneKode, Kommune kommune, String sogneNavn, int incidens) {
        Optional<Sogn> sognOptional = sognRepository.findSognByNavn(sogneNavn);

        if (sognOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sogn does not exist.");
        } else {
            if (kommune != null) {
                for (Kommune k : kommuneRepository.findAll()) {
                    if (k.getNavn().toLowerCase().equals(kommune.getNavn().toLowerCase())) {
                        Kommune kommune1 = kommuneRepository.findById(k.getId()).get();
                        kommuneRepository.save(kommune1);
                        sognOptional.get().setKommune(kommune1);
                    }
                }
            }

            sognOptional.get().setIncidens(incidens);
            sognOptional.get().setSogneKode(sogneKode);
            //sognOptional.get().setKommune(kommune);

            sognRepository.save(sognOptional.get());


            if (kommune != null) {
                return new SognStatistik(
                        sognOptional.get().getSogneKode(),
                        sognOptional.get().getNavn(),
                        sognOptional.get().getIncidens(),
                        sognOptional.get().getNedlukning(),
                        sognOptional.get().getKommune().getNavn()
                );
            } else {
                return new SognStatistik(
                        sognOptional.get().getSogneKode(),
                        sognOptional.get().getNavn(),
                        sognOptional.get().getIncidens(),
                        sognOptional.get().getNedlukning(),
                        null
                );

            }
        }
    }



    public void deleteSogn(String sogneNavn) {
        Optional<Sogn> sognOptional = sognRepository.findSognByNavn(sogneNavn);

        if (sognOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sogn does not exists, could not delete.");
        } else {
            sognRepository.deleteById(sognOptional.get().getId());
        }
    }

    public void closeSogn(String sogneNavn) {
        Optional<Sogn> sognOptional = sognRepository.findSognByNavn(sogneNavn);

        if (sognOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sogn does not exists, could not delete.");
        } else {
            sognOptional.get().setNedlukning(LocalDate.now());
            sognRepository.save(sognOptional.get());
        }
    }



    public Kommune getKommune(Optional<Kommune> optionalKommune, String kommuneNavn) {
        if (optionalKommune.isEmpty()) {
            Kommune nyKommune = new Kommune(kommuneNavn);
            return nyKommune;
        } else {
            return optionalKommune.get();
        }
    }

    public Sogn findByName(String navn){
        Optional<Sogn> optionalSogn = sognRepository.findSognByNavn(navn);
        if (!optionalSogn.isEmpty()) {
            return optionalSogn.get();
        } else {
            return null;
        }
    }


}
