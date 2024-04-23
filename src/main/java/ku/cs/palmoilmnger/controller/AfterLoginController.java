package ku.cs.palmoilmnger.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AfterLoginController {

    @GetMapping("/afterlogin")
    public String getHomePage(HttpServletRequest request) {
        if(request.isUserInRole("ADMIN")) {
            return "redirect:/admin/menu";
        }
        else if(request.isUserInRole("MANAGER")) {
            return "redirect:/manager/menu";
        }
        return "redirect:/";
    }
}
