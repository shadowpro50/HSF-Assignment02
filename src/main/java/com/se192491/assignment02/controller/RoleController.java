package com.se192491.assignment02.controller;

import com.se192491.assignment02.pojo.Account;
import com.se192491.assignment02.pojo.Role;
import com.se192491.assignment02.repository.RoleRepository;
import com.se192491.assignment02.service.RoleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
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
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        return "role/list";
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
        model.addAttribute("role", new Role());
        return "role/create";
    }

    @PostMapping("/create")
    public String create(Model model, HttpSession session, Role role) {
        Account sessionAccount = (Account) session.getAttribute("account");
        if (sessionAccount == null) {
            return "redirect:/login";
        }
        if (sessionAccount.getRole().getRoleID() == 2) {
            return "redirect:/403";
        }
        roleService.save(role);
        return "redirect:/role/list";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, HttpSession session, @PathVariable int id) {
        Account sessionAccount = (Account) session.getAttribute("account");
        if (sessionAccount == null) {
            return "redirect:/login";
        }
        if (sessionAccount.getRole().getRoleID() == 2) {
            return "redirect:/403";
        }
        Role role = roleService.findById(id);
        if (role == null) {
            return "redirect:/role/list";
        }
        model.addAttribute("role", new Role());
        return "role/update";
    }

    @PostMapping("/update/{id}")
    public String update(HttpSession session, @PathVariable int id, Role role) {
        Account sessionAccount = (Account) session.getAttribute("account");
        if (sessionAccount == null) {
            return "redirect:/login";
        }
        if (sessionAccount.getRole().getRoleID() == 2) {
            return "redirect:/403";
        }
        roleService.update(id, role);
        return "redirect:/role/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(HttpSession session, @PathVariable int id) {
        Account sessionAccount = (Account) session.getAttribute("account");
        if (sessionAccount == null) {
            return "redirect:/login";
        }
        if (sessionAccount.getRole().getRoleID() == 2) {
            return "redirect:/403";
        }
        roleService.delete(id);
        return "redirect:/role/list";
    }
}
