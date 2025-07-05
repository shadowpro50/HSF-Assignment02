package com.se192491.assignment02.controller;

import com.se192491.assignment02.pojo.Account;
import com.se192491.assignment02.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpSession session, @RequestParam String email, @RequestParam String password, Model model) {
        Account account = accountService.getLoginAccount(email, password);
        if (account == null) {
            model.addAttribute("message", "Invalid email or password");
            return "login";
        }
        session.setAttribute("account", account);
        model.addAttribute("message", "logged in successfully");
        return "redirect:/account/list";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("account") Account account, Model model) {
        if(accountService.isAccountExist(account.getEmail())){
            model.addAttribute("message", "This email already exists");
            return "register";
        };
        accountService.registerAccount(account);
        model.addAttribute("message", "Registered Successfully");
        return "login";


    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }
}
