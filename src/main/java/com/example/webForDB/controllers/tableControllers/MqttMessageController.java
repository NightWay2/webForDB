package com.example.webForDB.controllers.tableControllers;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.tables.MqttMessageUnitEdit;
import com.example.webForDB.services.tables.MqttMessageUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MqttMessageController {
    private MqttMessageUnitService service;
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public MqttMessageController(MqttMessageUnitService service, DBConnectHelper dbConnectHelper) {
        this.service = service;
        this.dbConnectHelper = dbConnectHelper;
    }

    @GetMapping("choose_option/choose_table/mqtt_message_unit_list")
    public String showMqttMessageUnitList(Model model) {
        if (!dbConnectHelper.checkConnection()) {
            return "redirect:/login";
        }
        List<MqttMessageUnitEdit> messageUnits = service.findAllMqttMessagesUnits();
        model.addAttribute("messageUnits", messageUnits);
        return "tables/mqtt_message_unit_list";

    }
}
