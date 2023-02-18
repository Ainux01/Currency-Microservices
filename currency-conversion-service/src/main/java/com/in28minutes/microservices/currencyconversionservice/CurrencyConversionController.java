package com.in28minutes.microservices.currencyconversionservice;

import com.in28minutes.microservices.currencyconversionservice.CurrencyConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {
    @Autowired
    private Environment environment;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CurrencyExchangeProxy currencyExchangeProxy;
    @GetMapping(path = "/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    CurrencyConversion getCurrencyConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){
        System.out.println(from+" "+to+" "+quantity);
        Map<String,String> uriVariables = new HashMap<>();
        uriVariables.put("from",from);
        uriVariables.put("to",to);
        System.out.println("Hello World");
        ResponseEntity<CurrencyConversion> forEntity = restTemplate.getForEntity("http://currency-exchange/currency-exchange/from/{from}/to/{to}",
                CurrencyConversion.class,
                uriVariables
        );
//        System.out.println(forEntity);
        CurrencyConversion currencyConversion = forEntity.getBody();
        return new CurrencyConversion(
                currencyConversion.getId(),
                from,
                to,
                currencyConversion.getConversionMultiple(),
                quantity,
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment()+" rest template"
        );
    }
    @GetMapping(path = "/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    CurrencyConversion getCurrencyConversionUsingFeignClient(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){
        System.out.println(from+" "+to+" "+quantity);
        CurrencyConversion currencyConversion = currencyExchangeProxy.currencyExchange(from, to);
        return new CurrencyConversion(
                currencyConversion.getId(),
                from,
                to,
                currencyConversion.getConversionMultiple(),
                quantity,
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment()+ " feign client"
        );
    }
}
