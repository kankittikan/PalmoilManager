package ku.cs.palmoilmnger.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class DeleteTransactionValidateController {

    @RequestMapping("/menu/round/manageRound/palm/ConfirmDeleteTransaction")
    public String getDeletePalmTransactionValidatePage(Model model) {
        model.addAttribute("username", "ชื่อผู้ใช้");

        model.addAttribute("idDelete", "230501001");
        model.addAttribute("typeDelete", "ค่าจัดการ");
        return "deletePalmTransactionValidate";
    }

    @RequestMapping("/menu/round/manageRound/fertilize/ConfirmDeleteTransaction")
    public String getDeleteFertilizeTransactionValidatePage(Model model) {
        model.addAttribute("username", "ชื่อผู้ใช้");

        model.addAttribute("idDelete", "230501001");
        model.addAttribute("typeDelete", "ค่าจัดการ");
        return "deleteFertilizeTransactionValidate";
    }

    @RequestMapping("/menu/round/manageRound/foliage/ConfirmDeleteTransaction")
    public String getDeleteFoliageTransactionValidatePage(Model model) {
        model.addAttribute("username", "ชื่อผู้ใช้");

        model.addAttribute("idDelete", "230501001");
        model.addAttribute("typeDelete", "ค่าจัดการ");
        return "deleteFoliageTransactionValidate";
    }
}
