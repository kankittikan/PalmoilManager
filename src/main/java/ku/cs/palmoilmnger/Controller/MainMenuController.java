package ku.cs.palmoilmnger.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainMenuController {
    @RequestMapping("/menu")
    public String getMenuPage(Model model) {
        return "menu";
    }
}
