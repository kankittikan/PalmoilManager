package ku.cs.palmoilmnger.Controller;

import ku.cs.palmoilmnger.entity.Description;
import ku.cs.palmoilmnger.entity.WorkRound;
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
public class CreateNewFertilizeController {

    @Autowired
    private WorkRoundService workRoundService;

    @Autowired
    private DescriptionService descriptionService;

    @Autowired
    private WorkTypeService workTypeService;

    @Autowired
    private DateTimeService dateTimeService;

    @RequestMapping("/menu/round/manageRound/fertilize/createFertilize/{idRound}")
    public String getCreateNewFertilizePage(Model model, @PathVariable String idRound) {
        WorkRound workRound = workRoundService.findById(idRound);
        RoundDTO roundDTO = workRoundService.transformToRoundDTO(workRound.getIdWorkRound());

        // System.out.println(workTypeService.getAllWorkTypes());

        List<Description> descriptions = descriptionService.getDescriptionsByWorkType(workTypeService.getWorkType("ใส่ปุ๋ย"));
        System.out.println(descriptions);

        model.addAttribute("idRound", roundDTO.getIdWorkRound());
        model.addAttribute("plotName", workRound.getPlantation().getName());
        model.addAttribute("year", roundDTO.getYear());
        model.addAttribute("month", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
        model.addAttribute("round", "รอบ "+roundDTO.getRound());
        model.addAttribute("username", "ชื่อผู้ใช้");
        return "createNewFertilize";
    }
}
