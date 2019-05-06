package com.gl.tuto.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Value("${gl.topic-name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    public void sendMessage(Order order){
        logger.info(String.format("$$ -> Producing message --> %s", order));
        ListenableFuture<SendResult<String, Order>> future = this.kafkaTemplate.send(topicName, order);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Order>>() {
            @Override
            public void onSuccess(SendResult<String, Order> result) {
                logger.info("order name=" + order.getName() + " sent");
            }
            @Override
            public void onFailure(Throwable ex) {
                logger.error("could not send order name=" + order.getName());
            }
        });
    }
}