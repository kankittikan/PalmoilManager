package ku.cs.palmoilmnger.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class FoliageRoundController {

    @RequestMapping("/menu/round/manageRound/foliage")
    public String getFoliageRoundPage(Model model) {
        model.addAttribute("username", "ชื่อผู้ใช้");

        model.addAttribute("plotName", "พลุดินนา");
        model.addAttribute("plotYear", "2023");
        model.addAttribute("plotMonth", "พฤษภาคม");
        model.addAttribute("plotRound", "รอบ "+"1");
        return "foliageRound";
    }
}
