package com.reports.report.Controller;

import com.reports.report.DTO.EmployerResponse;
import com.reports.report.Model.Employer;
import com.reports.report.Service.EmployerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/employers")
public class EmployerController {

    private final EmployerService employerService;


    @PostMapping("/add")
    public ResponseEntity<Employer> addEmployer(@RequestBody EmployerResponse request) {
        Employer employer = employerService.addEmployer(request);
        return ResponseEntity.ok(employer);
    }
    @GetMapping()
    public List<EmployerResponse> retrieveAll() {
        return employerService.retrieveAll();
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        employerService.delete(id);
    }

}
