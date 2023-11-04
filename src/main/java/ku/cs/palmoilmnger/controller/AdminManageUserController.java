package ku.cs.palmoilmnger.controller;

import ku.cs.palmoilmnger.entity.User;
import ku.cs.palmoilmnger.exception.UserException;
import ku.cs.palmoilmnger.model.ChangePassword;
import ku.cs.palmoilmnger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/manageUser")
public class AdminManageUserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public String getManageUserMenu(Model model) {
        model.addAttribute("users", userService.getAllUsersRole("ROLE_MANAGER"));
        return "manageUser";
    }

    /*
    Create User
     */
    @GetMapping("/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("username", "ผู้ใช้");
        return "createUser";
    }
    @PostMapping("/create")
    public String createManager(@ModelAttribute User user, Model model){
        if(user.getName().isEmpty() || user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            model.addAttribute("createError", "กรอกข้อมูลไม่ครบ");
        }
        else {
            try {
                userService.createManager(user);
                return "redirect:/admin/manageUser";
            } catch (UserException e) {
                model.addAttribute("createError", e.getMessage());
            }
        }
        return "createUser";
    }

    /*
    Delete User
     */
    @GetMapping("/remove/{username}")
    public String getRemoveUserPage(Model model, @PathVariable String username){
        User user = userService.getManager(username);
        if(user.getRole().equals("ROLE_ADMIN")){
            return "redirect:/admin/manageUser?cannot";
        }
        model.addAttribute("usernameDelete", user.getUsername());
        model.addAttribute("fullnameDelete", user.getName());
        return "deleteUserValidate";
    }

    @PostMapping("/remove/{username}")
    public String removeUserHandler(@PathVariable String username){
        userService.deleteUser(username);
        return "redirect:/admin/manageUser";
    }

    /*
    Change Password
     */
    @GetMapping("/changePassword")
    public String getChangePasswordPage(Model model){
        model.addAttribute("users", userService.getAllUsersRole("ROLE_MANAGER"));
        return "changePassword";
    }

    @PostMapping("/changePassword")
    public String changePasswordHandler(Model model, @ModelAttribute ChangePassword changePassword){
        try {
            userService.changePassword(changePassword);
            return "redirect:/admin/manageUser";
        } catch (UserException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "changePassword";
    }

}
