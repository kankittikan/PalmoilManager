package ku.cs.palmoilmnger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class ImageTransactionController {

    @RequestMapping("/menu/round/manageRound/palm/image")
    public String getPalmImageTransactionPage(Model model) {
        model.addAttribute("username", "ชื่อผู้ใช้");

        model.addAttribute("plotName", "พลุดินนา");
        model.addAttribute("year", "2023");
        model.addAttribute("month", "พฤษภาคม");
        model.addAttribute("round", "รอบ "+"1");
        model.addAttribute("ImageName", "");
        return "palmImageTransaction";
    }

    @RequestMapping("/menu/round/manageRound/fertilize/image")
    public String getFertilizeImageTransactionPage(Model model) {
        model.addAttribute("username", "ชื่อผู้ใช้");

        model.addAttribute("plotName", "พลุดินนา");
        model.addAttribute("year", "2023");
        model.addAttribute("month", "พฤษภาคม");
        model.addAttribute("round", "รอบ "+"1");
        model.addAttribute("ImageName", "");
        return "fertilizeImageTransaction";
    }

    @RequestMapping("/menu/round/manageRound/foliage/image")
    public String getFoliageImageTransactionPage(Model model) {
        model.addAttribute("username", "ชื่อผู้ใช้");

        model.addAttribute("plotName", "พลุดินนา");
        model.addAttribute("year", "2023");
        model.addAttribute("month", "พฤษภาคม");
        model.addAttribute("round", "รอบ "+"1");
        model.addAttribute("ImageName", "");
        return "foliageImageTransaction";
    }
}
