package com.reports.report.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Tolerate;

@Entity
@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NamedEntityGraph(name = "Employer.societe", attributeNodes = @NamedAttributeNode("societe"))
public class Employer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer salary;
    private Float commission;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "societe_id")
    private Societe societe;


    @Tolerate
    public Employer(){}
}
