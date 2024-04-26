package com.reports.report.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "societe")
@NamedEntityGraph(name = "Societe.employers", attributeNodes = @NamedAttributeNode("employers"))
public class Societe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(length = 100000)
    @Lob
    private byte[] logo;
    @Column(length = 100000)
    @Lob
    private byte[] signature;
    @OneToMany(mappedBy = "societe", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Employer> employers = new ArrayList<>();
}
