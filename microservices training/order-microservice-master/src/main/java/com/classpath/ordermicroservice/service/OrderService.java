package com.classpath.ordermicroservice.service;

import com.classpath.ordermicroservice.model.Order;
import org.springframework.data.domain.Sort;

import java.util.Map;
import java.util.Set;

public interface OrderService {
     Order saveOrder(Order order);
     Map<String, Object> fetchAll(int pageNo, int noOfRecords, Sort.Direction direction, String propertry);
     Order fetchOrderById(long orderId);

     Order updateOrder(long orderId, Order order);

     void deleteOrderById(long orderId);
}