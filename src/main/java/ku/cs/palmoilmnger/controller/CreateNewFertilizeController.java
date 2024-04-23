package ku.cs.palmoilmnger.controller;

import ku.cs.palmoilmnger.entity.Description;
import ku.cs.palmoilmnger.entity.WorkRound;
import ku.cs.palmoilmnger.exception.TransactionException;
import ku.cs.palmoilmnger.model.FertilizeFormulaDTO;
import ku.cs.palmoilmnger.model.RoundDTO;
import ku.cs.palmoilmnger.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class CreateNewFertilizeController {

    @Autowired
    private WorkRoundService workRoundService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private DateTimeService dateTimeService;

    @GetMapping("/menu/round/manageRound/fertilize/createFertilize/{idRound}")
    public String getCreateNewFertilizePage(Model model, @PathVariable String idRound) {
        WorkRound workRound = workRoundService.findById(idRound);
        RoundDTO roundDTO = workRoundService.transformToRoundDTO(workRound.getIdWorkRound());

        model.addAttribute("idRound", roundDTO.getIdWorkRound());
        model.addAttribute("plotName", workRound.getPlantation().getName());
        model.addAttribute("year", roundDTO.getYear());
        model.addAttribute("month", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
        model.addAttribute("round", "รอบ "+roundDTO.getRound());
        model.addAttribute("username", "ชื่อผู้ใช้");
        return "createNewFertilize";
    }

    @PostMapping("/menu/round/manageRound/fertilize/createFertilize/{idRound}")
    public String getCreateNewFertilizePageAdd(@PathVariable String idRound, @ModelAttribute FertilizeFormulaDTO fertilizeFormulaDTO, RedirectAttributes redirectAttributes) {
        WorkRound workRound = workRoundService.findById(idRound);
        try {
            transactionService.createFertilizeFormula(fertilizeFormulaDTO, workRound);
        } catch (TransactionException e) {
            redirectAttributes.addAttribute("notice", e.getMessage());
            return "redirect:/manager/menu/round/manageRound/fertilize/createFertilize/" + idRound;
        }
        return "redirect:/manager/menu/round/manageRound/fertilize/" + idRound + "?success";
    }
}
