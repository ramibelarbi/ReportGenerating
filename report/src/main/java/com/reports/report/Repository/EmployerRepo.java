package com.reports.report.Repository;

import com.reports.report.Model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepo extends JpaRepository<Employer, Long> {
    void deleteEmployerById(Long id);
}
