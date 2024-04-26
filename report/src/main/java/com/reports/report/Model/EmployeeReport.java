package com.reports.report.Model;


import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.*;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.reports.report.DTO.SocieteResponse;
import com.reports.report.Exception.ReportException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class EmployeeReport implements Report{

    private final SocieteResponse societe;

    public EmployeeReport(final SocieteResponse societe) {
        this.societe = societe;
    }

    public JasperPrint getReport() throws ReportException {
        try {
            DynamicReport dynaReport = getReportDesign();
            return DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(),
                    new JRBeanCollectionDataSource(societe.getEmployerResponseList()));
        } catch (JRException | ColumnBuilderException e) {
            throw new ReportException(e);
        }
    }

    private DynamicReport getReportDesign() throws ColumnBuilderException {
        DynamicReportBuilder report = new DynamicReportBuilder();

        // Define styles
        Style headerStyle = createHeaderStyle();
        Style detailTextStyle = createDetailTextStyle();
        Style detailNumStyle = createDetailNumberStyle();

        // Add columns
        AbstractColumn columnEmpNo = createColumn("id", Long.class, "Employee Number", headerStyle, detailTextStyle);
        AbstractColumn columnName = createColumn("name", String.class, "Name", headerStyle, detailTextStyle);
        AbstractColumn columnSalary = createColumn("salary", Integer.class, "Salary", headerStyle, detailNumStyle);
        AbstractColumn columnCommission = createColumn("commission", Float.class, "Commission", headerStyle, detailNumStyle);
        report.addColumn(columnEmpNo).addColumn(columnName).addColumn(columnSalary).addColumn(columnCommission);

        // Title and Subtitle
        StyleBuilder titleStyle = new StyleBuilder(true);
        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        titleStyle.setFont(new Font(20, null, true));

        StyleBuilder subTitleStyle = new StyleBuilder(true);
        subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        subTitleStyle.setFont(new Font(Font.MEDIUM, null, true));

        report.setTitle("Employee Report");
        report.setTitleHeight(10);
        report.setTitleStyle(titleStyle.build());
        report.setSubtitle("Commission received by Employee");
        report.setSubtitleStyle(subTitleStyle.build());
        report.setUseFullPageWidth(true);
        report.setSubtitleHeight(20);

        report.setMargins(10, 10, 10, 10);
        // Add logo at the top left
        byte[] logoData = societe.getLogo();
        if (logoData != null) {
            try {
                File tempFile = File.createTempFile("logo", ".png");
                try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                    fos.write(logoData);
                }
                report.addImageBanner(tempFile.getAbsolutePath(), 150, 50, ImageBanner.Alignment.Left,ImageScaleMode.FILL_PROPORTIONALLY);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Add signature at the bottom right
        byte[] signatureData = societe.getSignature();
        if (signatureData != null) {
            try {
                File tempFile = File.createTempFile("signature", ".png");
                try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                    fos.write(signatureData);
                }
                // Adjust the coordinates and alignment for the bottom-right corner
                report.addFooterImageBanner(tempFile.getAbsolutePath(), 100, 100, ImageBanner.Alignment.Right,ImageScaleMode.FILL);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return report.build();
    }

    private Style createHeaderStyle() {
        return new StyleBuilder(true)
                .setFont(Font.VERDANA_MEDIUM_BOLD)
                .setBorder(Border.THIN())
                .setBorderColor(Color.BLACK)
                .setBackgroundColor(Color.ORANGE)
                .setTextColor(Color.BLACK)
                .setHorizontalAlign(HorizontalAlign.CENTER)
                .setVerticalAlign(VerticalAlign.MIDDLE)
                .setTransparency(Transparency.OPAQUE)
                .build();
    }

    private Style createDetailTextStyle() {
        return new StyleBuilder(true)
                .setFont(Font.VERDANA_MEDIUM)
                .setBorder(Border.THIN())
                .setBorderColor(Color.BLACK)
                .setTextColor(Color.BLACK)
                .setHorizontalAlign(HorizontalAlign.LEFT)
                .setVerticalAlign(VerticalAlign.MIDDLE)
                .setPaddingLeft(5)
                .build();
    }

    private Style createDetailNumberStyle() {
        return new StyleBuilder(true)
                .setFont(Font.VERDANA_MEDIUM)
                .setBorder(Border.THIN())
                .setBorderColor(Color.BLACK)
                .setTextColor(Color.BLACK)
                .setHorizontalAlign(HorizontalAlign.RIGHT)
                .setVerticalAlign(VerticalAlign.MIDDLE)
                .setPaddingRight(5)
                .build();
    }

    private AbstractColumn createColumn(String property, Class<?> type, String title, Style headerStyle, Style detailStyle)
            throws ColumnBuilderException {
        return ColumnBuilder.getNew()
                .setColumnProperty(property, type.getName())
                .setTitle(title)
                .setWidth(30)
                .setStyle(detailStyle)
                .setHeaderStyle(headerStyle)
                .build();
    }
}