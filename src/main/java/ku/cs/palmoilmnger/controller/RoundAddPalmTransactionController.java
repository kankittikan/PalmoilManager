package ku.cs.palmoilmnger.controller;

import ku.cs.palmoilmnger.entity.Description;
import ku.cs.palmoilmnger.entity.WorkRound;
import ku.cs.palmoilmnger.entity.WorkType;
import ku.cs.palmoilmnger.model.RoundDTO;
import ku.cs.palmoilmnger.service.DateTimeService;
import ku.cs.palmoilmnger.service.DescriptionService;
import ku.cs.palmoilmnger.service.WorkRoundService;
import ku.cs.palmoilmnger.service.WorkTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class RoundAddPalmTransactionController {

    @Autowired
    private WorkRoundService workRoundService;

    @Autowired
    private DateTimeService dateTimeService;

    @Autowired
    private WorkTypeService workTypeService;

    @Autowired
    private DescriptionService descriptionService;

    @RequestMapping("/menu/round/manageRound/palm/create/{round}")
    public String getPalmCreateTransactionPage(Model model, @PathVariable String round) {
        WorkRound workRound = workRoundService.findById(round);
        RoundDTO roundDTO = workRoundService.transformToRoundDTO(workRound.getIdWorkRound());

//        System.out.println(workTypeService.getAllWorkTypes());

        List<Description> descriptions = descriptionService.getDescriptionsByWorkType(workTypeService.getWorkType("ตัดปาล์ม"));
        System.out.println(descriptions);

        model.addAttribute("idRound", roundDTO.getIdWorkRound());
        model.addAttribute("plotName", workRound.getPlantation().getName());
        model.addAttribute("year", roundDTO.getYear());
        model.addAttribute("month", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
        model.addAttribute("round", "รอบ "+roundDTO.getRound());
        model.addAttribute("descriptions", descriptions);
        return "palmCreateTransaction";
    }
}
