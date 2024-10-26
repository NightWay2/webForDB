package com.example.webForDB.controllers;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.Measurement;
import com.example.webForDB.models.modelsWithoutId.MeasurementClear;
import com.example.webForDB.services.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MeasurementController {
    private MeasurementService measurementService;
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, DBConnectHelper dbConnectHelper) {
        this.measurementService = measurementService;
        this.dbConnectHelper = dbConnectHelper;
    }

    /*@GetMapping("/choose_table/measurment_list")
    public String showCategoryList(Model model) {
        if (dbConnectHelper.checkConnection()) {
            List<Measurement> measurements = measurementService.findAllMeasurements();
            model.addAttribute("measurements", measurements);
            return "measurment_list";
        }
        return "redirect:/login";
    }*/

    @GetMapping("/choose_table/measurment_list")
    public String showCategoryList(Model model) {
        if (dbConnectHelper.checkConnection()) {
            List<MeasurementClear> measurements = measurementService.findAllClear();
            model.addAttribute("measurements", measurements);
            return "withoutId/measurment_list_clear";
        }
        return "redirect:/login";
    }
}
