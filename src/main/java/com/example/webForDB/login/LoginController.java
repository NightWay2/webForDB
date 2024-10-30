package com.example.webForDB.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    DBConnectHelper dbConnectHelper;

    @Autowired
    public LoginController(DBConnectHelper dbConnectHelper) {
        this.dbConnectHelper = dbConnectHelper;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginInfo", new LoginInfo());
        return "login";
    }

    @PostMapping("/login")
    public String submitLoginForm(@ModelAttribute("loginInfo") LoginInfo loginInfo, Model model) {
        String username = loginInfo.getUsername();
        String password = loginInfo.getPassword();

        try {
            if (dbConnectHelper.openConnection(username, password)) {
                dbConnectHelper.closeConnection();
                //model.addAttribute("error", "Success message");
                return "redirect:/choose_table";
            }
        } catch (Exception ignored) {
        }

        model.addAttribute("error", "Invalid username or password!");
        return "login";
    }
}
