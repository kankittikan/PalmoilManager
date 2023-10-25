package ku.cs.palmoilmnger.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainMenuController {
    @RequestMapping("/menu")
    public String getMenuPage(Model model) {
        model.addAttribute("username", "ชื่อผู้ใช้");
        model.addAttribute("plotName", "พลุดินนา");
        model.addAttribute("plotLo", "lorem lorem lorem");
        return "menu";
    }
}
