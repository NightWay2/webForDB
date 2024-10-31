package com.example.webForDB.controllers.reports;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.reports.StationWithMeasuredUnits;
import com.example.webForDB.services.reports.StationWithMeasuredUnitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class StationWithMeasuredUnitsController {
    private StationWithMeasuredUnitsService service;
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public StationWithMeasuredUnitsController(StationWithMeasuredUnitsService service, DBConnectHelper dbConnectHelper) {
        this.service = service;
        this.dbConnectHelper = dbConnectHelper;
    }

    @GetMapping("/choose_option/choose_report/report")
    public List<StationWithMeasuredUnits> showList() {
        return service.findAllFields();
    }

    /*@GetMapping("/choose_option/choose_report/report/export")
    public void createPDF(HttpServletResponse response) throws JRException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        service.exportJasperReport(response);
    }*/
}
