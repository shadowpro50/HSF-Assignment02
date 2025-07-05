package com.se192491.assignment02.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int accountID;
    @Column(name = "account_name")
    private String accountName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public Account(String accountName, String email, String password, Role role) {
        this.accountName = accountName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
