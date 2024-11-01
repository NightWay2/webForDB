package com.example.webForDB.controllers.reports;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.services.reports.MeasurementBetweenTimeLimitsService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MeasurementBetweenTimeLimitsController {
    private MeasurementBetweenTimeLimitsService service;
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public MeasurementBetweenTimeLimitsController(MeasurementBetweenTimeLimitsService service, DBConnectHelper dbConnectHelper) {
        this.service = service;
        this.dbConnectHelper = dbConnectHelper;
    }

    @GetMapping("/choose_option/choose_report/measurement_between_time_limits")
    public String showForm(Model model) {
        if (dbConnectHelper.checkConnection()) {
            model.addAttribute("stations", service.findAllStations());
            return "reports/measurement_between_time_limits";
        }

        return "redirect:/login";
    }

    @ResponseBody
    @PostMapping("/choose_option/choose_report/measurement_between_time_limits_report")
    public void showPdf(@RequestParam("station") String stationId,
                                                      @RequestParam("startTime") String startTime,
                                                      @RequestParam("endTime") String endTime,
                                                      HttpServletResponse response) throws IOException, JRException {

        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "inline; filename=report.pdf";
        response.setHeader(headerKey, headerValue);

        service.exportJasperReport(response, stationId, startTime, endTime);
    }

    @ResponseBody
    @PostMapping("/choose_option/choose_report/measurement_between_time_limits_report/export")
    public void exportPdf(@RequestParam("station") String stationId,
                        @RequestParam("startTime") String startTime,
                        @RequestParam("endTime") String endTime,
                        HttpServletResponse response) throws IOException, JRException {

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        service.exportJasperReport(response, stationId, startTime, endTime);
    }
}
