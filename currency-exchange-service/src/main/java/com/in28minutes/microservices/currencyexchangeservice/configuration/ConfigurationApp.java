package com.in28minutes.microservices.currencyexchangeservice.configuration;

import com.in28minutes.microservices.currencyexchangeservice.model.CurrencyExchange;
import com.in28minutes.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class ConfigurationApp {
    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;

    @Bean
    CommandLineRunner commandLineRunner(){
        return args -> {
            currencyExchangeRepository.save(new CurrencyExchange(10001L,"USD","INR", BigDecimal.valueOf(65),""));
            currencyExchangeRepository.save(new CurrencyExchange(10002L,"EUR","INR", BigDecimal.valueOf(75),""));
            currencyExchangeRepository.save(new CurrencyExchange(10003L,"AUD","INR", BigDecimal.valueOf(25),""));
        };
    }
}
