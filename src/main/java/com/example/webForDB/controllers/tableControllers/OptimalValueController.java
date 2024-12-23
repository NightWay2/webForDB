package com.example.webForDB.controllers.tableControllers;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.tables.OptimalValueEdit;
import com.example.webForDB.services.tables.OptimalValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OptimalValueController {
    private OptimalValueService service;
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public OptimalValueController(OptimalValueService service, DBConnectHelper dbConnectHelper) {
        this.service = service;
        this.dbConnectHelper = dbConnectHelper;
    }

    @GetMapping("choose_option/choose_table/optimal_value_list")
    public String showOptimalValueList(Model model) {
        if (!dbConnectHelper.checkConnection()) {
            return "redirect:/login";
        }
        List<OptimalValueEdit> optimalValues = service.findAllOptimalValues();
        model.addAttribute("optimalValues", optimalValues);
        return "tables/optimal_value_list";
    }
}
