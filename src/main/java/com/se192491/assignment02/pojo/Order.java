package com.se192491.assignment02.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @Column(name = "order_date")
    private LocalDate orderDate;
    @Column(name = "order_status")
    private String orderStatus;
    @Column(name = "total_amount")
    private int totalAmount;
}
