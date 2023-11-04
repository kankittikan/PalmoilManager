package ku.cs.palmoilmnger.controller;

import ku.cs.palmoilmnger.exception.PlantationException;
import ku.cs.palmoilmnger.model.SummaryAnnualDTO;
import ku.cs.palmoilmnger.model.SummaryQuarterDTO;
import ku.cs.palmoilmnger.service.DateTimeService;
import ku.cs.palmoilmnger.service.PlantationService;
import ku.cs.palmoilmnger.service.summary.Report;
import ku.cs.palmoilmnger.service.summary.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/manager")
public class SummaryController {
    @Autowired
    private PlantationService plantationService;
    @Autowired
    private DateTimeService dateTimeService;
    @Autowired
    private SummaryService summaryService;

    @GetMapping("/summary/year")
    public String getYearSummaryPage(Model model) {
        model.addAttribute("username", "ผู้ใช้");
        model.addAttribute("plantations", plantationService.getAllPlantation());
        model.addAttribute("years", dateTimeService.getYearListByRange(10));
        return "yearSummary";
    }
    @PostMapping("/summary/year")
    public String getYearPdf(@ModelAttribute SummaryAnnualDTO summaryAnnualDTO) {
        try {
            summaryService.sumUpAnnual(summaryAnnualDTO);
        } catch (IOException | PlantationException e) {
            return "redirect:/manager/summary/quarter?error";
        }
        return "redirect:/pdf/summary.pdf";
    }

    @GetMapping("/summary/quarter")
    public String getQuarterSummaryPage(Model model) {
        model.addAttribute("username", "ผู้ใช้");
        model.addAttribute("plantations", plantationService.getAllPlantation());
        model.addAttribute("years", dateTimeService.getYearListByRange(10));
        model.addAttribute("quarters", new int[]{1, 2, 3, 4});
        return "quarterSummary";
    }
    @PostMapping("/summary/quarter")
    public String getQuarterPdf(@ModelAttribute SummaryQuarterDTO summaryQuarterDTO) {
        try {
            summaryService.sumUpQuarter(summaryQuarterDTO);
        } catch (IOException | PlantationException e) {
            return "redirect:/manager/summary/quarter?error";
        }
        return "redirect:/pdf/summary.pdf";
    }
}
