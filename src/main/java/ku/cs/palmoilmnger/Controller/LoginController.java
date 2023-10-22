package ku.cs.palmoilmnger.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class LoginController {
    @RequestMapping("/")
    public String getHomePage(Model model) {
        //model.addAttribute("greeting", "Sawaddee");
        return "login";
    }

}
