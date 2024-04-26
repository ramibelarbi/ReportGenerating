package com.reports.report.DTO;

import com.reports.report.Model.Employer;
import com.reports.report.Model.Societe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployerResponse {
    private Long id;
    private String name;
    private Integer salary;
    private Float commission;
    private SocieteResponse societe;

    public static EmployerResponse fromEmployer(Employer employer) {
        return new EmployerResponse(
                employer.getId(),
                employer.getName(),
                employer.getSalary(),
                employer.getCommission(),
                new SocieteResponse(employer.getSociete())

        );
    }
}
