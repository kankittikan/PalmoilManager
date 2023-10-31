package ku.cs.palmoilmnger.controller;

import ku.cs.palmoilmnger.entity.WorkRound;
import ku.cs.palmoilmnger.model.RoundDTO;
import ku.cs.palmoilmnger.service.DateTimeService;
import ku.cs.palmoilmnger.service.WorkRoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class ManageRoundController {

    @Autowired
    WorkRoundService workRoundService;

    @Autowired
    DateTimeService dateTimeService;

    @GetMapping("/menu/round/manageRound/{round}")
    public String getManageRoundPage(Model model,@PathVariable("round") String round) {

        WorkRound workRound = workRoundService.findById(round);

        RoundDTO roundDTO = workRoundService.transformToRoundDTO(workRound.getIdWorkRound());

        model.addAttribute("idRound", roundDTO.getIdWorkRound());
        model.addAttribute("plotName", workRound.getPlantation().getName());
        model.addAttribute("plotYear", roundDTO.getYear());
        model.addAttribute("plotMonth", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
        model.addAttribute("plotRound", "รอบ "+roundDTO.getRound());
        return "manageRound";
    }

    @GetMapping("/menu/round/manageRound/palm/{round}")
    public String getPalmRoundPage(Model model, @PathVariable("round") String round) {
        WorkRound workRound = workRoundService.findById(round);

        RoundDTO roundDTO = workRoundService.transformToRoundDTO(workRound.getIdWorkRound());

        model.addAttribute("idRound", roundDTO.getIdWorkRound());
        model.addAttribute("plotName", workRound.getPlantation().getName());
        model.addAttribute("plotYear", roundDTO.getYear());
        model.addAttribute("plotMonth", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
        model.addAttribute("plotRound", "รอบ "+roundDTO.getRound());
        return "palmRound";
    }

    @GetMapping("/menu/round/manageRound/fertilize/{round}")
    public String getFertilizeRoundPage(Model model, @PathVariable String round) {
        WorkRound workRound = workRoundService.findById(round);

        RoundDTO roundDTO = workRoundService.transformToRoundDTO(workRound.getIdWorkRound());

        model.addAttribute("idRound", roundDTO.getIdWorkRound());
        model.addAttribute("plotName", workRound.getPlantation().getName());
        model.addAttribute("plotYear", roundDTO.getYear());
        model.addAttribute("plotMonth", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
        model.addAttribute("plotRound", "รอบ "+roundDTO.getRound());
        return "fertilizeRound";
    }

    @GetMapping("/menu/round/manageRound/foliage/{round}")
    public String getFoliageRoundPage(Model model, @PathVariable String round) {
        WorkRound workRound = workRoundService.findById(round);

        RoundDTO roundDTO = workRoundService.transformToRoundDTO(workRound.getIdWorkRound());

        model.addAttribute("idRound", roundDTO.getIdWorkRound());
        model.addAttribute("plotName", workRound.getPlantation().getName());
        model.addAttribute("plotYear", roundDTO.getYear());
        model.addAttribute("plotMonth", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
        model.addAttribute("plotRound", "รอบ "+roundDTO.getRound());
        return "foliageRound";
    }
}