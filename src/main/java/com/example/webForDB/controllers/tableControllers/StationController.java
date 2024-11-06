package com.example.webForDB.controllers.tableControllers;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.tables.modelsEdit.StationEdit;
import com.example.webForDB.services.tables.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StationController {
    private StationService service;
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public StationController(StationService service, DBConnectHelper dbConnectHelper) {
        this.service = service;
        this.dbConnectHelper = dbConnectHelper;
    }

    @GetMapping("choose_option/choose_table/station_list")
    public String showStationList(Model model) {
        if (!dbConnectHelper.checkConnection()) {
            return "redirect:/login";
        }
        List<StationEdit> stations = service.findAllStations();
        model.addAttribute("stations", stations);
        return "tables/edit/station_list";
    }
}
