package id.ac.ui.cs.eaap.lab.controller;

import id.ac.ui.cs.eaap.lab.model.CovidCaseModel;
import id.ac.ui.cs.eaap.lab.model.LastContactModel;
import id.ac.ui.cs.eaap.lab.service.LastContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Slf4j
@Controller
public class LastContactController {
    private final LastContactService lastContactService;

    public LastContactController(LastContactService lastContactService) {
        this.lastContactService = lastContactService;
    }

    @PostMapping(value = "/last/add", params = {"save"})
    public String addLastContact(@ModelAttribute LastContactModel lastContactModel, BindingResult result,
                                 RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("error", "The error occurred.");
            return "redirect:/lapor/view-all";
        }

        log.info(lastContactModel.toString());
        lastContactService.add(lastContactModel);
        log.info("after save\n{}", lastContactModel);

        CovidCaseModel covidCaseModel = lastContactModel.getCovidCaseModel();

        redirectAttrs.addFlashAttribute("success",
                String.format("Kasus berhasil disimpan dengan id %d", lastContactModel.getCaseContactId()));
        return "redirect:/covid/detail/" + covidCaseModel.getCaseId();
    }
}
