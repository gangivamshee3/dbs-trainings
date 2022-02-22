package com.classpath.ordermicroservice.service;

import com.classpath.ordermicroservice.event.EventType;
import com.classpath.ordermicroservice.event.OrderEvent;
import com.classpath.ordermicroservice.model.Order;
import com.classpath.ordermicroservice.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Supplier;

import static com.classpath.ordermicroservice.event.EventType.ORDER_ACCEPTED;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final Source source;
  //  private final WebClient webClient;

    //@CircuitBreaker(name = "inventoryservice", fallbackMethod = "fallback")
    //@Retry(name="retryService", fallbackMethod = "fallback")
    public Order saveOrder(Order order){
        log.info(":::: Invoking the rest endpoint for inventory-microservice ::::");
        Order savedOrder =  this.orderRepository.save(order);
        //ResponseEntity<Integer> responseEntity = this.restTemplate.postForEntity("http://inventory-service/api/v1/inventory", null, Integer.class);
    /*    this.webClient
                .post()
                .uri("/api/v1/inventory")
                .retrieve()
                .bodyToMono(Integer.class)
                .subscribe(response -> System.out.println("Response from inventory service :: "+response));
    */   // log.info("Invoking the Rest endpoint for inventory microservice :: {} " , responseEntity.getBody());
        // push the message to channel
        this.source.output().send(MessageBuilder.withPayload(OrderEvent.builder().eventType(ORDER_ACCEPTED).order(order).build()).build());
        return  savedOrder;
    }

    public Supplier<Order> orderMessageProducer(Order order) {
        return () -> order;
    }

    public Order fallback(Exception exception){
        log.error("Exception while communicating with inventory service :: {}", exception.getMessage());
        return Order.builder().build();
    }

    public Map<String, Object> fetchAll(int pageNo, int noOfRecords, Sort.Direction direction, String propertry){
        PageRequest pageRequest = PageRequest.of(pageNo, noOfRecords, direction, propertry);
        Page<Order> response = this.orderRepository.findAll(pageRequest);
        long totalRecords = response.getTotalElements();
        int totalPages = response.getTotalPages();
        List<Order> orders = response.getContent();

        //put them in a hashmap
        Map<String, Object> responseMap = new LinkedHashMap<>();
        responseMap.put("records", totalRecords);
        responseMap.put("pages", totalPages);
        responseMap.put("data", orders);
        return responseMap;
    }
    public Order fetchOrderById(long orderId){
        return this.orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Invalid order id"));
    }

    public Order updateOrder(long orderId, Order order){
        Order fetchedOrder = fetchOrderById(orderId);
        if (fetchedOrder != null){
            fetchedOrder.setCustomerEmail(order.getCustomerEmail());
            fetchedOrder.setCustomerName(order.getCustomerName());
            fetchedOrder.setLineItems(order.getLineItems());
            fetchedOrder.setDate(order.getDate());
            this.orderRepository.save(fetchedOrder);
        }
        return fetchedOrder;
    }

    public void deleteOrderById(long orderId){
        this.orderRepository.deleteById(orderId);
    }
}