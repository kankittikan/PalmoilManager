package ku.cs.palmoilmnger.controller;

import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.entity.WorkRound;
import ku.cs.palmoilmnger.exception.PlantationException;
import ku.cs.palmoilmnger.model.RoundDTO;
import ku.cs.palmoilmnger.service.DateTimeService;
import ku.cs.palmoilmnger.service.PlantationService;
import ku.cs.palmoilmnger.service.WorkRoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/manager")
public class RoundController {

    @Autowired
    private PlantationService plantationService;

    @Autowired
    private WorkRoundService workRoundService;

    @Autowired
    private DateTimeService dateTimeService;

    @GetMapping("/menu/round/{id}")
    public String getRoundPage(Model model, @PathVariable int id ) throws PlantationException {
        Plantation checkPlant = plantationService.getPlantation(id);
        model.addAttribute("rounds", workRoundService.findByPlantation(checkPlant));
        model.addAttribute("plotName", checkPlant.getName());
        return "round";
    }

    @GetMapping("/menu/round/create/{name}")
    public String getCreateRoundPage(Model model, @PathVariable String name) throws PlantationException {
        Plantation checkPlant = plantationService.getPlantationByName(name);
        model.addAttribute("years", dateTimeService.getYearListByRange(10));
        model.addAttribute("plotName", checkPlant.getName());
        return "createRound";
    }

    @PostMapping("/menu/round/create/{name}")
    public String createRoundHandler(Model model, @ModelAttribute RoundDTO roundDTO, @PathVariable String name){

        String outputPage = "redirect:/manager/menu/round/"+plantationService.getPlantationByName(name).getIdPlantation()+"?sucess";
        System.out.println(outputPage);
        return outputPage;
    }
}
