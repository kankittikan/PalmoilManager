package ku.cs.palmoilmnger.controller;

import ku.cs.palmoilmnger.entity.Transaction;
import ku.cs.palmoilmnger.entity.WorkRound;
import ku.cs.palmoilmnger.service.TransactionService;
import ku.cs.palmoilmnger.service.WorkRoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class DeleteTransactionValidateController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private WorkRoundService workRoundService;

    @GetMapping("/menu/round/manageRound/palm/ConfirmDeleteTransaction/{idTransaction}")
    public String getDeletePalmTransactionValidatePage(Model model, @PathVariable String idTransaction) {
        Transaction transaction = transactionService.getTransactionById(idTransaction);
        WorkRound workRound = transaction.getWorkRound();

        model.addAttribute("idDelete", transaction.getIdTransaction());
        model.addAttribute("typeDelete", transaction.getDescription().getName());
        model.addAttribute("idWorkRound", workRound.getIdWorkRound());
        return "deletePalmTransactionValidate";
    }

    @GetMapping("/menu/round/manageRound/fertilize/ConfirmDeleteTransaction/{idTransaction}")
    public String getDeleteFertilizeTransactionValidatePage(Model model, @PathVariable String idTransaction) {
        Transaction transaction = transactionService.getTransactionById(idTransaction);
        WorkRound workRound = transaction.getWorkRound();

        model.addAttribute("idDelete", transaction.getIdTransaction());
        model.addAttribute("typeDelete", transaction.getDescription().getName());
        model.addAttribute("idWorkRound", workRound.getIdWorkRound());
        return "deleteFertilizeTransactionValidate";
    }

    @GetMapping("/menu/round/manageRound/foliage/ConfirmDeleteTransaction/{idTransaction}")
    public String getDeleteFoliageTransactionValidatePage(Model model, @PathVariable String idTransaction) {
        Transaction transaction = transactionService.getTransactionById(idTransaction);
        WorkRound workRound = transaction.getWorkRound();

        model.addAttribute("idDelete", transaction.getIdTransaction());
        model.addAttribute("typeDelete", transaction.getDescription().getName());
        model.addAttribute("idWorkRound", workRound.getIdWorkRound());
        return "deleteFoliageTransactionValidate";
    }

    @PostMapping({"/menu/round/manageRound/palm/ConfirmDeleteTransaction/{idTransaction}", "/menu/round/manageRound/fertilize/ConfirmDeleteTransaction/{idTransaction}", "/menu/round/manageRound/foliage/ConfirmDeleteTransaction/{idTransaction}"})
    public String deleteTransactionHandler(Model model, @PathVariable String idTransaction){
        Transaction transaction = transactionService.getTransactionById(idTransaction);
        WorkRound workRound = transaction.getWorkRound();
        transactionService.deleteTransaction(transaction);
        System.out.println(workRound.getIdWorkRound());
        return "redirect:/manager/menu/round/manageRound/palm/" + workRound.getIdWorkRound() + "?delete";
    }
}
