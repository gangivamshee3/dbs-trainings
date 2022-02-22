package com.classpath.inventorymicroservice.consumer;

import com.classpath.inventorymicroservice.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderProcessor {

    @StreamListener(Sink.INPUT)
    public void processOrder(Order order){
      log.info("Processing the order:: "+ order);
    }
}