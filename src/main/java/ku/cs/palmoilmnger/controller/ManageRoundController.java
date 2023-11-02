package ku.cs.palmoilmnger.controller;

import ku.cs.palmoilmnger.entity.Transaction;
import ku.cs.palmoilmnger.entity.WorkRound;
import ku.cs.palmoilmnger.entity.WorkType;
import ku.cs.palmoilmnger.model.RoundDTO;
import ku.cs.palmoilmnger.service.DateTimeService;
import ku.cs.palmoilmnger.service.TransactionService;
import ku.cs.palmoilmnger.service.WorkRoundService;
import ku.cs.palmoilmnger.service.WorkTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManageRoundController {

    @Autowired
    private WorkRoundService workRoundService;

    @Autowired
    private DateTimeService dateTimeService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private WorkTypeService workTypeService;

    @GetMapping("/menu/round/manageRound/{round}")
    public String getManageRoundPage(Model model,@PathVariable("round") String round) {

        WorkRound workRound = workRoundService.findById(round);

        RoundDTO roundDTO = workRoundService.transformToRoundDTO(workRound.getIdWorkRound());

        model.addAttribute("idRound", roundDTO.getIdWorkRound());
        model.addAttribute("plotName", workRound.getPlantation().getName());
        model.addAttribute("plotYear", roundDTO.getYear());
        model.addAttribute("plotMonth", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
        model.addAttribute("plotRound", "รอบ "+roundDTO.getRound());
        return "manageRound";
    }

    @GetMapping("/menu/round/manageRound/{workTypeName}/{round}")
    public String getWorkTypeTransactionPage(Model model, @PathVariable("round") String round, @PathVariable("workTypeName") String workTypeName) {
        String name = "";
        String webPageName = "";
        switch (workTypeName){
            case "palm":
                name = "ตัดปาล์ม";
                webPageName = "palmRound";
                break;
            case "fertilize":
                name = "ใส่ปุ๋ย";
                webPageName = "fertilizeRound";
                break;
            case "foliage":
                name = "ตัดแต่งทางใบ";
                webPageName = "foliageRound";
                break;
        }
        WorkRound workRound = workRoundService.findById(round);

        RoundDTO roundDTO = workRoundService.transformToRoundDTO(workRound.getIdWorkRound());

        WorkType workType = workTypeService.getWorkType(name);
        List<Transaction> transactionList = transactionService.getTransactionsByWorkTypeAndWorkRound(workType, workRound);

        model.addAttribute("idRound", roundDTO.getIdWorkRound());
        model.addAttribute("plotName", workRound.getPlantation().getName());
        model.addAttribute("plotYear", roundDTO.getYear());
        model.addAttribute("plotMonth", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
        model.addAttribute("plotRound", "รอบ "+roundDTO.getRound());
        model.addAttribute("transactionList", transactionList);
        return webPageName;
    }

    @GetMapping("/menu/round/manageRound/{workTypeName}/{round}/{sort}")
    public String getWorkTypeTransactionSortPage(Model model, @PathVariable("round") String round, @PathVariable("workTypeName") String workTypeName, @PathVariable(value = "sort") String sortName) {
        String name = "";
        String webPageName = "";
        switch (workTypeName){
            case "palm":
                name = "ตัดปาล์ม";
                webPageName = "palmRound";
                break;
            case "fertilize":
                name = "ใส่ปุ๋ย";
                webPageName = "fertilizeRound";
                break;
            case "foliage":
                name = "ตัดแต่งทางใบ";
                webPageName = "foliageRound";
                break;
        }
        WorkRound workRound = workRoundService.findById(round);

        RoundDTO roundDTO = workRoundService.transformToRoundDTO(workRound.getIdWorkRound());

        WorkType workType = workTypeService.getWorkType(name);

        List<Transaction> transactionList = switch (sortName) {
            case "sortStart" -> transactionService.getTransactionsBySortIdTransactionByWorkTypeASC(workType, workRound);
            case "sortEnd" -> transactionService.getTransactionsBySortIdTransactionByWorkTypeDESC(workType, workRound);
            default -> transactionService.getTransactionsByWorkTypeAndWorkRound(workType, workRound);
        };

        model.addAttribute("idRound", roundDTO.getIdWorkRound());
        model.addAttribute("plotName", workRound.getPlantation().getName());
        model.addAttribute("plotYear", roundDTO.getYear());
        model.addAttribute("plotMonth", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
        model.addAttribute("plotRound", "รอบ "+roundDTO.getRound());
        model.addAttribute("transactionList", transactionList);
        return webPageName;
    }

//    @GetMapping("/menu/round/manageRound/fertilize/{round}")
//    public String getFertilizeRoundPage(Model model, @PathVariable String round) {
//        WorkRound workRound = workRoundService.findById(round);
//
//        RoundDTO roundDTO = workRoundService.transformToRoundDTO(workRound.getIdWorkRound());
//
//        WorkType workType = workTypeService.getWorkType("ใส่ปุ๋ย");
//        List<Transaction> transactionList = transactionService.getTransactionsByWorkType(workType, workRound);
//
//        model.addAttribute("idRound", roundDTO.getIdWorkRound());
//        model.addAttribute("plotName", workRound.getPlantation().getName());
//        model.addAttribute("plotYear", roundDTO.getYear());
//        model.addAttribute("plotMonth", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
//        model.addAttribute("plotRound", "รอบ "+roundDTO.getRound());
//        model.addAttribute("transactionList", transactionList);
//        return "fertilizeRound";
//    }
//
//    @GetMapping("/menu/round/manageRound/foliage/{round}")
//    public String getFoliageRoundPage(Model model, @PathVariable String round) {
//        WorkRound workRound = workRoundService.findById(round);
//
//        RoundDTO roundDTO = workRoundService.transformToRoundDTO(workRound.getIdWorkRound());
//
//        WorkType workType = workTypeService.getWorkType("ตัดแต่งทางใบ");
//        List<Transaction> transactionList = transactionService.getTransactionsByWorkType(workType, workRound);
//
//        model.addAttribute("idRound", roundDTO.getIdWorkRound());
//        model.addAttribute("plotName", workRound.getPlantation().getName());
//        model.addAttribute("plotYear", roundDTO.getYear());
//        model.addAttribute("plotMonth", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
//        model.addAttribute("plotRound", "รอบ "+roundDTO.getRound());
//        model.addAttribute("transactionList", transactionList);
//        return "foliageRound";
//    }
//    @GetMapping("/menu/round/manageRound/palm/{round}")
//    public String getFoliageRoundPage(Model model, @PathVariable String round) {
//        WorkRound workRound = workRoundService.findById(round);
//
//        RoundDTO roundDTO = workRoundService.transformToRoundDTO(workRound.getIdWorkRound());
//
//        WorkType workType = workTypeService.getWorkType("ตัดปาล์ม");
//        List<Transaction> transactionList = transactionService.getTransactionsByWorkType(workType, workRound);
//
//        model.addAttribute("idRound", roundDTO.getIdWorkRound());
//        model.addAttribute("plotName", workRound.getPlantation().getName());
//        model.addAttribute("plotYear", roundDTO.getYear());
//        model.addAttribute("plotMonth", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
//        model.addAttribute("plotRound", "รอบ "+roundDTO.getRound());
//        model.addAttribute("transactionList", transactionList);
//        return "palmRound";
//    }
}
