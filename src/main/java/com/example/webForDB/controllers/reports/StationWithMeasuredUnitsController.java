package com.example.webForDB.controllers.reports;

import com.example.webForDB.services.reports.StationWithMeasuredUnitsService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class StationWithMeasuredUnitsController {
    private StationWithMeasuredUnitsService service;

    @Autowired
    public StationWithMeasuredUnitsController(StationWithMeasuredUnitsService service) {
        this.service = service;
    }

    @ResponseBody
    @GetMapping("/choose_option/choose_report/station_with_measured_units_report")
    public void showPdf(HttpServletResponse response) throws JRException, IOException {
        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "inline; filename=report.pdf";
        response.setHeader(headerKey, headerValue);

        service.exportJasperReport(response);
    }

    @ResponseBody
    @GetMapping("/choose_option/choose_report/station_with_measured_units_report/export")
    public void createPdf(HttpServletResponse response) throws JRException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        service.exportJasperReport(response);
    }

    @GetMapping("/choose_option/choose_report/station_with_measured_units_powerbi_report")
    public String getPowerBiView1() {
        return "reports/powerbi_view1";
    }
}
