package com.example.webForDB.controllers.reports;

import com.example.webForDB.models.reports.StationWithMeasuredUnits;
import com.example.webForDB.services.reports.StationWithMeasuredUnitsService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class StationWithMeasuredUnitsController {
    private StationWithMeasuredUnitsService service;

    @Autowired
    public StationWithMeasuredUnitsController(StationWithMeasuredUnitsService service) {
        this.service = service;
    }

    @GetMapping("/choose_option/choose_report/report")
    public List<StationWithMeasuredUnits> findAllFields() {
        return service.findAllFields();
    }

    @GetMapping("/choose_option/choose_report/report/export")
    public void createPdf(HttpServletResponse response) throws JRException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        service.exportJasperReport(response);
    }
}
