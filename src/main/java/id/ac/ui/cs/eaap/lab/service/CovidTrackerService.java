package id.ac.ui.cs.eaap.lab.service;


import id.ac.ui.cs.eaap.lab.model.CovidCaseModel;
import id.ac.ui.cs.eaap.lab.repository.CovidCaseDb;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CovidTrackerService {

    private final CovidCaseDb covidCaseDb;

    public CovidTrackerService(CovidCaseDb covidCaseDb) {
        this.covidCaseDb = covidCaseDb;
    }

    public List<CovidCaseModel> findAll() {
        return covidCaseDb.findAll();
    }

    public List<CovidCaseModel> findActiveCases() {
        return new ArrayList<>();
    }

    public void add(CovidCaseModel covidCaseModel) {
        covidCaseDb.save(covidCaseModel);
    }

    public void update(CovidCaseModel covidCaseModel) {
    }

    public List<Long> getCountDate() {
        List<CovidCaseModel> allCase = covidCaseDb.findAll();
        List<Long> result = new ArrayList<>();
        Date date = new Date();

        for(CovidCaseModel covid : allCase) {
            long value = TimeUnit.DAYS.convert(date.getTime() - covid.getTanggalGejalaPertama().getTime(), TimeUnit.MILLISECONDS);
            result.add(value);
        }
        return result;
    }
}

