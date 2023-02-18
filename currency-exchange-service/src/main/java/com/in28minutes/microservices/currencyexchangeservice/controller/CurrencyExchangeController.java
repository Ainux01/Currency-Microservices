package com.in28minutes.microservices.currencyexchangeservice.controller;

import com.in28minutes.microservices.currencyexchangeservice.model.CurrencyExchange;
import com.in28minutes.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {
    private final CurrencyExchangeRepository currencyExchangeRepository;
    private final static Logger logger = LoggerFactory.getLogger(CurrencyExchange.class);

    private final Environment environment;

    public CurrencyExchangeController(Environment environment,CurrencyExchangeRepository currencyExchangeRepository) {
        this.environment = environment;
        this.currencyExchangeRepository = currencyExchangeRepository;

    }

    @GetMapping(path = "currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange currencyExchange(@PathVariable String from, @PathVariable String to) {
        logger.info("Request for retrieving Data from {} to {}",from,to);
       CurrencyExchange c = currencyExchangeRepository.findCurrencyExchangeByFromAndTo(from,to);

       if (c == null){
           throw new RuntimeException("Currency Exchange Data from "+from+" to "+to+" does not exist");
       }
        c.setEnvironment(environment.getProperty("server.port"));
       return c;
    }
}
