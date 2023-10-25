package ku.cs.palmoilmnger.Controller;

import ku.cs.palmoilmnger.entity.Manager;
import ku.cs.palmoilmnger.service.ManagerService;
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
    private ManagerService managerService;

    @GetMapping("/signup")
    public String getSignupPage(){
        return "signup";
    }

    @PostMapping("/signup")
    public String signupManager(@ModelAttribute Manager manager, Model model){
        if(managerService.isUsernameAvailable(manager.getUsername())){
            managerService.createManager(manager);
            model.addAttribute("signupSuccess", true);
        }else{
            model.addAttribute("signupError", "Username not available");
        }

        return "signup";
    }
}
