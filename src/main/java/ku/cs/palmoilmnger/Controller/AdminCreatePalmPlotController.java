package ku.cs.palmoilmnger.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminCreatePalmPlotController {

    @RequestMapping("/adminMenu/managePalmPlot/create")
    public String getManagePalmPlotPage(Model model) {
        model.addAttribute("username", "ผู้ใช้");
        return "createPalmPlot";
    }
}
