package id.ac.ui.cs.eaap.lab.service;


import id.ac.ui.cs.eaap.lab.model.CovidCaseModel;
import id.ac.ui.cs.eaap.lab.model.FacultyModel;
import id.ac.ui.cs.eaap.lab.repository.CovidCaseDb;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class CovidTrackerService {

    private final CovidCaseDb covidCaseDb;
    private final ListService listService;

    public CovidTrackerService(CovidCaseDb covidCaseDb, ListService listService) {
        this.covidCaseDb = covidCaseDb;
        this.listService = listService;
    }

    public List<CovidCaseModel> findAll() {
        return covidCaseDb.findAll();
    }

    public CovidCaseModel findById(Long id) {
        return covidCaseDb.findById(id).orElse(null);
    }

    public List<CovidCaseModel> findActiveCases() {
        return new ArrayList<>();
    }

    public void add(CovidCaseModel covidCaseModel) {
        covidCaseDb.save(covidCaseModel);
    }

    public void update(CovidCaseModel covidCaseModel) {
        covidCaseDb.save(covidCaseModel);
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

    public List<CovidCaseModel> findByName(String name) {
        List<CovidCaseModel> allCovidCase = covidCaseDb.findAll();
        List<CovidCaseModel> result = new ArrayList<>();

        for (CovidCaseModel caseCovid : allCovidCase) {
            if(caseCovid.getNama().contains(name)) {
                result.add(caseCovid);
            }
        }
        return result;
    }

    public List<CovidCaseModel> getActiveCovidCase() {
        List<CovidCaseModel> allCovidCase = covidCaseDb.findAll();
        List<CovidCaseModel> covidCaseActive = new ArrayList<>();
        for(CovidCaseModel covidCase : allCovidCase) {
            if(!covidCase.getStatus().equals("sembuh")) {
                covidCaseActive.add(covidCase);
            }
        }
        return covidCaseActive;
    }

    public List<FacultyModel> getStatistics() {
        List<FacultyModel> facultyModels = new ArrayList<>();

        for (String faculty : listService.getFakultasOptionsList()) {
            List<CovidCaseModel> listCovidByFaculty = covidCaseDb.findByFakultas(faculty);
            FacultyModel fakultas = new FacultyModel();
            fakultas.setFakultas(faculty);
            fakultas.setJumlahKasus(listCovidByFaculty.size());
            facultyModels.add(fakultas);
        }
        return facultyModels;
    }
}

