package id.ac.ui.cs.eaap.lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "covid_case")
public class CovidCaseModel implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "case_id")
    private long caseId;
    @Column(name = "nik")
    private String nik;
    @Column(name = "nama")
    private String nama;
    @Column(name = "status")
    private String status;
    @Column(name = "tanggalGejalaPertama")
    private Date tanggalGejalaPertama;
    @Column(name = "peran")
    private String peran;
    @Column(name = "fakultas")
    private String fakultas;

    public long getHariSetelahGejalaPertama() {
        java.util.Date date = new java.util.Date();
        long value = TimeUnit.DAYS.convert(date.getTime() - tanggalGejalaPertama.getTime(), TimeUnit.MILLISECONDS);

        return value;
    }

}