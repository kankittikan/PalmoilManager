package ku.cs.palmoilmnger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminMenuController {

    @RequestMapping("/admin")
    public String getHomePage(Model model) {
        model.addAttribute("username", "ผู้ใช้");
        return "adminMenu";
    }
}
