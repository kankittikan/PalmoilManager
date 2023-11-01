package ku.cs.palmoilmnger.controller;

import jakarta.servlet.http.HttpServletRequest;
import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.exception.PlantationException;
import ku.cs.palmoilmnger.exception.WorkRoundException;
import ku.cs.palmoilmnger.model.RoundDTO;
import ku.cs.palmoilmnger.service.DateTimeService;
import ku.cs.palmoilmnger.service.PlantationService;
import ku.cs.palmoilmnger.service.UserService;
import ku.cs.palmoilmnger.service.WorkRoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class WorkRoundController {

    @Autowired
    private PlantationService plantationService;

    @Autowired
    private WorkRoundService workRoundService;

    @Autowired
    private DateTimeService dateTimeService;

    @Autowired
    private UserService userService;

    @GetMapping("/menu/round/{name}")
    public String getRoundPage(Model model, @PathVariable String name ) throws PlantationException {
        Plantation checkPlant = plantationService.getPlantationByName(name);
        List<RoundDTO> roundDTOList = workRoundService.getRoundDTOListByPlantation(checkPlant);
        model.addAttribute("rounds", roundDTOList);
        model.addAttribute("plotName", checkPlant.getName());
        System.out.println(workRoundService.getLastOfWorkRound());
        return "round";
    }

//    @GetMapping("/menu/round/{name}?Sort")
//    public String getRoundSortPage(Model model, @PathVariable String name ) throws PlantationException {
//        Plantation checkPlant = plantationService.getPlantationByName(name);
//        List<RoundDTO> roundDTOList = workRoundService.getRoundDTOListByPlantation(checkPlant);
//        model.addAttribute("rounds", roundDTOList);
//        model.addAttribute("plotName", checkPlant.getName());
//        System.out.println(workRoundService.getLastOfWorkRound());
//        return "round";
//    }

    @GetMapping("/menu/round/create/{name}")
    public String getCreateRoundPage(Model model, @PathVariable String name) throws PlantationException {
        Plantation checkPlant = plantationService.getPlantationByName(name);

        model.addAttribute("years", dateTimeService.getYearListByRange(10));
        model.addAttribute("plotName", checkPlant.getName());
        return "createRound";
    }

    @PostMapping("/menu/round/create/{plantName}")
    public String createRoundHandler(RedirectAttributes redirectAttributes, @ModelAttribute RoundDTO roundDTO, @PathVariable String plantName, HttpServletRequest request) throws PlantationException {

        String outputPage = "";

        try{
            workRoundService.createWorkRoundRepeat(roundDTO, plantationService.getPlantationByName(plantName), userService.getManager(request.getUserPrincipal().getName()));
            outputPage = "redirect:/manager/menu/round/"+plantationService.getPlantationByName(plantName).getName()+"?success";
        }catch (WorkRoundException e){
            redirectAttributes.addFlashAttribute("errorNotice", e.getMessage());
            outputPage = "redirect:/manager/menu/round/create/"+plantationService.getPlantationByName(plantName).getName();
        }

        return outputPage;

    }
}
