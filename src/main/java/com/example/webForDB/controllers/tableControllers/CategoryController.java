package com.example.webForDB.controllers.tableControllers;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.Category;
import com.example.webForDB.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CategoryController {
    private CategoryService categoryService;
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public CategoryController(CategoryService categoryService, DBConnectHelper dbConnectHelper) {
        this.categoryService = categoryService;
        this.dbConnectHelper = dbConnectHelper;
    }

    @GetMapping("/choose_table/category_list")
    public String showCategoryList(Model model) {
        if (dbConnectHelper.checkConnection()) {
            List<Category> categories = categoryService.findAllCategories();
            model.addAttribute("categories", categories);
            return "tables/category_list";
        }
        return "redirect:/login";
    }
}
