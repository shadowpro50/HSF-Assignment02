package com.se192491.assignment02.service;

import com.se192491.assignment02.pojo.Account;

import java.util.List;

public interface AccountService {
    public void save(Account account);
    public Account getLoginAccount(String email, String password);
    public Account getAccount(int id);
    public List<Account> getAllAccounts();
    public Account registerAccount(Account account);
    public Account updateAccount(int id, Account account);
    public void deleteAccount(int id);
    public Boolean isAccountExist(String email);
}
