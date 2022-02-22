package com.classpath.kafkaexamples.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumerDemo {

    @KafkaListener(topics = "pradeep-topic", groupId = "app")
    public void consumer(String record){

        log.info("consuming the record:: {}", record);
    }
}