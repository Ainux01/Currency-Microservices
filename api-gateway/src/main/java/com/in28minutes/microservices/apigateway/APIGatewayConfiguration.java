package com.in28minutes.microservices.apigateway;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;
//The Api Gateway should be Eureka Client
@Configuration
public class APIGatewayConfiguration {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder){
        Function<PredicateSpec, Buildable<Route>> routeFunction =
                p -> p.path("/get")//http://localhost:8765/get
                        .filters(
                            f -> f.addRequestHeader("MyHeader","MyUri")
                                    .addRequestParameter("MyParam","MyParameter")
                        )
                        .uri("http://httpbin.org:80");// to call or redirect to http://httpbin.org.80 url
        return builder.routes()
                .route(routeFunction)
                .route(p -> p.path("/currency-exchange/**")
                        .uri("lb://currency-exchange")
                )
                .route(p -> p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-feign/**")
                        .uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-new/**")
                        .filters(f -> f.rewritePath(
                                "/currency-conversion-new/(?<segment>.*)",
                                "/currency-conversion-feign/${segment}"
                        ))
                        .uri("lb://currency-conversion"))
                .build();
    }
}
