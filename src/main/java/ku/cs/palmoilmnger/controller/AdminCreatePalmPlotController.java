package ku.cs.palmoilmnger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminCreatePalmPlotController {

    @GetMapping("/managePalmPlot/create")
    public String getCreatePalmPage(Model model){
        return "createPalmPlot";
    }

    @PostMapping("/managePalmPlot/create")
    public String createPalmHandler(Model model){
        return "";
    }
}
