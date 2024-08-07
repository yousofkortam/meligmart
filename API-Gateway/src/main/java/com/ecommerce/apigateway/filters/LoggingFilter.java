package com.ecommerce.apigateway.filters;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class LoggingFilter implements GlobalFilter {

    private final WebClient.Builder webClient;

    Logger logger = Logger.getLogger(LoggingFilter.class.getName());

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        this.logger.info("Path: " + exchange.getRequest().getPath());
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        String token;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        } else {
            token = "";
        }
        return webClient.build().post()
            .uri("http://localhost:3000/auth/token/validation")
            .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
            .exchangeToMono(clientResponse -> {
                if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                    return chain.filter(exchange);
                }
                Function<String, Mono<Void>> errorFun = error -> {
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(clientResponse.statusCode());
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    byte[] errorBytes = error.getBytes();
                    return response.writeWith(Mono.just(response.bufferFactory().wrap(errorBytes)));
                };
                return clientResponse.bodyToMono(String.class).flatMap(errorFun);
            });
    }

}
