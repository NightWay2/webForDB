package com.example.webForDB.controllers;

import com.example.webForDB.login.DBConnectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChooseOptionController {
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public ChooseOptionController(DBConnectHelper dbConnectHelper) {
        this.dbConnectHelper = dbConnectHelper;
    }

    @GetMapping("/choose_option")
    public String showChooseOption() {
        if (!dbConnectHelper.checkConnection()) {
            return "redirect:/login";
        }
        return "choose_option";
    }
}
