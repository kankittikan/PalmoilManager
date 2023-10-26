package ku.cs.palmoilmnger.Controller;

import ku.cs.palmoilmnger.entity.User;
import ku.cs.palmoilmnger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminCreateUserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/admin/manageUser/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("username", "ผู้ใช้");
        return "createUser";
    }

    // Create Manager Method
    @PostMapping("/admin/manageUser/create")
    public String createManager(@ModelAttribute User user, Model model){
        if(userService.isUsernameAvailable(user.getUsername())){
            userService.createManager(user);
        }
        return "createUser";
    }
}
