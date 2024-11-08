package com.example.webForDB.controllers.reports;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.services.reports.UniversalReportService;
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

@Controller
public class UniversalReportController {
    private UniversalReportService service;
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public UniversalReportController(UniversalReportService service, DBConnectHelper dbConnectHelper) {
        this.service = service;
        this.dbConnectHelper = dbConnectHelper;
    }

    @GetMapping("/choose_option/choose_report/report1")
    public String showFormForReport1() {
        if (!dbConnectHelper.checkConnection()) {
            return "redirect:/login";
        }

        return "reports/report1";
    }

    @ResponseBody
    @PostMapping("/choose_option/choose_report/report1/view")
    public void showPdf1(@RequestParam("startTime") String startTime,
                        @RequestParam("endTime") String endTime,
                        HttpServletResponse response) throws IOException, JRException {

        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "inline; filename=report.pdf";
        response.setHeader(headerKey, headerValue);

        service.exportJasperReport1(response, startTime, endTime);
    }

    @GetMapping("/choose_option/choose_report/report2")
    public String showFormForReport2(Model model) {
        if (dbConnectHelper.checkConnection()) {
            model.addAttribute("stations", service.findAllStationsForReport(2));
            return "reports/report2";
        }

        return "redirect:/login";
    }

    @ResponseBody
    @PostMapping("/choose_option/choose_report/report2/view")
    public void showPdf2(@RequestParam("station") String stationId, HttpServletResponse response)
            throws IOException, JRException {

        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "inline; filename=report.pdf";
        response.setHeader(headerKey, headerValue);

        service.exportJasperReport234(response, stationId, 2);
    }

    @GetMapping("/choose_option/choose_report/report3")
    public String showFormForReport3(Model model) {
        if (dbConnectHelper.checkConnection()) {
            model.addAttribute("stations", service.findAllStationsForReport(3));
            return "reports/report3";
        }

        return "redirect:/login";
    }

    @ResponseBody
    @PostMapping("/choose_option/choose_report/report3/view")
    public void showPdf3(@RequestParam("station") String stationId, HttpServletResponse response)
            throws IOException, JRException {

        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "inline; filename=report.pdf";
        response.setHeader(headerKey, headerValue);

        service.exportJasperReport234(response, stationId, 3);
    }

    @GetMapping("/choose_option/choose_report/report4")
    public String showFormForReport4(Model model) {
        if (dbConnectHelper.checkConnection()) {
            model.addAttribute("stations", service.findAllStationsForReport(4));
            return "reports/report4";
        }

        return "redirect:/login";
    }

    @ResponseBody
    @PostMapping("/choose_option/choose_report/report4/view")
    public void showPdf4(@RequestParam("station") String stationId, HttpServletResponse response)
            throws IOException, JRException {

        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "inline; filename=report.pdf";
        response.setHeader(headerKey, headerValue);

        service.exportJasperReport234(response, stationId, 4);
    }
}
