package ku.cs.palmoilmnger.controller;

import ku.cs.palmoilmnger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminChangePasswordController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/manageUser/changePassword")
    public String getChangePasswordPage(Model model){
        return "changePassword";
    }

    @PostMapping("/manageUser/changePassword")
    public String changePasswordHandler(Model model, @RequestParam(value = "username") String username, @RequestParam(value = "newPassword") String newPassword, @RequestParam(value = "confirmPassword") String confirmPassword){
        if(userService.isUsernameAvailable(username)){
            model.addAttribute("changeError", true);
        }else{
            if(newPassword.equals(confirmPassword) && !newPassword.isEmpty()){
                userService.changePassword(userService.getManager(username), newPassword);
                model.addAttribute("changeSuccess", true);
            }else {
                model.addAttribute("changeError", true);
            }
        }

        return "changePassword";
    }
}
