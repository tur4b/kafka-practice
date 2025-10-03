package com.tur4b.kafkapractice.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageConsumer {

    @KafkaListener(topics = { "tests"})
    public void myTopicListener(String message) {
        log.info(">> Received: {}", message);
    }
}
