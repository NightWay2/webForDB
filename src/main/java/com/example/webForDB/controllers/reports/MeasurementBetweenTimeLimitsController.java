package com.example.webForDB.controllers.reports;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.reports.MeasurementBetweenTimeLimits;
import com.example.webForDB.services.reports.MeasurementBetweenTimeLimitsService;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.type.descriptor.java.CharacterArrayJavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.CharArrayWrapperSequence;

import java.time.LocalDateTime;
import java.util.List;

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
    public List<MeasurementBetweenTimeLimits> showPdf(@RequestParam("station") String stationId,
                                                      @RequestParam("startTime") String startTime,
                                                      @RequestParam("endTime") String endTime,
                                                      HttpServletResponse response) {

        LocalDateTime startDateTime = LocalDateTime.parse(startTime);
        LocalDateTime endDateTime = LocalDateTime.parse(endTime);

        String stationName = service.findStationNameById(stationId);

        return service.findAllFields(stationId, startDateTime, endDateTime);
    }

    /*@ResponseBody
    @PostMapping("/choose_option/choose_report/measurement_between_time_limits_report")
    public void showPdf(@RequestParam("station") int stationId,
                        @RequestParam("startTime") String startTime,
                        @RequestParam("endTime") String endTime,
                        HttpServletResponse response) {

        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "inline; filename=report.pdf";
        response.setHeader(headerKey, headerValue);

        LocalDateTime startDateTime = LocalDateTime.parse(startTime);
        LocalDateTime endDateTime = LocalDateTime.parse(endTime);

        service.exportJasperReport(response, stationId, startDateTime, endDateTime);
    }*/
}
