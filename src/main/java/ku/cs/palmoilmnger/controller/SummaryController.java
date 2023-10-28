package ku.cs.palmoilmnger.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class SummaryController {

    @RequestMapping("/menu/yearSummary")
    public String getQuarterSummaryPage(Model model) {
        model.addAttribute("username", "ผู้ใช้");
        return "yearSummary";
    }

    @RequestMapping("/menu/quarterSummary")
    public String getYearSummaryPage(Model model) {
        model.addAttribute("username", "ผู้ใช้");
        return "quarterSummary";
    }
}
