package ku.cs.palmoilmnger.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminChangePasswordUserController {

    @RequestMapping("/adminMenu/manageUser/changePassword")
    public String getChangePasswordPage(Model model) {
        model.addAttribute("username", "ชื่อผู้ใช้");
        return "changePassword";
    }
}
