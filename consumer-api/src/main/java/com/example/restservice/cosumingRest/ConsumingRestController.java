package com.example.restservice.cosumingRest;

import com.example.restservice.Greeting;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ConsumingRestController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @GetMapping("/consume/greeting")
   /*@CircuitBreaker(name= "getInvoiceCB", fallbackMethod = "getGreetingFallback")*/
    public Greeting consumeGreeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return restTemplate.getForObject("http://localhost:9090/greeting/", Greeting.class);
    }

    /*public ResponseEntity<String> getGreetingFallback(Exception e)
    {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Greeting Rest service is down");
    }*/

    public String getGreetingFallback(Exception e) {
        return "SERVICE IS DOWN, PLEASE TRY AFTER SOMETIME !!!";
    }
}