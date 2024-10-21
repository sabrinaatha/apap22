package id.ac.ui.cs.eaap.lab.controller;

import id.ac.ui.cs.eaap.lab.model.CovidCaseModel;
import id.ac.ui.cs.eaap.lab.service.CovidTrackerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    CovidTrackerService covidTrackerService;

    @GetMapping(value = "/active")
    public String viewActiveCovidCase(Model model) {
        List<CovidCaseModel> covidList = covidTrackerService.getActiveCovidCase();
        List<Long> countDate = covidTrackerService.getCountDate();
        model.addAttribute("countDate", countDate);
        model.addAttribute("caseList", covidList);
        return "report/report-active-covid-case";
    }

    @GetMapping(value = "/statistics")
    public String viewStatistics(Model model) {
        return "report/report-statistics";
    }

}
