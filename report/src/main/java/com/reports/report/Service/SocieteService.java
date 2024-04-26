package com.reports.report.Service;

import com.reports.report.DTO.EmployerResponse;
import com.reports.report.DTO.SocieteResponse;
import com.reports.report.Model.Employer;
import com.reports.report.Model.Societe;
import com.reports.report.Repository.SocieteRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SocieteService {
    private final SocieteRepo societeRepo;

    public Societe addSociete(SocieteResponse request){
        var societe =Societe.builder()
                .name(request.getName())
                .logo(request.getLogo())
                .signature(request.getSignature())
                .build();
        return societeRepo.save(societe);
    }
    public List<SocieteResponse> retrieveAll() {
        List<Societe> societes = societeRepo.findAll();
        List<SocieteResponse> societeResponses = new ArrayList<>();

        for (Societe societe : societes) {
            SocieteResponse societeResponse = new SocieteResponse(societe);
            societeResponses.add(societeResponse);
        }

        return societeResponses;
    }
    public byte[] getSocieteLogo(Long societeId) {
        Optional<Societe> optionalSociete = societeRepo.findById(societeId);
        if (optionalSociete.isPresent()) {
            Societe societe = optionalSociete.get();
            return societe.getLogo();
        }
        return null;
    }
    public byte[] getSocieteSignature(Long societeId) {
        Optional<Societe> optionalSociete = societeRepo.findById(societeId);
        if (optionalSociete.isPresent()) {
            Societe societe = optionalSociete.get();
            return societe.getSignature();
        }
        return null;
    }
    public SocieteResponse getSocieteById(Long id) throws EntityNotFoundException {
        Optional<Societe> societeOptional = societeRepo.findById(id);
        Societe societe = societeOptional.orElseThrow(() -> new EntityNotFoundException("Societe not found with ID: " + id));

        return new SocieteResponse(societe);
    }

}
