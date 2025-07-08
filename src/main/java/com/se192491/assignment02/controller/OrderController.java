package com.se192491.assignment02.controller;

import com.se192491.assignment02.dto.OrderForm;
import com.se192491.assignment02.pojo.Account;
import com.se192491.assignment02.pojo.Order;
import com.se192491.assignment02.service.AccountService;
import com.se192491.assignment02.service.OrchidService;
import com.se192491.assignment02.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrchidService orchidService;

    @Autowired
    private AccountService accountService;

    // Show order creation form
    @GetMapping("/create")
    public String showCreateForm(Model model, HttpSession session) {
        Account sessionAccount = (Account) session.getAttribute("account");
        if (sessionAccount == null) {
            return "redirect:/login";
        }

        model.addAttribute("orchids", orchidService.findAll());
        model.addAttribute("orderForm", new OrderForm());
        return "order/create";
    }

    // Process order creation
    @PostMapping("/create")
    public String createOrder(@ModelAttribute OrderForm orderForm,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        Account sessionAccount = (Account) session.getAttribute("account");
        if (sessionAccount == null) {
            return "redirect:/login";
        }

        Order order = orderService.createOrder(sessionAccount, orderForm.getOrchidIds(), orderForm.getQuantities());
        return "redirect:/order/" + order.getId();
    }

    // View order details
    @GetMapping("/{id}")
    public String viewOrder(@PathVariable int id,
                            Model model,
                            HttpSession session) {
        Account sessionAccount = (Account) session.getAttribute("account");
        if (sessionAccount == null) {
            return "redirect:/login";
        }
        Order order = orderService.getOrderById(id);
        if (order.getAccount().getAccountID() != sessionAccount.getAccountID() && sessionAccount.getRole().getRoleID() == 2) {
            return "redirect:/403";
        }

        model.addAttribute("order", order);
        model.addAttribute("account", sessionAccount);
        model.addAttribute("orderDetails", orderService.getOrderDetails(order));
        return "order/view";
    }

    // List orders (with pagination)
    @GetMapping("list")
    public String listOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpSession session,
            Model model) {

        Account sessionAccount = (Account) session.getAttribute("account");
        if (sessionAccount == null) {
            return "redirect:/login";
        }
        if (sessionAccount.getRole().getRoleID() == 2) {
            return "redirect:/403";
        }

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("orderDate").descending());
        Page<Order> orderPage;

        if (sessionAccount.getRole().getRoleID() == 1) {
            orderPage = orderService.getAllOrders(pageable);
        } else {
            orderPage = orderService.getOrdersByAccount(sessionAccount, pageable);
        }

        model.addAttribute("account", sessionAccount);
        model.addAttribute("orders", orderPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orderPage.getTotalPages());

        return "order/list";
    }

    @GetMapping("/my-order")
    public String viewMyOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpSession session,
            Model model) {

        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/login";
        }

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("orderDate").descending());
        Page<Order> orderPage = orderService.getOrdersByAccount(account, pageable);

        model.addAttribute("account", account);
        model.addAttribute("orders", orderPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orderPage.getTotalPages());

        return "order/myList";
    }

    // Update order status (admin only)
    @PostMapping("/{id}/status")
    public String updateOrderStatus(
            @PathVariable int id,
            @RequestParam String status,
            HttpSession session) {

        Account sessionAccount = (Account) session.getAttribute("account");
        if (sessionAccount == null) {
            return "redirect:/login";
        }
        if (sessionAccount.getRole().getRoleID() == 2) {
            return "redirect:/403";
        }

        orderService.updateOrderStatus(id, status);
        return "redirect:/order/" + id;
    }
}
