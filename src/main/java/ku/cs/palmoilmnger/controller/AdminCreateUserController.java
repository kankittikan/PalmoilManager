package ku.cs.palmoilmnger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminCreateUserController {
    @RequestMapping("/adminMenu/manageUser/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("username", "ผู้ใช้");
        return "createUser";
    }
}
