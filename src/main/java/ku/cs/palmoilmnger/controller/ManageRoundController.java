package ku.cs.palmoilmnger.controller;

import ku.cs.palmoilmnger.entity.Description;
import ku.cs.palmoilmnger.entity.Transaction;
import ku.cs.palmoilmnger.entity.WorkRound;
import ku.cs.palmoilmnger.entity.WorkType;
import ku.cs.palmoilmnger.model.DropdownItemNew;
import ku.cs.palmoilmnger.model.RoundDTO;
import ku.cs.palmoilmnger.model.TransactionFertilize;
import ku.cs.palmoilmnger.model.TransactionSortDTO;
import ku.cs.palmoilmnger.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private DescriptionService descriptionService;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping("/menu/round/manageRound/{round}")
    public String getManageRoundPage(Model model, @PathVariable("round") String round) {

        WorkRound workRound = workRoundService.findById(round);

        RoundDTO roundDTO = workRoundService.transformToRoundDTO(workRound.getIdWorkRound());

        model.addAttribute("idRound", roundDTO.getIdWorkRound());
        model.addAttribute("plotName", workRound.getPlantation().getName());
        model.addAttribute("plotId", workRound.getPlantation().getIdPlantation());
        model.addAttribute("plotYear", roundDTO.getYear());
        model.addAttribute("plotMonth", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
        model.addAttribute("plotRound", "รอบ " + roundDTO.getRound());
        return "manageRound";
    }

    @GetMapping("/menu/round/manageRound/palm/{round}")
    public String getPalmRoundPage(Model model, @PathVariable("round") String round) {
        WorkRound workRound = workRoundService.findById(round);

        RoundDTO roundDTO = workRoundService.transformToRoundDTO(workRound.getIdWorkRound());

        WorkType workType = workTypeService.getWorkType("ตัดปาล์ม");
        List<Transaction> transactionList = transactionService.getTransactionsByWorkType(workType, workRound);
        transactionList.sort(Comparator.comparing(Transaction::getIdTransaction));
        Collections.reverse(transactionList);

        List<String> descriptions = new ArrayList<>();
        for (Description description : descriptionService.getDescriptionsByWorkType(workTypeService.getWorkType("ตัดปาล์ม"))) {
            descriptions.add(description.getName());
        }
        descriptions.add(0, "ทุกชนิด");

        List<String> sort = new ArrayList<>();
        sort.add("เวลาใกล้ -> ไกล");
        sort.add("เวลาไกล -> ใกล้");

        model.addAttribute("idRound", roundDTO.getIdWorkRound());
        model.addAttribute("plotName", workRound.getPlantation().getName());
        model.addAttribute("plotYear", roundDTO.getYear());
        model.addAttribute("plotMonth", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
        model.addAttribute("plotRound", "รอบ " + roundDTO.getRound());
        model.addAttribute("transactionList", transactionList);
        model.addAttribute("descriptions", descriptions);
        model.addAttribute("sorts", sort);
        return "palmRound";
    }

    @PostMapping("/menu/round/manageRound/palm/{round}")
    public String getPalmRoundPageSort(Model model, @PathVariable("round") String round, @ModelAttribute TransactionSortDTO sortDTO) {
        WorkRound workRound = workRoundService.findById(round);

        RoundDTO roundDTO = workRoundService.transformToRoundDTO(workRound.getIdWorkRound());

        WorkType workType = workTypeService.getWorkType("ตัดปาล์ม");
        List<Transaction> transactionList = transactionService.getTransactionsByWorkType(workType, workRound);
        transactionList.sort(Comparator.comparing(Transaction::getIdTransaction));
        if (sortDTO.getSort().equals("เวลาใกล้ -> ไกล")) {
            Collections.reverse(transactionList);
        }

        if (!sortDTO.getType().equals("ทุกชนิด")) {
            transactionList = transactionList.stream().filter(transaction -> transaction.getDescription().getName().equals(sortDTO.getType())).collect(Collectors.toList());
        }

        List<String> descriptions = new ArrayList<>();
        for (Description description : descriptionService.getDescriptionsByWorkType(workTypeService.getWorkType("ตัดปาล์ม"))) {
            descriptions.add(description.getName());
        }
        descriptions.add(0, sortDTO.getType());
        descriptions.add(1, "ทุกชนิด");

        List<String> sort = new ArrayList<>();
        sort.add(0, sortDTO.getSort());
        sort.add("เวลาใกล้ -> ไกล");
        sort.add("เวลาไกล -> ใกล้");

        model.addAttribute("idRound", roundDTO.getIdWorkRound());
        model.addAttribute("plotName", workRound.getPlantation().getName());
        model.addAttribute("plotYear", roundDTO.getYear());
        model.addAttribute("plotMonth", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
        model.addAttribute("plotRound", "รอบ " + roundDTO.getRound());
        model.addAttribute("transactionList", transactionList);
        model.addAttribute("descriptions", descriptions);
        model.addAttribute("sorts", sort);
        return "palmRound";
    }

    @GetMapping("/menu/round/manageRound/fertilize/{round}")
    public String getFertilizeRoundPage(Model model, @PathVariable String round) {
        WorkRound workRound = workRoundService.findById(round);

        RoundDTO roundDTO = workRoundService.transformToRoundDTO(workRound.getIdWorkRound());

        WorkType workType = workTypeService.getWorkType("ใส่ปุ๋ย");
        List<Transaction> transactionList = transactionService.getTransactionsByWorkType(workType, workRound);
        transactionList.sort(Comparator.comparing(Transaction::getIdTransaction));
        Collections.reverse(transactionList);

        List<String> descriptions = new ArrayList<>();
        for (Description description : descriptionService.getDescriptionsByWorkType(workTypeService.getWorkType("ใส่ปุ๋ย"))) {
            descriptions.add(description.getName());
        }
        descriptions.add(0, "ทุกชนิด");

        List<String> sort = new ArrayList<>();
        sort.add("เวลาใกล้ -> ไกล");
        sort.add("เวลาไกล -> ใกล้");

        List<TransactionFertilize> transactionFertilizes = new ArrayList<>();
        for (Transaction t : transactionList) {
            TransactionFertilize transactionFertilize = modelMapper.map(t, TransactionFertilize.class);
            if(t.getDescription().getName().equals("สูตรปุ๋ย")) {
                String value = transactionFertilize.getValue();
                value = value.substring(1, 7);
                transactionFertilize.setValue(value.substring(0, 2) + "-" + value.substring(2, 4) + "-" + value.substring(4, 6));
            }
            transactionFertilizes.add(transactionFertilize);
        }

        model.addAttribute("idRound", roundDTO.getIdWorkRound());
        model.addAttribute("plotName", workRound.getPlantation().getName());
        model.addAttribute("plotYear", roundDTO.getYear());
        model.addAttribute("plotMonth", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
        model.addAttribute("plotRound", "รอบ " + roundDTO.getRound());
        model.addAttribute("transactionList", transactionFertilizes);
        model.addAttribute("descriptions", descriptions);
        model.addAttribute("sorts", sort);
        return "fertilizeRound";
    }

    @PostMapping("/menu/round/manageRound/fertilize/{round}")
    public String getFertilizeRoundPageSort(Model model, @PathVariable String round, @ModelAttribute TransactionSortDTO transactionSortDTO) {
        WorkRound workRound = workRoundService.findById(round);

        RoundDTO roundDTO = workRoundService.transformToRoundDTO(workRound.getIdWorkRound());

        WorkType workType = workTypeService.getWorkType("ใส่ปุ๋ย");
        List<Transaction> transactionList = transactionService.getTransactionsByWorkType(workType, workRound);
        transactionList.sort(Comparator.comparing(Transaction::getIdTransaction));

        if (transactionSortDTO.getSort().equals("เวลาใกล้ -> ไกล")) {
            Collections.reverse(transactionList);
        }

        if (!transactionSortDTO.getType().equals("ทุกชนิด")) {
            transactionList = transactionList.stream().filter(transaction -> transaction.getDescription().getName().equals(transactionSortDTO.getType())).collect(Collectors.toList());
        }

        List<String> descriptions = new ArrayList<>();
        for (Description description : descriptionService.getDescriptionsByWorkType(workTypeService.getWorkType("ใส่ปุ๋ย"))) {
            descriptions.add(description.getName());
        }
        descriptions.add(0, transactionSortDTO.getType());
        descriptions.add(1, "ทุกชนิด");

        List<String> sort = new ArrayList<>();
        sort.add("เวลาใกล้ -> ไกล");
        sort.add("เวลาไกล -> ใกล้");
        sort.add(0, transactionSortDTO.getSort());

        List<TransactionFertilize> transactionFertilizes = new ArrayList<>();
        for (Transaction t : transactionList) {
            TransactionFertilize transactionFertilize = modelMapper.map(t, TransactionFertilize.class);
            if(t.getDescription().getName().equals("สูตรปุ๋ย")) {
                String value = transactionFertilize.getValue();
                value = value.substring(1, 7);
                transactionFertilize.setValue(value.substring(0, 2) + "-" + value.substring(2, 4) + "-" + value.substring(4, 6));
            }
            transactionFertilizes.add(transactionFertilize);
        }

        model.addAttribute("idRound", roundDTO.getIdWorkRound());
        model.addAttribute("plotName", workRound.getPlantation().getName());
        model.addAttribute("plotYear", roundDTO.getYear());
        model.addAttribute("plotMonth", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
        model.addAttribute("plotRound", "รอบ " + roundDTO.getRound());
        model.addAttribute("transactionList", transactionFertilizes);
        model.addAttribute("descriptions", descriptions);
        model.addAttribute("sorts", sort);
        return "fertilizeRound";
    }

    @GetMapping("/menu/round/manageRound/foliage/{round}")
    public String getFoliageRoundPage(Model model, @PathVariable String round) {
        WorkRound workRound = workRoundService.findById(round);

        RoundDTO roundDTO = workRoundService.transformToRoundDTO(workRound.getIdWorkRound());

        WorkType workType = workTypeService.getWorkType("ตัดแต่งทางใบ");
        List<Transaction> transactionList = transactionService.getTransactionsByWorkType(workType, workRound);
        transactionList.sort(Comparator.comparing(Transaction::getIdTransaction));
        Collections.reverse(transactionList);

        List<String> descriptions = new ArrayList<>();
        for (Description description : descriptionService.getDescriptionsByWorkType(workTypeService.getWorkType("ตัดแต่งทางใบ"))) {
            descriptions.add(description.getName());
        }
        descriptions.add(0, "ทุกชนิด");

        List<String> sort = new ArrayList<>();
        sort.add("เวลาใกล้ -> ไกล");
        sort.add("เวลาไกล -> ใกล้");

        model.addAttribute("idRound", roundDTO.getIdWorkRound());
        model.addAttribute("plotName", workRound.getPlantation().getName());
        model.addAttribute("plotYear", roundDTO.getYear());
        model.addAttribute("plotMonth", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
        model.addAttribute("plotRound", "รอบ " + roundDTO.getRound());
        model.addAttribute("transactionList", transactionList);
        model.addAttribute("descriptions", descriptions);
        model.addAttribute("sorts", sort);
        return "foliageRound";
    }

    @PostMapping("/menu/round/manageRound/foliage/{round}")
    public String getFoliageRoundPageSort(Model model, @PathVariable String round, @ModelAttribute TransactionSortDTO transactionSortDTO) {
        WorkRound workRound = workRoundService.findById(round);

        RoundDTO roundDTO = workRoundService.transformToRoundDTO(workRound.getIdWorkRound());

        WorkType workType = workTypeService.getWorkType("ตัดแต่งทางใบ");
        List<Transaction> transactionList = transactionService.getTransactionsByWorkType(workType, workRound);
        transactionList.sort(Comparator.comparing(Transaction::getIdTransaction));

        if (transactionSortDTO.getSort().equals("เวลาใกล้ -> ไกล")) {
            Collections.reverse(transactionList);
        }

        if (!transactionSortDTO.getType().equals("ทุกชนิด")) {
            transactionList = transactionList.stream().filter(transaction -> transaction.getDescription().getName().equals(transactionSortDTO.getType())).collect(Collectors.toList());
        }

        List<String> descriptions = new ArrayList<>();
        for (Description description : descriptionService.getDescriptionsByWorkType(workTypeService.getWorkType("ตัดแต่งทางใบ"))) {
            descriptions.add(description.getName());
        }
        descriptions.add(0, transactionSortDTO.getType());
        descriptions.add(1, "ทุกชนิด");

        List<String> sort = new ArrayList<>();
        sort.add("เวลาใกล้ -> ไกล");
        sort.add("เวลาไกล -> ใกล้");
        sort.add(0, transactionSortDTO.getSort());

        model.addAttribute("idRound", roundDTO.getIdWorkRound());
        model.addAttribute("plotName", workRound.getPlantation().getName());
        model.addAttribute("plotYear", roundDTO.getYear());
        model.addAttribute("plotMonth", dateTimeService.getMonthTextThai(roundDTO.getMonth()));
        model.addAttribute("plotRound", "รอบ " + roundDTO.getRound());
        model.addAttribute("transactionList", transactionList);
        model.addAttribute("descriptions", descriptions);
        model.addAttribute("sorts", sort);
        return "foliageRound";
    }

    @GetMapping("/showImg")
    public String showImg(Model model, @RequestParam String imgName, String from, int id) {
        model.addAttribute("imgPath", "/transactionImg/" + imgName);

        if (from.equals("palm")) model.addAttribute("to", "/manager/menu/round/manageRound/palm/" + id);
        else if (from.equals("fertilize"))
            model.addAttribute("to", "/manager/menu/round/manageRound/fertilize/" + id);
        else if (from.equals("foliage"))
            model.addAttribute("to", "/manager/menu/round/manageRound/foliage/" + id);
        else model.addAttribute("to", "@{/}");

        return "palmImageTransaction";
    }
}
