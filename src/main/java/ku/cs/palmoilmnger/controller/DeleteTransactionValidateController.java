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

//    @GetMapping("/menu/round/manageRound/palm/ConfirmDeleteTransaction/{idTransaction}")
//    public String getDeletePalmTransactionValidatePage(Model model, @PathVariable String idTransaction) {
//        Transaction transaction = transactionService.getTransactionById(idTransaction);
//        WorkRound workRound = transaction.getWorkRound();
//
//        model.addAttribute("idDelete", transaction.getIdTransaction());
//        model.addAttribute("typeDelete", transaction.getDescription().getName());
//        model.addAttribute("idWorkRound", workRound.getIdWorkRound());
//        return "deletePalmTransactionValidate";
//    }
//
//    @GetMapping("/menu/round/manageRound/fertilize/ConfirmDeleteTransaction/{idTransaction}")
//    public String getDeleteFertilizeTransactionValidatePage(Model model, @PathVariable String idTransaction) {
//        Transaction transaction = transactionService.getTransactionById(idTransaction);
//        WorkRound workRound = transaction.getWorkRound();
//
//        model.addAttribute("idDelete", transaction.getIdTransaction());
//        model.addAttribute("typeDelete", transaction.getDescription().getName());
//        model.addAttribute("idWorkRound", workRound.getIdWorkRound());
//        return "deleteFertilizeTransactionValidate";
//    }
//
//    @GetMapping("/menu/round/manageRound/foliage/ConfirmDeleteTransaction/{idTransaction}")
//    public String getDeleteFoliageTransactionValidatePage(Model model, @PathVariable String idTransaction) {
//        Transaction transaction = transactionService.getTransactionById(idTransaction);
//        WorkRound workRound = transaction.getWorkRound();
//
//        model.addAttribute("idDelete", transaction.getIdTransaction());
//        model.addAttribute("typeDelete", transaction.getDescription().getName());
//        model.addAttribute("idWorkRound", workRound.getIdWorkRound());
//        return "deleteFoliageTransactionValidate";
//    }

    @GetMapping("/menu/round/manageRound/{workTypeName}/ConfirmDeleteTransaction/{idTransaction}")
    public String getDeleteTransactionValidatePage(Model model, @PathVariable(value = "idTransaction") String idTransaction, @PathVariable(value = "workTypeName") String workTypeName) {
        String webPageName = switch (workTypeName) {
            case "palm" -> "deletePalmTransactionValidate";
            case "fertilize" -> "deleteFertilizeTransactionValidate";
            case "foliage" -> "deleteFoliageTransactionValidate";
            default -> "";
        };
        Transaction transaction = transactionService.getTransactionById(idTransaction);
        WorkRound workRound = transaction.getWorkRound();

        model.addAttribute("idDelete", transaction.getIdTransaction());
        model.addAttribute("typeDelete", transaction.getDescription().getName());
        model.addAttribute("idWorkRound", workRound.getIdWorkRound());
        return webPageName;
    }

//    @PostMapping("/menu/round/manageRound/palm/ConfirmDeleteTransaction/{idTransaction}")
//    public String deletePalmTransactionHandler(Model model, @PathVariable String idTransaction){
//        Transaction transaction = transactionService.getTransactionById(idTransaction);
//        WorkRound workRound = transaction.getWorkRound();
//        transactionService.deleteTransaction(transaction);
//        System.out.println(workRound.getIdWorkRound());
//        return "redirect:/manager/menu/round/manageRound/palm/" + workRound.getIdWorkRound() + "?delete";
//    }
//
//    @PostMapping("/menu/round/manageRound/fertilize/ConfirmDeleteTransaction/{idTransaction}")
//    public String deleteFertilizeTransactionHandler(Model model, @PathVariable String idTransaction){
//        Transaction transaction = transactionService.getTransactionById(idTransaction);
//        WorkRound workRound = transaction.getWorkRound();
//        transactionService.deleteTransaction(transaction);
//        System.out.println(workRound.getIdWorkRound());
//        return "redirect:/manager/menu/round/manageRound/fertilize/" + workRound.getIdWorkRound() + "?delete";
//    }
//
//    @PostMapping("/menu/round/manageRound/foliage/ConfirmDeleteTransaction/{idTransaction}")
//    public String deleteFoliageTransactionHandler(Model model, @PathVariable String idTransaction){
//        Transaction transaction = transactionService.getTransactionById(idTransaction);
//        WorkRound workRound = transaction.getWorkRound();
//        transactionService.deleteTransaction(transaction);
//        System.out.println(workRound.getIdWorkRound());
//        return "redirect:/manager/menu/round/manageRound/foliage/" + workRound.getIdWorkRound() + "?delete";
//    }

    @PostMapping("/menu/round/manageRound/{workTypeName}/ConfirmDeleteTransaction/{idTransaction}")
    public String deleteFoliageTransactionHandler(Model model, @PathVariable(value = "idTransaction") String idTransaction, @PathVariable(value = "workTypeName") String workTypeName){
        Transaction transaction = transactionService.getTransactionById(idTransaction);
        WorkRound workRound = transaction.getWorkRound();
        transactionService.deleteTransaction(transaction);
        System.out.println(workRound.getIdWorkRound());
        return "redirect:/manager/menu/round/manageRound/"+workTypeName+"/" + workRound.getIdWorkRound() + "?delete";
    }
}
