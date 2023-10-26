package ku.cs.palmoilmnger.controller;

import ku.cs.palmoilmnger.entity.User;
import ku.cs.palmoilmnger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

//Admin signup to manager
@Controller
public class SignupController {
    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String getSignupPage(){
        return "signup";
    }

    @PostMapping("/signup")
    public String signupManager(@ModelAttribute User user, Model model){
        if(userService.isUsernameAvailable(user.getUsername())){
            userService.createManager(user);
            model.addAttribute("signupSuccess", true);
        }else{
            model.addAttribute("signupError", "Username not available");
        }

        return "signup";
    }
}
