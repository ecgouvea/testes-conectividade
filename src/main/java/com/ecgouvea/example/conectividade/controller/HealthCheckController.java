package com.ecgouvea.example.conectividade.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HealthCheckController {

    private static final Logger log = LoggerFactory.getLogger(HealthCheckController.class);
    private RestTemplate restTemplate;

    @GetMapping(path={"/api/HealthCheck", "/api/healthCheck", "/api/healthcheck", "/api/health"})
    public String healthCheck() {
        System.out.println("Something has to go into log!");
        System.out.println("\n\n\n==========================================");
        System.out.println("H E A L T H Y !");
        System.out.println("==========================================\n\n\n");
        return "true";
    }

}
