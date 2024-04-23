package ku.cs.palmoilmnger.controller;

import ku.cs.palmoilmnger.entity.WorkRound;
import ku.cs.palmoilmnger.exception.WorkRoundException;
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
        model.addAttribute("plotName", showWorkRound.getPlantation().getName());
        model.addAttribute("plotId", showWorkRound.getPlantation().getIdPlantation());
        return "deleteRoundValidate";
    }

    @PostMapping("/menu/round/ConfirmDeleteRound/{idRound}")
    public String deleteRoundValidateHandler(Model model, @PathVariable String idRound){
        int plotId = workRoundService.findById(idRound).getPlantation().getIdPlantation();
        try {
            workRoundService.deleteRound(idRound);
        } catch (WorkRoundException e) {
            //System.out.println(e.getMessage());
            return "redirect:/manager/menu/round/"+plotId+"?cannotDelete";
        }
        return "redirect:/manager/menu/round/"+plotId+"?delete";
    }
}
