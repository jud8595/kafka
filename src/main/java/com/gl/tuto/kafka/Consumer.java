package com.gl.tuto.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    ProducerTopicProcessed producer;

    @KafkaListener(topics = "${gl.topic-name}", groupId = "group_id")
    public String consume(Order order){
        logger.info(String.format("$$ -> Received Message -> %s", order.getName()));
        OrderProcessed orderProcessed = processOrder(order);
        producer.sendMessage(orderProcessed);
        return order.getName();
    }

    private OrderProcessed processOrder(Order order) {
        logger.info(String.format("$$ -> Order processed -> %s", order.getName()));
        OrderProcessed orderProcessed = new OrderProcessed();
        orderProcessed.setId(order.getName());
        return orderProcessed;
    }
}