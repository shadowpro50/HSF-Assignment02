package com.se192491.assignment02.service;

import com.se192491.assignment02.pojo.Account;
import com.se192491.assignment02.pojo.Role;
import com.se192491.assignment02.repository.AccountRepository;
import com.se192491.assignment02.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account getLoginAccount(String email, String password) {
        Account account = accountRepository.findByEmail(email);
        if (account == null || !account.getPassword().equals(password)) {
            return null;
        }
        return account;
    }

    @Override
    public Account getAccount(int id) {
        return accountRepository.findById(id)
                .orElse(null);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account registerAccount(Account account) {
        Role role = roleRepository.findById(2).orElse(null);
        account.setRole(role);
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(int id ,Account account) {
        Account oldAccount = accountRepository.findById(id).orElse(null);
        if (oldAccount == null) {
            return null;
        }
        oldAccount.setAccountName(account.getAccountName());
        oldAccount.setEmail(account.getEmail());
        oldAccount.setPassword(account.getPassword());
        oldAccount.setRole(account.getRole());

        return accountRepository.save(oldAccount);
    }

    @Override
    public void deleteAccount(int id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Boolean isAccountExist(String email) {
        accountRepository.findByEmail(email);
        if (accountRepository.findByEmail(email) != null) {
            return true;
        }
        return false;
    }


}
