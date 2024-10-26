package com.example.webForDB.controllers.tableControllers;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.modelsEdit.StationEdit;
import com.example.webForDB.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StationController {
    private StationService stationService;
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public StationController(StationService stationService, DBConnectHelper dbConnectHelper) {
        this.stationService = stationService;
        this.dbConnectHelper = dbConnectHelper;
    }

    @GetMapping("choose_table/station_list")
    public String showStationList(Model model) {
        if (dbConnectHelper.checkConnection()) {
            List<StationEdit> stations = stationService.findAllStations();
            model.addAttribute("stations", stations);
            return "tables/edit/station_list";
        }

        return "redirect:/login";
    }
}
