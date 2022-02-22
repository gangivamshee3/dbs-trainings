package com.classpath.ordermicroservice.repository;

import com.classpath.ordermicroservice.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerNameLike(String customerName);
    @Query("select order from Order order where order.customerEmail = ?1")
    Order findByEmailAddress(String emailAddress);
}