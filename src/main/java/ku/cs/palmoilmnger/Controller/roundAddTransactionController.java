package ku.cs.palmoilmnger.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class roundAddTransactionController {

    @RequestMapping("/menu/round/manageRound/palm/create")
    public String getCreateTransactionPage(Model model) {
        model.addAttribute("username", "ชื่อผู้ใช้");

        model.addAttribute("plotName", "พลุดินนา");
        model.addAttribute("year", "2023");
        model.addAttribute("month", "พฤษภาคม");
        model.addAttribute("round", "รอบ "+"1");
        return "palmCreateTransaction";
    }
}
