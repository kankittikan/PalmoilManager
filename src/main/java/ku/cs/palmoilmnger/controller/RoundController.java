package ku.cs.palmoilmnger.controller;

import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.entity.WorkRound;
import ku.cs.palmoilmnger.service.PlantationService;
import ku.cs.palmoilmnger.service.WorkRoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class RoundController {

    @Autowired
    private PlantationService plantationService;

    @Autowired
    private WorkRoundService workRoundService;

    @GetMapping("/menu/round/{name}")
    public String getRoundPage(Model model, @PathVariable String name) {
        Plantation checkPlant = plantationService.getPlantation(name);
        model.addAttribute("rounds", workRoundService.findByPlantation(checkPlant));
        model.addAttribute("plotName", checkPlant.getName());
        return "round";
    }

    @GetMapping("/menu/round/create/{name}")
    public String getCreateRoundPage(Model model, @PathVariable String name) {
        Plantation checkPlant = plantationService.getPlantation(name);
        model.addAttribute("plotName", checkPlant.getName());
        return "createRound";
    }
}
