package com.reports.report.Service;

import com.reports.report.DTO.EmployerResponse;
import com.reports.report.Model.Employer;
import com.reports.report.Model.Societe;
import com.reports.report.Repository.EmployerRepo;
import com.reports.report.Repository.SocieteRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployerService {
    private final EmployerRepo employerRepo;
    private final SocieteRepo societeRepo;


    public Employer addEmployer(EmployerResponse request) {
        Optional<Societe> societeOptional = societeRepo.findByName(request.getSociete().getName());
        if(societeOptional.isPresent()) {
            Societe societe = societeOptional.get();
        var employer = Employer.builder()
                .name(request.getName())
                .salary(request.getSalary())
                .commission(request.getCommission())
                .societe(societe)
                .build();
        return employerRepo.save(employer);
        } else {
            return null;
        }
    }
    public List<EmployerResponse> retrieveAll() {
        List<Employer> employers = employerRepo.findAll();
        List<EmployerResponse> employerResponses = new ArrayList<>();

        for (Employer employer : employers) {
            EmployerResponse employerResponse = EmployerResponse.builder()
                    .id(employer.getId())
                    .name(employer.getName())
                    .salary(employer.getSalary())
                    .commission(employer.getCommission())
                    .build();
            employerResponses.add(employerResponse);
        }

        return employerResponses;
    }
    @Transactional
    public void delete(Long id) {
        employerRepo.deleteEmployerById(id);
    }


}
