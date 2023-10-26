package ku.cs.palmoilmnger.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminDeletePalmPlotValidateController {

    @RequestMapping("/adminMenu/managePalmPlot/deletePalmPlot")
    public String getDeleteUserPage(Model model) {
        model.addAttribute("username", "ชื่อผู้ใช้");
        model.addAttribute("PalmNameDelete", "พลุดินนา");
        model.addAttribute("numberDelete", "1 ไร่");
        model.addAttribute("locationDelete", "lorem lorem lorem lorem lorem lorem loremeeeeeeeeeeeee");
        return "deletePalmPlotValidate";
    }
}
