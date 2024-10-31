package com.example.webForDB.controllers.tableControllers;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.modelsEdit.MqttMessageUnitEdit;
import com.example.webForDB.services.MqttMessageUnitService;
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
        if (dbConnectHelper.checkConnection()) {
            List<MqttMessageUnitEdit> messageUnits = service.findAllMqttMessagesUnits();
            model.addAttribute("messageUnits", messageUnits);
            return "tables/edit/mqtt_message_unit_list";
        }

        return "redirect:/login";
    }
}
