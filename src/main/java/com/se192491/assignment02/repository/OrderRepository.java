package com.se192491.assignment02.repository;

import com.se192491.assignment02.pojo.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Integer> {
}
