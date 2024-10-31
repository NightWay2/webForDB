package com.example.webForDB.controllers.tableControllers;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.tables.Category;
import com.example.webForDB.services.tables.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CategoryController {
    private CategoryService service;
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public CategoryController(CategoryService service, DBConnectHelper dbConnectHelper) {
        this.service = service;
        this.dbConnectHelper = dbConnectHelper;
    }

    @GetMapping("choose_option/choose_table/category_list")
    public String showCategoryList(Model model) {
        if (dbConnectHelper.checkConnection()) {
            List<Category> categories = service.findAllCategories();
            model.addAttribute("categories", categories);
            return "tables/category_list";
        }
        return "redirect:/login";
    }
}
