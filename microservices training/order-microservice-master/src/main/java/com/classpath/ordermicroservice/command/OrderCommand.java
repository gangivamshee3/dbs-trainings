package com.classpath.ordermicroservice.command;

import com.classpath.ordermicroservice.model.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderCommand {
    private Order order;
}