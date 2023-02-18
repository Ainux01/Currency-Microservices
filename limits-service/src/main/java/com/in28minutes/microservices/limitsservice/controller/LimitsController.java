package com.in28minutes.microservices.limitsservice.controller;

import com.in28minutes.microservices.limitsservice.configuration.LimitsConfiguration;
import com.in28minutes.microservices.limitsservice.model.Limits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {
    private final LimitsConfiguration limitsConfiguration;

    public LimitsController(LimitsConfiguration limitsConfiguration) {
        this.limitsConfiguration = limitsConfiguration;
    }

    @GetMapping(path = "/limits")
    public Limits retrieveLimits() {
        Limits limits = new Limits(limitsConfiguration.getMinimum(),limitsConfiguration.getMaximum());
        System.out.println(limits.toString());
       return limits;
    }
}
