package ku.cs.palmoilmnger.controller;

import ku.cs.palmoilmnger.entity.WorkRound;
import ku.cs.palmoilmnger.model.RoundDTO;
import ku.cs.palmoilmnger.service.DateTimeService;
import ku.cs.palmoilmnger.service.WorkRoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class DeleteRoundValidateController {

    @Autowired
    private WorkRoundService workRoundService;

    @Autowired
    private DateTimeService dateTimeService;

    @RequestMapping("/menu/round/ConfirmDeleteRound/{idRound}")
    public String getDeleteRoundValidatePage(Model model, @PathVariable String idRound) {
        WorkRound showWorkRound = workRoundService.findById(idRound);
        RoundDTO roundDTO = workRoundService.transformToRoundDTO(showWorkRound.getIdWorkRound());
        model.addAttribute("idRound", showWorkRound.getIdWorkRound());
        model.addAttribute("yearDelete", roundDTO.getYear());
        model.addAttribute("monthDelete", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
        model.addAttribute("roundDelete", roundDTO.getRound());
        return "deleteRoundValidate";
    }

    @PostMapping("/menu/round/ConfirmDeleteRound/{idRound}")
    public String deleteRoundValidateHandler(Model model, @PathVariable String idRound){
        String plotName = workRoundService.findById(idRound).getPlantation().getName();
        workRoundService.deleteRound(idRound);
        return "redirect:/manager/menu/round/"+plotName+"?delete";
    }
}
