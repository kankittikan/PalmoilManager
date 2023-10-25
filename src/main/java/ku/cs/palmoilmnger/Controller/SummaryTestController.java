package ku.cs.palmoilmnger.Controller;

import ku.cs.palmoilmnger.service.summary.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
@RestController
public class SummaryTestController {
    @Autowired
    SummaryService service;

    @GetMapping("/summary/quarter")
    public void getPdf() throws IOException {
        service.export();
    }
}
