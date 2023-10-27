package ku.cs.palmoilmnger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuarterSummaryController {

    @RequestMapping("/menu/quarterSummary")
    public String getQuarterSummaryPage(Model model) {
        model.addAttribute("username", "ผู้ใช้");
        return "quarterSummary";
    }
}
