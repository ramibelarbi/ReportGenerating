package com.reports.report.Controller;
import com.reports.report.DTO.ReportResponse;
import com.reports.report.DTO.SocieteResponse;
import com.reports.report.Exception.ReportException;
import com.reports.report.Service.ReportGenerationService;
import com.reports.report.Service.SocieteService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportGenerationController {

    private final ReportGenerationService reportGenerationService;
    private final SocieteService societeService;

    @Autowired
    public ReportGenerationController(ReportGenerationService reportGenerationService, SocieteService societeService) {
        this.reportGenerationService = reportGenerationService;
        this.societeService = societeService;
    }

    @GetMapping("/{societeId}")
    public ReportResponse generateEmployeeReport(@PathVariable Long societeId) throws ReportException, JRException {
        return reportGenerationService.generateEmployeeReport(societeId);
    }
    @GetMapping(value = "/{societeId}/employeeReport.pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public HttpEntity<byte[]> generateEmployeeReportPdf(@PathVariable Long societeId, HttpServletResponse response) throws ReportException, JRException {
        ReportResponse reportResponse = reportGenerationService.generateEmployeeReport(societeId);
        SocieteResponse societe = societeService.getSocieteById(societeId);
        byte[] reportData = reportResponse.getReportData();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "Report_" + societe.getName() + ".pdf");
        headers.setContentLength(reportData.length);

        return new HttpEntity<>(reportData, headers);
}
}