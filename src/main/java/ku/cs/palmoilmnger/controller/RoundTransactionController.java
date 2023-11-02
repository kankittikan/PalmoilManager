package ku.cs.palmoilmnger.controller;

import ku.cs.palmoilmnger.entity.Description;
import ku.cs.palmoilmnger.entity.Transaction;
import ku.cs.palmoilmnger.entity.WorkRound;
import ku.cs.palmoilmnger.exception.TransactionException;
import ku.cs.palmoilmnger.model.RoundDTO;
import ku.cs.palmoilmnger.model.TransactionDTO;
import ku.cs.palmoilmnger.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class RoundTransactionController {

    @Autowired
    private WorkRoundService workRoundService;

    @Autowired
    private DateTimeService dateTimeService;

    @Autowired
    private WorkTypeService workTypeService;

    @Autowired
    private DescriptionService descriptionService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/menu/round/manageRound/palm/create/{round}")
    public String getPalmCreateTransactionPage(Model model, @PathVariable String round) {
        WorkRound workRound = workRoundService.findById(round);
        RoundDTO roundDTO = workRoundService.transformToRoundDTO(workRound.getIdWorkRound());

//        System.out.println(workTypeService.getAllWorkTypes());

        List<Description> descriptions = descriptionService.getDescriptionsByWorkType(workTypeService.getWorkType("ตัดปาล์ม"));
        System.out.println(descriptions);

        model.addAttribute("idRound", roundDTO.getIdWorkRound());
        model.addAttribute("plotName", workRound.getPlantation().getName());
        model.addAttribute("year", roundDTO.getYear());
        model.addAttribute("month", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
        model.addAttribute("round", "รอบ "+roundDTO.getRound());
        model.addAttribute("descriptions", descriptions);
        return "palmCreateTransaction";
    }

    @GetMapping("/menu/round/manageRound/foliage/create/{round}")
    public String getFoliageCreateTransactionPage(Model model, @PathVariable String round) {
        WorkRound workRound = workRoundService.findById(round);
        RoundDTO roundDTO = workRoundService.transformToRoundDTO(workRound.getIdWorkRound());

        System.out.println(workTypeService.getAllWorkTypes());

        List<Description> descriptions = descriptionService.getDescriptionsByWorkType(workTypeService.getWorkType("ตัดแต่งทางใบ"));
        System.out.println(descriptions);

        model.addAttribute("idRound", roundDTO.getIdWorkRound());
        model.addAttribute("plotName", workRound.getPlantation().getName());
        model.addAttribute("year", roundDTO.getYear());
        model.addAttribute("month", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
        model.addAttribute("round", "รอบ "+roundDTO.getRound());
        model.addAttribute("descriptions", descriptions);
        return "foliageCreateTransaction";
    }

    @GetMapping("/menu/round/manageRound/fertilize/create/{round}")
    public String getFertilizeCreateTransactionPage(Model model, @PathVariable String round) {
        WorkRound workRound = workRoundService.findById(round);
        RoundDTO roundDTO = workRoundService.transformToRoundDTO(workRound.getIdWorkRound());

        // System.out.println(workTypeService.getAllWorkTypes());

        List<Description> descriptions = descriptionService.getDescriptionsByWorkType(workTypeService.getWorkType("ใส่ปุ๋ย"));
        System.out.println(descriptions);

        model.addAttribute("idRound", roundDTO.getIdWorkRound());
        model.addAttribute("plotName", workRound.getPlantation().getName());
        model.addAttribute("year", roundDTO.getYear());
        model.addAttribute("month", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
        model.addAttribute("round", "รอบ "+roundDTO.getRound());
        model.addAttribute("descriptions", descriptions);
        return "fertilizeCreateTransaction";
    }

    @PostMapping("/menu/round/manageRound/palm/create/{round}")
    public String addPalmTransactionHandler(RedirectAttributes redirectAttributes, @PathVariable String round, @ModelAttribute TransactionDTO transactionDTO){
        Description description = descriptionService.getDescriptionByName(transactionDTO.getTransactionType());
        WorkRound workRound = workRoundService.findById(round);
        try{
            transactionService.createTransaction(transactionDTO, description, workRound);
        }catch (TransactionException e){
            redirectAttributes.addAttribute("notice", e.getMessage());
            return "redirect:/manager/menu/round/manageRound/palm/create/" + round;
        }

        return "redirect:/manager/menu/round/manageRound/palm/" + round + "?success";
    }

    @PostMapping("/menu/round/manageRound/fertilize/create/{round}")
    public String addFertilizeTransactionHandler(RedirectAttributes redirectAttributes, @PathVariable String round, @ModelAttribute TransactionDTO transactionDTO){
        Description description = descriptionService.getDescriptionByName(transactionDTO.getTransactionType());
        WorkRound workRound = workRoundService.findById(round);
        try{
            transactionService.createTransaction(transactionDTO, description, workRound);
        }catch (TransactionException e){
            redirectAttributes.addAttribute("notice", e.getMessage());
            return "redirect:/manager/menu/round/manageRound/fertilize/create/" + round;
        }

        return "redirect:/manager/menu/round/manageRound/fertilize/" + round + "?success";
    }

    @PostMapping("/menu/round/manageRound/foliage/create/{round}")
    public String addFoliageTransactionHandler(RedirectAttributes redirectAttributes, @PathVariable String round, @ModelAttribute TransactionDTO transactionDTO){
        Description description = descriptionService.getDescriptionByName(transactionDTO.getTransactionType());
        WorkRound workRound = workRoundService.findById(round);
        try{
            transactionService.createTransaction(transactionDTO, description, workRound);
        }catch (TransactionException e){
            redirectAttributes.addAttribute("notice", e.getMessage());
            return "redirect:/manager/menu/round/manageRound/foliage/create/" + round;
        }

        return "redirect:/manager/menu/round/manageRound/foliage/" + round + "?success";
    }
}
