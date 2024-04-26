package com.reports.report.Model;

import com.reports.report.Exception.ReportException;
import net.sf.jasperreports.engine.JasperPrint;

public interface Report {
    JasperPrint getReport() throws ReportException;
}