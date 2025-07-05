package com.se192491.assignment02.repository;

import com.se192491.assignment02.pojo.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    public Account findByEmail(String email);
}
