package id.ac.ui.cs.eaap.lab.controller;

import id.ac.ui.cs.eaap.lab.model.CovidCaseModel;
import id.ac.ui.cs.eaap.lab.model.LastContactModel;
import id.ac.ui.cs.eaap.lab.service.CovidTrackerService;
import id.ac.ui.cs.eaap.lab.service.LastContactService;
import id.ac.ui.cs.eaap.lab.service.ListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Calendar;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/covid")
public class CovidTrackingController {

    private final CovidTrackerService covidTrackerService;
    private final LastContactService lastContactService;
    private final ListService listService;

    public CovidTrackingController(CovidTrackerService covidTrackerService, LastContactService lastContactService, ListService listService) {
        this.covidTrackerService = covidTrackerService;
        this.lastContactService = lastContactService;
        this.listService = listService;
    }

    @GetMapping(value = "/")
    public String getAll(Model model) {
        log.info("access home");
        return "home";
    }


    @GetMapping("/add")
    public String addPatientFormPage(Model model) {

        CovidCaseModel covidCaseModel = new CovidCaseModel();
        // set default value to TODAY
        covidCaseModel.setTanggalGejalaPertama(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

        model.addAttribute("covidCaseModel", covidCaseModel);
        model.addAttribute("listService", listService);

        return "case/form-add-covid-case";
    }

    @PostMapping(value = "/add", params = {"save"})
    public String addPatientSubmitPage(@ModelAttribute CovidCaseModel covidCaseModel, BindingResult result,
                                       RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("error", "The error occurred.");
            return "redirect:/covid/add";
        }

        covidTrackerService.add(covidCaseModel);
        redirectAttrs.addFlashAttribute("success",
                String.format("Kasus baru berhasil disimpan sebagai id %d", covidCaseModel.getCaseId()));
        return "redirect:/covid/view-all";
    }

    @GetMapping("/view-all")
    public String viewAllCovidCase(Model model) {
        List<CovidCaseModel> covidCaseModelList = covidTrackerService.findAll();
        model.addAttribute("caseList", covidCaseModelList);
        return "case/view-all-covid-case";
    }

    @GetMapping(value = "/update/{id}")
    public String updateStatus(@PathVariable Long id, Model model) {
        CovidCaseModel covidCaseModel = covidTrackerService.findById(id);
        model.addAttribute("covidCaseModel", covidCaseModel);
        model.addAttribute("listService", listService);

        return "case/form-update-status-covid";
    }

    @PostMapping(value = "/update", params = {"save"})
    public String updateStatus(@ModelAttribute CovidCaseModel covidCaseModel, BindingResult result,
                               RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("error", "The error occurred.");
            return "redirect:/covid/view-all";
        }

        covidTrackerService.update(covidCaseModel);

        redirectAttrs.addFlashAttribute("success",
                "Status berhasil di update");
        return "redirect:/covid/view-all";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model, RedirectAttributes redirectAttrs) {
        CovidCaseModel covidCaseModel = covidTrackerService.findById(id);
        model.addAttribute("covidCaseModel", covidCaseModel);

        List<LastContactModel> lastContactModelList = lastContactService.getLastContactByRoom(covidCaseModel);
        model.addAttribute("lastContactModelList", lastContactModelList);

        LastContactModel lastContactModel = new LastContactModel();
        lastContactModel.setCovidCaseModel(covidCaseModel);

        model.addAttribute("lastContactModel", lastContactModel);
        model.addAttribute("listService", listService);

        return "case/detail";
    }

    @GetMapping("/search")
    public String searchByName(@RequestParam(name = "nama") String name, Model model, RedirectAttributes redirectAttrs) {
        var listCovid = covidTrackerService.findByName(name);
        model.addAttribute("caseList", listCovid);

        return "case/view-all-covid-case";
    }
}
