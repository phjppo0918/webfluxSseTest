package com.example.webfluxssetest.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class WebfluxAuthFilter implements WebFilter {
    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
        final String authorization = exchange.getRequest().getHeaders().get("Authorization").stream().findFirst().get();
        return ReactiveSecurityContextHolder.getContext()
                .doOnNext(c -> c.setAuthentication(new UsernamePasswordAuthenticationToken(authorization, "test")))
                .then(chain.filter(exchange));
    }
}
