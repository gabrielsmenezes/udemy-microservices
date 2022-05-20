package com.example.bookservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/book-service")
public class FooBarController {

    private Logger logger = LoggerFactory.getLogger(FooBarController.class);

    @GetMapping("/foo-bar")
//    @Retry(name = "foo-bar", fallbackMethod = "fallbackMethod")
//    @CircuitBreaker(name = "default", fallbackMethod = "fallbackMethod")
    @RateLimiter(name = "default", fallbackMethod = "fallbackMethod")
    @Bulkhead(name="default", fallbackMethod = "fallbackMethod")
    public String fooBar() {
        this.logger.info("Request to foo-bar is received!");
        return new RestTemplate().getForEntity("http://localhost:8080/foo-bar", String.class).getBody();
    }

    public String fallbackMethod(Throwable throwable) {
        return "fallbackMethod foo-bar!";
    }

}
