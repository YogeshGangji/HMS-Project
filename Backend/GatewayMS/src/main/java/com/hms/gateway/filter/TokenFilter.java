package com.hms.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class    TokenFilter extends AbstractGatewayFilterFactory<TokenFilter.Config> {

    private static final String SECRET = "d410406514a2b2b819b77d6e6cf1d7fde563ae68193cc946503ba3c0cbbebe148cfb6478f60080b8406ac229758614a539223d24d5bc5e7af57e3d26d102812e";

    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            SECRET.getBytes(StandardCharsets.UTF_8));

    public TokenFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            // 1. Allow browser CORS preflight request
            if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
                return chain.filter(exchange);
            }

            String path = exchange.getRequest().getPath().toString();

            if (path.equals("/user/login") || path.equals("/user/register")) {
                return chain.filter(exchange.mutate().request(r -> r.header("X-Secret-Key", "SECRET")).build());
            }

            HttpHeaders headers = exchange.getRequest().getHeaders();

            if (!headers.containsHeader(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("Authorization header is missing");
            }
            String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new RuntimeException("Authorization header is invalid");
            }
            String token = authHeader.substring(7);

            try {

                Claims claims = Jwts.parser()
                        .verifyWith(SECRET_KEY)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

                ServerWebExchange mutatedExchange = exchange.mutate()
                        .request(r -> r.header("X-Secret-Key", "SECRET"))
                        .build();
                // return chain.filter(mutatedExchange);
            } catch (Exception e) {
                throw new RuntimeException("Token is invalid");
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {

    }
}
