package ku.cs.palmoilmnger.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminDeleteUserValidateController {

    @RequestMapping("/adminMenu/manageUser/deleteUser")
    public String getDeleteUserPage(Model model) {
        model.addAttribute("username", "ชื่อผู้ใช้");
        model.addAttribute("usernameDelete", "เทพซ่า");
        model.addAttribute("fullnameDelete", "พีรสิษฐ์ พลอยอร่าม");
        return "deleteUserValidate";
    }
}
