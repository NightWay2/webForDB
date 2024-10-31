package com.example.webForDB.controllers.tableControllers;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.tables.Measured_Unit;
import com.example.webForDB.services.tables.MeasuredUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MeasuredUnitController {
    private MeasuredUnitService service;
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public MeasuredUnitController(MeasuredUnitService service, DBConnectHelper dbConnectHelper) {
        this.service = service;
        this.dbConnectHelper = dbConnectHelper;
    }

    @GetMapping("choose_option/choose_table/measured_unit_list")
    public String showMeasuredUnitList(Model model) {
        if (dbConnectHelper.checkConnection()) {
            List<Measured_Unit> measured_units = service.findAllMeasuredUnits();
            model.addAttribute("measured_units", measured_units);
            return "tables/measured_unit_list";
        }

        return "redirect:/login";
    }
}
