package id.ac.ui.cs.eaap.lab.service;

import id.ac.ui.cs.eaap.lab.model.CovidCaseModel;
import id.ac.ui.cs.eaap.lab.model.LastContactModel;
import id.ac.ui.cs.eaap.lab.repository.LastContactDb;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LastContactService {
    private final LastContactDb lastContactDb;

    public LastContactService(LastContactDb lastContactDb) {
        this.lastContactDb = lastContactDb;
    }
    public List<LastContactModel> getLastContactByRoom(CovidCaseModel covidCaseModel) {
        List<LastContactModel> allLastContact = lastContactDb.findAll();
        List<LastContactModel> result = new ArrayList<>();
        for(LastContactModel lastContact : allLastContact) {
            if(lastContact.getCovidCaseModel().equals(covidCaseModel)) {
                result.add(lastContact);
            }
        }
        return result;
    }

    public void add(LastContactModel lastContactModel) {
        lastContactDb.save(lastContactModel);
    }
}
