package com.classpath.ordermicroservice.controller;

import com.classpath.ordermicroservice.command.OrderCommand;
import com.classpath.ordermicroservice.model.Order;
import com.classpath.ordermicroservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Slf4j
public class OrderRestController {

    private final OrderService orderService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Map<String, Object> fetchOrders(
            @RequestParam(value = "page", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "records", defaultValue = "10", required = false) int noOfRecords,
            @RequestParam(value = "field", defaultValue = "customerName", required = false) String property,
            @RequestParam(value = "order", defaultValue = "asc", required = false) String order){

        /*UserDetails principal = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Fetching all the orders : {}", principal.getUsername());*/
        Sort.Direction direction = order.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return this.orderService.fetchAll(pageNo, noOfRecords, direction, property);
    }

    @GetMapping("/{id}")
    public Order fetchById(@PathVariable("id") long orderId){
        log.info("Fetching the order by Id : {}", orderId);
        return this.orderService.fetchOrderById(orderId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order saveOrder(@RequestBody @Valid Order order) {
        //setting the order to line-items
        //Order order = orderCommand.getOrder();
        order.getLineItems().forEach(lineItem -> lineItem.setOrder(order));
        return this.orderService.saveOrder(order);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("id") long orderId){
        log.info("Deleting the order by Id : {}", orderId);
        this.orderService.deleteOrderById(orderId);
    }
}