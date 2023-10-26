package ku.cs.palmoilmnger.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminManagePalmPlotController {

    @RequestMapping("/adminMenu/managePalmPlot")
    public String getManagePalmPage(Model model) {
        model.addAttribute("username", "ชื่อผู้ใช้");
        return "managePalmPlot";
    }
}
