package ku.cs.palmoilmnger.controller;

import ku.cs.palmoilmnger.entity.User;
import ku.cs.palmoilmnger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminCreateUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/manageUser/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("username", "ผู้ใช้");
        return "createUser";
    }

    // Create Manager Method
    @PostMapping("/manageUser/create")
    public String createManager(@ModelAttribute User user, Model model){
        boolean isUsername = userService.isUsernameAvailable(user.getUsername());
        boolean isName = userService.isNameAvailable(user.getName());
        if(user.getUsername().isEmpty() || user.getPassword().isEmpty()){
            model.addAttribute("createError", true);
        }else{
            if(isName && isUsername){
                userService.createManager(user);
                model.addAttribute("createSuccess", true);
            }else if (!isName){
                model.addAttribute("createError", true);
            }else{
                model.addAttribute("createError", true);
            }
        }
        return "createUser";
    }
}