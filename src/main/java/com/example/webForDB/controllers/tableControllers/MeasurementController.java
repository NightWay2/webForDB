package com.example.webForDB.controllers.tableControllers;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.modelsEdit.MeasurementEdit;
import com.example.webForDB.services.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MeasurementController {
    private MeasurementService service;
    private DBConnectHelper dbConnectHelper;

    private static final int RECORDS_PER_PAGE = 1_000;
    volatile static boolean isMethodReady = true;

    @Autowired
    public MeasurementController(MeasurementService service, DBConnectHelper dbConnectHelper) {
        this.service = service;
        this.dbConnectHelper = dbConnectHelper;
    }

    /*@GetMapping("/choose_table/measurment_list") // todo del
    public String showCategoryList(Model model) {
        if (dbConnectHelper.checkConnection()) {
            List<Measurement> measurements = measurementService.findAllMeasurements();
            model.addAttribute("measurements", measurements);
            return "measurment_list";
        }
        return "redirect:/login";
    }*/

    /*@GetMapping("/choose_table/measurment_list")
    public String showCategoryList(Model model) {
        if (dbConnectHelper.checkConnection()) {
            List<MeasurementClear> measurements = measurementService.findAllClear();
            model.addAttribute("measurements", measurements);
            return "withoutId/measurment_list_clear";
        }
        return "redirect:/login";
    }*/

    /*@GetMapping("/choose_table/measurment_list/{pageNum}")
    public String showMeasurementList(@PathVariable("pageNum") int pageNum, Model model) {
        if (dbConnectHelper.checkConnection()) {
            int totalRecords = service.countAllMeasurements();
            int totalPages = (int) Math.ceil((double) totalRecords / RECORDS_PER_PAGE);

            int offset = (pageNum - 1) * RECORDS_PER_PAGE;

            List<MeasurementEdit> measurements = service.findMeasurementsWithPagination(offset, RECORDS_PER_PAGE);

            model.addAttribute("measurements", measurements);
            model.addAttribute("currentPage", pageNum);
            model.addAttribute("totalPages", totalPages);

            return "tables/edit/measurment_list";
        }
        return "redirect:/login";
    }*/

    @GetMapping("/choose_table/measurment_list/{pageNum}")
    public String showMeasurementList(@PathVariable("pageNum") int pageNum, Model model) {
        if (isMethodReady) {
            isMethodReady = false;
            if (dbConnectHelper.checkConnection()) {
                int totalRecords = service.countAllMeasurements();
                int totalPages = (int) Math.ceil((double) totalRecords / RECORDS_PER_PAGE);

                int offset = (pageNum - 1) * RECORDS_PER_PAGE;

                List<MeasurementEdit> measurements = service.findMeasurementsWithPagination(offset, RECORDS_PER_PAGE);

                model.addAttribute("measurements", measurements);
                model.addAttribute("currentPage", pageNum);
                model.addAttribute("totalPages", totalPages);

                isMethodReady = true;
                return "tables/edit/measurment_list";
            }
            isMethodReady = true;
            return "redirect:/login";
        }
        return "redirect:/choose_table/measurment_list/" + (pageNum);
    }
}
