package ku.cs.palmoilmnger.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class CreateNewFertilizeController {

    @RequestMapping("/menu/round/manageRound/fertilize/createFertilize")
    public String getCreateNewFertilizePage(Model model) {
        model.addAttribute("username", "ชื่อผู้ใช้");
        return "createNewFertilize";
    }
}
