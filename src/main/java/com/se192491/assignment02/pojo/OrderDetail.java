package com.se192491.assignment02.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OrderDetails")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "orchid_id")
    private Orchid orchid;
    @Column(name = "price")
    private int price;
    @Column(name = "quantity")
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
