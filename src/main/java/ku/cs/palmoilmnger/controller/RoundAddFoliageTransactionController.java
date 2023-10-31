package ku.cs.palmoilmnger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class RoundAddFoliageTransactionController {

    @RequestMapping("/menu/round/manageRound/foliage/create")
    public String getFoliageCreateTransactionPage(Model model) {
        model.addAttribute("username", "ชื่อผู้ใช้");

        model.addAttribute("plotName", "พลุดินนา");
        model.addAttribute("year", "2023");
        model.addAttribute("month", "พฤษภาคม");
        model.addAttribute("round", "รอบ "+"1");
        return "foliageCreateTransaction";
    }
}
