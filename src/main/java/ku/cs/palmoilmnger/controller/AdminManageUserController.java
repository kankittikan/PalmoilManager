package ku.cs.palmoilmnger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminManageUserController {

    @RequestMapping("/admin/manageUser")
    public String getHomePage(Model model) {
        model.addAttribute("username", "ชื่อผู้ใช้");
        return "manageUser";
    }}
