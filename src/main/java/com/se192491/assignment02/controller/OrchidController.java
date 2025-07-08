package com.se192491.assignment02.controller;

import com.se192491.assignment02.pojo.Account;
import com.se192491.assignment02.pojo.Category;
import com.se192491.assignment02.pojo.Orchid;
import com.se192491.assignment02.service.CategoryService;
import com.se192491.assignment02.service.OrchidService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/orchid")
public class OrchidController {
    @Autowired
    private OrchidService orchidService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public String list(Model model, HttpSession session) {
        Account sessionAccount = (Account) session.getAttribute("account");
        if (sessionAccount == null) {
            return "redirect:/login";
        }
        if (sessionAccount.getRole().getRoleID() == 2) {
            return "redirect:/403";
        }
        List<Orchid> orchids = orchidService.findAll();
        model.addAttribute("account", sessionAccount);
        model.addAttribute("orchids", orchids);
        return "orchid/list";
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
        List<Category> list = categoryService.findAll();
        model.addAttribute("orchid", new Orchid());
        model.addAttribute("categories", list);
        return "orchid/create";
    }

    @PostMapping("/create")
    public String create(Model model, HttpSession session, Orchid orchid) {
        Account sessionAccount = (Account) session.getAttribute("account");
        if (sessionAccount == null) {
            return "redirect:/login";
        }
        if (sessionAccount.getRole().getRoleID() == 2) {
            return "redirect:/403";
        }
        orchidService.save(orchid);
        return "redirect:/orchid/upload/" + orchid.getOrchidID();
    }

    @GetMapping("/upload/{id}")
    public String upload(@PathVariable int id, Model model) {
        Orchid orchid = orchidService.findById(id);

        model.addAttribute("orchid", orchid); // Add the actual object, not Optional
        model.addAttribute("orchidId", id);
        return "orchid/upload";
    }

    @PostMapping("/upload/{id}")
    public String uploadImage(@PathVariable int id,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              Model model) throws IOException {
        Boolean result = orchidService.upload(imageFile, id);
        if (!result) {
            model.addAttribute("message", "you have not uploaded your image or wrong id");
            return "orchid/upload";
        }
        return "redirect:/orchid/list";
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
        Orchid orchid = orchidService.findById(id);
        if (orchid == null) {
            return "redirect:/orchid/list";
        }
        List<Category> list = categoryService.findAll();
        model.addAttribute("orchid", orchid);
        model.addAttribute("categories", list);
        return "orchid/update";
    }

    @PostMapping("/update/{id}")
    public String update(HttpSession session, @PathVariable int id, Orchid orchid) {
        Account sessionAccount = (Account) session.getAttribute("account");
        if (sessionAccount == null) {
            return "redirect:/login";
        }
        if (sessionAccount.getRole().getRoleID() == 2) {
            return "redirect:/403";
        }
        orchidService.update(id, orchid);
        return "redirect:/orchid/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(HttpSession session, @PathVariable int id) throws IOException {
        Account sessionAccount = (Account) session.getAttribute("account");
        if (sessionAccount == null) {
            return "redirect:/login";
        }
        if (sessionAccount.getRole().getRoleID() == 2) {
            return "redirect:/403";
        }
        orchidService.delete(id);
        return "redirect:/orchid/list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable("id") int id, Model model, HttpSession session) {
        Account sessionAccount = (Account) session.getAttribute("account");
        if (sessionAccount == null) {
            return "redirect:/login";
        }
        Orchid orchid = orchidService.findById(id);
        model.addAttribute("account", sessionAccount);
        model.addAttribute("orchid", orchid);
        return "orchid/view";
    }
}
