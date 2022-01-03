package com.example.sklep.order;

import com.example.common.OrderKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    private final KafkaTemplate<String, OrderKafka> kafkaTemplate;


    public OrderProducer(KafkaTemplate<String, OrderKafka> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(OrderKafka orderKafka) {
        kafkaTemplate.send("warehouse", orderKafka);
    }
}
