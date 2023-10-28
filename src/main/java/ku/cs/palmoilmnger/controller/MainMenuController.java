package ku.cs.palmoilmnger.controller;

import ku.cs.palmoilmnger.service.PlantationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class MainMenuController {
    @Autowired
    private PlantationService plantationService;
    @RequestMapping("/menu")
    public String getMenuPage(Model model) {
        model.addAttribute("plots", plantationService.getAllPlantation());
        return "menu";
    }
}
