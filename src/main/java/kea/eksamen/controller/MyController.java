package kea.eksamen.controller;


import kea.eksamen.model.database.Kommune;
import kea.eksamen.model.database.Sogn;
import kea.eksamen.service.Dataservice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;

@Controller
public class MyController {

    private Dataservice dataService;

    public MyController(Dataservice dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/")
    public String hentSogne(Model model) {
        model.addAttribute("sogne", dataService.getAllSogne());
        model.addAttribute("kommuner", dataService.getAllKommuner());


        return "index";
    }

    @GetMapping("/kommunestatistik")
    public String hentKommuneStatistik(Model model) {
        model.addAttribute("kommuner", dataService.getAllKommuner());


        return "kommunestatistik";
    }

    @PostMapping("/create")
    public String opretSogn(WebRequest request) {

        int sogneKode = Integer.parseInt(request.getParameter("sogneKode"));
        String sogneNavn = request.getParameter("sogneNavn");
        String kommuneNavn = request.getParameter("kommune");
        int incidens = Integer.parseInt(request.getParameter("incidens"));
        //LocalDate nedlukning = LocalDate.parse(request.getParameter("nedlukning"));

        dataService.createSogn(sogneKode, kommuneNavn, sogneNavn, incidens);

        return "redirect:/";
    }




    @RequestMapping(value = "/delete/{navn}")
    public String deleteSogn(@PathVariable String navn, Model model) {
        Sogn sogn = dataService.findByName(navn);
        sogn.setKommune(null);
        dataService.updateSogn(sogn.getSogneKode(), null, sogn.getNavn(), sogn.getIncidens());

        dataService.deleteSogn(navn);

        model.addAttribute("sogne", dataService.getAllSogne());
        model.addAttribute("kommuner", dataService.getAllKommuner());

        return "redirect:/";

    }

    @RequestMapping(value = "/open/{navn}")
    public String openSogn(@PathVariable String navn, Model model) {
        Sogn sogn = dataService.findByName(navn);
        dataService.openSogn(sogn.getNavn());


        model.addAttribute("sogne", dataService.getAllSogne());
        model.addAttribute("kommuner", dataService.getAllKommuner());

        return "redirect:/";

    }

    @GetMapping("/lukned/{navn}")
    public String lukSogn(@PathVariable("navn") String navn, Model model){

        model.addAttribute("sogn", dataService.findByName(navn));

        return "lukned";
    }

    @PostMapping("/lukned")
    public String closeSogn(WebRequest request, Model model) {
        String sogneNavn = request.getParameter("navn");
        LocalDate dato = LocalDate.parse(request.getParameter("date"));

        dataService.lukSogn(sogneNavn, dato);

        model.addAttribute("sogne", dataService.getAllSogne());
        model.addAttribute("kommuner", dataService.getAllKommuner());


        return "redirect:/";
    }

    @GetMapping("/update/{navn}")
    public String updateSogn(@PathVariable("navn") String navn, Model model){

        model.addAttribute("sogn", dataService.findByName(navn));

        return "update";
    }


    @PostMapping("/update")
    public String update(WebRequest request, Model model) {
        String sogneNavn = request.getParameter("navn");
        int incidens = Integer.parseInt(request.getParameter("incidens"));
        Kommune kommune = new Kommune(request.getParameter("kommune"));
        int sogneKode = Integer.parseInt(request.getParameter("sogneKode"));


        dataService.updateSogn(sogneKode,kommune, sogneNavn, incidens);

        model.addAttribute("sogne", dataService.getAllSogne());
        model.addAttribute("kommuner", dataService.getAllKommuner());

        return "index";
    }



}
