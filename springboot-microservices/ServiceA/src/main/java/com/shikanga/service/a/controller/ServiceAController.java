package com.shikanga.service.a.controller;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
    @CircuitBreaker(name = SERVICE_A)
    public String serviceA(){
        String url = SERVICE_B_BASE_URL + "/b";
        return restTemplate.getForObject(
                url,
                String.class
        );
    }
}
