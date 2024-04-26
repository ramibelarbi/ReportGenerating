package com.reports.report.Service;

import com.reports.report.DTO.ReportResponse;
import com.reports.report.DTO.SocieteResponse;
import com.reports.report.Exception.ReportException;
import com.reports.report.Model.EmployeeReport;
import com.reports.report.Model.Societe;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportGenerationService {

    private final SocieteService societeService;

    @Autowired
    public ReportGenerationService(SocieteService societeService) {
        this.societeService = societeService;
    }

    public ReportResponse generateEmployeeReport(Long societeId) throws ReportException, JRException {
        System.out.println("Generating employee report for societeId: " + societeId);
        SocieteResponse societe = societeService.getSocieteById(societeId);
        System.out.println("Retrieved societe: " + societe.getName());
        System.out.println("Employer response list size: " + societe.getEmployerResponseList().size());
        EmployeeReport employeeReport = new EmployeeReport(societe);
        System.out.println("Generating JasperPrint...");
        JasperPrint jasperPrint = employeeReport.getReport();
        System.out.println("JasperPrint generated successfully.");
        byte[] reportData = ReportGenerationService.getReportPdf(jasperPrint); // Call the static method
        System.out.println("Report PDF generated successfully.");
        return new ReportResponse(reportData);
    }



    public static byte[] getReportPdf(final JasperPrint jasperPrint) throws ReportException {
        try {
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            throw new ReportException(e);
        }
    }

}
