package ku.cs.palmoilmnger.controller;

import ku.cs.palmoilmnger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/manageUser")
public class AdminManageUserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String getManageUserMenu(Model model) {
        model.addAttribute("users", userService.getAllUsersRole("ROLE_MANAGER"));
        return "manageUser";
    }


}
