package com.shikanga.service.a.controller;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@RestController
@RequestMapping("/a")
public class ServiceAController {

    private final RestTemplate restTemplate;

    private static final String SERVICE_B_BASE_URL = "http://localhost:8182";
    private static final String SERVICE_A = "service-A";

    public ServiceAController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    //@CircuitBreaker(name = SERVICE_A, fallbackMethod = "serviceAFallback")
    @Retry(name = SERVICE_A)
    public String serviceA(){
        int count = 1;
        String url = SERVICE_B_BASE_URL + "/b";
        System.out.println("Retry method called " + count++ + " times at " + new Date());
        return restTemplate.getForObject(
                url,
                String.class
        );
    }

    public String serviceAFallback(Exception exception){
        return "This is a fallback method for Service A";
    }
}
