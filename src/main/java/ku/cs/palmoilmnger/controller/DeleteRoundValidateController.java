package ku.cs.palmoilmnger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class DeleteRoundValidateController {

    @RequestMapping("/menu/round/ConfirmDeleteRound")
    public String getDeleteRoundValidatePage(Model model) {
        model.addAttribute("username", "ชื่อผู้ใช้");

        model.addAttribute("yearDelete", "2023");
        model.addAttribute("monthDelete", "พฤษภาคม");
        model.addAttribute("roundDelete", "1");
        return "deleteRoundValidate";
    }
}
