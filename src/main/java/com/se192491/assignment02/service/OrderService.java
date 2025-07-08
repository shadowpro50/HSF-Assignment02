package com.se192491.assignment02.service;

import com.se192491.assignment02.pojo.Account;
import com.se192491.assignment02.pojo.Orchid;
import com.se192491.assignment02.pojo.Order;
import com.se192491.assignment02.pojo.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    public Order createOrder(Account account, List<Integer> orchidIds, List<Integer> quantities);
    public Order getOrderById(int id);
    public List<OrderDetail> getOrderDetails(Order order);
    public Page<Order> getOrdersByAccount(Account account, Pageable pageable);
    public Page<Order> getAllOrders(Pageable pageable);
    public Order updateOrderStatus(int orderId, String status);
}
