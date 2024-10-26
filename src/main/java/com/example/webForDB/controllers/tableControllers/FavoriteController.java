package com.example.webForDB.controllers.tableControllers;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.modelsEdit.FavoriteEdit;
import com.example.webForDB.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FavoriteController {
    private FavoriteService service;
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public FavoriteController(FavoriteService service, DBConnectHelper dbConnectHelper) {
        this.service = service;
        this.dbConnectHelper = dbConnectHelper;
    }

    @GetMapping("choose_table/favorite_list")
    public String showFavoriteList(Model model) {
        if (dbConnectHelper.checkConnection()) {
            List<FavoriteEdit> favorites = service.findAllFavorites();
            model.addAttribute("favorites", favorites);
            return "tables/edit/favorite_list";
        }

        return "redirect:/login";
    }
}
