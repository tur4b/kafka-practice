package com.tur4b.kafkapractice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tests")
public class TestController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping
    public String test() {
        kafkaTemplate.send("tests", "test-key", "test-value");
        return "test";
    }
}
