package com.in28minutes.microservices.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
//this is an example of logging filter
// I Want to log every Request That Comes to the gateway
//Implementing the GlobalFilter interface
@Component
public class LoggingFilter implements GlobalFilter {
    private final static Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("Path of The request received -> {}",exchange.getRequest().getPath());
        // let the execution as it was
        return chain.filter(exchange);
    }
}
