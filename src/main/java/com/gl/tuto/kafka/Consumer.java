package com.gl.tuto.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @KafkaListener(topics = "${gl.topic-name}", groupId = "group_id")
    public void consume(Order order){
        logger.info(String.format("$$ -> Consumed Message -> %s", order.getName()));
    }
}