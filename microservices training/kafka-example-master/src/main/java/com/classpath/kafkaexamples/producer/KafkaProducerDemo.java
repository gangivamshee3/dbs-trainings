package com.classpath.kafkaexamples.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerDemo implements CommandLineRunner {

    private final KafkaTemplate<String, String> kafkaTemplate;
    @Override
    public void run(String... args) throws Exception {
        log.info("Sending message to the topic :: {}", "pradeep-topic");
        for(int i = 0; i < 1000; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>("pradeep-topic", "message :: "+ i);
            Thread.sleep(2000);
            kafkaTemplate.send(record);
        }
    }
}