package com.classpath.inventorymicroservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LineItem {

    private Integer itemId;

    private String name;

    private int qty;
    private double price;

    @JsonIgnore
    private Order order;
}