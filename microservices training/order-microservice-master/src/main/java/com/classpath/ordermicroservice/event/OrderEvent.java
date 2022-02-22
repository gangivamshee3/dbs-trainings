package com.classpath.ordermicroservice.event;

import com.classpath.ordermicroservice.model.Order;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEvent {
    private EventType eventType;
    private Order order;
    private String timestamp = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);
}

