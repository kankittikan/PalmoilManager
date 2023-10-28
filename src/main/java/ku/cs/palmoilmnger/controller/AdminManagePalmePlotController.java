package ku.cs.palmoilmnger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminManagePalmePlotController {

    @GetMapping("/managePalmPlot")
    public String getManagePalmPage(Model model){
        return "managePalmPlot";
    }
}
