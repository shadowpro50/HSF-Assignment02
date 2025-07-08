package com.se192491.assignment02.controller;

import com.se192491.assignment02.pojo.Account;
import com.se192491.assignment02.pojo.Orchid;
import com.se192491.assignment02.service.CategoryService;
import com.se192491.assignment02.service.OrchidService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @Autowired
    private OrchidService orchidService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listOrchids(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "2") int size,
            Model model,
            HttpSession session) {
        Account sessionAccount = (Account) session.getAttribute("account");
        Page<Orchid> pageOrchids = orchidService.findAllPaginated(page, size);

        model.addAttribute("account", sessionAccount);
        model.addAttribute("pageOrchids", pageOrchids);
        model.addAttribute("orchids", pageOrchids.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageOrchids.getTotalPages());
        model.addAttribute("totalItems", pageOrchids.getTotalElements());
        model.addAttribute("pageSize", size);

        return "/home/list";
    }
}
