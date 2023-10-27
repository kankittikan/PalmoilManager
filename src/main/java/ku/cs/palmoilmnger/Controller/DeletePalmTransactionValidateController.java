package ku.cs.palmoilmnger.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DeletePalmTransactionValidateController {

    @RequestMapping("/menu/round/manageRound/palm/ConfirmDeleteTransaction")
    public String getDeleteRoundValidatePage(Model model) {
        model.addAttribute("username", "ชื่อผู้ใช้");

        model.addAttribute("idDelete", "230501001");
        model.addAttribute("typeDelete", "ค่าจัดการ");
        return "deletePalmTransactionValidate";
    }
}
