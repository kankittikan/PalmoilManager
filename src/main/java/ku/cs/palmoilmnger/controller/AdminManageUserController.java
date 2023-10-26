package ku.cs.palmoilmnger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminManageUserController {

    @GetMapping("/manageUser")
    public String getHomePage(Model model) {
        model.addAttribute("username", "ชื่อผู้ใช้");
        return "manageUser";
    }}
