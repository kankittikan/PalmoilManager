package ku.cs.palmoilmnger.controller;

import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.exception.PlantationException;
import ku.cs.palmoilmnger.model.PlantationDTO;
import ku.cs.palmoilmnger.service.PlantationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/managePlantation")
public class AdminManagePlantationController {
    @Autowired
    PlantationService plantationService;

    @GetMapping()
    public String getManagePalmPage(Model model) {
        model.addAttribute("plantations", plantationService.getAllPlantation());
        return "managePalmPlot";
    }

    /*
    Create Plantation
     */
    @GetMapping("/create")
    public String getCreatePalmPage(Model model) {
        return "createPalmPlot";
    }

    @PostMapping("/create")
    public String createPalmHandler(Model model, @ModelAttribute PlantationDTO plantation) {
        try {
            plantationService.insertNew(plantation);
            return "redirect:/admin/managePlantation";
        } catch (PlantationException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "createPalmPlot";
    }

    /*
    Remove
     */
    @GetMapping("/remove/{id}")
    public String remove(@PathVariable int id, Model model) {
        try {
            model.addAttribute("plantation", plantationService.getPlantation(id));
        } catch (PlantationException e) {
            return "redirect:/admin/managePlantation";
        }
        return "deletePalmPlotValidate";
    }

    @PostMapping("/remove/{id}")
    public String remove(@PathVariable int id) {
        plantationService.deletePlantation(id);
        return "redirect:/admin/managePlantation";
    }

}