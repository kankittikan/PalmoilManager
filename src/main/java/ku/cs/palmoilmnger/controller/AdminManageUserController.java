package ku.cs.palmoilmnger.controller;

import ku.cs.palmoilmnger.entity.User;
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

    @GetMapping("/")
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
        return "redirect:/admin/manageUser?remove";
    }

    /*
    Change Password
     */
    @GetMapping("/changePassword")
    public String getChangePasswordPage(Model model){
        return "changePassword";
    }

    @PostMapping("/changePassword")
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
