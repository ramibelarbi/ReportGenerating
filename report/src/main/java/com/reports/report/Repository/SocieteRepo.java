package com.reports.report.Repository;

import com.reports.report.Model.Societe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocieteRepo extends JpaRepository<Societe, Long> {
    Optional<Societe> findByName(String name);
}
