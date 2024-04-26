package com.reports.report.DTO;

import com.reports.report.Model.Societe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocieteResponse {
    private Long id;
    private String name;
    private byte[] logo;
    private byte[] signature;
    private List<EmployerResponse> employerResponseList;

    public SocieteResponse(Societe societe) {
        this.id = societe.getId();
        this.name = societe.getName();
        this.logo = societe.getLogo();
        this.signature = societe.getSignature();
        this.employerResponseList = societe.getEmployers().stream()
                .map(employer -> new EmployerResponse(
                        employer.getId(),
                        employer.getName(),
                        employer.getSalary(),
                        employer.getCommission(),
                        null // Do not pass SocieteResponse here
                ))
                .collect(Collectors.toList());
    }

    public static SocieteResponse fromSociete(Societe societe) {

        return new SocieteResponse(societe);
    }
}
