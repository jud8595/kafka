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
public class ProducerTopicProcessed {

    private static final Logger logger = LoggerFactory.getLogger(ProducerTopicProcessed.class);

    @Value("${gl.topic-processed}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, OrderProcessed> kafkaTemplate;

    public void sendMessage(OrderProcessed orderProcessed){
        logger.info(String.format("$$ -> [Processed topic] Sending message --> %s", orderProcessed.getId()));
        ListenableFuture<SendResult<String, OrderProcessed>> future = this.kafkaTemplate.send(topicName, orderProcessed);

        future.addCallback(new ListenableFutureCallback<SendResult<String, OrderProcessed>>() {
            @Override
            public void onSuccess(SendResult<String, OrderProcessed> result) {
                logger.info("[Processed topic] id=" + orderProcessed.getId() + " sent");
            }
            @Override
            public void onFailure(Throwable ex) {
                logger.error("[Processed topic] failed to process id==" + orderProcessed.getId());
            }
        });
    }
}