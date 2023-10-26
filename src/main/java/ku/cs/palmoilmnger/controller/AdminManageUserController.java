package ku.cs.palmoilmnger.controller;

import ku.cs.palmoilmnger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminManageUserController {
    @Autowired
    private UserService userService;

    @GetMapping("/manageUser")
    public String getHomePage(Model model) {
        model.addAttribute("username", "ชื่อผู้ใช้");
        model.addAttribute("users", userService.getAllUsers());
        return "manageUser";
    }}
