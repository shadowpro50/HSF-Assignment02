package com.se192491.assignment02.controller;

import com.se192491.assignment02.pojo.Account;
import com.se192491.assignment02.pojo.Role;
import com.se192491.assignment02.repository.AccountRepository;
import com.se192491.assignment02.service.AccountService;
import com.se192491.assignment02.service.RoleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public String list(Model model, HttpSession session) {
        Account sessionAccount = (Account) session.getAttribute("account");
        if (sessionAccount == null) {
            return "redirect:/login";
        }
        if (sessionAccount.getRole().getRoleID() == 2) {
            return "redirect:/403";
        }

        List<Account> accounts = accountService.getAllAccounts();
        model.addAttribute("account", sessionAccount);
        model.addAttribute("accounts", accounts);
        return "account/list";
    }

    @GetMapping("/create")
    public String create(Model model, HttpSession session) {
        Account sessionAccount = (Account) session.getAttribute("account");
        if (sessionAccount == null) {
            return "redirect:/login";
        }
        if (sessionAccount.getRole().getRoleID() == 2) {
            return "redirect:/403";
        }
        List<Role> list = roleService.findAll();
        model.addAttribute("account", new Account());
        model.addAttribute("roles", list);
        return "account/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Account account, HttpSession session) {
        Account sessionAccount = (Account) session.getAttribute("account");
        if (sessionAccount == null) {
            return "redirect:/login";
        }
        if (sessionAccount.getRole().getRoleID() == 2) {
            return "redirect:/403";
        }
        accountService.save(account);
        return "redirect:/account/list";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") int id, Model model, HttpSession session) {
        Account sessionAccount = (Account) session.getAttribute("account");
        if (sessionAccount == null) {
            return "redirect:/login";
        }
        if (sessionAccount.getRole().getRoleID() != 1 &&  // Not admin
                sessionAccount.getAccountID() != id) {
            return "redirect:/403";
        }
        Account account = accountService.getAccount(id);
        if (account == null) {
            return "redirect:/account/" + id;
        }
        Account account1 = accountService.getAccount(id);
        List<Role> list = roleService.findAll();
        model.addAttribute("account", sessionAccount);
        model.addAttribute("roles", list);
        return "account/update";
    }

    @PostMapping("/update/{id}")
    public String update(
            @PathVariable("id") int id,
            @ModelAttribute Account account,
            HttpSession session) {

        // Check if user is logged in
        Account sessionAccount = (Account) session.getAttribute("account");
        if (sessionAccount == null) {
            return "redirect:/login";
        }

        // Authorization check
        if (sessionAccount.getRole().getRoleID() != 1 &&  // Not admin
                sessionAccount.getAccountID() != id) {        // Not their own account
            return "redirect:/403";
        }

            // For non-admin users, ensure they can't change their role
            if (sessionAccount.getRole().getRoleID() != 1) {
                Account existingAccount = accountService.getAccount(id);
                account.setRole(existingAccount.getRole());
            }

            Account newSessionAccount = accountService.updateAccount(id, account);

            // Update session if user is updating their own account
            if (sessionAccount.getAccountID() == id) {
                session.setAttribute("account", newSessionAccount);
            }
            return "redirect:/account/" + id;
        }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, HttpSession session) {
        Account sessionAccount = (Account) session.getAttribute("account");
        if (sessionAccount == null) {
            return "redirect:/login";
        }
        if (sessionAccount.getRole().getRoleID() == 2) {
            return "redirect:/403";
        }
        accountService.deleteAccount(id);
        return "redirect:/account/list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable("id") int id, Model model, HttpSession session) {
        Account sessionAccount = (Account) session.getAttribute("account");
        if (sessionAccount == null) {
            return "redirect:/login";
        }
        if (sessionAccount.getRole().getRoleID() != 1 &&  // Not admin
                sessionAccount.getAccountID() != id) {
            return "redirect:/403";
        }
        Account account = accountService.getAccount(id);
        model.addAttribute("account", account);
        return "account/view";
    }
}
