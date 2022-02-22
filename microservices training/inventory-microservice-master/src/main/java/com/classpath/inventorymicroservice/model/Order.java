package com.classpath.inventorymicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    private Long orderId;
    private String customerName;
    private String customerEmail;
    private double price;
    private LocalDate date;
    private Set<LineItem> lineItems;
}