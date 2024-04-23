package ku.cs.palmoilmnger.controller;

import jakarta.servlet.http.HttpServletRequest;
import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.exception.PlantationException;
import ku.cs.palmoilmnger.exception.WorkRoundException;
import ku.cs.palmoilmnger.model.DropdownItemNew;
import ku.cs.palmoilmnger.model.RoundDTO;
import ku.cs.palmoilmnger.model.RoundSortDTO;
import ku.cs.palmoilmnger.service.DateTimeService;
import ku.cs.palmoilmnger.service.PlantationService;
import ku.cs.palmoilmnger.service.UserService;
import ku.cs.palmoilmnger.service.WorkRoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.SequenceInputStream;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/menu/round/{id}")
    public String getRoundPage(Model model, @PathVariable int id) throws PlantationException {
        Plantation checkPlant = plantationService.getPlantation(id);

        List<RoundDTO> roundDTOList = workRoundService.getRoundDTOListByPlantation(checkPlant);
        roundDTOList.sort(Comparator.comparing(RoundDTO::getIdWorkRound));
        Collections.reverse(roundDTOList);

        roundDTOList = roundDTOList.stream().filter(roundDTO -> roundDTO.getYear().equals(String.valueOf(dateTimeService.getYear()))).collect(Collectors.toList());

        List<String> months = new ArrayList<>();
        for(int i = 1; i<=12; i++) {
            months.add(String.format("%02d", i));
        }
        months.add(0, "ทุกเดือน");

        model.addAttribute("rounds", roundDTOList);
        model.addAttribute("plotName", checkPlant.getName());
        model.addAttribute("plotId", checkPlant.getIdPlantation());
        model.addAttribute("years", dateTimeService.getYearListByRange(10));
        model.addAttribute("months", months);
        return "round";
    }

    @PostMapping("/menu/round/{id}")
    public String getRoundPageFilter(Model model, @PathVariable int id, @ModelAttribute RoundSortDTO sortDTO) throws PlantationException {
        Plantation checkPlant = plantationService.getPlantation(id);

        List<RoundDTO> roundDTOList = workRoundService.getRoundDTOListByPlantation(checkPlant);
        roundDTOList.sort(Comparator.comparing(RoundDTO::getIdWorkRound));
        Collections.reverse(roundDTOList);

        roundDTOList = roundDTOList.stream().filter(roundDTO -> roundDTO.getYear().equals(sortDTO.getYear())).collect(Collectors.toList());
        if(!sortDTO.getMonth().equals("ทุกเดือน")) {
            roundDTOList = roundDTOList.stream().filter(roundDTO -> roundDTO.getMonth().equals(sortDTO.getMonth())).collect(Collectors.toList());
        }

        List<String> months = new ArrayList<>();
        for(int i = 1; i<=12; i++) {
            months.add(String.format("%02d", i));
        }
        months.add(0, "ทุกเดือน");

        List<Integer> years = dateTimeService.getYearListByRange(10);

        years.add(0, Integer.valueOf(sortDTO.getYear()));
        months.add(0, sortDTO.getMonth());

        model.addAttribute("rounds", roundDTOList);
        model.addAttribute("plotName", checkPlant.getName());
        model.addAttribute("plotId", checkPlant.getIdPlantation());
        model.addAttribute("years", years);
        model.addAttribute("months", months);
        return "round";
    }

    @GetMapping("/menu/round/create/{id}/{year}")
    public String getCreateRoundPage(Model model, @PathVariable int id, @PathVariable int year) throws PlantationException {
        Plantation checkPlant = plantationService.getPlantation(id);

        List<Integer> years = dateTimeService.getYearListByRange(10);
        if(year == dateTimeService.getYear()) {
            model.addAttribute("months", dateTimeService.getAllMonthByNow());
        }
        else {
            model.addAttribute("months", dateTimeService.getAllMonth());
            years.add(0, year);
        }
        model.addAttribute("years", years);
        model.addAttribute("plotName", checkPlant.getName());
        model.addAttribute("plotId", checkPlant.getIdPlantation());
        return "createRound";
    }

    @PostMapping("/menu/round/create/{id}")
    public String createRoundHandler(RedirectAttributes redirectAttributes, @ModelAttribute RoundDTO roundDTO, @PathVariable int id, HttpServletRequest request) throws PlantationException {

        try {
            workRoundService.createWorkRoundRepeat(roundDTO, plantationService.getPlantation(id), userService.getManager(request.getUserPrincipal().getName()));
            return "redirect:/manager/menu/round/" + plantationService.getPlantation(id).getIdPlantation() + "?success";
        } catch (WorkRoundException e) {
            redirectAttributes.addFlashAttribute("errorNotice", e.getMessage());
            return "redirect:/manager/menu/round/create/" + plantationService.getPlantation(id).getIdPlantation();
        }

    }

}
