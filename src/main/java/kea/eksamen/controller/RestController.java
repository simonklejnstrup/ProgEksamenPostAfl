package kea.eksamen.controller;


import kea.eksamen.model.database.Kommune;
import kea.eksamen.model.database.Sogn;
import kea.eksamen.model.statistik.SognStatistik;
import kea.eksamen.repository.KommuneRepository;
import kea.eksamen.repository.SognRepository;
import kea.eksamen.service.Dataservice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private Dataservice dataService;

    public RestController(Dataservice dataService) {
        this.dataService = dataService;
    }

    @PostMapping("createSogn/{kommuneNavn}/{sogneKode}/{sogneNavn}/{incidens}")
    public ResponseEntity<SognStatistik> createNewSogn( @PathVariable String kommuneNavn,
            @PathVariable int sogneKode, @PathVariable String sogneNavn, @PathVariable int incidens) {

        try {
            //LocalDate nedlukningsDato = LocalDate.parse(nedlukning);
            return new ResponseEntity<>(dataService.createSogn(sogneKode, kommuneNavn, sogneNavn, incidens), HttpStatus.CREATED);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formatting of 'nedlukning' not correct - Please follow the YYYY-MM-DD format.");
        }
    }

    @PutMapping("updateSogn/{sogneKode}/{kommune}/{incidens}")
    public ResponseEntity<SognStatistik> updateExistingSogn(@PathVariable int sogneKode,@PathVariable Kommune kommune, @PathVariable String sogneNavn,
            @PathVariable int incidens, @PathVariable String nedlukning) {

        try {
            LocalDate nedlukningsDato = LocalDate.parse(nedlukning);
            return new ResponseEntity<>(dataService.updateSogn(sogneKode,kommune, sogneNavn, incidens), HttpStatus.OK);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formatting of 'nedlukning' not correct - Please follow the YYYY-MM-DD format.");
        }

    }

    @DeleteMapping("deleteSogn/{sogneNavn}")
    public ResponseEntity<HttpStatus> deleteSogn(@PathVariable String sogneNavn) {
        dataService.deleteSogn(sogneNavn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("getSogne")
    public ResponseEntity<ArrayList<SognStatistik>> getAllSogne() {
        return new ResponseEntity<>(dataService.getAllSogne(), HttpStatus.OK);
    }





}
