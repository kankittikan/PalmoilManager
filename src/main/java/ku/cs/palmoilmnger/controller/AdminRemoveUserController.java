package ku.cs.palmoilmnger.controller;

import ku.cs.palmoilmnger.entity.User;
import ku.cs.palmoilmnger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminRemoveUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/manageUser/remove/{username}")
    public String getRemoveUserPage(Model model, @PathVariable String username){
        User user = userService.getManager(username);
        if(user.getRole().equals("ROLE_ADMIN")){
            return "redirect:/admin/manageUser?cannot";
        }
        model.addAttribute("usernameDelete", user.getUsername());
        model.addAttribute("fullnameDelete", user.getName());
        return "deleteUserValidate";
    }

    @PostMapping("/manageUser/remove/{username}")
    public String removeUserHandler(@PathVariable String username){
        userService.deleteUser(username);
        return "redirect:/admin/manageUser?remove";
    }
}
