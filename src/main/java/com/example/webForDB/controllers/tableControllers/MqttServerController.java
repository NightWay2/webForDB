package com.example.webForDB.controllers.tableControllers;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.tables.MqttServer;
import com.example.webForDB.services.tables.MqttServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MqttServerController {
    private MqttServerService service;
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public MqttServerController(MqttServerService service, DBConnectHelper dbConnectHelper) {
        this.service = service;
        this.dbConnectHelper = dbConnectHelper;
    }

    @GetMapping("choose_option/choose_table/mqtt_server_list")
    public String showMqttServerList(Model model) {
        if (!dbConnectHelper.checkConnection()) {
            return "redirect:/login";
        }
        List<MqttServer> mqttServers = service.findAllMqttServers();
        model.addAttribute("mqttServers", mqttServers);
        return "tables/mqtt_server_list";
    }
}
