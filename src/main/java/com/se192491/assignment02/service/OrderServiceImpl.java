package com.se192491.assignment02.service;

import com.se192491.assignment02.pojo.Account;
import com.se192491.assignment02.pojo.Orchid;
import com.se192491.assignment02.pojo.Order;
import com.se192491.assignment02.pojo.OrderDetail;
import com.se192491.assignment02.repository.OrderDetailRepository;
import com.se192491.assignment02.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrchidService orchidService;
    @Autowired
    private AccountService accountService;

    // Create new order with orchid items
    public Order createOrder(Account account, List<Integer> orchidIds, List<Integer> quantities) {
        // Validate input
        if (orchidIds == null || quantities == null || orchidIds.size() != quantities.size()) {
            throw new IllegalArgumentException("Invalid orchid IDs or quantities");
        }

        // Create new order
        Order order = new Order();
        order.setAccount(account);
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus("PENDING");

        // Group quantities by orchid ID
        Map<Integer, Integer> orchidQuantityMap = new HashMap<>();
        for (int i = 0; i < orchidIds.size(); i++) {
            int orchidId = orchidIds.get(i);
            int quantity = quantities.get(i);

            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be positive");
            }

            orchidQuantityMap.merge(orchidId, quantity, Integer::sum);
        }

        // Create order details
        List<OrderDetail> orderDetails = new ArrayList<>();
        int totalAmount = 0;

        for (Map.Entry<Integer, Integer> entry : orchidQuantityMap.entrySet()) {
            Orchid orchid = orchidService.findById(entry.getKey());
            if (orchid == null) {
                throw new IllegalArgumentException("Orchid not found with ID: " + entry.getKey());
            }

            int quantity = entry.getValue();

            OrderDetail detail = new OrderDetail();
            detail.setOrchid(orchid);
            detail.setPrice(orchid.getPrice());
            detail.setQuantity(quantity);
            detail.setOrder(order);

            orderDetails.add(detail);
            totalAmount += orchid.getPrice() * quantity;
        }

        if (orderDetails.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }

        // Save order and details
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);
        orderDetailRepository.saveAll(orderDetails);

        return order;
    }

    // Get order by ID
    public Order getOrderById(int id) {
        return orderRepository.findById(id)
                .orElse(null);
    }

    // Get all order details for an order
    public List<OrderDetail> getOrderDetails(Order order) {
        return orderDetailRepository.findByOrderId(order.getId());
    }

    // Get all orders for an account
    public Page<Order> getOrdersByAccount(Account account, Pageable pageable) {
        return orderRepository.findByAccount(account, pageable);
    }

    // Get all orders (admin only)
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    // Update order status
    public Order updateOrderStatus(int orderId, String status) {
        Order order = getOrderById(orderId);

        if (!Arrays.asList("PENDING", "COMPLETED", "CANCELLED").contains(status)) {
            throw new IllegalArgumentException("Invalid status");
        }

        order.setOrderStatus(status);
        return orderRepository.save(order);
    }
}
