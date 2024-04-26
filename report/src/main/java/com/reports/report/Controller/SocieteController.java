package com.reports.report.Controller;

import com.reports.report.DTO.EmployerResponse;
import com.reports.report.DTO.SocieteResponse;
import com.reports.report.Model.Employer;
import com.reports.report.Model.Societe;
import com.reports.report.Service.SocieteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/societe")
public class SocieteController {
    private final SocieteService societeService;
    @PostMapping("/add")
    public ResponseEntity<Societe> addEmployer(@RequestBody SocieteResponse request) {
        Societe societe = societeService.addSociete(request);
        return ResponseEntity.ok(societe);
    }
    @GetMapping()
    public List<SocieteResponse> retrieveAll() {
        return societeService.retrieveAll();
    }
    @GetMapping("/{societeId}")
    public ResponseEntity<SocieteResponse> getSocieteById(@PathVariable Long societeId) {
        try {
            SocieteResponse societe = societeService.getSocieteById(societeId);
            return ResponseEntity.ok(societe);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/logo/{societeId}")
    public ResponseEntity<?> getSocieteLogo(@PathVariable Long societeId) {
        // Fetch the logo data from the service
        byte[] logoData = societeService.getSocieteLogo(societeId);

        // If logoData is null, return a 404 Not Found response
        if (logoData == null) {
            return ResponseEntity.notFound().build();
        }
        ByteArrayResource resource = new ByteArrayResource(logoData);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG) // Assuming the logo is in PNG format
                .contentLength(logoData.length)
                .body(resource);
    }
    @GetMapping("/signature/{societeId}")
    public ResponseEntity<?> getSocieteSignature(@PathVariable Long societeId) {
        // Fetch the logo data from the service
        byte[] logoData = societeService.getSocieteSignature(societeId);

        // If logoData is null, return a 404 Not Found response
        if (logoData == null) {
            return ResponseEntity.notFound().build();
        }

        // Return the logo data in the response with appropriate content type
        ByteArrayResource resource = new ByteArrayResource(logoData);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG) // Assuming the logo is in PNG format
                .contentLength(logoData.length)
                .body(resource);
    }
}
