package com.se192491.assignment02;

import com.se192491.assignment02.pojo.Account;
import com.se192491.assignment02.pojo.Category;
import com.se192491.assignment02.pojo.Orchid;
import com.se192491.assignment02.pojo.Role;
import com.se192491.assignment02.service.AccountService;
import com.se192491.assignment02.service.CategoryService;
import com.se192491.assignment02.service.OrchidService;
import com.se192491.assignment02.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Assignment02Application implements CommandLineRunner {
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OrchidService orchidService;

    public static void main(String[] args) {
        SpringApplication.run(Assignment02Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleService.findAll().isEmpty()) {
            Role role1 = new Role("Admin");
            Role role2 = new Role("Customer");
            roleService.save(role1);
            roleService.save(role2);
        }

        if (accountService.getAllAccounts().isEmpty()) {
            Account account1 = new Account("Khoi", "admin@cinestar.com", "@4", roleService.findById(1));
            Account account2 = new Account("Hoang", "customer@cinestar.com", "@4", roleService.findById(2));
            accountService.save(account1);
            accountService.save(account2);
        }

        if (categoryService.findAll().isEmpty()) {
            Category category1 = new Category("Phalaenopsis");
            Category category2 = new Category("Dendrobium");
            Category category3 = new Category("Oncidium");
            categoryService.save(category1);
            categoryService.save(category2);
            categoryService.save(category3);
        }

        if (orchidService.findAll().isEmpty()) {
            Orchid orchid1 = new Orchid(true,
                    "A classic moth orchid with long-lasting white flowers. Perfect for beginners and indoor spaces.",
                    "Phalaenopsis 'White Dream'",
                    "https://example.com/images/phalaenopsis-white.jpg",
                    25,
                    categoryService.findById(1));
            Orchid orchid2 = new Orchid(false,
                    "A vibrant purple Dendrobium with clustered blooms. Thrives in bright light.",
                    "Dendrobium 'Purple Rain'",
                    "https://example.com/images/dendrobium-purple.jpg",
                    30,
                    categoryService.findById(2));
            Orchid orchid3 = new Orchid(true,
                    "Golden-yellow 'Dancing Lady' orchid with a sweet vanilla-like fragrance.",
                    "Oncidium 'Sharry Baby'",
                    "https://example.com/images/oncidium-yellow.jpg",
                    35,
                    categoryService.findById(3)
            );
            orchidService.save(orchid1);
            orchidService.save(orchid2);
            orchidService.save(orchid3);
        }
    }
}
