package com.se192491.assignment02;

import com.se192491.assignment02.pojo.Account;
import com.se192491.assignment02.pojo.Role;
import com.se192491.assignment02.service.AccountService;
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

    public static void main(String[] args) {
        SpringApplication.run(Assignment02Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleService.findAll().isEmpty()) {
            Role role1 = new Role( "Customer");
            Role role2 = new Role("Admin");
            roleService.save(role1);
            roleService.save(role2);
        }

        if (accountService.getAllAccounts().isEmpty()) {
            Account account1 = new Account("Admin", "admin@gmail.com", "1", roleService.findById(1));
            Account account2 = new Account("Customer", "customer@gmail.com", "2", roleService.findById(2));
            accountService.save(account1);
            accountService.save(account2);
        }

    }
}
